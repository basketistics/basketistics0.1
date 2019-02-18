package de.berlin.hwr.basketistics.UI;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import de.berlin.hwr.basketistics.R;

public class TeamActivity extends AppCompatActivity {

    private RecyclerView teamRecyclerView;
    private RecyclerView.LayoutManager teamLayoutManager;
    private RecyclerView.Adapter teamAdapter;
    private String[] teamDataset = {"Spieler 1", "Spieler 2", "Spieler 3", "Spieler 4", "Spieler 5", "Spieler 6"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);


        // navabr
        final Intent start_activity = new Intent(this, StartActivity.class);
        BottomNavigationView bot_nav = (BottomNavigationView) findViewById(R.id.navbar);
        bot_nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.nav_games:
                        startActivity(start_activity);
                        return true;

                    case R.id.nav_team:
                        return false;
                }



                return false;



            }
        });

        // Attach RecyclerView
        teamRecyclerView = (RecyclerView) findViewById(R.id.teamRecyclerView);

        ///// Use only if size of layout does not change!
        teamRecyclerView.setHasFixedSize(true);

        //// use a linear LayoutManager
        teamLayoutManager = new LinearLayoutManager(this);
        teamRecyclerView.setLayoutManager(teamLayoutManager);

        //// specify adapter
        teamAdapter = new TeamAdapter(teamDataset);
        teamRecyclerView.setAdapter(teamAdapter);

    }
}
