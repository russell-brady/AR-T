package com.arproject.russell.ar_t.models;

import android.app.Activity;
import com.arproject.russell.ar_t.R;
import com.google.ar.sceneform.Node;
import com.google.ar.sceneform.math.Quaternion;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.Color;
import com.google.ar.sceneform.rendering.MaterialFactory;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.ShapeFactory;

public class ModelRenderables {

    private ModelRenderable plane1;
    private ModelRenderable plane2;

    private ModelRenderable vertPlane;
    private ModelRenderable horizPlane;


    private ModelRenderable cube;
    private ModelRenderable pyramid;
    private ModelRenderable cone;
    private ModelRenderable sphere;
    private ModelRenderable cylinder;

    public ModelRenderables(Activity activity) {

        MaterialFactory.makeOpaqueWithColor(activity, new Color(android.graphics.Color.RED))
                .thenAccept(
                        material -> plane1 = ShapeFactory.makeCube(new Vector3(0.4f, 0.001f, 0.4f), new Vector3(0.2f, 0.0f, 0.0f), material));

        MaterialFactory.makeOpaqueWithColor(activity, new Color(android.graphics.Color.BLUE))
                .thenAccept(
                        material -> plane2 = ShapeFactory.makeCube(new Vector3(0.4f, 0.001f, 0.4f), new Vector3(0.2f, 0.0f, 0.0f), material));

        MaterialFactory.makeOpaqueWithColor(activity, new Color(android.graphics.Color.RED))
                .thenAccept(
                        material -> horizPlane = ShapeFactory.makeCube(new Vector3(0.8f, 0.001f, 0.8f), new Vector3(0.0f, 0.0f, 0.0f), material));

        MaterialFactory.makeOpaqueWithColor(activity, new Color(android.graphics.Color.BLUE))
                .thenAccept(
                        material -> vertPlane = ShapeFactory.makeCube(new Vector3(0.6f, 0.4f, 0.001f), new Vector3(0.0f, 0.0f, 0.0f), material));

        MaterialFactory.makeOpaqueWithColor(activity, new Color(android.graphics.Color.BLUE))
                .thenAccept(
                        material -> cube = ShapeFactory.makeCube(new Vector3(0.3f, 0.3f, 0.3f), new Vector3(0.0f, 0.15f, 0.0f), material));


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
                .setSource(activity, R.raw.color_pyramid)
                .build()
                .thenAccept(renderable -> this.pyramid = renderable);

    }

    public Node getPlaneAngleModel(float angle) {

        Node base = new Node();
        base.setLocalPosition(new Vector3(-0.2f, 0.2f, 0.0f));

        Node horizNode = new Node();
        horizNode.setParent(base);
        horizNode.setRenderable(plane1.makeCopy());

        Node horizNode1 = new Node();
        horizNode1.setParent(base);
        horizNode1.setRenderable(plane2.makeCopy());
        horizNode1.setLocalRotation(Quaternion.axisAngle(new Vector3(0, 0, 1f), angle));

        return base;
    }

    public Node getVerticalPlane() {
        Node base = new Node();

        Node vertNode = new Node();
        vertNode.setParent(base);
        vertNode.setLocalPosition(new Vector3(0.0f, 0.2f, 0.0f));
        vertNode.setRenderable(vertPlane);

        return base;
    }

    public Node getHorizontalPlane() {
        Node base = new Node();

        Node horizNode = new Node();
        horizNode.setParent(base);
        horizNode.setRenderable(horizPlane);

        return base;
    }

    public Node getParallelPlanes() {
        Node base = new Node();

        Node horizNode1 = new Node();
        horizNode1.setParent(base);
        horizNode1.setRenderable(horizPlane.makeCopy());
        horizNode1.setLocalPosition(new Vector3(0.0f, 0.3f, 0.0f));

        Node horizNode2 = new Node();
        horizNode2.setParent(base);
        horizNode2.setRenderable(horizPlane.makeCopy());
        horizNode2.setLocalPosition(new Vector3(0.0f, 0.1f, 0.0f));

        return base;
    }

    public Node getIntersectingPlanes() {
        Node base = new Node();

        Node horizNode = new Node();
        horizNode.setParent(base);
        horizNode.setRenderable(horizPlane);
        horizNode.setLocalPosition(new Vector3(0.0f, 0.2f, 0.0f));

        Node vertNode = new Node();
        vertNode.setParent(base);
        vertNode.setRenderable(vertPlane);
        vertNode.setLocalPosition(new Vector3(0.0f, 0.2f, 0.0f));

        return base;
    }


    public Node getCone() {
        Node base = new Node();

        Node node = new Node();
        node.setParent(base);
        node.setRenderable(cone);
        node.setLocalScale(new Vector3(0.3f, 0.3f, 0.3f));

        return base;
    }

    public Node getSphere() {
        Node base = new Node();

        Node node = new Node();
        node.setParent(base);
        node.setRenderable(sphere);

        return base;
    }

    public Node getCube() {
        Node base = new Node();

        Node node = new Node();
        node.setParent(base);
        node.setRenderable(cube);

        return base;
    }

    public Node getCylinder() {
        Node base = new Node();

        Node node = new Node();
        node.setParent(base);
        node.setRenderable(cylinder);

        return base;
    }

    public Node getPyramid() {
        Node base = new Node();

        Node rotatedNode = new Node();
        rotatedNode.setParent(base);
        rotatedNode.setRenderable(pyramid);
        rotatedNode.setLocalScale(new Vector3(0.3f, 0.3f, 0.3f));
        rotatedNode.setLocalPosition(new Vector3(0.0f, 0.15f, 0.0f));
        rotatedNode.setLocalRotation(Quaternion.axisAngle(new Vector3(1f, 0f, 0f), -90));

        return base;
    }
}
