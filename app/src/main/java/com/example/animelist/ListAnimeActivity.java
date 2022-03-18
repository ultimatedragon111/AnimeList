package com.example.animelist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;

public class ListAnimeActivity extends AppCompatActivity {


    Toolbar toolbar;
    User user;
    FrameLayout mainLayout;
    private ListAnimeFragment listAnimeFragment;
    private PerfilFragment perfilFragment;
    private AnimeFavoriteListFragment animeFavoriteListFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_anime);

        toolbar = findViewById(R.id.toolbarAnime);
        mainLayout = findViewById(R.id.mMainNav);
        if (getIntent().hasExtra("user")) {
            user = (User) getIntent().getExtras().getSerializable("user");
        }
        listAnimeFragment = new ListAnimeFragment();
        perfilFragment = new PerfilFragment();
        animeFavoriteListFragment = new AnimeFavoriteListFragment();
        listAnimeFragment = ListAnimeFragment.newInstance(user);
        setFragment(listAnimeFragment);
    }


    private void setFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.mMainNav, fragment);
        fragmentTransaction.commit();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent i;
        switch (item.getItemId()) {
            case R.id.nav_menu:
                listAnimeFragment = ListAnimeFragment.newInstance(user);
                setFragment(listAnimeFragment);
                return true;
            case R.id.nav_profile:
                perfilFragment = PerfilFragment.newInstance(user);
                setFragment(perfilFragment);
                return true;
            case R.id.nav_fav:
                animeFavoriteListFragment = AnimeFavoriteListFragment.newInstance(user);
                setFragment(animeFavoriteListFragment);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }
}