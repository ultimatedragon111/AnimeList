package com.example.animelist;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PerfilFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PerfilFragment extends Fragment {


    private EditText nameUpdate,passwordUpdate,phoneUpdate;
    private TextView emailUpdate;
    private Button buttonUpdate,buttonDelete;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private User mParam1;

    public PerfilFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment PerfilFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PerfilFragment newInstance(Serializable param1) {
        PerfilFragment fragment = new PerfilFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);
        mParam1 = (User) getArguments().getSerializable(ARG_PARAM1);
        nameUpdate = view.findViewById(R.id.nameUpdate);
        passwordUpdate = view.findViewById(R.id.passwordUpdate);
        phoneUpdate = view.findViewById(R.id.phoneUpdate);
        emailUpdate  = view.findViewById(R.id.emailUpdate);
        buttonDelete = view.findViewById(R.id.buttonDelete);
        buttonUpdate = view.findViewById(R.id.buttonUpdate);



        nameUpdate.setText(mParam1.getName());
        emailUpdate.setText(mParam1.getEmail());
        phoneUpdate.setText(mParam1.getPhone());
        passwordUpdate.setText(mParam1.getPassword());

        buttonUpdate.setOnClickListener(view1 -> {
            updateUser();
        });
        buttonDelete.setOnClickListener(view1 -> {
            deleteUser();
        });

        return view;
    }
    private void updateUser(){
        RequestQueue queue = Volley.newRequestQueue(this.getContext());
        StringRequest stringrequest = new StringRequest(
                Request.Method.POST,
                "https://www.joanseculi.com/edt69/updateuser.php",
                response -> {
                    mParam1.setPassword(passwordUpdate.getText().toString());
                    mParam1.setName(nameUpdate.getText().toString());
                    mParam1.setPhone(phoneUpdate.getText().toString());
                },
                error -> Log.d("tag","onErrorResponse: " + error.getMessage())){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name", nameUpdate.getText().toString() );
                params.put("email", emailUpdate.getText().toString() );
                params.put("password", passwordUpdate.getText().toString() );
                params.put("phone", phoneUpdate.getText().toString() );
                return params;
            }
        };
        queue.add(stringrequest);


    }
    private void deleteUser(){
        RequestQueue queue = Volley.newRequestQueue(this.getContext());
        StringRequest stringrequest = new StringRequest(
                Request.Method.POST,
                "https://www.joanseculi.com/edt69/deleteuser.php",
                response -> {
                    Intent i = new Intent(this.getContext(), LoginActivity.class);
                    startActivity(i);
                },
                error -> Log.d("tag","onErrorResponse: " + error.getMessage())){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", mParam1.getEmail() );
                return params;
            }
        };
        queue.add(stringrequest);


    }
}
