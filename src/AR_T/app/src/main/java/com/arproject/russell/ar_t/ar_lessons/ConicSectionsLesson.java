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

public class ConicSectionsLesson {

    ArLessonActivity activity;
    private ViewRenderablesController viewRenderablesController;
    private ViewRenderable controller;
    private AnchorNode anchorNode;

    private TextView prompt;

    private ModelRenderable horizPlane;
    private ModelRenderable coneRenderable;

    public ConicSectionsLesson(ArLessonActivity activity) {
        this.activity = activity;
        this.viewRenderablesController = new ViewRenderablesController(activity);
        this.prompt = activity.findViewById(R.id.find_surface);

        MaterialFactory.makeOpaqueWithColor(activity, new Color(android.graphics.Color.RED))
                .thenAccept(
                        material -> horizPlane = ShapeFactory.makeCube(new Vector3(0.6f, 0.001f, 0.6f), new Vector3(0.0f, 0.0f, 0.0f), material));

        ModelRenderable.builder().setSource(activity.getApplicationContext(), R.raw.cone).build().thenAccept(
                (renderable) -> this.coneRenderable = renderable)
                .exceptionally(
                        (throwable) -> {
                            throw new AssertionError("Could not load plane card view.", throwable);
                        });

    }

    public void startLesson(AnchorNode anchorNode) {
        this.anchorNode = anchorNode;

        CustomNode part1 = new CustomNode();
        part1.setParent(anchorNode);
        part1.setLocalPosition(new Vector3(0.0f, 0.3f, 0.0f));

        ViewRenderable descriptiveInfoCard = viewRenderablesController.getDescriptiveInfoCard();
        part1.setRenderable(descriptiveInfoCard);

        controller = viewRenderablesController.getSeekBarController();

        viewRenderablesController.getInfoCardTitle().setText(R.string.conic_section);
        viewRenderablesController.getInfoCardDesc().setText(R.string.conic_section_desc);

        prompt.setText(R.string.tap_to_continue);
        prompt.setVisibility(View.VISIBLE);

        prompt.setOnClickListener(view -> part1(part1));
    }

    public void part1(CustomNode node) {

        node.setLocalPosition(new Vector3(0.0f, 0.6f, 0.0f));

        viewRenderablesController.getInfoCardDesc().setText(R.string.conic_section_desc2);

        Node base = new Node();
        base.setParent(anchorNode);

        Node horizNode = new Node();
        horizNode.setParent(base);
        horizNode.setRenderable(horizPlane);
        horizNode.setLocalPosition(new Vector3(0.0f, 0.2f, 0.0f));

        Node coneNode = new Node();
        coneNode.setParent(base);
        coneNode.setRenderable(coneRenderable);
        coneNode.setLocalScale(new Vector3(0.4f, 0.4f, 0.4f));

        prompt.setOnClickListener(view -> part2(node, base, horizNode, coneNode));

    }

    private void part2(CustomNode node, Node base, Node horizNode, Node coneNode) {

        viewRenderablesController.getInfoCardDesc().setText(R.string.conic_section_desc3);

        horizNode.setLocalRotation(Quaternion.axisAngle(new Vector3(0, 0, 1f), 65));

        prompt.setOnClickListener(view -> part3(node, base, horizNode, coneNode));

    }

    private void part3(CustomNode node, Node base, Node horizNode, Node coneNode) {

        viewRenderablesController.getInfoCardDesc().setText(R.string.conic_section_desc_4);

        horizNode.setLocalRotation(Quaternion.axisAngle(new Vector3(0, 0, 1f), 30));

        prompt.setOnClickListener(view -> part4(node, base, horizNode, coneNode));
    }

    private void part4(CustomNode node, Node base, Node horizNode, Node coneNode) {

        viewRenderablesController.getInfoCardDesc().setText(R.string.conic_section_desc_5);

        horizNode.setLocalRotation(Quaternion.axisAngle(new Vector3(0, 0, 1f), 85));

        prompt.setOnClickListener(view -> getConeSection(node, base, horizNode, coneNode));
    }

    private void getConeSection(CustomNode node, Node base, Node horizNode, Node coneNode) {

        node.setParent(null);

        CustomNode planeController = new CustomNode();
        planeController.setParent(base);
        planeController.setRenderable(controller);
        planeController.setLocalPosition(new Vector3(0.0f, 0.6f, 0.0f));

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
        viewRenderablesController.getView3().setText("");
        viewRenderablesController.getView1().setText(R.string.control_rotation);
        viewRenderablesController.getSeekBar1().setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        float ratio = (float) progress / (float) seekBar.getMax();
                        float degrees = ratio * 100;
                        horiz.setLocalRotation(Quaternion.axisAngle(new Vector3(0, 0, 1f), degrees));

                        if (degrees == 0) {
                            viewRenderablesController.getView3().setText(R.string.circle_section);
                        } else if (degrees > 0 && degrees < 65) {
                            viewRenderablesController.getView3().setText(R.string.ellipse_section);
                        } else if (degrees == 65) {
                            viewRenderablesController.getView3().setText(R.string.parabola_section);
                        } else {
                            viewRenderablesController.getView3().setText(R.string.hyperbola_section);
                        }
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {}

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {}
                });
        viewRenderablesController.getView2().setText(R.string.control_positon);
        viewRenderablesController.getSeekBar2().setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        float ratio = (float) progress / (float) seekBar.getMax();
                        float position = ratio * 0.4f;
                        horiz.setLocalPosition(new Vector3(position, 0.3f, 0));
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {}

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {}
                });
    }

}
