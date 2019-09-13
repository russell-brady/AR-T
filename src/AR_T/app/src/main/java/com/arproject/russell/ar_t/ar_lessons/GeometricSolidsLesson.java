package com.arproject.russell.ar_t.ar_lessons;

import android.view.View;
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

public class GeometricSolidsLesson {

    private ArLessonActivity activity;
    private ViewRenderablesController viewRenderablesController;
    private AnchorNode anchorNode;

    private TextView prompt;

    private ModelRenderable cube;
    private ModelRenderable cuboid;
    private ModelRenderable octahedron;
    private ModelRenderable pyramid;
    private ModelRenderable cone;
    private ModelRenderable sphere;
    private ModelRenderable cylinder;

    private ViewRenderable info1;
    private ViewRenderable info2;
    private ViewRenderable info3;
    private ViewRenderable info4;

    public GeometricSolidsLesson(ArLessonActivity activity) {
        this.activity = activity;
        this.viewRenderablesController = new ViewRenderablesController(activity);
        this.prompt = activity.findViewById(R.id.find_surface);

        MaterialFactory.makeOpaqueWithColor(activity, new Color(android.graphics.Color.BLUE))
                .thenAccept(
                        material -> cube = ShapeFactory.makeCube(new Vector3(0.3f, 0.3f, 0.3f), new Vector3(0.0f, 0.15f, 0.0f), material));

        MaterialFactory.makeOpaqueWithColor(activity, new Color(android.graphics.Color.RED))
                .thenAccept(
                        material -> cuboid = ShapeFactory.makeCube(new Vector3(0.3f, 0.2f, 0.3f), new Vector3(0.0f, 0.1f, 0.0f), material));

        MaterialFactory.makeOpaqueWithColor(activity, new Color(android.graphics.Color.RED))
                .thenAccept(
                        material -> cylinder = ShapeFactory.makeCylinder(0.1f, 0.3f, new Vector3(0.0f, 0.15f, 0.0f), material));

        MaterialFactory.makeOpaqueWithColor(activity, new Color(android.graphics.Color.RED))
                .thenAccept(
                        material -> sphere = ShapeFactory.makeSphere(0.15f, new Vector3(0.0f, 0.15f, 0.0f), material));

        ModelRenderable.builder()
                .setSource(activity, R.raw.cone)
                .build()
                .thenAccept(renderable -> this.cone = renderable);

        ModelRenderable.builder()
                .setSource(activity, R.raw.octahedron)
                .build()
                .thenAccept(renderable -> this.octahedron = renderable);

        ModelRenderable.builder()
                .setSource(activity, R.raw.color_pyramid)
                .build()
                .thenAccept(renderable -> this.pyramid = renderable);

        ViewRenderable.builder().setView(activity, R.layout.info_card).build().thenAccept(
                (renderable) -> this.info1 = renderable);

        ViewRenderable.builder().setView(activity, R.layout.info_card).build().thenAccept(
                (renderable) -> this.info2 = renderable);

        ViewRenderable.builder().setView(activity, R.layout.info_card).build().thenAccept(
                (renderable) -> this.info3 = renderable);

        ViewRenderable.builder().setView(activity, R.layout.info_card).build().thenAccept(
                (renderable) -> this.info4 = renderable);
    }

    public void startLesson(AnchorNode anchorNode) {
        this.anchorNode = anchorNode;

        CustomNode part1 = new CustomNode();
        part1.setParent(anchorNode);
        part1.setLocalPosition(new Vector3(0.0f, 0.2f, 0.0f));

        ViewRenderable descriptiveInfoCard = viewRenderablesController.getDescriptiveInfoCard();
        part1.setRenderable(descriptiveInfoCard);

        viewRenderablesController.getInfoCardTitle().setText(R.string.solid_geo);
        viewRenderablesController.getInfoCardDesc().setText(R.string.solid_geo_desc);

        prompt.setText(R.string.tap_to_continue);
        prompt.setVisibility(View.VISIBLE);

        prompt.setOnClickListener(view -> part2(part1));
    }

    private void part2(CustomNode part1) {

        viewRenderablesController.getInfoCardTitle().setText(R.string.geometric_solids);
        viewRenderablesController.getInfoCardDesc().setText(R.string.geometric_solids_desc);

        prompt.setOnClickListener(view -> part3(part1));
    }

    private void part3(CustomNode part1) {
        viewRenderablesController.getInfoCardTitle().setText(R.string.polyhedra_solids);
        viewRenderablesController.getInfoCardDesc().setText(R.string.polyhedra_desc);

        prompt.setOnClickListener(view -> part4(part1));
    }

