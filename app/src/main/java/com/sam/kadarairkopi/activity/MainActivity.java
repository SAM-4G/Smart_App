package com.sam.kadarairkopi.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.sam.kadarairkopi.SQLite.DBSource;
import com.sam.kadarairkopi.SQLite.LogData;
import com.sam.kadarairkopi.data.DataCore;
import com.sam.kadarairkopi.preference.SharedData;
import com.sam.kadarairkopi.R;
import com.sam.kadarairkopi.preference.VolleySing;
import com.sam.kadarairkopi.utilityAttribute.ClickUtility;
import com.sam.kadarairkopi.utilityAttribute.UrlClass;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import mQtt.MqttHelper;

public class MainActivity extends AppCompatActivity {

    MqttHelper mqttHelper;

    CircleImageView circleImageView, circleImageView2;
    TextView waterLevel, waterLevel2, weight, weight2, userName, note, weightVal, waterVal;
    CardView cardView, cardLabel1, cardLabel2, cardLabel3;
    Button resultButton, logButton, delButton;
    ImageView closePopUp, resultIcon;
    Dialog resultDialog, indicatorDialog, logDialog;
    SwipeRefreshLayout refreshLayout;

    private DBSource dbSource;

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
        ab.setTitle("Logout or exit").setIcon(R.drawable.user_icon);
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
        logButton = findViewById(R.id.buttonLog);

        circleImageView.getBackground().setAlpha(225);
        circleImageView2.getBackground().setAlpha(225);
        cardView.getBackground().setAlpha(225);
        waterLevel2.getBackground().setAlpha(225);
        weight2.getBackground().setAlpha(225);
        cardLabel1.getBackground().setAlpha(225);
        cardLabel2.getBackground().setAlpha(225);
        cardLabel3.getBackground().setAlpha(225);
        userName.getBackground().setAlpha(225);

        dbSource = new DBSource(this);

        setUser();

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
                dbSource.open();

//                getMqttRequest();
//                getDataKopi();

                Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_LONG).show();
            }
        });
        logButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logDialog = new Dialog(MainActivity.this);
                showLog();
            }
        });
    }

    public void setUser() {
        String logUser = SharedData.getInstance(this).getUserEmail();
        userName.setText(String.format("User : %s", logUser));
    }

    public void showResult() {
        resultDialog.setContentView(R.layout.pop_up_result);

        String resultValue = "12";

        closePopUp = resultDialog.findViewById(R.id.closeTop);
        resultIcon = resultDialog.findViewById(R.id.resultIcon);
        note = resultDialog.findViewById(R.id.noteResult);
        weightVal = resultDialog.findViewById(R.id.weightValue);
        waterVal = resultDialog.findViewById(R.id.waterValue);

        String wLevel = waterLevel.getText().toString();
        String wEight = weight.getText().toString();

        weightVal.setText(wEight);
        waterVal.setText(wLevel);

        if (waterVal.getText().equals(resultValue)) {
            resultIcon.setImageResource(R.drawable.ic_check_circle_black_24dp);
            note.setText(R.string.good_quality_coffee_beans);
        }
        if (waterVal.getText().equals(wLevel)) {
            resultIcon.setImageResource(R.drawable.ic_error_outline_black_24dp);
            note.setText(R.string.bad_quality_coffee_beans);
        }

        closePopUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resultDialog.dismiss();
            }
        });

        Objects.requireNonNull(resultDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
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

        Objects.requireNonNull(indicatorDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
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

                                String beratKopi = dataKopi.getWeight();
                                String kadarAir = dataKopi.getWaterLevel();

                                LogData logData = dbSource.createLog(beratKopi, kadarAir);

//                                Toast.makeText(getApplicationContext(), "Berat Kopi : " + logData.getBeratKopi()
//                                        + "\nKadar Air : " + logData.getKadarAirKopi(), Toast.LENGTH_LONG).show();

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

    private void showLog() {
        logDialog.setContentView(R.layout.log_data_kopi);
        ListView lisLog = logDialog.findViewById(R.id.listLog);
        delButton = logDialog.findViewById(R.id.deleteLog);

        dbSource = new DBSource(this);
        dbSource.open();

        ArrayList<LogData> arrayList = dbSource.getAllLog();
        ArrayAdapter<LogData> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayList);
        lisLog.setAdapter(adapter);

        delButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbSource.deleteLog();
                Toast.makeText(MainActivity.this, "Log was deleted", Toast.LENGTH_LONG).show();
            }
        });

        Objects.requireNonNull(logDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        logDialog.show();

    }

    private void getMqttRequest() {
        mqttHelper = new MqttHelper(getApplicationContext());

        mqttHelper.setCallback(new MqttCallbackExtended() {
            @Override
            public void connectComplete(boolean b, String s) {
                Log.i("Mqtt", "connection success");
            }

            @Override
            public void connectionLost(Throwable throwable) {
                Log.i("Mqtt", "connection lost");
            }

            @Override
            public void messageArrived(String topic, MqttMessage mqttMessage) {
                Log.w("Mqtt", mqttMessage.toString());
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
                Log.i("Mqtt", "msg delivered");
            }
        });
    }
}