package com.example.animelist;

import static android.text.TextUtils.isEmpty;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;

import java.io.Serializable;

public class LoginActivity extends AppCompatActivity {


    private EditText email,password;
    private Button buttonSignUp,buttonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        buttonLogin = findViewById(R.id.loginButton1);
        buttonSignUp = findViewById(R.id.signUpButton1);
        buttonSignUp.setOnClickListener(view -> {
            Intent i = new Intent(getApplicationContext(), SignUpActivity.class);
            startActivity(i);

        });
        buttonLogin.setOnClickListener(view -> {
            if (email.getText().toString().trim().length() == 0 || password.getText().toString().trim().length() == 0 )   // correcto
                Toast.makeText(getApplicationContext(), "Falta por rellenar campos", Toast.LENGTH_SHORT).show();
            else {
                loginUser();
            }
        });


    }
    private void loginUser(){
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                "https://joanseculi.com/edt69/loginuser.php?email=" + email.getText().toString() + "&password=" + password.getText().toString(),
        null,
                response -> {
                    try {
                        User user = new User();
                        user.setId(Integer.parseInt(response.getString("id")));
                        user.setName(response.getString("name"));
                        user.setEmail(response.getString("email"));
                        user.setPassword(response.getString("password"));
                        user.setPhone(response.getString("phone"));

                        Intent i = new Intent(getApplicationContext(), ListAnimeActivity.class);
                        i.putExtra("user",  user);
                        startActivity(i);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                },
                error -> Toast.makeText(getApplicationContext(), "Error en el usuario o en la contraseña", Toast.LENGTH_SHORT).show()
        );
        queue.add(jsonObjectRequest);
    }
}