    private void part4(CustomNode part1) {

        part1.setParent(null);

        Node base = new Node();
        base.setParent(anchorNode);

        Node cubeNode = new Node();
        cubeNode.setParent(base);
        cubeNode.setRenderable(cube);
        cubeNode.setLocalPosition(new Vector3(0.2f, 0.0f, 0.2f));

        CustomNode cubeViewNode = new CustomNode();
        cubeViewNode.setParent(cubeNode);
        cubeViewNode.setRenderable(info1);
        cubeViewNode.setLocalPosition(new Vector3(0.0f, 0.4f, 0.0f));
        TextView textView = (TextView) info1.getView();
        textView.setText(R.string.cube);

        Node cuboidNode = new Node();
        cuboidNode.setParent(base);
        cuboidNode.setRenderable(cuboid);
        cuboidNode.setLocalPosition(new Vector3(-0.2f, 0.0f, -0.2f));

        CustomNode cuboidViewNode = new CustomNode();
        cuboidViewNode.setParent(cuboidNode);
        cuboidViewNode.setRenderable(info2);
        cuboidViewNode.setLocalPosition(new Vector3(0.0f, 0.4f, 0.0f));
        TextView textView1 = (TextView) info2.getView();
        textView1.setText(R.string.cuboid);

        Node octahedronNode = new Node();
        octahedronNode.setParent(base);
        octahedronNode.setRenderable(octahedron);
        octahedronNode.setLocalPosition(new Vector3(0.2f, 0.0f, -0.2f));
        octahedronNode.setLocalScale(new Vector3(0.3f, 0.3f, 0.3f));

        CustomNode octaViewNode = new CustomNode();
        octaViewNode.setParent(base);
        octaViewNode.setRenderable(info3);
        octaViewNode.setLocalPosition(new Vector3(0.2f, 0.4f, -0.2f));
        TextView textView2 = (TextView) info3.getView();
        textView2.setText(R.string.octahedron);

        Node pyramidNode = new Node();
        pyramidNode.setParent(base);
        pyramidNode.setLocalPosition(new Vector3(-0.2f, 0.0f, 0.35f));

        CustomNode pyramidViewNode = new CustomNode();
        pyramidViewNode.setParent(base);
        pyramidViewNode.setRenderable(info4);
        pyramidViewNode.setLocalPosition(new Vector3(-0.2f, 0.4f, 0.2f));
        TextView textView3 = (TextView) info4.getView();
        textView3.setText(R.string.pyramid);

        Node rotatedNode = new Node();
        rotatedNode.setParent(pyramidNode);
        rotatedNode.setRenderable(pyramid);
        rotatedNode.setLocalScale(new Vector3(0.3f, 0.3f, 0.3f));
        rotatedNode.setLocalPosition(new Vector3(0.0f, 0.15f, 0.0f));
        rotatedNode.setLocalRotation(Quaternion.axisAngle(new Vector3(1f, 0f, 0f), -90));

        prompt.setOnClickListener(view ->  {
            base.setParent(null);
            part5(part1);
        });
    }

    private void part5(CustomNode part1) {

        part1.setParent(anchorNode);

        viewRenderablesController.getInfoCardTitle().setText(R.string.non_polyhedra);
        viewRenderablesController.getInfoCardDesc().setText(R.string.non_polyhedra_desc);

        prompt.setOnClickListener(view -> part6(part1));
    }

    private void part6(CustomNode part1) {

        part1.setParent(null);

        Node base = new Node();
        base.setParent(anchorNode);

        Node coneNode = new Node();
        coneNode.setParent(base);
        coneNode.setRenderable(cone);
        coneNode.setLocalPosition(new Vector3(0.2f, 0.0f, 0.2f));
        coneNode.setLocalScale(new Vector3(0.3f, 0.3f, 0.3f));

        CustomNode coneViewNode = new CustomNode();
        coneViewNode.setParent(base);
        coneViewNode.setRenderable(info1);
        coneViewNode.setLocalPosition(new Vector3(0.2f, 0.4f, 0.2f));
        TextView textView = (TextView) info1.getView();
        textView.setText(R.string.cone);

        Node sphereNode = new Node();
        sphereNode.setParent(base);
        sphereNode.setRenderable(sphere);
        sphereNode.setLocalPosition(new Vector3(-0.2f, 0.0f, -0.2f));

        CustomNode sphereViewNode = new CustomNode();
        sphereViewNode.setParent(sphereNode);
        sphereViewNode.setRenderable(info2);
        sphereViewNode.setLocalPosition(new Vector3(0.0f, 0.4f, 0.0f));
        TextView textView1 = (TextView) info2.getView();
        textView1.setText(R.string.sphere);

        Node cylinderNode = new Node();
        cylinderNode.setParent(base);
        cylinderNode.setRenderable(cylinder);
        cylinderNode.setLocalPosition(new Vector3(0.2f, 0.0f, -0.2f));

        CustomNode cylinderViewNode = new CustomNode();
        cylinderViewNode.setParent(base);
        cylinderViewNode.setRenderable(info3);
        cylinderViewNode.setLocalPosition(new Vector3(0.2f, 0.4f, -0.2f));
        TextView textView2 = (TextView) info3.getView();
        textView2.setText(R.string.cylinder);

        prompt.setOnClickListener(view -> new SweetAlertDialog(activity, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText(activity.getString(R.string.lesson_complete))
                .setContentText(activity.getString(R.string.lesson_complete_desc))
                .setConfirmClickListener(sweetAlertDialog -> {
                    activity.onLessonCompleted();
                    activity.finish();
                })
                .show());
    }


}
