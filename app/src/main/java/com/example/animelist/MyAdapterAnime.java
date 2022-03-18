package com.example.animelist;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyAdapterAnime extends RecyclerView.Adapter<MyAdapterAnime.MyViewHolder> {
    @NonNull
    private Context mContext;
    private User mUser;
    private ArrayList<Anime> mAnimes;
    FrameLayout mainLayout;

    public MyAdapterAnime(Context mContext, User mUser, ArrayList<Anime> mAnimes) {
        this.mContext = mContext;
        this.mUser = mUser;
        this.mAnimes = mAnimes;
    }



    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.my_row_anime, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.animeName.setText(mAnimes.get(position).getName());
        holder.animeDesc.setText(mAnimes.get(position).getDescription());
        holder.animeYear.setText(mAnimes.get(position).getYear());
        holder.animeType.setText(mAnimes.get(position).getType());
        Picasso.get().load(mAnimes.get(position).getImage())
                .fit()
                .centerCrop()
                .into(holder.animeImage);
        if (mAnimes.get(position).getFavorite().equals("null")) {
            holder.favImage.setImageResource(R.drawable.ic_baseline_favorite_border_red);
        }
        else {
            holder.favImage.setImageResource(R.drawable.ic_baseline_favorite_red);
        }
        holder.favImage.setOnClickListener(view -> {
            Drawable.ConstantState constantState;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                constantState = mContext.getResources().getDrawable(R.drawable.ic_baseline_favorite_red, mContext.getTheme()).getConstantState();
            }else {
                constantState = mContext.getResources().getDrawable(R.drawable.ic_baseline_favorite_red).getConstantState();
            }
            if (holder.favImage.getDrawable().getConstantState() == constantState) {
                favMinus(mAnimes.get(position),holder.favImage,mUser.getEmail());
            }
            else {
                favPlus(mAnimes.get(position),holder.favImage,mUser.getEmail());
            }
        });
        holder.animeClick.setOnClickListener(view -> {
            Intent i = new Intent(mContext, DetailActivity.class);
            i.putExtra("anime", (Serializable) mAnimes.get(position));
            i.putExtra("user",mUser);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            mContext.startActivity(i);

        });

    }


    @Override
    public int getItemCount() {
        return mAnimes.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView animeName,animeDesc,animeType,animeYear;
        private ImageView animeImage,favImage;
        private LinearLayout animeClick;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            animeName = itemView.findViewById(R.id.nameAnime);
            animeDesc = itemView.findViewById(R.id.descAnime);
            animeType = itemView.findViewById(R.id.typeAnime);
            animeYear = itemView.findViewById(R.id.yearAnime);
            animeImage = itemView.findViewById(R.id.animeImage);
            favImage = itemView.findViewById(R.id.favImage);
            animeClick = itemView.findViewById(R.id.animeClick);
        }
    }
    private void favPlus(Anime anime,ImageView fav,String email){
        RequestQueue queue = Volley.newRequestQueue(mContext.getApplicationContext());
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
        RequestQueue queue = Volley.newRequestQueue(mContext.getApplicationContext());
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
