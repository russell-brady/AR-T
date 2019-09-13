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

public class SectionsLesson {

    ArLessonActivity activity;
    private ViewRenderablesController viewRenderablesController;
    private AnchorNode anchorNode;

    private TextView prompt;

    private ModelRenderable cuboid;
    private ModelRenderable horizPlane;

    private Node cuboidNode;

    private float yRatio;

    public SectionsLesson(ArLessonActivity activity) {
        this.activity = activity;
        this.viewRenderablesController = new ViewRenderablesController(activity);
        this.prompt = activity.findViewById(R.id.find_surface);

        MaterialFactory.makeOpaqueWithColor(activity, new Color(android.graphics.Color.GREEN))
                .thenAccept(
                        material -> cuboid = ShapeFactory.makeCube(new Vector3(0.3f, 0.5f, 0.3f), new Vector3(0.0f, 0.25f, 0.0f), material));

        MaterialFactory.makeOpaqueWithColor(activity, new Color(android.graphics.Color.RED))
                .thenAccept(
                        material -> horizPlane = ShapeFactory.makeCube(new Vector3(0.8f, 0.001f, 0.8f), new Vector3(0.0f, 0.0f, 0.0f), material));

    }

    public void makeCuboid(float height) {
        MaterialFactory.makeOpaqueWithColor(activity, new Color(android.graphics.Color.GREEN))
                .thenAccept(
                        material -> {
                            cuboid = ShapeFactory.makeCube(new Vector3(0.3f, height + 0.00101f, 0.3f), new Vector3(0.0f, yRatio / 2, 0.0f), material);
                            cuboidNode.setRenderable(cuboid);

                        });
    }

    public void startLesson(AnchorNode anchorNode) {
        this.anchorNode = anchorNode;

        CustomNode part1 = new CustomNode();
        part1.setParent(anchorNode);
        part1.setLocalPosition(new Vector3(0.0f, 0.2f, 0.0f));

        ViewRenderable descriptiveInfoCard = viewRenderablesController.getDescriptiveInfoCard();
        part1.setRenderable(descriptiveInfoCard);

        viewRenderablesController.getInfoCardTitle().setText(R.string.cross_section);
        viewRenderablesController.getInfoCardDesc().setText(R.string.cross_section_desc);

        prompt.setText(R.string.tap_to_continue);
        prompt.setVisibility(View.VISIBLE);

        prompt.setOnClickListener(view -> part2(part1));
    }

    private void part2(CustomNode part1) {

        part1.setLocalPosition(new Vector3(0.0f, 0.6f, 0.0f));
        viewRenderablesController.getInfoCardDesc().setText(R.string.cross_section_desc_2);

        Node base = new Node();
        base.setParent(anchorNode);

        Node horizNode = new Node();
        horizNode.setParent(base);
        horizNode.setRenderable(horizPlane);
        horizNode.setLocalPosition(new Vector3(0.0f, 0.3f, 0.0f));

        cuboidNode = new Node();
        cuboidNode.setParent(base);
        cuboidNode.setRenderable(cuboid);

        prompt.setOnClickListener(view -> part3(part1, base, horizNode));
    }

    private void part3(Node part1, Node base, Node horizNode) {

        viewRenderablesController.getInfoCardDesc().setText(R.string.cross_section_desc_3);

        prompt.setOnClickListener(view -> part4(part1, base, horizNode));
    }

    private void part4(Node part1, Node base, Node horizNode) {

        part1.setParent(null);

        CustomNode planeController = new CustomNode();
        planeController.setParent(base);
        planeController.setRenderable(viewRenderablesController.getSeekBarController());
        planeController.setLocalPosition(new Vector3(0.0f, 0.8f, 0.0f));

        getDevSeekBar(horizNode);

        prompt.setOnClickListener(view -> part5(part1, base, horizNode));
    }

    private void part5(Node part1, Node base, Node horizNode) {

        makeCuboid(yRatio);

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
        viewRenderablesController.getSeekBar1().setVisibility(View.GONE);
        viewRenderablesController.getView1().setVisibility(View.GONE);
        viewRenderablesController.getView2().setText(R.string.use_slider_to_cut);
        viewRenderablesController.getSeekBar2().setProgress(60);
        viewRenderablesController.getSeekBar2().setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        float ratio = (float) progress / (float) seekBar.getMax();
                        yRatio = ratio * 0.5f;
                        horiz.setLocalPosition(new Vector3(0.05f, yRatio, 0.0f));
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {}

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {}
                });
    }


}
