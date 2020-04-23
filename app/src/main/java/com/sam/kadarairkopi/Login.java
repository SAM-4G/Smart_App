package com.sam.kadarairkopi;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.sam.kadarairkopi.Data.DataUser;
import com.sam.kadarairkopi.Preference.SharedData;
import com.sam.kadarairkopi.Preference.VolleySing;
import com.sam.kadarairkopi.UtilityAttribute.EncryptsMD5;
import com.sam.kadarairkopi.UtilityAttribute.UrlClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity implements View.OnClickListener {

    Button loginUser;
    Button registerUser;
    EditText emailInput;
    EditText passInput;
    CardView cardView, overlayCard;
    ImageView logoLogin;
    ImageButton imageButton;
    RelativeLayout button1, button2;

    @Override
    protected void onStart() {
        super.onStart();
        if (SharedData.getInstance(this).getSaveLoggedIn()) {
            Login.this.finish();
            SharedData.getInstance(getApplicationContext()).loginUser();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);

        loginUser = findViewById(R.id.loginButton);
        registerUser = findViewById(R.id.registerButton);
        emailInput = findViewById(R.id.emailInput1);
        passInput = findViewById(R.id.passInput1);
        cardView = findViewById(R.id.cardInput);
        overlayCard = findViewById(R.id.relativeBotIn2);
        imageButton = findViewById(R.id.imageButton);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        logoLogin = findViewById(R.id.logoLogin);

        cardView.getBackground().setAlpha(225);
        overlayCard.getBackground().setAlpha(225);
        imageButton.getBackground().setAlpha(225);
        button1.getBackground().setAlpha(225);
        button2.getBackground().setAlpha(225);
        logoLogin.getBackground().setAlpha(225);

        registerUser.setOnClickListener(this);
        loginUser.setOnClickListener(this);
    }

    public void goToMainActivity() {
        Intent intent = new Intent(Login.this, MainActivity.class);
        Login.this.finish();
        startActivity(intent);
    }

    public void goToRegister() {
        Intent intent = new Intent(Login.this, Register.class);
        startActivity(intent);
        emailInput.getText().clear();
        passInput.getText().clear();
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder ab = new AlertDialog.Builder(Login.this);
        ab.setTitle("are you sure to exit?").setIcon(R.drawable.user_icon);
        ab.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
                Login.this.finish();
            }
        });
        ab.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        ab.show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.loginButton:
                final String email = emailInput.getText().toString();
                final String password = passInput.getText().toString();
                final String passEncrypt = EncryptsMD5.MD5(password);

                Log.d("Password ", "Password MD5 Login " + EncryptsMD5.MD5(password));

                if (TextUtils.isEmpty(emailInput.getText())) {
                    emailInput.setError("Email harus diisi");
                    emailInput.requestFocus();
                } else if (TextUtils.isEmpty(passInput.getText())) {
                    passInput.setError("Password harus diisi");
                    passInput.requestFocus();
                } else {
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlClass.Url_Login,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject obj = new JSONObject(response);
                                        JSONArray array = obj.getJSONArray("data");

                                        for (int i = 0; i < array.length(); i++) {
                                            JSONObject jsonObject = array.getJSONObject(i);
                                            DataUser user1 = new DataUser(jsonObject);

                                            final String emailUser = user1.getUserEmail();
                                            final String passUser = user1.getUserPassword();

                                            final boolean userAndPassTrue = (emailUser.equals(email) && passUser.equals(password));
                                            final boolean emailTrue = (emailUser.equals(email));
                                            final boolean passTrue = (passUser.equals(password));

                                            if (userAndPassTrue) {
                                                Toast.makeText(Login.this, "Selamat Datang " + email + "\n" + passEncrypt, Toast.LENGTH_LONG).show();
                                                SharedData.getInstance(getApplicationContext()).storeUserEmail(email);
                                                SharedData.getInstance(getApplicationContext()).storeUserPassword(password);
                                                SharedData.getInstance(getApplicationContext()).saveLoggedIn(SharedData.USER_LOGIN_STATUS, true);

                                                goToMainActivity();
                                            }
                                            if (passTrue) {
                                                Toast.makeText(Login.this, "Email salah", Toast.LENGTH_LONG).show();
                                            } else if (emailTrue) {
                                                Toast.makeText(Login.this, "Password salah", Toast.LENGTH_LONG).show();
                                            }
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
                            }) {

                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<>();
                            params.put("email", email);
                            params.put("password", passEncrypt);
                            return params;
                        }
                    };
                    VolleySing.getInstance(Login.this).addToRequestQueue(stringRequest);
                }
                break;

            case R.id.registerButton:
                goToRegister();
        }
    }
}