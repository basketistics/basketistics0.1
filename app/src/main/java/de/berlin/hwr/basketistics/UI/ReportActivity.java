package de.berlin.hwr.basketistics.UI;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import java.util.List;
import java.util.Vector;

import de.berlin.hwr.basketistics.R;


public class ReportActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private final static String TAG = "ReportActivity";
    private static SharedPreferences sharedPreferences = null;
    private static String teamImageFilename;
    private static String teamName;
    ViewPager viewPager;
    int playerId;


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
                Intent mainIntent;
                switch (menuItem.getItemId()) {
                    case R.id.team:
                        mainIntent = new Intent(ReportActivity.this, MainActivity.class);
                        startActivity(mainIntent);
                        overridePendingTransition(R.anim.from_left_in, R.anim.from_right_out);
                        break;
                    case R.id.matches:
                        mainIntent = new Intent(ReportActivity.this, MainActivity.class);
                        startActivity(mainIntent);
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
        fragments.add(Fragment.instantiate(this, SinglePlayerReportFragment.class.getName(),page2));

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager = findViewById(R.id.pager);
        viewPager.setAdapter(viewPagerAdapter);

    }

    public void setPlayerId(int playerId){
        this.playerId = playerId;
    }

    public int getPlayerId(){
        return playerId;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.report_activity);

        setUpNavBar();

        inflateFragment();
    }


}
