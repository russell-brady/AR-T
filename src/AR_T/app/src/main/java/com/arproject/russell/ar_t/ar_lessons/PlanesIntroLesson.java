package com.arproject.russell.ar_t.ar_lessons;

import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.arproject.russell.ar_t.R;
import com.arproject.russell.ar_t.ar_models.CustomNode;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.Node;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.Color;
import com.google.ar.sceneform.rendering.MaterialFactory;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.ShapeFactory;
import com.google.ar.sceneform.rendering.ViewRenderable;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class PlanesIntroLesson {

    private ArLessonActivity activity;
    private ViewRenderablesController viewRenderablesController;
    private AnchorNode anchorNode;

    private ModelRenderable vertPlane;
    private ModelRenderable horizPlane;

    private ViewRenderable descriptiveInfoCard;

    private TextView prompt;

    private float xRatio = 0f;
    private float yRatio = 0f;

    public PlanesIntroLesson(ArLessonActivity activity) {
        this.activity = activity;
        this.viewRenderablesController = new ViewRenderablesController(activity);
        this.prompt = activity.findViewById(R.id.find_surface);

        MaterialFactory.makeOpaqueWithColor(activity, new Color(android.graphics.Color.RED))
                .thenAccept(
                        material -> horizPlane = ShapeFactory.makeCube(new Vector3(0.8f, 0.001f, 0.8f), new Vector3(0.0f, 0.0f, 0.0f), material));

        MaterialFactory.makeOpaqueWithColor(activity, new Color(android.graphics.Color.BLUE))
                .thenAccept(
                        material -> vertPlane = ShapeFactory.makeCube(new Vector3(0.6f, 0.4f, 0.001f), new Vector3(0.0f, 0.0f, 0.0f), material));

    }

    public void startLesson(AnchorNode anchorNode){

        this.anchorNode = anchorNode;

        CustomNode part1 = new CustomNode();
        part1.setParent(anchorNode);
        part1.setLocalPosition(new Vector3(0.0f, 0.4f, 0.0f));

        descriptiveInfoCard = viewRenderablesController.getDescriptiveInfoCard();
        part1.setRenderable(descriptiveInfoCard);

        viewRenderablesController.getInfoCardTitle().setText(R.string.plane);
        viewRenderablesController.getInfoCardDesc().setText(R.string.plane_desc);

        prompt.setText(R.string.tap_to_continue);
        prompt.setVisibility(View.VISIBLE);

        prompt.setOnClickListener(view -> {
            part1.setParent(null);
            part2();
        });
    }

    private void part2() {

        Node planeNode = new Node();
        planeNode.setParent(anchorNode);
        planeNode.setRenderable(horizPlane);
        planeNode.setLocalPosition(new Vector3(0.0f, 0.1f, 0.0f));

        CustomNode viewNode = new CustomNode();
        viewNode.setParent(anchorNode);
        viewNode.setLocalPosition(new Vector3(0.0f, 0.4f, 0.0f));

        viewNode.setRenderable(descriptiveInfoCard);

        viewRenderablesController.getInfoCardTitle().setText(R.string.horizontal_plane);
        viewRenderablesController.getInfoCardDesc().setText(R.string.horiz_plane_desc);

        prompt.setOnClickListener(view -> {
            viewNode.setParent(null);
            part3(planeNode);
        });

    }

    private void part3(Node planeNode) {
        CustomNode viewNode = new CustomNode();
        viewNode.setParent(anchorNode);
        viewNode.setLocalPosition(new Vector3(0.0f, 0.6f, 0.0f));

        ViewRenderable descriptiveInfoCard = viewRenderablesController.getDescriptiveInfoCard();
        viewNode.setRenderable(descriptiveInfoCard);

        viewRenderablesController.getInfoCardDesc().setText(R.string.horiz_plane_desc_2);

        prompt.setOnClickListener(view -> {
            viewNode.setParent(null);
            part4(planeNode);
        });
    }

    private void part4(Node planeNode) {

        CustomNode planeController = new CustomNode();
        planeController.setParent(anchorNode);
        planeController.setLocalPosition(new Vector3(0.0f, 0.6f, 0.0f));

        ViewRenderable seekBarController = viewRenderablesController.getSeekBarController();
        planeController.setRenderable(seekBarController);

        seekBarListener(planeNode);

        prompt.setOnClickListener(view -> {
            planeNode.setParent(null);
            planeController.setParent(null);
            part5();
        });
    }

    private void part5() {
        Node raisedBaseNode = new Node();
        raisedBaseNode.setParent(anchorNode);
        raisedBaseNode.setLocalPosition(new Vector3(0.0f, 0.2f, 0.0f));

        Node planeNode = new Node();
        planeNode.setParent(raisedBaseNode);
        planeNode.setRenderable(vertPlane);

        CustomNode viewNode = new CustomNode();
        viewNode.setParent(anchorNode);
        viewNode.setLocalPosition(new Vector3(0.0f, 0.6f, 0.0f));

        viewNode.setRenderable(descriptiveInfoCard);

        viewRenderablesController.getInfoCardTitle().setText(R.string.vertical_plane);
        viewRenderablesController.getInfoCardDesc().setText(R.string.vert_plane_desc);

        prompt.setOnClickListener(view -> {
            viewNode.setParent(null);
            part6(planeNode);
        });

    }

    private void part6(Node planeNode) {

        CustomNode planeController = new CustomNode();
        planeController.setParent(anchorNode);

        ViewRenderable seekBarController = viewRenderablesController.getSeekBarController();
        planeController.setRenderable(seekBarController);
        planeController.setLocalPosition(new Vector3(0.0f, 0.6f, 0.0f));

        seekBarListener(planeNode);

        prompt.setOnClickListener(view -> new SweetAlertDialog(activity, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText(activity.getString(R.string.lesson_complete))
                .setContentText(activity.getString(R.string.lesson_complete_desc))
                .setConfirmClickListener(sweetAlertDialog -> {
                    activity.onLessonCompleted();
                    activity.finish();
                })
                .show());
    }

    private void seekBarListener(Node planeNode) {
        viewRenderablesController.getSeekBar1().setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                float ratio = (float) i / (float) seekBar.getMax();
                yRatio = ratio * 0.4f;
                planeNode.setLocalPosition(new Vector3(xRatio, yRatio, 0.0f));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        viewRenderablesController.getSeekBar2().setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                float ratio = (float) i / (float) seekBar.getMax();
                xRatio = -0.25f + (ratio * 0.4f);
                planeNode.setLocalPosition(new Vector3(xRatio, yRatio, 0.0f));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }


}
