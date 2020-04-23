package com.sam.kadarairkopi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;

public class SplashScreen extends AppCompatActivity {

    ProgressBar progressBar;
    CardView cardMiddle, cardInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        progressBar = findViewById(R.id.progressSplash);
        cardMiddle = findViewById(R.id.cardMiddle);
        cardInput = findViewById(R.id.cardInputSplash);

        cardMiddle.getBackground().setAlpha(125);
        cardInput.getBackground().setAlpha(125);

        progressBar.setVisibility(View.INVISIBLE);


        PlayScreen();
    }

    public void PlayScreen() {
        ProgressBar();
        Thread();
    }

    public void Thread() {

        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2300);
                    Login();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    public void Login() {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }

    public void ProgressBar() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                super.run();
                progressBar.setVisibility(View.VISIBLE);
                for (int i = 0; i <= 100; ) {
                    try {
                        sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    progressBar.setProgress(i);
                    i = i + 1;
                }
            }
        };
        thread.start();
    }
}
