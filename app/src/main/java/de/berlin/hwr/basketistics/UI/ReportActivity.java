package de.berlin.hwr.basketistics.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.arch.lifecycle.ViewModelProviders;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import de.berlin.hwr.basketistics.R;
import de.berlin.hwr.basketistics.ViewModel.SingleGameReportViewModel;
import de.berlin.hwr.basketistics.ViewModel.TeamViewModel;

public class ReportActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private final static String TAG = "ReportActivity";





    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);










        FragmentManager fragmentManager = getSupportFragmentManager();


        // Set up navbar
        bottomNavigationView = findViewById(R.id.matchesBottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.matches);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.team:
                        Intent teamIntent = new Intent(ReportActivity.this, TeamActivity.class);
                        startActivity(teamIntent);
                        overridePendingTransition(R.anim.from_right_in, R.anim.from_left_out);
                        break;
                    case R.id.matches:
                        Intent matchIntend = new Intent(ReportActivity.this, TeamActivity.class);
                        startActivity(matchIntend);
                        overridePendingTransition(R.anim.from_right_in, R.anim.from_left_out);
                        break;
                    case R.id.reports:
                        Log.i(TAG, "Already in ReportActivity.");
                        break;
                    default:
                        return false;
                }
                return true;
            }
        });













    }


}
