package com.arproject.russell.ar_t.models;

import com.arproject.russell.ar_t.R;

import java.util.ArrayList;
import java.util.List;

public class Topics {

    private static final List<Topic> TOPICS = new ArrayList<>();
    private static final List<Topic> QUIZ_TOPICS = new ArrayList<>();

    static {
        addItem(new Topic("Intro to Planes", "Learn and visualise the fundamental building block of 3-D representation", R.drawable.parallel, 0, "In mathematics, a plane is a flat, two-dimensional surface that extends infinitely far. In this lesson you will get two visualise two fundamental planes - the horizontal and vertical planes. These are the two fundamental planes used in technical graphics for object projection and representation."));
        addItem(new Topic("Parallel Planes", "Learn about parallel planes and their relationship to eachother", R.drawable.parallel, 0, "Two planes that never meet are said to be parallel. This lesson aims to show you parallel planes and their relationship with eachother. "));
        addItem(new Topic("Intersecting Planes", "Learn about intersecting planes and their relationship to eachother", R.drawable.intersecting, 0, "Two planes that meet are said to be intersecting. Two planes that are not parallel always intersect in a line. This lesson will show the relationship between these intersecting planes and how intersecting pnanes always meet at a single line"));
        addItem(new Topic("Angle Between Planes", "Learn the different types of angles", R.drawable.planeangle, 0, "The aim of this lesson is to explain the different types and pairs of angles. This will be done by using the angle between intersecting planes as an example"));
        addItem(new Topic("Geometric Solids", "Learn about solid geometry and and what are geometric solids", R.drawable.shapes, 0, "This lesson will explain what is a geometric solid and the difference between polyhedra and non-polyhedra solids."));
        addItem(new Topic("Sections Intro", "Gain an understanding of what happens when a plane cuts a geometric solid", R.drawable.conic, 0, "This lesson is an introduction to what is a section and what happens when a plane cuts a geometric solid."));
        addItem(new Topic("Cylinder Sections", "Learn the different types of cylindrical sections", R.drawable.conic, 0, "The aim of this lesson is to explain the different types of cylindrical sections"));
        addItem(new Topic("Conic Sections", "Learn the different types of conic sections", R.drawable.conic, 0, "The aim of this lesson is to explain the 4 conic sections - Circle, Ellipse, Parabola and Hyperbola. You will see the type of cutting plane causes each section. "));
        addItem(new Topic("Cube Development", "See first-hand how a cube is made up of a number of 2-D sides", R.drawable.devs, 0, "A cube is an example of a 3-D object which can be opened or unfolded along its edges to create a flat shape. The unfolded shape is called the net of the solid."));
        addItem(new Topic("Cuboid Development", "See first-hand how 3-D objects are made up of a number of 2-D sides", R.drawable.devs, 0, "A cuboid is an example of a 3-D object which can be opened or unfolded along its edges to create a flat shape. The unfolded shape is called the net of the solid."));
        //addItem(new Topic("Orthographic Projection", "How 3-D objects are represented in drawing", R.drawable.orthographic, 0, ""));

        addQuizItem(new Topic("Planes Quiz", "Test your knowledge on the different types of planes and relationships between planes", 0, 0, ""));
        addQuizItem(new Topic("Angles Quiz", "Test your knowledge on angles between planes", 0, 0, ""));
        addQuizItem(new Topic("Solids Quiz", "Test your knowledge on different 3-D solids and the types of solids", 0, 0, ""));
    }

    private static void addItem(Topic item) {
        TOPICS.add(item);
    }

    private static void addQuizItem(Topic item) {
        QUIZ_TOPICS.add(item);
    }

    public static ArrayList<Topic> getItems() {
        return (ArrayList<Topic>) TOPICS;
    }

    public static ArrayList<Topic> getQuizItems() {
        return (ArrayList<Topic>) QUIZ_TOPICS;
    }
}
