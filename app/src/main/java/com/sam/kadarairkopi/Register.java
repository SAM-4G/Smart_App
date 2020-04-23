package com.sam.kadarairkopi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.sam.kadarairkopi.Preference.VolleySing;
import com.sam.kadarairkopi.UtilityAttribute.EncryptsMD5;
import com.sam.kadarairkopi.UtilityAttribute.UrlClass;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity implements View.OnClickListener {

    Button registButton;
    EditText userEmail, userPassword, confirmPass, phoneNumber;
    CardView cardView;
    RelativeLayout button1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_register);

        userEmail = findViewById(R.id.emailRegistInput1);
        userPassword = findViewById(R.id.passRegistInput1);
        registButton = findViewById(R.id.registButton);
        cardView = findViewById(R.id.cardInput);
        phoneNumber = findViewById(R.id.phoneRegistInput1);
        confirmPass = findViewById(R.id.passRegistConfirmInput1);
        button1 = findViewById(R.id.button1Regist);

        cardView.getBackground().setAlpha(225);
        button1.getBackground().setAlpha(225);

        registButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.registButton) {
            Log.d("onclik", "onClick : ");
            final String email = userEmail.getText().toString();
            final String password = userPassword.getText().toString();
            final String passConfirm = confirmPass.getText().toString();
            final String phone = phoneNumber.getText().toString();

            final String passwordEncrypt = EncryptsMD5.MD5(password);

            if (TextUtils.isEmpty(email)) {
                userEmail.setError("Masukan email");
                userEmail.requestFocus();
            } else if (TextUtils.isEmpty(password)) {
                userPassword.setError("Masukan password");
                userPassword.requestFocus();
            } else if (TextUtils.isEmpty(passConfirm)) {
                confirmPass.setError("Konfirmasi password");
                confirmPass.requestFocus();
            } else if (TextUtils.isEmpty(phone)) {
                phoneNumber.setError("Masukan nomor ponsel");
                phoneNumber.requestFocus();
            } else if (!password.equals(passConfirm)) {
                userPassword.setError("Password tidak sama");
                userPassword.requestFocus();
            } else {
                StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlClass.Url_Register,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject obj = new JSONObject(response);
                                    if (obj.getBoolean("error")) {
                                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                                    } else {

                                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                    }
                                } catch (JSONException e) {
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
                        params.put("password", password);
                        params.put("encrypt", passwordEncrypt);
                        params.put("phone", phone);

                        return params;
                    }
                };
                VolleySing.getInstance(Register.this).addToRequestQueue(stringRequest);
            }
        }
    }
}
