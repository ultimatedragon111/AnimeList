package com.example.animelist;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AnimeFavoriteListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AnimeFavoriteListFragment extends Fragment {
    private RecyclerView recyclerFavorites;
    private ArrayList<Anime> animes = new ArrayList<>();
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    public static AnimeFavoriteListFragment newInstance(Serializable param1) {
        AnimeFavoriteListFragment fragment = new AnimeFavoriteListFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_anime_favorite_list, container, false);
        recyclerFavorites = view.findViewById(R.id.recyclerFavorites);
        User mParam1 = (User)getArguments().getSerializable(ARG_PARAM1);
        getAnimeList(mParam1);
        return view;
    }
    private void getAnimeList(User user){
        RequestQueue queue = Volley.newRequestQueue(getContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                "https://joanseculi.com/edt69/animesfavorites2.php?email="+user.getEmail(),
                null,
                (Response.Listener<JSONObject>) response -> {
                    try {
                        JSONArray jsonArray = response.getJSONArray("animesfavorites");

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject animeObject = jsonArray.getJSONObject(i);

                            Anime anime = new Anime();
                            anime.setName(animeObject.getString("name"));
                            anime.setDescription(animeObject.getString("description"));
                            anime.setType(animeObject.getString("type"));
                            anime.setYear(animeObject.getString("year"));
                            anime.setFavorite(String.valueOf(user.getId()));
                            String link = animeObject.getString("image");
                            anime.setImage("https://joanseculi.com/" + link);

                            animes.add(anime);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    recyclerFavorites.setLayoutManager(new LinearLayoutManager(getContext()));
                    MyAdapterAnime adapter = new MyAdapterAnime(getContext(), user, animes);
                    recyclerFavorites.setAdapter(adapter);


                },error -> Log.d("tag","onErrorResponse: " + error.getMessage())
        );

        queue.add(jsonObjectRequest);
    }
}