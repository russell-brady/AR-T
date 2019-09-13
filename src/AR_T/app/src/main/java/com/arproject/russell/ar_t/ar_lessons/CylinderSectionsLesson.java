package com.arproject.russell.ar_t.ar_lessons;

import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.arproject.russell.ar_t.R;
import com.arproject.russell.ar_t.ar_models.CustomNode;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.Node;
import com.google.ar.sceneform.math.Quaternion;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.Color;
import com.google.ar.sceneform.rendering.MaterialFactory;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.ShapeFactory;
import com.google.ar.sceneform.rendering.ViewRenderable;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class CylinderSectionsLesson {

    ArLessonActivity activity;
    private ViewRenderablesController viewRenderablesController;
    private ViewRenderable controller;
    private AnchorNode anchorNode;

    private TextView prompt;

    private ModelRenderable cylinder;
    private ModelRenderable horizPlane;
    private ModelRenderable vertPlane;

    public CylinderSectionsLesson(ArLessonActivity activity) {
        this.activity = activity;
        this.viewRenderablesController = new ViewRenderablesController(activity);
        this.prompt = activity.findViewById(R.id.find_surface);

        MaterialFactory.makeOpaqueWithColor(activity, new Color(android.graphics.Color.GREEN))
                .thenAccept(
                        material -> cylinder = ShapeFactory.makeCylinder(0.15f, 0.5f, new Vector3(0.0f, 0.25f, 0.0f), material));

        MaterialFactory.makeOpaqueWithColor(activity, new Color(android.graphics.Color.RED))
                .thenAccept(
                        material -> horizPlane = ShapeFactory.makeCube(new Vector3(0.6f, 0.001f, 0.6f), new Vector3(0.0f, 0.0f, 0.0f), material));

        MaterialFactory.makeOpaqueWithColor(activity, new Color(android.graphics.Color.BLUE))
                .thenAccept(
                        material -> vertPlane = ShapeFactory.makeCube(new Vector3(0.6f, 0.6f, 0.001f), new Vector3(0.0f, 0.0f, 0.0f), material));

    }

    public void startLesson(AnchorNode anchorNode) {
        this.anchorNode = anchorNode;

        CustomNode part1 = new CustomNode();
        part1.setParent(anchorNode);
        part1.setLocalPosition(new Vector3(0.0f, 0.3f, 0.0f));

        ViewRenderable descriptiveInfoCard = viewRenderablesController.getDescriptiveInfoCard();
        part1.setRenderable(descriptiveInfoCard);

        controller = viewRenderablesController.getSeekBarController();

        viewRenderablesController.getInfoCardTitle().setText(R.string.cylinder_section);
        viewRenderablesController.getInfoCardDesc().setText(R.string.cylinder_section_desc);

        prompt.setText(R.string.tap_to_continue);
        prompt.setVisibility(View.VISIBLE);

        prompt.setOnClickListener(view -> getCylinderHorizontalSection(part1));
    }

    private void getCylinderHorizontalSection(CustomNode part1) {

        part1.setLocalPosition(new Vector3(0.0f, 0.65f, 0.0f));

        viewRenderablesController.getInfoCardTitle().setText(R.string.horiz_section);
        viewRenderablesController.getInfoCardDesc().setText(R.string.horiz_section_desc);

        Node base = new Node();
        base.setParent(anchorNode);

        Node horizNode = new Node();
        horizNode.setParent(base);
        horizNode.setRenderable(horizPlane);
        horizNode.setLocalPosition(new Vector3(0.0f, 0.3f, 0.0f));

        Node cylinderNode = new Node();
        cylinderNode.setParent(base);
        cylinderNode.setRenderable(cylinder);

        prompt.setOnClickListener(view -> {
            horizNode.setParent(null);
            getCylinderVerticalSection(part1, cylinderNode);
        });
    }

    private void getCylinderVerticalSection(CustomNode part1, Node cylinderNode) {

        viewRenderablesController.getInfoCardTitle().setText(R.string.vert_section);
        viewRenderablesController.getInfoCardDesc().setText(R.string.vert_section_desc);

        Node raisedBaseNode = new Node();
        raisedBaseNode.setParent(anchorNode);
        raisedBaseNode.setLocalPosition(new Vector3(0.0f, 0.3f, 0.0f));
        raisedBaseNode.setLocalRotation(Quaternion.axisAngle(new Vector3(0, 1f, 0), 90));

        Node vertNode = new Node();
        vertNode.setParent(raisedBaseNode);
        vertNode.setRenderable(vertPlane);

        prompt.setOnClickListener(view -> {
            vertNode.setParent(null);
            getCylinderCrossSection(part1, cylinderNode);
        });
    }

    private void getCylinderCrossSection(CustomNode part1, Node cylinderNode) {

        viewRenderablesController.getInfoCardTitle().setText(R.string.angle_section);
        viewRenderablesController.getInfoCardDesc().setText(R.string.angle_section_desc);

        Node base = new Node();
        anchorNode.addChild(base);

        Node horizNode = new Node();
        horizNode.setParent(base);
        horizNode.setRenderable(horizPlane.makeCopy());
        horizNode.setLocalPosition(new Vector3(0.0f, 0.3f, 0.0f));
        horizNode.setLocalRotation(Quaternion.axisAngle(new Vector3(0, 0, 1f), 40));

        prompt.setOnClickListener(view -> {
            horizNode.setParent(null);
            part1.setParent(null);
            end();
        });
    }

    private void end() {


        CustomNode planeController = new CustomNode();
        planeController.setParent(anchorNode);
        planeController.setRenderable(controller);
        planeController.setLocalPosition(new Vector3(0.0f, 0.8f, 0.0f));

        Node horizNode = new Node();
        horizNode.setParent(anchorNode);
        horizNode.setRenderable(horizPlane);
        horizNode.setLocalPosition(new Vector3(0.0f, 0.3f, 0.0f));

        getDevSeekBar(horizNode);

        prompt.setOnClickListener(view -> new SweetAlertDialog(activity, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText(activity.getString(R.string.lesson_complete))
                .setContentText(activity.getString(R.string.lesson_complete_desc))
                .setConfirmClickListener(sweetAlertDialog -> {
                    activity.onLessonCompleted();
                    activity.finish();
                })
                .show());
    }

    private void getDevSeekBar(Node horiz) {
        viewRenderablesController.getView3().setVisibility(View.VISIBLE);
        viewRenderablesController.getView3().setText(R.string.section_circle);
        viewRenderablesController.getView1().setText(R.string.control_rotation);
        viewRenderablesController.getSeekBar1().setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        float ratio = (float) progress / (float) seekBar.getMax();
                        float degrees = ratio * 90;
                        horiz.setLocalRotation(Quaternion.axisAngle(new Vector3(0, 0, 1f), degrees));
                        if (degrees == 0) {
                            viewRenderablesController.getView3().setText(R.string.section_circle);
                        } else if (degrees == 90) {
                            viewRenderablesController.getView3().setText(R.string.section_rectangle);
                        } else {
                            viewRenderablesController.getView3().setText(R.string.section_ellipse);
                        }
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {}

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {}
                });
        viewRenderablesController.getView1().setText(R.string.control_positon);
        viewRenderablesController.getSeekBar2().setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        float ratio = (float) progress / (float) seekBar.getMax();
                        float pos = -0.2f + (ratio * 0.4f);
                        horiz.setLocalPosition(new Vector3(pos, 0.3f, 0));
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {}

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {}
                });
    }


}
