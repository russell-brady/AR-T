package com.arproject.russell.ar_t.augmentedimages;

import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arproject.russell.ar_t.R;
import com.google.ar.core.AugmentedImageDatabase;
import com.google.ar.core.Config;
import com.google.ar.core.Session;
import com.google.ar.sceneform.ux.ArFragment;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class AugmentedImagesFragment extends ArFragment {

    private static final String TAG = "AugmentedImageFragment";

    private SnackbarHelper snackbarHelper = new SnackbarHelper();

    public enum CONTENT_TYPE {
        VIDEO,
        MODEL
    }

    public static Map<String, AugmentedImageItem> augmentedImageItemMap = new HashMap<String, AugmentedImageItem>(){{
        put("cone.jpg", new AugmentedImageItem("Cone", "This 3D model is a cone", CONTENT_TYPE.MODEL, R.raw.cone));
        put("hyper.jpg", new AugmentedImageItem("Hyperbola", "A hyperbola is created when a cone is cut by a plane",  CONTENT_TYPE.MODEL, R.raw.octahedron));
        put("conics.jpg", new AugmentedImageItem("Octohedron", "An octohedron is a 3D shape which has 10 sides. ",  CONTENT_TYPE.MODEL, R.raw.octahedron));
        put("parabola.jpg", new AugmentedImageItem("Intro Video", "Testing Video. ",  CONTENT_TYPE.VIDEO, R.raw.intro));
    }};

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        getPlaneDiscoveryController().hide();
        getPlaneDiscoveryController().setInstructionView(null);
        getArSceneView().getPlaneRenderer().setEnabled(false);
        return view;
    }

    @Override
    protected Config getSessionConfiguration(Session session) {
        Config config = super.getSessionConfiguration(session);
        if (!setupAugmentedImageDatabase(config, session)) {
            snackbarHelper.showError(getActivity(), "Could not setup augmented image database");
        }
        return config;
    }

    private boolean setupAugmentedImageDatabase(Config config, Session session) {
        AugmentedImageDatabase augmentedImageDatabase;

        AssetManager assetManager = getContext() != null ? getContext().getAssets() : null;
        if (assetManager == null) {
            Log.e(TAG, "Context is null, cannot initialize image database.");
            return false;
        }

        augmentedImageDatabase = new AugmentedImageDatabase(session);

        for (String item : augmentedImageItemMap.keySet()) {
            Bitmap augmentedImageBitmap = loadAugmentedImage(item);
            if (augmentedImageBitmap == null) {
                return false;
            }
            augmentedImageDatabase.addImage(item,augmentedImageBitmap);
        }

        config.setAugmentedImageDatabase(augmentedImageDatabase);
        config.setUpdateMode(Config.UpdateMode.LATEST_CAMERA_IMAGE);
        return true;
    }

    private Bitmap loadAugmentedImage(String fileName) {
        try (InputStream is = getActivity().getAssets().open(fileName)) {
            return BitmapFactory.decodeStream(is);
        } catch (IOException e) {
            Log.e(TAG, "IO exception loading augmented image bitmap.", e);
        }
        return null;
    }
}
