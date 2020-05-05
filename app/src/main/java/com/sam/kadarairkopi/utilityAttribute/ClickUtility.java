package com.sam.kadarairkopi.utilityAttribute;

import android.view.View;

public class ClickUtility {

    public static void clickSession(final View view) {
        view.setEnabled(false);

        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                view.setEnabled(true);
            }
        }, 3000);
    }

}
