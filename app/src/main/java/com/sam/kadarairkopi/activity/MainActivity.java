package com.sam.kadarairkopi.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.DialogCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.sam.kadarairkopi.data.DataCore;
import com.sam.kadarairkopi.preference.SharedData;
import com.sam.kadarairkopi.R;
import com.sam.kadarairkopi.preference.VolleySing;
import com.sam.kadarairkopi.utilityAttribute.ClickUtility;
import com.sam.kadarairkopi.utilityAttribute.UrlClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    CircleImageView circleImageView, circleImageView2;
    TextView waterLevel, waterLevel2, weight, weight2, userName, note, weightVal, waterVal;
    CardView cardView, cardLabel1, cardLabel2, cardLabel3;
    Button resultButton;
    ImageView closePopUp, resultIcon;
    Dialog resultDialog, indicatorDialog;
    SwipeRefreshLayout refreshLayout;

    @Override
    protected void onStart() {
        super.onStart();
        if (!SharedData.getInstance(this).getSaveLoggedIn()) {
            finish();
            SharedData.getInstance(getApplicationContext()).logout();
        }
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
        refreshLayout = findViewById(R.id.swipeLayout);

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
//        configWater();

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                indicatorDialog = new Dialog(MainActivity.this);
                showIndicator();
            }
        });

        resultButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resultDialog = new Dialog(MainActivity.this);
                showResult();
            }
        });

        refreshLayout.setColorSchemeColors(getResources().getColor(R.color.indigo_500));
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setRefreshing(false);
                ClickUtility.clickSession(refreshLayout);
                Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_LONG).show();
//                getDataKopi();
            }
        });
    }

    private void configWater() {
        String resultValue;
        resultValue = "12";
        if (waterLevel.equals(resultValue)) {
        }
    }

    public void setUser() {
        String logUser = SharedData.getInstance(this).getUserEmail();
        userName.setText(String.format("User : %s", logUser));
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

    private void showIndicator() {
        indicatorDialog.setContentView(R.layout.indicator_detail);

        closePopUp = indicatorDialog.findViewById(R.id.closeTop);
        closePopUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                indicatorDialog.dismiss();
            }
        });

        indicatorDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        indicatorDialog.show();

    }

    private void getDataKopi() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlClass.Url_DataKopi,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            JSONArray array = obj.getJSONArray("data");

                            for (int i = 0; i < array.length(); i++) {
                                JSONObject jsonObject = array.getJSONObject(i);
                                DataCore dataKopi = new DataCore(jsonObject);

                                final String beratKopi = dataKopi.getWeight();
                                final String kadarAir = dataKopi.getWaterLevel();

                                weight.setText(beratKopi);
                                waterLevel.setText(kadarAir);
                            }
                        } catch (
                                JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Connection Error" + error, Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                    }
                });
        VolleySing.getInstance(MainActivity.this).addToRequestQueue(stringRequest);
    }

}