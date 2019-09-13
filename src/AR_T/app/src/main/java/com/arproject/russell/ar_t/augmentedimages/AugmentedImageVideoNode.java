package com.arproject.russell.ar_t.augmentedimages;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.view.Gravity;
import android.widget.Toast;

import com.arproject.russell.ar_t.R;
import com.google.ar.core.AugmentedImage;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.Node;
import com.google.ar.sceneform.math.Quaternion;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.Color;
import com.google.ar.sceneform.rendering.ExternalTexture;
import com.google.ar.sceneform.rendering.ModelRenderable;

public class AugmentedImageVideoNode extends AnchorNode {

    private ExternalTexture texture = new ExternalTexture();
    private MediaPlayer mediaPlayer;

    private static final Color CHROMA_KEY_COLOR = new Color(0.1843f, 1.0f, 0.098f);

    private ModelRenderable videoRenderable;
    private Node videoNode;

    public AugmentedImageVideoNode(Context context, AugmentedImageItem item, AugmentedImage augmentedImage) {

        // Create an Android MediaPlayer to capture the video on the external texture's surface.
        mediaPlayer = MediaPlayer.create(context, item.getResId());
        mediaPlayer.setSurface(texture.getSurface());
        mediaPlayer.setLooping(false);

        ModelRenderable.builder()
                .setSource(context, R.raw.video_screen)
                .build()
                .thenAccept(renderable -> {
                    this.videoRenderable = renderable;
                    renderable.getMaterial().setExternalTexture("videoTexture", texture);
                    renderable.getMaterial().setFloat4("keyColor", CHROMA_KEY_COLOR);

                    setImage(augmentedImage);
                })
                .exceptionally(throwable -> {
                    Toast toast = Toast.makeText(context, "Unable to load video renderable", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    return null;
                });

    }

    public void setImage(AugmentedImage image) {

        if (videoRenderable != null) {
            setAnchor(image.createAnchor(image.getCenterPose()));

            Vector3 localScale = new Vector3();
            Vector3 localPosition = new Vector3();
            videoNode = new Node();

            localScale.set(image.getExtentX(), image.getExtentZ(), 1);
            localPosition.set(0.0f, 0.0f, 0.5f * image.getExtentZ());

            videoNode.setParent(this);
            videoNode.setLocalScale(localScale);
            videoNode.setLocalPosition(localPosition);
            videoNode.setLocalRotation(Quaternion.axisAngle(new Vector3(1f, 0, 0), 270));
            playMedia(texture, videoNode);
        }
    }

    private void playMedia(ExternalTexture texture, Node videoNode) {
        if (!mediaPlayer.isPlaying()) {
            mediaPlayer.start();

            // Wait to set the renderable until the first frame of the  video becomes available.
            // This prevents the renderable from briefly appearing as a black quad before the video
            // plays.
            texture.getSurfaceTexture()
                    .setOnFrameAvailableListener(
                            (SurfaceTexture surfaceTexture) -> {
                                videoNode.setRenderable(videoRenderable);
                                texture.getSurfaceTexture().setOnFrameAvailableListener(null);
                            });
        } else {
            videoNode.setRenderable(videoRenderable);
        }
    }

    public Node getVideoNode() {
        return videoNode;
    }

    public void stopVideo() {
        mediaPlayer.stop();
        mediaPlayer.release();
    }

}
