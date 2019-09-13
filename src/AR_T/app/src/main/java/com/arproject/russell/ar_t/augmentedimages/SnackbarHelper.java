package com.arproject.russell.ar_t.augmentedimages;

import android.app.Activity;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.widget.TextView;

public final class SnackbarHelper {
    private static final int BACKGROUND_COLOR = 0xbf323232;
    private Snackbar messageSnackbar;
    private enum DismissBehavior { HIDE, SHOW, FINISH }
    private int maxLines = 2;

    public void showMessage(Activity activity, String message) {
        show(activity, message, DismissBehavior.HIDE);
    }

    public void showError(Activity activity, String errorMessage) {
        show(activity, errorMessage, DismissBehavior.FINISH);
    }

    public void showMessageWithDismiss(Activity activity, String message) {
        show(activity, message, DismissBehavior.SHOW);
    }

    public void hide(Activity activity) {
        activity.runOnUiThread(
                () -> {
                    if (messageSnackbar != null) {
                        messageSnackbar.dismiss();
                    }
                    messageSnackbar = null;
                });
    }

    private void show(
            final Activity activity, final String message, final DismissBehavior dismissBehavior) {
        activity.runOnUiThread(
                new Runnable() {
                    @Override
                    public void run() {
                        messageSnackbar = Snackbar.make(activity.findViewById(android.R.id.content), message, Snackbar.LENGTH_INDEFINITE);
                        messageSnackbar.getView().setBackgroundColor(BACKGROUND_COLOR);
                        if (dismissBehavior != DismissBehavior.HIDE) {
                            messageSnackbar.setAction("Dismiss", v -> messageSnackbar.dismiss());
                            if (dismissBehavior == DismissBehavior.FINISH) {
                                messageSnackbar.addCallback(new BaseTransientBottomBar.BaseCallback<Snackbar>() {
                                    @Override
                                    public void onDismissed(Snackbar transientBottomBar, int event) {
                                        super.onDismissed(transientBottomBar, event);
                                        activity.finish();
                                    }
                                });
                            }
                        }
                        ((TextView) messageSnackbar.getView().findViewById(android.support.design.R.id.snackbar_text)).setMaxLines(maxLines);messageSnackbar.show();
                    }
                });
    }
}
