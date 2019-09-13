package com.arproject.russell.ar_t.models;

import com.arproject.russell.ar_t.quizzes.QuizActivity;

import java.util.ArrayList;

public class QuizQuestions {

    private ArrayList<QuizQuestion> QUIZ_QUESTIONS = new ArrayList<>();
    private ModelRenderables quizModelRenderables;
    private int position;

    public QuizQuestions(QuizActivity activity, int position) {
        quizModelRenderables = new ModelRenderables(activity);
        this.position = position;
    }

    public void initialiseQuizItems() {
        if (position == 1) {
            addItem(new QuizQuestion("A plane is a ________", "Flat 2-D surface", "3-D solid", null, 1, quizModelRenderables.getHorizontalPlane()));
            addItem(new QuizQuestion("What type of plane is this?", "Horizontal", "Vertical", "Intersecting", 1, quizModelRenderables.getHorizontalPlane()));
            addItem(new QuizQuestion("What type of plane is this?", "Horizontal", "Vertical", "Intersecting", 2, quizModelRenderables.getVerticalPlane()));
            addItem(new QuizQuestion("What type of plane is this?", "Parallel", "Vertical", "Intersecting", 3, quizModelRenderables.getIntersectingPlanes()));
            addItem(new QuizQuestion("What type of plane is this?", "Horizontal", "Parallel", "Intersecting", 2, quizModelRenderables.getParallelPlanes()));
        } else if (position == 2) {
            addItem(new QuizQuestion("What type of angle is this?", "Acute", "Obtuse", "Reflex", 1, quizModelRenderables.getPlaneAngleModel(45)));
            addItem(new QuizQuestion("What type of angle is this?", "Acute", "Obtuse", "Reflex", 2, quizModelRenderables.getPlaneAngleModel(135)));
            addItem(new QuizQuestion("What type of angle is this?", "Acute", "Obtuse", "Reflex", 3, quizModelRenderables.getPlaneAngleModel(215)));
            addItem(new QuizQuestion("What type of angle is this?", "Acute", "Obtuse", "Right Angle", 3, quizModelRenderables.getPlaneAngleModel(90)));
            addItem(new QuizQuestion("What type of angle is this?", "Straight Angle", "Obtuse", "Reflex", 1, quizModelRenderables.getPlaneAngleModel(180)));
        } else if (position == 3) {
            addItem(new QuizQuestion("What type of geometric solid is this?", "Polyhedra Solid", "Non-Polyhedra Solid", null, 2, quizModelRenderables.getCone()));
            addItem(new QuizQuestion("What type of geometric solid is this?", "Polyhedra Solid", "Non-Polyhedra Solid", null, 2, quizModelRenderables.getSphere()));
            addItem(new QuizQuestion("What type of geometric solid is this?", "Polyhedra Solid", "Non-Polyhedra Solid", null, 1, quizModelRenderables.getCube()));
            addItem(new QuizQuestion("What type of geometric solid is this?", "Polyhedra Solid", "Non-Polyhedra Solid", null, 2, quizModelRenderables.getCylinder()));
            addItem(new QuizQuestion("What type of geometric solid is this?", "Polyhedra Solid", "Non-Polyhedra Solid", null, 1, quizModelRenderables.getPyramid()));
        }
    }

    private void addItem(QuizQuestion item) {
        QUIZ_QUESTIONS.add(item);
    }

    public int getQuizQuestionsSize() {
        return QUIZ_QUESTIONS.size();
    }

    public QuizQuestion getQuizQuestion(int mQuestionNumber) {
        return QUIZ_QUESTIONS.get(mQuestionNumber);
    }
}
