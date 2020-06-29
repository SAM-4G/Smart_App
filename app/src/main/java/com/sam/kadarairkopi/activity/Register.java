package com.sam.kadarairkopi.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
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
import com.sam.kadarairkopi.preference.VolleySing;
import com.sam.kadarairkopi.R;
import com.sam.kadarairkopi.utilityAttribute.EncryptsMD5;
import com.sam.kadarairkopi.utilityAttribute.UrlClass;

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
                registerUser(email, passwordEncrypt, phone, Register.this);
                Toast.makeText(Register.this, "Register success", Toast.LENGTH_LONG).show();
                Register.this.finish();
            }
        }
    }

    private void registerUser(final String emails, final String passwordEncrpts, final String phones, final Context context) {
        String urlRegister = UrlClass.Url_Register + "hp=" + phones + "&pass=" + passwordEncrpts + "&user=" + emails + UrlClass.API_KEY;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, urlRegister,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            String kode = obj.getString("Statuse_code");
                            String pesan = obj.getString("status_message");
                            Log.d("TAG", "onResponse: " + pesan);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Register.this, "Connection Error" + error, Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", emails);
                params.put("password", passwordEncrpts);
                params.put("phone", phones);

                return params;
            }
        };
        VolleySing.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }
}
