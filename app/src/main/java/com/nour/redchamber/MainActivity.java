package com.nour.redchamber;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.nour.redchamber.models.Senators;
import com.nour.redchamber.services.ApiService;
import com.nour.redchamber.services.ServiceBuilder;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SenatorsRecyclerAdapter.OnSenatorCardClickListener {
    private ApiService taskService;
    private Call<List<Senators>> apiCall;
    static List<Senators> senatorData;
    private RecyclerView senatorRecycler;
    private SenatorsRecyclerAdapter senatorsRecyclerAdapter;
    private GridLayoutManager layoutManager;
    private int senatorPos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        senatorRecycler = findViewById(R.id.senatorRecycler);
        layoutManager = new GridLayoutManager(this, 2);
        senatorRecycler.setLayoutManager(layoutManager);

        fetchSenatorsData();


    }

    private void fetchSenatorsData() {
        taskService = ServiceBuilder.buildService(ApiService.class);
        apiCall = taskService.getSenators();

        apiCall.enqueue(new Callback<List<Senators>>() {
            @Override
            public void onResponse(Call<List<Senators>> call, Response<List<Senators>> response) {
                senatorData = response.body();
                populateViews();
                Log.d("TAG:", senatorData.get(0).getName());
            }

            @Override
            public void onFailure(Call<List<Senators>> call, Throwable t) {
                Log.d("ERROR:", t.getMessage());
            }
        });
    }

    private void populateViews() {
        senatorsRecyclerAdapter = new SenatorsRecyclerAdapter(this, senatorData,this);
        senatorRecycler.setAdapter(senatorsRecyclerAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                senatorsRecyclerAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                senatorsRecyclerAdapter.getFilter().filter(newText);
                return false;
            }
        });

        return true;
    }

    @Override
    public void onSenatorCardClick(int position) {
        senatorPos = position;
        Intent profileIntent = new Intent(this, SenatorProfileActivity.class);
        profileIntent.putExtra("senatorPosition", senatorPos);
        startActivity(profileIntent);
    }
}
