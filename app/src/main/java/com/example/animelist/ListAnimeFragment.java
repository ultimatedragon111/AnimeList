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

import java.util.ArrayList;


public class ListAnimeFragment extends Fragment {
    private RecyclerView recyclerView;
    private ArrayList<Anime> animes = new ArrayList<>();
    private static final String ARG_PARAM1 = "param1";


    public static ListAnimeFragment newInstance(User param1) {
        ListAnimeFragment fragment = new ListAnimeFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_anime, container, false);
        recyclerView = view.findViewById(R.id.recyclerViewAnime);
        User mParam1 = (User)getArguments().getSerializable(ARG_PARAM1);

        getAnimeList(mParam1);
        return view;
    }
    private void getAnimeList(User user){
        RequestQueue queue = Volley.newRequestQueue(getContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                "https://joanseculi.com/edt69/animes3.php?email="+user.getEmail(),
                null,
                (Response.Listener<JSONObject>) response -> {
                    try {
                        JSONArray jsonArray = response.getJSONArray("animes");

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject animeObject = jsonArray.getJSONObject(i);

                            Anime anime = new Anime();
                            anime.setName(animeObject.getString("name"));
                            anime.setDescription(animeObject.getString("description"));
                            anime.setType(animeObject.getString("type"));
                            anime.setYear(animeObject.getString("year"));
                            anime.setFavorite(animeObject.getString("favorite"));

                            String link = animeObject.getString("image");
                            anime.setImage("https://joanseculi.com/" + link);

                            animes.add(anime);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    MyAdapterAnime adapter = new MyAdapterAnime(getContext(), user, animes);
                    recyclerView.setAdapter(adapter);


                },error -> Log.d("tag","onErrorResponse: " + error.getMessage())
        );

        queue.add(jsonObjectRequest);
    }
}