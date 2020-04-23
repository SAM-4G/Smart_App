package com.sam.kadarairkopi;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.sam.kadarairkopi.Preference.SharedData;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    CircleImageView circleImageView, circleImageView2;
    TextView waterLevel, waterLevel2, weight, weight2, userName;
    CardView cardView, cardLabel1, cardLabel2, cardLabel3;
    Button refreshButton;

    @Override
    protected void onStart() {
        super.onStart();
        if (!SharedData.getInstance(this).getSaveLoggedIn()) {
            finish();
            SharedData.getInstance(getApplicationContext()).logout();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        circleImageView = findViewById(R.id.circle);
        circleImageView2 = findViewById(R.id.circle2);

        waterLevel = findViewById(R.id.waterLevel);
        waterLevel2 = findViewById(R.id.waterLevelText);
        weight = findViewById(R.id.weightBeans);
        weight2 = findViewById(R.id.weightBeansText);
        userName = findViewById(R.id.userState);

        cardView = findViewById(R.id.cardIndicator1);
        cardLabel1 = findViewById(R.id.cardLabel1);
        cardLabel2 = findViewById(R.id.cardLabel2);
        cardLabel3 = findViewById(R.id.cardLabel3);

        refreshButton = findViewById(R.id.buttonRefresh);

        circleImageView.getBackground().setAlpha(225);
        circleImageView2.getBackground().setAlpha(225);
        cardView.getBackground().setAlpha(225);
        waterLevel2.getBackground().setAlpha(75);
        weight2.getBackground().setAlpha(75);
        cardLabel1.getBackground().setAlpha(225);
        cardLabel2.getBackground().setAlpha(225);
        cardLabel3.getBackground().setAlpha(225);
        userName.getBackground().setAlpha(225);


        setUser();
    }

    public void setUser() {
        String logUser = SharedData.getInstance(this).LoggedInUser();
        userName.setText(String.format("User : %s", logUser));
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder ab = new AlertDialog.Builder(MainActivity.this);
        ab.setTitle("Logout or Exit").setIcon(R.drawable.user_icon);
        ab.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
                SharedData.getInstance(getApplicationContext()).logout();
            }
        });
        ab.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
                MainActivity.this.finish();
            }
        });
        ab.show();
    }

}