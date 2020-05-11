package com.sam.kadarairkopi.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.DialogCompat;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.sam.kadarairkopi.preference.SharedData;
import com.sam.kadarairkopi.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    CircleImageView circleImageView, circleImageView2;
    TextView waterLevel, waterLevel2, weight, weight2, userName, note, weightVal, waterVal;
    CardView cardView, cardLabel1, cardLabel2, cardLabel3;
    Button resultButton;
    ImageView closePopUp, resultIcon;
    Dialog resultDialog;

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

        resultButton = findViewById(R.id.buttonRefresh);

        circleImageView.getBackground().setAlpha(225);
        circleImageView2.getBackground().setAlpha(225);
        cardView.getBackground().setAlpha(225);
        waterLevel2.getBackground().setAlpha(225);
        weight2.getBackground().setAlpha(225);
        cardLabel1.getBackground().setAlpha(225);
        cardLabel2.getBackground().setAlpha(225);
        cardLabel3.getBackground().setAlpha(225);
        userName.getBackground().setAlpha(225);

        setUser();

        resultButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resultDialog = new Dialog(MainActivity.this);
                showResult();
            }
        });
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
                MainActivity.this.finish();
                SharedData.getInstance(getApplicationContext()).logout();
            }
        });
        ab.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                MainActivity.this.finish();
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
            }
        });
        ab.show();
    }

    public void showResult() {
        resultDialog.setContentView(R.layout.pop_up_result);

        String resultValue;
        resultValue = "12";

        closePopUp = resultDialog.findViewById(R.id.closeTop);
        resultIcon = resultDialog.findViewById(R.id.resultIcon);
        note = resultDialog.findViewById(R.id.noteResult);
        weightVal = resultDialog.findViewById(R.id.weightValue);
        waterVal = resultDialog.findViewById(R.id.waterValue);

        if (waterVal.getText().equals(resultValue)) {
            resultIcon.setImageResource(R.drawable.ic_check_circle_black_24dp);
            note.setText(R.string.good_quality_coffee_beans);
        }
        if (!waterVal.getText().equals(resultValue)) {
            resultIcon.setImageResource(R.drawable.ic_error_outline_black_24dp);
            note.setText(R.string.bad_quality_coffee_beans);
        }

        closePopUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resultDialog.dismiss();
            }
        });

        resultDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        resultDialog.show();

    }

}