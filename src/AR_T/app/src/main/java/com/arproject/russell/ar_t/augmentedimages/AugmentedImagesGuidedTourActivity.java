package com.arproject.russell.ar_t.augmentedimages;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;

import com.arproject.russell.ar_t.R;
import com.arproject.russell.ar_t.utils.PrefConfig;
import com.github.paolorotolo.appintro.AppIntro2;
import com.github.paolorotolo.appintro.AppIntroFragment;
import com.github.paolorotolo.appintro.model.SliderPage;

public class AugmentedImagesGuidedTourActivity extends AppIntro2 {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        PrefConfig prefConfig = new PrefConfig(this);
        setTheme(prefConfig.getTheme());
        super.onCreate(savedInstanceState);

        SliderPage sliderPage1 = new SliderPage();
        sliderPage1.setTitle(getString(R.string.augmented_images));
        sliderPage1.setDescription(getString(R.string.augmented_images_desc));
        sliderPage1.setImageDrawable(R.drawable.arcore);

        TypedValue typedValue = new TypedValue();
        getTheme().resolveAttribute(R.attr.themeColorBackground, typedValue, true);
        int color = ContextCompat.getColor(this, typedValue.resourceId);

        sliderPage1.setBgColor(color);
        addSlide(AppIntroFragment.newInstance(sliderPage1));

        setFadeAnimation();
        showSkipButton(false);
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        Intent intent = new Intent(this, AugmentedImagesActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
