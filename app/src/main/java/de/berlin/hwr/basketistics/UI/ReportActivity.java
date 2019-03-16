package de.berlin.hwr.basketistics.UI;

import android.arch.lifecycle.ReportFragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.arch.lifecycle.ViewModelProviders;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.List;
import java.util.Vector;

import de.berlin.hwr.basketistics.R;
import de.berlin.hwr.basketistics.ViewModel.SingleGameReportViewModel;
import de.berlin.hwr.basketistics.ViewModel.TeamViewModel;



public class ReportActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private final static String TAG = "ReportActivity";
    private static SharedPreferences sharedPreferences = null;
    private static String teamImageFilename;
    private static String teamName;
    ViewPager viewPager;




    void setUpNavBar(){
        if (sharedPreferences == null) {
            sharedPreferences = getSharedPreferences(FirstRunActivity.PREFERENCES, MODE_PRIVATE);
            Log.e(TAG, "SharedPreferences was null");
        }

        if (teamImageFilename == null) {
            Log.e(TAG, "teamImageFilename was null");
            teamImageFilename = sharedPreferences.getString("team_image", ""); }
        if (teamName == null) { teamName = sharedPreferences.getString("team_name", ""); }

        bottomNavigationView = findViewById(R.id.reportBottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.reports);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.team:
                        Intent teamIntent = new Intent(ReportActivity.this, TeamActivity.class);
                        teamIntent.putExtra("team_name", teamName);
                        teamIntent.putExtra("team_image", teamImageFilename);
                        startActivity(teamIntent);
                        overridePendingTransition(R.anim.from_left_in, R.anim.from_right_out);
                        break;
                    case R.id.matches:
                        Intent matchIntend = new Intent(ReportActivity.this, MatchesActivity.class);
                        startActivity(matchIntend);
                        overridePendingTransition(R.anim.from_left_in, R.anim.from_right_out);
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


    void inflateFragment(){
        List<Fragment> fragments = new Vector<Fragment>();

        Bundle page = new Bundle();
        page.putString("url", "Team Stats");
        fragments.add(Fragment.instantiate(this, TeamReportFragment.class.getName(),page));

        Bundle page2 = new Bundle();
        page2.putString("url", "Player Stats");
        fragments.add(Fragment.instantiate(this, PlayerReportsFragment.class.getName(),page2));

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager = findViewById(R.id.pager);
        viewPager.setAdapter(viewPagerAdapter);

    }




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.report_activity);

        setUpNavBar();

        inflateFragment();
    }


}
