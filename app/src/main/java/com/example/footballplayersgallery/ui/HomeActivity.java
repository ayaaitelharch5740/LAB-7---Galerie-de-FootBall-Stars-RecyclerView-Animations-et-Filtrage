package com.example.footballplayersgallery.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ShareCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.footballplayersgallery.R;
import com.example.footballplayersgallery.adapter.PlayerAdapter;
import com.example.footballplayersgallery.service.PlayerService;

public class HomeActivity extends AppCompatActivity {

    private PlayerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("⚽ Football Stars");
        }

        RecyclerView rv = findViewById(R.id.recycler_players);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PlayerAdapter(this, PlayerService.getInstance().fetchAll());
        rv.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.menu_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        if (searchView != null) {
            searchView.setQueryHint("Joueur ou club...");
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override public boolean onQueryTextSubmit(String q) {
                    adapter.getFilter().filter(q);
                    return true;
                }
                @Override public boolean onQueryTextChange(String t) {
                    adapter.getFilter().filter(t);
                    return true;
                }
            });
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_share) {
            ShareCompat.IntentBuilder
                    .from(this)
                    .setType("text/plain")
                    .setChooserTitle("Partager Football Stars")
                    .setText("⚽ Découvrez Football Stars, l'app pour noter les meilleurs joueurs du monde !")
                    .startChooser();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}