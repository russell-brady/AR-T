package com.arproject.russell.ar_t.guided_tour;

import android.app.Activity;
import android.os.Handler;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.widget.Toolbar;

import com.arproject.russell.ar_t.R;
import com.arproject.russell.ar_t.utils.PrefConfig;

import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt;
import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetSequence;

public class TapTargetPrompts {

    private Activity activity;
    private PrefConfig prefConfig;

    public TapTargetPrompts(Activity activity) {
        this.activity = activity;
        prefConfig = new PrefConfig(activity);
    }

    public void showSequence(Toolbar toolbar) {
        if (prefConfig.showMainSequence()){

            Handler handler = new Handler();
            handler.postDelayed(() -> new MaterialTapTargetSequence()
                    .addPrompt(new MaterialTapTargetPrompt.Builder(activity)
                            .setPrimaryText("Navigation Menu")
                            .setSecondaryText("Tap the menu icon to access content, change settings & more")
                            .setFocalPadding(R.dimen.dp40)
                            .setAnimationInterpolator(new FastOutSlowInInterpolator())
                            .setMaxTextWidth(R.dimen.tap_target_menu_max_width)
                            .setIcon(R.drawable.ic_menu)
                            .setTarget(toolbar.getChildAt(1))
                            .create())
                    .addPrompt(new MaterialTapTargetPrompt.Builder(activity)
                            .setPrimaryText("More actions")
                            .setSecondaryText("Tap the 3 vertical dots to see more options")
                            .setFocalPadding(R.dimen.dp40)
                            .setAnimationInterpolator(new FastOutSlowInInterpolator())
                            .setMaxTextWidth(R.dimen.tap_target_menu_max_width)
                            .setIcon(R.drawable.ic_more_vert)
                            .setTarget(toolbar.getChildAt(2))
                            .create())
                    //.addPrompt()
                    .show(), 1000);
        }
        prefConfig.showSequence(false);
        prefConfig.setFirstRun(false);
    }


}
