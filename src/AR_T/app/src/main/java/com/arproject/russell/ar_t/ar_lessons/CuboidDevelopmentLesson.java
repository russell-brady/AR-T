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

public class CuboidDevelopmentLesson {

    private ArLessonActivity activity;
    private ViewRenderablesController viewRenderablesController;
    private AnchorNode anchorNode;

    private TextView prompt;

    private ModelRenderable cuboidLargeFace;
    private ModelRenderable cuboidShortFace;
    private ModelRenderable cuboidMediumFace;
    private ModelRenderable cuboidLargeFace2;
    private ModelRenderable cuboid;

    private ViewRenderable descriptiveInfoCard;

    public CuboidDevelopmentLesson(ArLessonActivity activity) {
        this.activity = activity;
        this.viewRenderablesController = new ViewRenderablesController(activity);
        this.prompt = activity.findViewById(R.id.find_surface);

        MaterialFactory.makeOpaqueWithColor(activity, new Color(android.graphics.Color.MAGENTA))
                .thenAccept(
                        material -> cuboidLargeFace = ShapeFactory.makeCube(new Vector3(0.5f, 0.001f, 0.3f), new Vector3(0.25f, 0.0f, 0.0f), material));

        MaterialFactory.makeOpaqueWithColor(activity, new Color(android.graphics.Color.MAGENTA))
                .thenAccept(
                        material -> cuboidLargeFace2 = ShapeFactory.makeCube(new Vector3(0.3f, 0.001f, 0.5f), new Vector3(0.15f, 0.0f, 0.0f), material));

        MaterialFactory.makeOpaqueWithColor(activity, new Color(android.graphics.Color.WHITE))
                .thenAccept(
                        material -> cuboidShortFace = ShapeFactory.makeCube(new Vector3(0.16f, 0.001f, 0.3f), new Vector3(0.08f, 0.0f, 0.0f), material));

        MaterialFactory.makeOpaqueWithColor(activity, new Color(android.graphics.Color.WHITE))
                .thenAccept(
                        material -> cuboidMediumFace = ShapeFactory.makeCube(new Vector3(0.16f, 0.001f, 0.5f), new Vector3(0.08f, 0.0f, 0.0f), material));

        MaterialFactory.makeOpaqueWithColor(activity, new Color(android.graphics.Color.WHITE))
                .thenAccept(
                        material -> cuboid = ShapeFactory.makeCube(new Vector3(0.5f, 0.16f, 0.3f), new Vector3(0.08f, 0.08f, 0.0f), material));

    }

    public void startLesson(AnchorNode anchorNode) {
        this.anchorNode = anchorNode;

        CustomNode part1 = new CustomNode();
        part1.setParent(anchorNode);
        part1.setLocalPosition(new Vector3(0.0f, 0.2f, 0.0f));

        descriptiveInfoCard = viewRenderablesController.getDescriptiveInfoCard();
        part1.setRenderable(descriptiveInfoCard);

        viewRenderablesController.getInfoCardTitle().setText(R.string.cuboid);
        viewRenderablesController.getInfoCardDesc().setText(R.string.cuboid_desc);

        prompt.setText(R.string.tap_to_continue);
        prompt.setVisibility(View.VISIBLE);

        prompt.setOnClickListener(view -> part1(part1));
    }

    private void part1(CustomNode part1) {

        part1.setLocalPosition(new Vector3(0.0f, 0.5f, 0.0f));
        viewRenderablesController.getInfoCardDesc().setText(R.string.cuboid_desc_2);

        Node cubeNode = new Node();
        cubeNode.setParent(anchorNode);
        cubeNode.setRenderable(cuboid);

        prompt.setOnClickListener(view -> part2(part1, cubeNode));
    }

    private void part2(CustomNode part1, Node cubeNode) {

        viewRenderablesController.getInfoCardDesc().setText(R.string.cuboid_desc_3);

        prompt.setOnClickListener(view -> {
            cubeNode.setParent(null);
            part3(part1);
        });
    }

