package de.berlin.hwr.basketistics.UI;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import de.berlin.hwr.basketistics.R;
import de.berlin.hwr.basketistics.ViewModel.BasketisticsViewModel;

public class StartActivity extends AppCompatActivity {

    private ImageView mockgame;

    public static final String TAG = "StartActivity";
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.actionbar_startactivity, menu);
        return true;
    }

    protected void onCreate(Bundle savedInstanceState) {

        final Intent games_activity = new Intent(this, GameActivity.class);
        final Intent team_activity = new Intent(this, TeamActivity.class);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.navbar);
        //----------MOCK FÜR ZWISCHENPRÄSI---------
        mockgame = findViewById(R.id.mockgame);
    mockgame.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            startActivity(games_activity);
        }
    });



        BottomNavigationView bot_nav = (BottomNavigationView) findViewById(R.id.navbar);
        bot_nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.nav_games:
                        startActivity(games_activity);
                        return true;

                    case R.id.nav_team:
                            startActivity(team_activity);
                            return true;
                }



                return false;



            }
        });

    }

}
