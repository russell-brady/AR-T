package com.arproject.russell.ar_t.ar_models;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.arproject.russell.ar_t.R;
import com.arproject.russell.ar_t.augmentedimages.SnackbarHelper;
import com.arproject.russell.ar_t.models.ARModel;
import com.arproject.russell.ar_t.models.CloudAnchorResponse;
import com.arproject.russell.ar_t.utils.ApiClient;
import com.arproject.russell.ar_t.utils.ApiInterface;
import com.arproject.russell.ar_t.utils.ApiUtils;
import com.arproject.russell.ar_t.utils.PrefConfig;
import com.google.ar.core.Anchor;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.FrameTime;
import com.google.ar.sceneform.assets.RenderableSource;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.ViewRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArUploadActivity extends AppCompatActivity {

    private enum AppAnchorState {
        NONE,
        HOSTING,
        HOSTED,
    }

    private AppAnchorState appAnchorState = AppAnchorState.NONE;

    private ArFragment arFragment;
    private ModelRenderable renderable;
    private SnackbarHelper snackbarHelper;
    private Anchor cloudAnchor;
    private TransformableNode transformableNode;
    private ContentLoadingProgressBar progressBar;
    private ApiInterface apiInterface;
    private ARModel arModel;
    private String modelLocation;
    private AppCompatButton cloudAnchorIdView;
    private ViewRenderable solarControlsRenderable;
    private boolean isPlaced = false;
    private TakePhotoHelper takePhotoHelper;

    @Override
    @SuppressWarnings({"AndroidApiChecker", "FutureReturnValueIgnored"})
    protected void onCreate(Bundle savedInstanceState) {
        PrefConfig prefConfig = new PrefConfig(this);
        setTheme(prefConfig.getTheme());
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        arModel = intent.getParcelableExtra("ArModel");
        if (arModel != null) {
            modelLocation = ApiClient.BASE_URL + "/" + arModel.getModelLocation();
        }

        apiInterface = ApiUtils.getApiService();
        snackbarHelper = new SnackbarHelper();

        setContentView(R.layout.activity_ux);
        progressBar = findViewById(R.id.ar_progress_bar);
        arFragment = (CustomArFragment) getSupportFragmentManager().findFragmentById(R.id.sceneform_fragment);
        arFragment.getArSceneView().getScene().addOnUpdateListener(this::onUpdateFrame);
        takePhotoHelper = new TakePhotoHelper(this, arFragment);

        buildModel();

        FloatingActionButton clearButton = findViewById(R.id.clear_button);
        clearButton.setOnClickListener(view -> {
            isPlaced = false;
            removeAnchor();
        });

        FloatingActionButton hostButton = findViewById(R.id.host_button);
        hostButton.setOnClickListener(view -> addCloudAnchor(""));

        FloatingActionButton resolveButton = findViewById(R.id.resolve_button);
        resolveButton.setVisibility(View.GONE);

        ImageView cameraButton = findViewById(R.id.takePhoto);
        cameraButton.setOnClickListener(view -> takePhotoHelper.takePhoto());

        cloudAnchorIdView = findViewById(R.id.anchorIdButton);
        cloudAnchorIdView.setVisibility(View.GONE);

        arFragment.setOnTapArPlaneListener(
            (HitResult hitResult, Plane plane, MotionEvent motionEvent) -> {
                if (!isPlaced) {
                    if (renderable == null) {
                        showToast(getString(R.string.please_resolve));
                        return;
                    } else if (modelLocation == null){
                        showToast(getString(R.string.resolve_anchor_toast));
                        return;
                    }
                    isPlaced = true;
                    arFragment.getPlaneDiscoveryController().hide();
                    // Create the Anchor.
                    Anchor anchor = hitResult.createAnchor();
                    setCloudAnchor(anchor);
                    createAnchor();
                }
            });

        ViewRenderable.builder()
                .setView(getApplicationContext(), R.layout.info_card)
                .build()
                .thenAccept(
                        (renderable) -> {
                            this.solarControlsRenderable = renderable;
                            TextView textView = (TextView) renderable.getView();
                            textView.setText(arModel.getModelName());
                        })
                .exceptionally(
                        (throwable) -> {
                            throw new AssertionError("Could not load plane card view.", throwable);
                        });
    }

    private void createAnchor() {
        AnchorNode anchorNode = new AnchorNode(cloudAnchor);
        arFragment.getArSceneView().getScene().addChild(anchorNode);
        transformableNode = new TransformableNode(arFragment.getTransformationSystem());
        transformableNode.setRenderable(renderable);
        // Set the min and max scales of the ScaleController.
        // Default min is 0.75, default max is 1.75.
        transformableNode.getScaleController().setMinScale(0.4f);
        transformableNode.getScaleController().setMaxScale(2.0f);
        // Set the local scale of the transformableNode BEFORE setting its parent
        transformableNode.setLocalScale(new Vector3(0.55f, 0.55f, 0.55f));
        transformableNode.setParent(anchorNode);

        CustomNode titleView = new CustomNode();
        titleView.setParent(transformableNode);
        titleView.setRenderable(solarControlsRenderable);
        titleView.setLocalPosition(new Vector3(0.0f, 1.5f, 0.0f));

    }

    private void buildModel() {
        if (modelLocation != null) {
            ModelRenderable.builder()
                    .setSource(this, RenderableSource.builder().setSource(
                            this,
                            Uri.parse(modelLocation),
                            RenderableSource.SourceType.GLB)
                            .build())
                    .setRegistryId(modelLocation)
                    .build()
                    .thenAccept(renderable -> this.renderable = renderable)
                    .exceptionally(
                            throwable -> {
                                showToast(getString(R.string.load_renderable_failed) + modelLocation);
                                return null;
                            });
        }
    }

    private void hostModel() {
        if (cloudAnchor != null) {
            progressBar.setVisibility(View.VISIBLE);
            cloudAnchor = arFragment.getArSceneView().getSession().hostCloudAnchor(cloudAnchor);
            appAnchorState = AppAnchorState.HOSTING;
            showToast(getString(R.string.hosting_anchor));
        } else {
            showToast(getString(R.string.no_anchor));
        }
    }

    private void setCloudAnchor (Anchor newAnchor){
        if (cloudAnchor != null){
            cloudAnchor.detach();
        }

        cloudAnchor = newAnchor;
        appAnchorState = AppAnchorState.NONE;
        snackbarHelper.hide(this);
    }

    private void removeAnchor() {
        if (transformableNode != null){
            transformableNode.setParent(null);
        }

        if (cloudAnchor != null){
            cloudAnchor.detach();
        }
        cloudAnchor = null;
        transformableNode = null;
        appAnchorState = AppAnchorState.NONE;
    }

    private void onUpdateFrame(FrameTime frameTime){
        if (cloudAnchor != null) {
            checkUpdatedAnchor();
        }
    }

    private synchronized void checkUpdatedAnchor(){
        if (appAnchorState != AppAnchorState.HOSTING){
            return;
        }
        Anchor.CloudAnchorState cloudState = cloudAnchor.getCloudAnchorState();
        if (appAnchorState == AppAnchorState.HOSTING) {
            if (cloudState.isError()) {
                showToast(getString(R.string.error_hosting_anchor));
                appAnchorState = AppAnchorState.NONE;
            } else if (cloudState == Anchor.CloudAnchorState.SUCCESS) {
                addCloudAnchor(cloudAnchor.getCloudAnchorId());
                appAnchorState = AppAnchorState.HOSTED;
            }
        }
    }

    public void addCloudAnchor(String anchor_id) {
        progressBar.setVisibility(View.VISIBLE);
        apiInterface.addCloudAnchor(anchor_id, arModel.getArModelId()).enqueue(new Callback<CloudAnchorResponse>() {
            @Override
            public void onResponse(@NonNull Call<CloudAnchorResponse> call, @NonNull Response<CloudAnchorResponse> response) {
                if (response.body() != null) {
                    int key = response.body().getAnchorKey();
                    showCloudId(key);
                } else {
                    Toast.makeText(getApplicationContext(), R.string.anchor_add_failed, Toast.LENGTH_LONG).show();
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(@NonNull Call<CloudAnchorResponse> call, @NonNull Throwable t) {
                Toast.makeText(getApplicationContext(), R.string.anchor_add_failed, Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void showCloudId(int key) {
        showToast(getString(R.string.anchor_hosted_id) + key);
        cloudAnchorIdView.setText(getString(R.string.anchor_id) + key);
        cloudAnchorIdView.setVisibility(View.VISIBLE);
    }

    private void showToast(String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}
