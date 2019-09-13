package com.arproject.russell.ar_t.augmentedimages;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.arproject.russell.ar_t.R;
import com.arproject.russell.ar_t.utils.PrefConfig;
import com.google.ar.core.AugmentedImage;
import com.google.ar.core.Frame;
import com.google.ar.sceneform.FrameTime;
import com.google.ar.sceneform.rendering.ViewRenderable;
import com.google.ar.sceneform.ux.ArFragment;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static com.google.ar.core.TrackingState.PAUSED;
import static com.google.ar.core.TrackingState.STOPPED;
import static com.google.ar.core.TrackingState.TRACKING;

public class AugmentedImagesActivity extends AppCompatActivity {

    private static final String TAG = AugmentedImagesActivity.class.getSimpleName();
    private static final double MIN_OPENGL_VERSION = 3.0;

    private ArFragment arFragment;
    private SnackbarHelper snackbarHelper = new SnackbarHelper();
    private ImageView fitToScanView;
    private ViewRenderable infoCard;
    private FloatingActionButton fab;

    private final Map<AugmentedImage, AugmentedImageModelNode> augmentedImageModelNodeHashMap = new HashMap<>();
    private final Map<AugmentedImage, AugmentedImageVideoNode> augmentedImageVideoNodeMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        PrefConfig prefConfig = new PrefConfig(this);
        setTheme(prefConfig.getTheme());
        super.onCreate(savedInstanceState);

        if (!checkIsSupportedDeviceOrFinish(this)) {
            return;
        }

        setContentView(R.layout.augmented_images_activity);
        arFragment = (AugmentedImagesFragment) getSupportFragmentManager().findFragmentById(R.id.augmented_images_fragment);
        fitToScanView = findViewById(R.id.image_view_fit_to_scan);
        arFragment.getArSceneView().getScene().addOnUpdateListener(this::onUpdateFrame);
        fab = findViewById(R.id.clear_button);
        fab.setOnClickListener(view -> clearNodes());

        createModel();
    }

    private void clearNodes() {
        for (Map.Entry<AugmentedImage, AugmentedImageModelNode> d : augmentedImageModelNodeHashMap.entrySet()) {
            AugmentedImageModelNode node = d.getValue();
            node.getView().setParent(null);
            node.getObjectNode().setParent(null);
            node.getAnchor().detach();
        }
        for (Map.Entry<AugmentedImage, AugmentedImageVideoNode> d : augmentedImageVideoNodeMap.entrySet()) {
            AugmentedImageVideoNode node = d.getValue();
            node.getVideoNode().setParent(null);
            node.stopVideo();
            node.getAnchor().detach();
        }
        augmentedImageModelNodeHashMap.clear();
        augmentedImageVideoNodeMap.clear();
    }

    private void onUpdateFrame(FrameTime frameTime) {
        Frame frame = arFragment.getArSceneView().getArFrame();

        // If there is no frame or ARCore is not tracking yet, just return.
        if (frame == null || frame.getCamera().getTrackingState() != TRACKING) {
            return;
        }

        Collection<AugmentedImage> updatedAugmentedImages = frame.getUpdatedTrackables(AugmentedImage.class);
        for (AugmentedImage augmentedImage : updatedAugmentedImages) {
            if (augmentedImage.getTrackingState() == PAUSED) {
                // When an image is in PAUSED state, but the camera is not PAUSED, it has been detected,
                // but not yet tracked.
                String text = "Detecting Image";
                snackbarHelper.showMessage(this, text);
            } else if (augmentedImage.getTrackingState() == TRACKING) {
                // Have to switch to UI Thread to update View.
                fitToScanView.setVisibility(View.GONE);
                snackbarHelper.hide(this);
                // Create a new anchor for newly found images.
                if (!augmentedImageModelNodeHashMap.containsKey(augmentedImage) && !augmentedImageVideoNodeMap.containsKey(augmentedImage)) {
                    AugmentedImageItem item = AugmentedImagesFragment.augmentedImageItemMap.get(augmentedImage.getName());
                    if (item.getType() == AugmentedImagesFragment.CONTENT_TYPE.MODEL) {
                        AugmentedImageModelNode node = new AugmentedImageModelNode(getApplicationContext(), item, infoCard.makeCopy());
                        node.setImage(augmentedImage);
                        augmentedImageModelNodeHashMap.put(augmentedImage, node);
                        arFragment.getArSceneView().getScene().addChild(node);
                    } else if (item.getType() == AugmentedImagesFragment.CONTENT_TYPE.VIDEO) {
                        AugmentedImageVideoNode node = new AugmentedImageVideoNode(getApplicationContext(), item, augmentedImage);
                        augmentedImageVideoNodeMap.put(augmentedImage, node);
                        arFragment.getArSceneView().getScene().addChild(node);
                    }
                    fab.setVisibility(View.VISIBLE);
                    Toast.makeText(this, "Image detected", Toast.LENGTH_LONG).show();
                }
            } else if (augmentedImage.getTrackingState() == STOPPED) {
                    augmentedImageModelNodeHashMap.remove(augmentedImage);
            }
        }
    }

    private void createModel() {
        ViewRenderable.builder().setView(getApplicationContext(), R.layout.augmented_image_view).build().thenAccept(
                (renderable) -> this.infoCard = renderable)
                .exceptionally(
                        (throwable) -> {
                            throw new AssertionError("Could not load plane card view.", throwable);
                        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public static boolean checkIsSupportedDeviceOrFinish(final Activity activity) {
        String openGlVersionString =
            ((ActivityManager) activity.getSystemService(Context.ACTIVITY_SERVICE))
                    .getDeviceConfigurationInfo()
                    .getGlEsVersion();
        if (Double.parseDouble(openGlVersionString) < MIN_OPENGL_VERSION) {
            Log.e(TAG, "Sceneform requires OpenGL ES 3.0 later");
            Toast.makeText(activity, "Sceneform requires OpenGL ES 3.0 or later", Toast.LENGTH_LONG)
                    .show();
            activity.finish();
            return false;
        }
        return true;
    }
}
