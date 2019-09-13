package com.arproject.russell.ar_t.ar_lessons;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import com.arproject.russell.ar_t.R;
import com.google.ar.sceneform.rendering.ViewRenderable;
import com.skumar.flexibleciruclarseekbar.CircularSeekBar;

public class ViewRenderablesController {

    private ViewRenderable angleController;
    private ViewRenderable seekBarController;
    private ViewRenderable infoCard;
    private ViewRenderable descriptiveInfoCard;

    private SeekBar seekBar1;
    private SeekBar seekBar2;
    private SeekBar seekBar3;
    private TextView view1;
    private TextView view2;
    private TextView view3;

    private CircularSeekBar circularSeekBar;

    private TextView infoCardTitle;
    private TextView infoCardDesc;

    public ViewRenderablesController(AppCompatActivity activity) {

        ViewRenderable.builder().setView(activity, R.layout.controller).build().thenAccept(
                (renderable) -> {
                    this.seekBarController = renderable;
                    View seekBarControllerView = seekBarController.getView();
                    view1 = seekBarControllerView.findViewById(R.id.header1);
                    view2 = seekBarControllerView.findViewById(R.id.header2);
                    view3 = seekBarControllerView.findViewById(R.id.header3);
                    seekBar1 = seekBarControllerView.findViewById(R.id.seekbar1);
                    seekBar2 = seekBarControllerView.findViewById(R.id.seekbar2);
                    seekBar3 = seekBarControllerView.findViewById(R.id.seekbar3);
                })
                .exceptionally(
                        (throwable) -> {
                            throw new AssertionError("Could not load plane card view.", throwable);
                        });

        ViewRenderable.builder().setView(activity, R.layout.info_card).build().thenAccept(
                (renderable) -> this.infoCard = renderable)
                .exceptionally(
                        (throwable) -> {
                            throw new AssertionError("Could not load plane card view.", throwable);
                        });

        ViewRenderable.builder().setView(activity, R.layout.augmented_image_view).build().thenAccept(
                (renderable) -> {
                    this.descriptiveInfoCard = renderable;
                    View info = descriptiveInfoCard.getView();
                    infoCardTitle = info.findViewById(R.id.viewTitle);
                    infoCardDesc = info.findViewById(R.id.viewDesc);
                })
                .exceptionally(
                        (throwable) -> {
                            throw new AssertionError("Could not load plane card view.", throwable);
                        });

        ViewRenderable.builder().setView(activity, R.layout.angle_controller).build().thenAccept(
                (renderable) -> {
                    this.angleController = renderable;
                    View view = angleController.getView();
                    circularSeekBar = view.findViewById(R.id.mCircularSeekBar);
                })
                .exceptionally(
                        (throwable) -> {
                            throw new AssertionError("Could not load plane card view.", throwable);
                        });


    }

    public ViewRenderable getSeekBarController() {
        return seekBarController;
    }

    public ViewRenderable getInfoCard() {
        return infoCard;
    }

    public ViewRenderable getDescriptiveInfoCard() {
        return descriptiveInfoCard;
    }

    public SeekBar getSeekBar1() {
        return seekBar1;
    }

    public SeekBar getSeekBar2() {
        return seekBar2;
    }

    public SeekBar getSeekBar3() {
        return seekBar3;
    }

    public TextView getView1() {
        return view1;
    }

    public TextView getView2() {
        return view2;
    }

    public TextView getView3() {
        return view3;
    }

    public TextView getInfoCardTitle() {
        return infoCardTitle;
    }

    public TextView getInfoCardDesc() {
        return infoCardDesc;
    }

    public ViewRenderable getAngleController() {
        return angleController;
    }

    public CircularSeekBar getCircularSeekBar() {
        return circularSeekBar;
    }
}
