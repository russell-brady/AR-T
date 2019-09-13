package com.arproject.russell.ar_t.augmentedimages;

import android.animation.ObjectAnimator;
import android.support.annotation.Nullable;
import android.view.animation.LinearInterpolator;

import com.google.ar.sceneform.FrameTime;
import com.google.ar.sceneform.Node;
import com.google.ar.sceneform.math.Quaternion;
import com.google.ar.sceneform.math.QuaternionEvaluator;
import com.google.ar.sceneform.math.Vector3;

public class RotatingNode extends Node {

  @Nullable
  private ObjectAnimator rotateAnimation = null;
    private float lastSpeedMultiplier = 0.0f;
  private float updatedSpeedMultiplier = 0.0f;
  private boolean objectAnimated = false;

    public RotatingNode(boolean clockwise) {
        boolean clockwise1 = clockwise;
  }

  @Override
  public void onUpdate(FrameTime frameTime) {
    super.onUpdate(frameTime);

    // Animation hasn't been set up.
    if (rotateAnimation == null) {
      return;
    }

    // Nothing has changed. Continue rotating at the same speed.
    if (lastSpeedMultiplier == updatedSpeedMultiplier) {
      return;
    }

    if (updatedSpeedMultiplier == 0.0f) {
      rotateAnimation.pause();
    } else {
      rotateAnimation.resume();

      float animatedFraction = rotateAnimation.getAnimatedFraction();
      rotateAnimation.setDuration(getAnimationDuration());
      rotateAnimation.setCurrentFraction(animatedFraction);
    }
    lastSpeedMultiplier = updatedSpeedMultiplier;
  }

  @Override
  public void onActivate() {
    startAnimation();
  }

  @Override
  public void onDeactivate() {
    stopAnimation();
  }

  private long getAnimationDuration() {
      float degreesPerSecond = 90.0f;
      return (long) (1000 * 360 / (degreesPerSecond * updatedSpeedMultiplier));
  }

  public void setUpdatedSpeedMultiplier(boolean animateObject) {
      if (animateObject) {
          updatedSpeedMultiplier = 1.0f;
      } else {
          updatedSpeedMultiplier = 0f;
      }
      objectAnimated = animateObject;
  }

  public boolean isObjectAnimated() {
      return objectAnimated;
  }

  private void startAnimation() {
    if (rotateAnimation != null) {
      return;
    }

    rotateAnimation = createAnimator(true, 0);
    rotateAnimation.setTarget(this);
    rotateAnimation.setDuration(getAnimationDuration());
    rotateAnimation.start();
  }

  private void stopAnimation() {
    if (rotateAnimation == null) {
      return;
    }
    rotateAnimation.cancel();
    rotateAnimation = null;
  }

  /** Returns an ObjectAnimator that makes this node rotate. */
  private static ObjectAnimator createAnimator(boolean clockwise, float axisTiltDeg) {
    // Node's setLocalRotation method accepts Quaternions as parameters.
    // First, set up orientations that will animate a circle.
    Quaternion[] orientations = new Quaternion[4];
    // Rotation to apply first, to tilt its axis.
    Quaternion baseOrientation = Quaternion.axisAngle(new Vector3(1.0f, 0f, 0.0f), axisTiltDeg);
    for (int i = 0; i < orientations.length; i++) {
      float angle = i * 360 / (orientations.length - 1);
      if (clockwise) {
        angle = 360 - angle;
      }
      Quaternion orientation = Quaternion.axisAngle(new Vector3(0.0f, 1.0f, 0.0f), angle);
      orientations[i] = Quaternion.multiply(baseOrientation, orientation);
    }

    ObjectAnimator orbitAnimation = new ObjectAnimator();
    // Cast to Object[] to make sure the varargs overload is called.
    orbitAnimation.setObjectValues((Object[]) orientations);

    // Next, give it the localRotation property.
    orbitAnimation.setPropertyName("localRotation");

    // Use Sceneform's QuaternionEvaluator.
    orbitAnimation.setEvaluator(new QuaternionEvaluator());

    //  Allow rotateAnimation to repeat forever
    orbitAnimation.setRepeatCount(ObjectAnimator.INFINITE);
    orbitAnimation.setRepeatMode(ObjectAnimator.RESTART);
    orbitAnimation.setInterpolator(new LinearInterpolator());
    orbitAnimation.setAutoCancel(true);

    return orbitAnimation;
  }
}
