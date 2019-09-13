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

public class PlanesIntersectingLesson {

    private ArLessonActivity activity;
    private ViewRenderablesController viewRenderablesController;
    private AnchorNode anchorNode;

    private TextView prompt;

    private ModelRenderable horizPlane;
    private ModelRenderable vertPlane;


    public PlanesIntersectingLesson(ArLessonActivity activity) {
        this.activity = activity;
        this.viewRenderablesController = new ViewRenderablesController(activity);
        this.prompt = activity.findViewById(R.id.find_surface);

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
        part1.setLocalPosition(new Vector3(0.0f, 0.4f, 0.0f));

        ViewRenderable descriptiveInfoCard = viewRenderablesController.getDescriptiveInfoCard();
        part1.setRenderable(descriptiveInfoCard);

        viewRenderablesController.getInfoCardTitle().setText(R.string.intersecting_planes);
        viewRenderablesController.getInfoCardDesc().setText(R.string.intersecting_planes_desc);

        prompt.setText(R.string.tap_to_continue);
        prompt.setVisibility(View.VISIBLE);

        prompt.setOnClickListener(view -> part1(part1));
    }

    private void part1(CustomNode node) {

        node.setLocalPosition(new Vector3(0.0f, 0.8f, 0.0f));

        viewRenderablesController.getInfoCardDesc().setText(R.string.intersecting_planes_desc_2);

        Node base = new Node();
        base.setParent(anchorNode);

        Node horizNode = new Node();
        horizNode.setParent(base);
        horizNode.setRenderable(horizPlane);
        horizNode.setLocalPosition(new Vector3(0.0f, 0.4f, 0.0f));

        Node vertNode = new Node();
        vertNode.setParent(base);
        vertNode.setRenderable(vertPlane);
        vertNode.setLocalPosition(new Vector3(0.0f, 0.4f, 0.0f));

        prompt.setOnClickListener(view -> part2(node, horizNode, vertNode, base));
    }

    private void part2(CustomNode node, Node horizNode, Node vertNode, Node base) {

        viewRenderablesController.getInfoCardDesc().setText(R.string.intersecting_planes_desc_3);

        prompt.setOnClickListener(view -> part3(node, horizNode, vertNode, base));
    }

    private void part3(CustomNode node, Node horizNode, Node vertNode, Node base) {

        viewRenderablesController.getInfoCardDesc().setText(R.string.intersecting_planes_desc_4);

        prompt.setOnClickListener(view -> part4(node, horizNode, vertNode, base));
    }

    private void part4(CustomNode part1, Node horizNode, Node vertNode, Node base) {

        part1.setParent(null);

        CustomNode planeController = new CustomNode();
        planeController.setParent(base);
        planeController.setRenderable(viewRenderablesController.getSeekBarController());
        planeController.setLocalPosition(new Vector3(0.0f, 0.8f, 0.0f));

        viewRenderablesController.getView1().setText(R.string.move_vert_plane);
        viewRenderablesController.getView2().setText(R.string.move_horiz_plane);
        getDevSeekBar(vertNode, horizNode);

        prompt.setOnClickListener(view -> new SweetAlertDialog(activity, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText(activity.getString(R.string.lesson_complete))
                .setContentText(activity.getString(R.string.lesson_complete_desc))
                .setConfirmClickListener(sweetAlertDialog -> {
                    activity.onLessonCompleted();
                    activity.finish();
                })
                .show());
    }

    private void getDevSeekBar(Node vert, Node horiz) {
        viewRenderablesController.getSeekBar1().setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        float ratio = (float) progress / (float) seekBar.getMax();
                        vert.setLocalRotation(Quaternion.axisAngle(new Vector3(0, 1f, 0), ratio * 100));
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {}

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {}
                });

        viewRenderablesController.getSeekBar2().setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        float ratio = (float) progress / (float) seekBar.getMax();
                        horiz.setLocalRotation(Quaternion.axisAngle(new Vector3(0, 0, 1f), ratio * 100));
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {}

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {}
                });
    }



}
