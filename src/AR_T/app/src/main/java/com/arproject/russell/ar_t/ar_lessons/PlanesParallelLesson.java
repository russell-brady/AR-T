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

public class PlanesParallelLesson {

    private ArLessonActivity activity;
    private ViewRenderablesController viewRenderablesController;
    private AnchorNode anchorNode;

    private TextView prompt;

    private ModelRenderable horizPlane;


    public PlanesParallelLesson(ArLessonActivity activity) {
        this.activity = activity;
        this.viewRenderablesController = new ViewRenderablesController(activity);
        this.prompt = activity.findViewById(R.id.find_surface);

        MaterialFactory.makeOpaqueWithColor(activity, new Color(android.graphics.Color.RED))
                .thenAccept(
                        material -> horizPlane = ShapeFactory.makeCube(new Vector3(0.8f, 0.001f, 0.8f), new Vector3(0.0f, 0.0f, 0.0f), material));

    }

    public void startLesson(AnchorNode anchorNode) {
        this.anchorNode = anchorNode;

        CustomNode part1 = new CustomNode();
        part1.setParent(anchorNode);
        part1.setLocalPosition(new Vector3(0.0f, 0.6f, 0.0f));

        ViewRenderable descriptiveInfoCard = viewRenderablesController.getDescriptiveInfoCard();
        part1.setRenderable(descriptiveInfoCard);

        viewRenderablesController.getInfoCardTitle().setText(R.string.parallel_planes);
        viewRenderablesController.getInfoCardDesc().setText(R.string.parallel_planes_desc);

        prompt.setText(R.string.tap_to_continue);
        prompt.setVisibility(View.VISIBLE);

        prompt.setOnClickListener(view -> part1(part1));
    }

    private void part1(CustomNode node) {

        viewRenderablesController.getInfoCardDesc().setText(R.string.parallel_planes_desc_2);

        Node base = new Node();
        anchorNode.addChild(base);

        Node horizNode1 = new Node();
        horizNode1.setParent(base);
        horizNode1.setRenderable(horizPlane.makeCopy());
        horizNode1.setLocalPosition(new Vector3(0.0f, 0.5f, 0.0f));

        Node horizNode2 = new Node();
        horizNode2.setParent(base);
        horizNode2.setRenderable(horizPlane.makeCopy());
        horizNode2.setLocalPosition(new Vector3(0.0f, 0.3f, 0.0f));

        prompt.setOnClickListener(view -> part2(node, horizNode1, horizNode2, base));
    }

    private void part2(CustomNode part1, Node horizNode1, Node horizNode2, Node base) {

        viewRenderablesController.getInfoCardDesc().setText(R.string.parallel_planes_desc_3);

        Node horizNode = new Node();
        horizNode.setParent(base);
        horizNode.setRenderable(horizPlane.makeCopy());
        horizNode.setLocalPosition(new Vector3(0.0f, 0.1f, 0.0f));

        prompt.setOnClickListener(view -> part3(part1, horizNode1, horizNode2, horizNode, base));
    }

    private void part3(CustomNode node, Node horizNode1, Node horizNode2, Node horizNode, Node base) {

        node.setParent(null);

        CustomNode planeController = new CustomNode();
        planeController.setParent(anchorNode);
        planeController.setRenderable(viewRenderablesController.getSeekBarController());
        planeController.setLocalPosition(new Vector3(0.0f, 0.7f, 0.0f));

        getDevSeekBar(horizNode1, horizNode2, horizNode);

        prompt.setOnClickListener(view -> new SweetAlertDialog(activity, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText(activity.getString(R.string.lesson_complete))
                .setContentText(activity.getString(R.string.lesson_complete_desc))
                .setConfirmClickListener(sweetAlertDialog -> {
                    activity.onLessonCompleted();
                    activity.finish();
                })
                .show());
    }

    private void getDevSeekBar(Node node1, Node node2, Node node3) {
        viewRenderablesController.getView1().setText(R.string.top_plane);
        viewRenderablesController.getSeekBar1().setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        float ratio = (float) progress / (float) seekBar.getMax();
                        float xRatio = ratio * 0.4f;
                        node1.setLocalPosition(new Vector3(xRatio, 0.5f, 0.0f));
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {}

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {}
                });
        viewRenderablesController.getView2().setText(R.string.middle_plane);
        viewRenderablesController.getSeekBar2().setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        float ratio = (float) progress / (float) seekBar.getMax();
                        float xRatio = ratio * 0.4f;
                        node2.setLocalPosition(new Vector3(xRatio, 0.3f, 0.0f));
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {}

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {}
                });
        viewRenderablesController.getSeekBar3().setVisibility(View.VISIBLE);
        viewRenderablesController.getView3().setText(R.string.bottom_plane);
        viewRenderablesController.getView3().setVisibility(View.VISIBLE);
        viewRenderablesController.getSeekBar3().setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        float ratio = (float) progress / (float) seekBar.getMax();
                        float xRatio = ratio * 0.4f;
                        node3.setLocalPosition(new Vector3(xRatio, 0.1f, 0.0f));
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {}

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {}
                });
    }

}
