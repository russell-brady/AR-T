
package com.arproject.russell.ar_t.augmentedimages;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.arproject.russell.ar_t.R;
import com.arproject.russell.ar_t.ar_models.CustomNode;
import com.google.ar.core.AugmentedImage;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.ViewRenderable;

import java.util.concurrent.CompletableFuture;

public class AugmentedImageModelNode extends AnchorNode {

    private static final String TAG = "AugmentedImageModelNode";

    private AugmentedImage image;
    private static CompletableFuture<ModelRenderable> modelFuture;
    private ViewRenderable infoCard;
    private AugmentedImageItem augmentedImageItem;
    private Context context;

    private RotatingNode objectNode;
    private CustomNode view;

    public AugmentedImageModelNode(Context context, AugmentedImageItem item, ViewRenderable infoCard) {
        this.augmentedImageItem = item;
        this.context = context;
        // Upon construction, start loading the modelFuture
        if (modelFuture == null) {
            modelFuture = ModelRenderable.builder().setRegistryId("modelFuture")
                    .setSource(context, augmentedImageItem.getResId())
                    .build();
        }
        this.infoCard = infoCard;
    }

    public void setImage(AugmentedImage image) {
        this.image = image;

        if (!modelFuture.isDone()) {
            CompletableFuture.allOf(modelFuture).thenAccept((Void aVoid) -> setImage(image)).exceptionally(throwable -> {
                Log.e(TAG, "Exception loading", throwable);
                return null;
            });
        }

        setAnchor(image.createAnchor(image.getCenterPose()));

        objectNode = new RotatingNode(true);
        objectNode.setParent(this);
        objectNode.setLocalPosition(new Vector3(0f, 0.1f, 0f));
        objectNode.setLocalScale(new Vector3(0.3f, 0.3f, 0.3f));
        objectNode.setRenderable(modelFuture.getNow(null));

        view = new CustomNode();
        view.setParent(this);
        view.setRenderable(infoCard);
        view.setLocalPosition(new Vector3(0.0f, 0.5f, 0.0f));

        View info = infoCard.getView();
        TextView title = info.findViewById(R.id.viewTitle);
        TextView desc = info.findViewById(R.id.viewDesc);

        title.setText(augmentedImageItem.getTitle());
        desc.setText(augmentedImageItem.getDesc());

        objectNode.setOnTapListener((hitTestResult, motionEvent) -> {
            info.setVisibility(View.VISIBLE);
            objectNode.setUpdatedSpeedMultiplier(!objectNode.isObjectAnimated());
            String text = !objectNode.isObjectAnimated() ? "Finished rotating" : "Rotating object";
            Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
        });
    }

    public AugmentedImage getImage() {
        return image;
    }

    public RotatingNode getObjectNode() {
        return objectNode;
    }

    public CustomNode getView() {
        return view;
    }
}
