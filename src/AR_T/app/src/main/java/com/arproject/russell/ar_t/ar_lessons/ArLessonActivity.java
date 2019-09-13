package com.arproject.russell.ar_t.ar_lessons;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.arproject.russell.ar_t.R;
import com.arproject.russell.ar_t.models.ApiResponse;
import com.arproject.russell.ar_t.utils.ApiInterface;
import com.arproject.russell.ar_t.utils.ApiUtils;
import com.arproject.russell.ar_t.utils.PrefConfig;
import com.google.ar.core.Anchor;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.ux.ArFragment;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArLessonActivity extends AppCompatActivity {

    private ArFragment arFragment;
    private AnchorNode anchorNode;
    private int route;
    private TextView findSurface;

    private ApiInterface apiInterface;
    private PrefConfig prefConfig;

    private PlanesIntroLesson planesIntroLesson;
    private CubeDevelopmentLesson cubeDevelopmentLesson;
    private CuboidDevelopmentLesson cuboidDevelopmentLesson;
    private PlaneAnglesLesson planeAnglesLesson;
    private PlanesParallelLesson planesParallelLesson;
    private PlanesIntersectingLesson planesIntersectingLesson;
    private GeometricSolidsLesson geometricSolidsLesson;
    private SectionsLesson sectionsLesson;
    private CylinderSectionsLesson cylinderSectionsLesson;
    private ConicSectionsLesson conicSectionsLesson;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        prefConfig = new PrefConfig(this);
        setTheme(prefConfig.getTheme());
        super.onCreate(savedInstanceState);

        apiInterface = ApiUtils.getApiService();

        setContentView(R.layout.ar_activity);
        arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.sceneform_fragment);
        arFragment.setOnTapArPlaneListener(this::onPlaneTap);

        route = getIntent().getIntExtra("route", -1);
        findSurface = findViewById(R.id.find_surface);

        planesIntroLesson = new PlanesIntroLesson(this);
        cubeDevelopmentLesson = new CubeDevelopmentLesson(this);
        cuboidDevelopmentLesson = new CuboidDevelopmentLesson(this);
        planeAnglesLesson = new PlaneAnglesLesson(this);
        planesParallelLesson = new PlanesParallelLesson(this);
        planesIntersectingLesson = new PlanesIntersectingLesson(this);
        geometricSolidsLesson = new GeometricSolidsLesson(this);
        sectionsLesson = new SectionsLesson(this);
        cylinderSectionsLesson = new CylinderSectionsLesson(this);
        conicSectionsLesson = new ConicSectionsLesson(this);
    }

    private void onPlaneTap(HitResult hitResult, Plane plane, MotionEvent motionEvent) {

        if (anchorNode == null) {
            findSurface.setVisibility(View.GONE);
            Anchor anchor = hitResult.createAnchor();
            anchorNode = new AnchorNode(anchor);
            anchorNode.setParent(arFragment.getArSceneView().getScene());

            switch (route) {
                case 1:
                    planesIntroLesson.startLesson(anchorNode);
                    break;
                case 2:
                    planesParallelLesson.startLesson(anchorNode);
                    break;
                case 3:
                    planesIntersectingLesson.startLesson(anchorNode);
                    break;
                case 4:
                    planeAnglesLesson.startLesson(anchorNode);
                    break;
                case 5:
                    geometricSolidsLesson.startLesson(anchorNode);
                    break;
                case 6:
                    sectionsLesson.startLesson(anchorNode);
                    break;
                case 7:
                    cylinderSectionsLesson.startLesson(anchorNode);
                    break;
                case 8:
                    conicSectionsLesson.startLesson(anchorNode);
                    break;
                case 9:
                    cubeDevelopmentLesson.startLesson(anchorNode);
                    break;
                case 10:
                    cuboidDevelopmentLesson.startLesson(anchorNode);
                    break;
            }
        }
    }

    public void onLessonCompleted() {
        apiInterface.setLessonsCompleted(prefConfig.getLoggedInUser().getId(), route).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(@NonNull Call<ApiResponse> call, @NonNull Response<ApiResponse> response) {}
            @Override
            public void onFailure(@NonNull Call<ApiResponse> call, @NonNull Throwable t) {}
        });
    }
}
