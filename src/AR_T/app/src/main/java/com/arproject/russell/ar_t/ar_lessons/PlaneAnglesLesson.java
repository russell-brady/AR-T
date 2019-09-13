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

public class PlaneAnglesLesson {

    private ArLessonActivity activity;
    private ViewRenderablesController viewRenderablesController;
    private AnchorNode anchorNode;

    private TextView prompt;

    private ModelRenderable plane1;
    private ModelRenderable plane2;

    public PlaneAnglesLesson(ArLessonActivity activity) {
        this.activity = activity;
        this.viewRenderablesController = new ViewRenderablesController(activity);
        this.prompt = activity.findViewById(R.id.find_surface);

        MaterialFactory.makeOpaqueWithColor(activity, new Color(android.graphics.Color.RED))
                .thenAccept(
                        material -> plane1 = ShapeFactory.makeCube(new Vector3(0.4f, 0.001f, 0.4f), new Vector3(0.2f, 0.0f, 0.0f), material));

        MaterialFactory.makeOpaqueWithColor(activity, new Color(android.graphics.Color.BLUE))
                .thenAccept(
                        material -> plane2 = ShapeFactory.makeCube(new Vector3(0.4f, 0.001f, 0.4f), new Vector3(0.2f, 0.0f, 0.0f), material));

    }

    public void startLesson(AnchorNode anchorNode) {
        this.anchorNode = anchorNode;

        CustomNode part1 = new CustomNode();
        part1.setParent(anchorNode);
        part1.setLocalPosition(new Vector3(0.0f, 0.3f, 0.0f));

        ViewRenderable descriptiveInfoCard = viewRenderablesController.getDescriptiveInfoCard();
        part1.setRenderable(descriptiveInfoCard);

        viewRenderablesController.getInfoCardTitle().setText(R.string.angle_between_planes);
        viewRenderablesController.getInfoCardDesc().setText(R.string.angle_between_planes_desc);

        prompt.setText(R.string.tap_to_continue);
        prompt.setVisibility(View.VISIBLE);

        prompt.setOnClickListener(view -> part2(part1));
    }

    private void part2(CustomNode part1) {

        part1.setLocalPosition(new Vector3(0.0f, 0.6f, 0.0f));

        Node base = new Node();
        base.setParent(anchorNode);
        base.setLocalPosition(new Vector3(-0.2f, 0.2f, 0.0f));

        Node horizNode = new Node();
        horizNode.setParent(base);
        horizNode.setRenderable(plane1.makeCopy());

        Node horizNode1 = new Node();
        horizNode1.setParent(base);
        horizNode1.setRenderable(plane2.makeCopy());
        horizNode1.setLocalRotation(Quaternion.axisAngle(new Vector3(0, 0, 1f), 45));

        viewRenderablesController.getInfoCardTitle().setText(R.string.acute_angle);
        viewRenderablesController.getInfoCardDesc().setText(R.string.acute_angle_desc);

        prompt.setOnClickListener(view -> part3(part1, horizNode1));

    }

    private void part3(CustomNode part1, Node horizNode1) {

        horizNode1.setLocalRotation(Quaternion.axisAngle(new Vector3(0, 0, 1f), 90));

        viewRenderablesController.getInfoCardTitle().setText(R.string.right_angle);
        viewRenderablesController.getInfoCardDesc().setText(R.string.right_angle_desc);

        prompt.setOnClickListener(view -> part4(part1, horizNode1));
    }

    private void part4(CustomNode part1, Node horizNode1) {
        horizNode1.setLocalRotation(Quaternion.axisAngle(new Vector3(0, 0, 1f), 120));

        viewRenderablesController.getInfoCardTitle().setText(R.string.obtuse_angle);
        viewRenderablesController.getInfoCardDesc().setText(R.string.obtuse_angle_desc);

        prompt.setOnClickListener(view -> part5(part1, horizNode1));
    }

    private void part5(CustomNode part1, Node horizNode1) {
        horizNode1.setLocalRotation(Quaternion.axisAngle(new Vector3(0, 0, 1f), 225));

        viewRenderablesController.getInfoCardTitle().setText(R.string.reflex_angle);
        viewRenderablesController.getInfoCardDesc().setText(R.string.reflex_angle_desc);

        prompt.setOnClickListener(view -> {
            part1.setParent(null);
            getPlaneAngle(horizNode1);
        });
    }

    private void getPlaneAngle(Node horizNode1) {
        Node base = new Node();
        base.setParent(anchorNode);

        CustomNode planeController = new CustomNode();
        planeController.setParent(base);
        planeController.setRenderable(viewRenderablesController.getSeekBarController());
        planeController.setLocalPosition(new Vector3(0.0f, 0.7f, 0.0f));

        getDevSeekBar(horizNode1);

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
        viewRenderablesController.getView1().setText(R.string.control_angle);
        viewRenderablesController.getSeekBar1().setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        float ratio = (float) progress / (float) seekBar.getMax();
                        float degrees = ratio * 360;
                        horiz.setLocalRotation(Quaternion.axisAngle(new Vector3(0, 0, 1f), degrees));
                        if (degrees < 90) {
                            String acute = activity.getString(R.string.acute_angle) + ": " + degrees + "°";
                            viewRenderablesController.getView2().setText(acute);
                        } else if (degrees == 90) {
                            String right = activity.getString(R.string.right_angle) + ": " + degrees + "°";
                            viewRenderablesController.getView2().setText(right);
                        } else if (degrees > 90 && degrees < 180) {
                            String obtuse = activity.getString(R.string.obtuse_angle) + ": " + degrees + "°";
                            viewRenderablesController.getView2().setText(obtuse);
                        } else if (degrees == 180) {
                            String straight = activity.getString(R.string.straight_angle) + ": " + degrees + "°";
                            viewRenderablesController.getView2().setText(straight);
                        } else {
                            String reflex = activity.getString(R.string.reflex_angle) + ": " + degrees + "°";
                            viewRenderablesController.getView2().setText(reflex);
                        }
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {}

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {}
                });
        viewRenderablesController.getSeekBar2().setVisibility(View.GONE);
    }


}
