package com.example.animelist;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class DetailActivity extends AppCompatActivity {


    private TextView nameDetail,descDetail,typeDetail,yearDetail;
    private ImageView imageDetail,favDetail;
    Anime anime;
    User user;
    Toolbar toolbarDetail;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        nameDetail = findViewById(R.id.nameDetail);
        descDetail = findViewById(R.id.descDetail);
        typeDetail = findViewById(R.id.typeDetail);
        yearDetail = findViewById(R.id.yearDetail);
        imageDetail = findViewById(R.id.imageDetail);
        favDetail = findViewById(R.id.favDetail);
        toolbarDetail = findViewById(R.id.toolbarDetail);



        anime = (Anime) getIntent().getExtras().getSerializable("anime");
        user = (User) getIntent().getExtras().getSerializable("user");

        nameDetail.setText(anime.getName());
        descDetail.setText(anime.getDescription());
        typeDetail.setText(anime.getType());
        yearDetail.setText(anime.getYear());
        Picasso.get().load(anime.getImage())
                .fit()
                .centerCrop()
                .into(imageDetail);
        if (anime.getFavorite().equals("null")) {
            favDetail.setImageResource(R.drawable.ic_baseline_favorite_border_red);
        }
        else {
            favDetail.setImageResource(R.drawable.ic_baseline_favorite_red);
        }

        favDetail.setOnClickListener(view -> {
            Drawable.ConstantState constantState;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                constantState = this.getResources().getDrawable(R.drawable.ic_baseline_favorite_red, getApplicationContext().getTheme()).getConstantState();
            }else {
                constantState = this.getResources().getDrawable(R.drawable.ic_baseline_favorite_red).getConstantState();
            }
            if (favDetail.getDrawable().getConstantState() == constantState) {
                favMinus(anime,favDetail,user.getEmail());
            }
            else {
                favPlus(anime,favDetail,user.getEmail());
            }
        });





    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed(){
        Intent i = new Intent(this, ListAnimeActivity.class);
        i.putExtra("user",user);
        startActivity(i);

    }


    private void favPlus(Anime anime,ImageView fav,String email){
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringrequest = new StringRequest(
                Request.Method.POST,
                "https://joanseculi.com/edt69/insertfavorite.php",
                response -> {
                    fav.setImageResource(R.drawable.ic_baseline_favorite_red);
                },
                error -> Log.d("tag","onErrorResponse: " + error.getMessage())){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email );
                params.put("anime", anime.getName());
                return params;
            }
        };
        queue.add(stringrequest);

    }
    private void favMinus(Anime anime,ImageView fav,String email){
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringrequest = new StringRequest(
                Request.Method.POST,
                "https://www.joanseculi.com/edt69/deletefavorite.php",
                response -> {
                    fav.setImageResource(R.drawable.ic_baseline_favorite_border_red);
                },
                error -> Log.d("tag","onErrorResponse: " + error.getMessage())){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email );
                params.put("anime", anime.getName());
                return params;
            }
        };
        queue.add(stringrequest);

    }
}