package com.example.animelist;

import static android.text.TextUtils.isEmpty;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    private EditText nameSign,emailSign,passwordSign,phoneSign;
    private Button buttonSign,buttonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        nameSign = findViewById(R.id.nameSign);
        emailSign = findViewById(R.id.emailSign);
        passwordSign = findViewById(R.id.phoneSign);
        phoneSign = findViewById(R.id.phoneSign);
        buttonSign = findViewById(R.id.signUpButton2);
        buttonLogin = findViewById(R.id.loginButton2);
        buttonLogin.setOnClickListener(view ->{
            Intent i = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(i);
        });
        buttonSign.setOnClickListener(view -> {
            if (nameSign.getText().toString().trim().length() == 0 || emailSign.getText().toString().trim().length() == 0 || passwordSign.getText().toString().trim().length() == 0 || phoneSign.getText().toString().trim().length() == 0){
                Toast.makeText(getApplicationContext(), "Falta por rellenar campos", Toast.LENGTH_SHORT).show();
            }
            else{
                signUpUser();
            }
        });

    }
    private void signUpUser(){
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringrequest = new StringRequest(
                Request.Method.POST,
                "https://joanseculi.com/edt69/createuser2.php" ,
                response -> {
                    Toast.makeText(this, response, Toast.LENGTH_SHORT).show();
                },
                error -> Log.d("tag","onErrorResponse: " + error.getMessage())){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name", nameSign.getText().toString());
                params.put("email", emailSign.getText().toString());
                params.put("password", passwordSign.getText().toString());
                params.put("phone", phoneSign.getText().toString());
                return params;
            }
        };
        queue.add(stringrequest);
    }


}