    private void part3(CustomNode node) {

        node.setParent(null);

        Node base = new Node();
        base.setParent(anchorNode);
        base.setLocalPosition(new Vector3(-0.2f, 0.0f, 0.0f));

        Node horizNode = new Node();
        horizNode.setParent(base);
        horizNode.setRenderable(cuboidLargeFace.makeCopy());

        Node horizNode1 = new Node();
        horizNode1.setParent(base);
        horizNode1.setRenderable(cuboidShortFace.makeCopy());
        horizNode1.setLocalRotation(Quaternion.axisAngle(new Vector3(0, 0, 1f), 180));

        Node horizNode2 = new Node();
        horizNode2.setParent(base);
        horizNode2.setRenderable(cuboidShortFace.makeCopy());
        horizNode2.setLocalPosition(new Vector3(0.5f, 0.0f, 0.0f));

        Node mediumFace = new Node();
        mediumFace.setParent(base);
        mediumFace.setLocalPosition(new Vector3(0.25f, 0.0f, -0.15f));
        mediumFace.setLocalRotation(Quaternion.axisAngle(new Vector3(0, 1f, 0), 90));

        Node face = new Node();
        face.setParent(mediumFace);
        face.setRenderable(cuboidMediumFace.makeCopy());

        Node mediumFace2 = new Node();
        mediumFace2.setParent(base);
        mediumFace2.setLocalPosition(new Vector3(0.25f, 0.0f, 0.15f));
        mediumFace2.setLocalRotation(Quaternion.axisAngle(new Vector3(0, 1f, 0), -90));

        Node face2 = new Node();
        face2.setParent(mediumFace2);
        face2.setRenderable(cuboidMediumFace.makeCopy());

        Node horizNode5 = new Node();
        horizNode5.setLocalPosition(new Vector3(0.16f, 0.0f, 0.0f));
        horizNode5.setParent(mediumFace2);
        horizNode5.setRenderable(cuboidLargeFace2.makeCopy());

        CustomNode viewNode = new CustomNode();
        viewNode.setParent(anchorNode);
        viewNode.setLocalPosition(new Vector3(0.0f, 0.5f, 0.0f));

        viewNode.setRenderable(descriptiveInfoCard);

        viewRenderablesController.getInfoCardTitle().setText(R.string.cuboid_dev);
        viewRenderablesController.getInfoCardDesc().setText(R.string.cuboid_dev_desc);

        prompt.setOnClickListener(view -> {
            viewNode.setParent(null);
            part3(horizNode1, horizNode2, face, face2, horizNode5);
        });

    }

    private void part3(Node horizNode1, Node horizNode2, Node face, Node face2, Node horizNode5) {
        CustomNode viewNode = new CustomNode();
        viewNode.setParent(anchorNode);
        viewNode.setLocalPosition(new Vector3(0.0f, 0.5f, 0.0f));

        viewNode.setRenderable(descriptiveInfoCard);

        viewRenderablesController.getInfoCardDesc().setText(R.string.cuboid_dev_desc_2);

        prompt.setOnClickListener(view -> {
            viewNode.setParent(null);
            part4(horizNode1, horizNode2, face, face2, horizNode5);
        });
    }

    private void part4(Node horizNode1, Node horizNode2, Node face, Node face2, Node horizNode5) {

        CustomNode planeController = new CustomNode();
        planeController.setParent(anchorNode);
        ViewRenderable seekBarController = viewRenderablesController.getSeekBarController();
        planeController.setRenderable(seekBarController);
        planeController.setLocalPosition(new Vector3(0.0f, 0.6f, 0.0f));

        viewRenderablesController.getView1().setText(R.string.first_fold);
        viewRenderablesController.getView2().setText(R.string.second_fold);
        getDevSeekBar(horizNode1, horizNode2, face, face2, horizNode5, 0.16f);

        prompt.setOnClickListener(view -> new SweetAlertDialog(activity, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText(activity.getString(R.string.lesson_complete))
                .setContentText(activity.getString(R.string.lesson_complete_desc))
                .setConfirmClickListener(sweetAlertDialog -> {
                    activity.onLessonCompleted();
                    activity.finish();
                })
                .show());

    }

    private void getDevSeekBar(Node face1, Node face2, Node face3, Node face4, Node face5, float radius) {
        viewRenderablesController.getView1().setText(R.string.cuboid_dev);
        viewRenderablesController.getSeekBar1().setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        float ratio = (float) progress / (float) seekBar.getMax();
                        face1.setLocalRotation(Quaternion.axisAngle(new Vector3(0, 0, 1f), ((1 - ratio) * 90) + 90));
                        face2.setLocalRotation(Quaternion.axisAngle(new Vector3(0, 0, 1f), 90 - ((1 - ratio) * 90)));
                        face3.setLocalRotation(Quaternion.axisAngle(new Vector3(0, 0, 1f), 90 - ((1 - ratio) * 90)));
                        face4.setLocalRotation(Quaternion.axisAngle(new Vector3(0, 0, 1f), 90 - ((1 - ratio) * 90)));

                        double d = ratio * 90;
                        double radians = Math.toRadians(d);
                        double cos = Math.cos(radians);

                        float x = (float) cos * radius;

                        double tan = Math.tan(radians);
                        float y = (float) (x * tan);

                        float face5Rotation = (90 - ((1 - ratio) * 90)) + 90 - ((1 - ratio) * 90);

                        face5.setLocalPosition(new Vector3(x, y, 0f));
                        face5.setLocalRotation(Quaternion.axisAngle(new Vector3(0, 0, 1f), face5Rotation));
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {}

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {}
                });

        viewRenderablesController.getSeekBar2().setVisibility(View.GONE);
        viewRenderablesController.getView2().setVisibility(View.GONE);
    }
}
