package com.arproject.russell.ar_t.guided_tour;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.arproject.russell.ar_t.R;
import com.arproject.russell.ar_t.main.MainActivity;
import com.arproject.russell.ar_t.utils.PrefConfig;
import com.github.paolorotolo.appintro.AppIntro2;
import com.github.paolorotolo.appintro.AppIntroFragment;
import com.github.paolorotolo.appintro.model.SliderPage;

public class GuidedTourActivity extends AppIntro2 {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        PrefConfig prefConfig = new PrefConfig(this);
        setTheme(prefConfig.getTheme());
        super.onCreate(savedInstanceState);

        SliderPage sliderPage1 = new SliderPage();
        sliderPage1.setTitle(getString(R.string.slider1title));
        sliderPage1.setDescription(getString(R.string.slider1desc));
        sliderPage1.setImageDrawable(R.drawable.arcore);
        sliderPage1.setBgColor(getResources().getColor(R.color.colorPrimaryDark));
        addSlide(AppIntroFragment.newInstance(sliderPage1));

        SliderPage sliderPage2 = new SliderPage();
        sliderPage2.setTitle(getString(R.string.slider2title));
        sliderPage2.setDescription(getString(R.string.slider2desc));
        sliderPage2.setImageDrawable(R.drawable.motion);
        sliderPage2.setBgColor(getResources().getColor(R.color.colorPrimaryDark));
        addSlide(AppIntroFragment.newInstance(sliderPage2));

        SliderPage sliderPage3 = new SliderPage();
        sliderPage3.setTitle(getString(R.string.slider3title));
        sliderPage3.setDescription(getString(R.string.slider3desdc));
        sliderPage3.setImageDrawable(R.drawable.anchors);
        sliderPage3.setBgColor(getResources().getColor(R.color.colorPrimaryDark));
        addSlide(AppIntroFragment.newInstance(sliderPage3));

        setFadeAnimation();
        showSkipButton(false);

    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
        super.onSlideChanged(oldFragment, newFragment);

    }

}
