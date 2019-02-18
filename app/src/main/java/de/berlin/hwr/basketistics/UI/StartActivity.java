package de.berlin.hwr.basketistics.UI;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import de.berlin.hwr.basketistics.R;
import de.berlin.hwr.basketistics.ViewModel.BasketisticsViewModel;

public class StartActivity extends AppCompatActivity {

    public static final String TAG = "StartActivity";

    protected void onCreate(Bundle savedInstanceState) {


        final Intent intent = new Intent(this, GameActivity.class);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.navbar);
        BottomNavigationView bot_nav = (BottomNavigationView) findViewById(R.id.navbar);
        bot_nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                startActivity(intent);
                return false;
            }
        });

    }
}
