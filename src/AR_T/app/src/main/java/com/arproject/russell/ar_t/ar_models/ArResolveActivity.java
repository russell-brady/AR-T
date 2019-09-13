package com.arproject.russell.ar_t.ar_models;

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
import android.widget.Toast;

import com.arproject.russell.ar_t.R;
import com.arproject.russell.ar_t.augmentedimages.SnackbarHelper;
import com.arproject.russell.ar_t.dialog.ResolveDialogFragment;
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
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArResolveActivity extends AppCompatActivity {

    private enum AppAnchorState {
        NONE,
        RESOLVING,
        RESOLVED
    }
    private ArResolveActivity.AppAnchorState appAnchorState = ArResolveActivity.AppAnchorState.NONE;

    private ApiInterface apiInterface;
    private SnackbarHelper snackbarHelper;
    private ContentLoadingProgressBar progressBar;
    private ArFragment arFragment;
    private AppCompatButton cloudAnchorIdView;
    private ModelRenderable renderable;
    private Anchor resolvedAnchor;
    private int anchorKey;
    private TakePhotoHelper takePhotoHelper;
    private TransformableNode transformableNode;

    private boolean isPlaced;
    private String modelLocation;

    @Override
    @SuppressWarnings({"AndroidApiChecker", "FutureReturnValueIgnored"})
    protected void onCreate(Bundle savedInstanceState) {
        PrefConfig prefConfig = new PrefConfig(this);
        setTheme(prefConfig.getTheme());
        super.onCreate(savedInstanceState);

        apiInterface = ApiUtils.getApiService();
        snackbarHelper = new SnackbarHelper();

        setContentView(R.layout.activity_ux);

        progressBar = findViewById(R.id.ar_progress_bar);
        arFragment = (CustomArFragment) getSupportFragmentManager().findFragmentById(R.id.sceneform_fragment);
        arFragment.getArSceneView().getScene().addOnUpdateListener(this::onUpdateFrame);
        takePhotoHelper = new TakePhotoHelper(this, arFragment);

        FloatingActionButton clearButton = findViewById(R.id.clear_button);
        clearButton.setOnClickListener(view -> removeAnchor());

        FloatingActionButton hostButton = findViewById(R.id.host_button);
        hostButton.setVisibility(View.GONE);

        FloatingActionButton resolveButton = findViewById(R.id.resolve_button);
        resolveButton.setOnClickListener(view -> resolveAnchor());

        ImageView cameraButton = findViewById(R.id.takePhoto);
        cameraButton.setOnClickListener(view -> takePhotoHelper.takePhoto());

        cloudAnchorIdView = findViewById(R.id.anchorIdButton);
        cloudAnchorIdView.setVisibility(View.GONE);

        arFragment.setOnTapArPlaneListener(
                (HitResult hitResult, Plane plane, MotionEvent motionEvent) -> {
                    if (!isPlaced) {
                        if (renderable == null) {
                            showToast(getString(R.string.resolving_model));
                            return;
                        } else if (modelLocation == null){
                            showToast(getString(R.string.resolving_model));
                            return;
                        }
                        isPlaced = true;
                        arFragment.getPlaneDiscoveryController().hide();
                        // Create the Anchor.
                        Anchor anchor = hitResult.createAnchor();
                        createAnchor(anchor);
                    }
                });

    }

    private void resolveAnchor() {
        ResolveDialogFragment dialog = new ResolveDialogFragment();
        dialog.setOkListener(this::onResolveOkPressed);
        dialog.show(getSupportFragmentManager(), "Resolve");
    }

    private void onResolveOkPressed(String dialogValue){
        anchorKey = Integer.parseInt(dialogValue);
        //snackbarHelper.showMessage(this, getString(R.string.resolving_anchor));
        appAnchorState = ArResolveActivity.AppAnchorState.RESOLVING;
        getCloudAnchorId(anchorKey);
    }

    private void setResolvedAnchor(Anchor newAnchor){
        if (resolvedAnchor != null){
            resolvedAnchor.detach();
        }

        resolvedAnchor = newAnchor;
        appAnchorState = AppAnchorState.NONE;
        //snackbarHelper.hide(this);
    }

    private void createAnchor(Anchor anchor) {
        AnchorNode anchorNode = new AnchorNode(anchor);
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
    }

    private void removeAnchor() {
        if (transformableNode != null){
            transformableNode.setParent(null);
        }

        if (resolvedAnchor != null){
            resolvedAnchor.detach();
        }
        isPlaced = false;
        resolvedAnchor = null;
        transformableNode = null;
        appAnchorState = AppAnchorState.NONE;
    }


    public void getCloudAnchorId(int key) {
        progressBar.setVisibility(View.VISIBLE);
        apiInterface.getCloudAnchor(key).enqueue(new Callback<CloudAnchorResponse>() {
            @Override
            public void onResponse(@NonNull Call<CloudAnchorResponse> call, @NonNull Response<CloudAnchorResponse> response) {
                if (response.body() != null) {
                    String resolvedAnchorId = response.body().getAnchorId();
                    modelLocation = ApiClient.BASE_URL + "/" + response.body().getModelLocation();
                    buildModel(resolvedAnchorId, modelLocation);
                } else {
                    Toast.makeText(getApplicationContext(), R.string.anchor_failed, Toast.LENGTH_LONG).show();
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(@NonNull Call<CloudAnchorResponse> call, @NonNull Throwable t) {
                Toast.makeText(getApplicationContext(), R.string.anchor_failed, Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void buildModel(String resolvedAnchorId, String modelLocation) {
        if (modelLocation != null) {
            ModelRenderable.builder()
                    .setSource(this, RenderableSource.builder().setSource(
                            this,
                            Uri.parse(modelLocation),
                            RenderableSource.SourceType.GLB)
                            .build())
                    .setRegistryId(modelLocation)
                    .build()
                    .thenAccept(renderable -> {
                        this.renderable = renderable;
                        //placeResolvedAnchor(resolvedAnchorId);
                    })
                    .exceptionally(
                            throwable -> {
                                showToast(getString(R.string.load_renderable_failed) + modelLocation);
                                return null;
                            });
        }
    }

//    private void placeResolvedAnchor(String anchor_id) {
//        resolvedAnchor = arFragment.getArSceneView().getSession().resolveCloudAnchor(anchor_id);
//        setResolvedAnchor(resolvedAnchor);
//        createAnchor(resolvedAnchor);
//    }

    private void onUpdateFrame(FrameTime frameTime){
        checkUpdatedAnchor();
    }

    private synchronized void checkUpdatedAnchor(){
        if (appAnchorState != AppAnchorState.RESOLVING){
            return;
        } if (resolvedAnchor == null) {
            return;
        }
        Anchor.CloudAnchorState cloudState = resolvedAnchor.getCloudAnchorState();
        if (appAnchorState == ArResolveActivity.AppAnchorState.RESOLVING){
            if (cloudState.isError()) {
                showToast(getString(R.string.error_resolving_anchor));
                appAnchorState = ArResolveActivity.AppAnchorState.NONE;
            } else if (cloudState == Anchor.CloudAnchorState.SUCCESS){
                showToast(getString(R.string.resolved_success));
                appAnchorState = ArResolveActivity.AppAnchorState.RESOLVED;
                showCloudId(anchorKey);
            }
        }
    }

    private void showToast(String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    private void showCloudId(int key) {
        showToast(getString(R.string.anchor_resolved_id) + key);
        cloudAnchorIdView.setText(getString(R.string.anchor_id) + key);
        cloudAnchorIdView.setVisibility(View.VISIBLE);
    }

}
