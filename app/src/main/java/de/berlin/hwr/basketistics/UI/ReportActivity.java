package de.berlin.hwr.basketistics.UI;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.List;
import java.util.Vector;

import de.berlin.hwr.basketistics.R;
import de.berlin.hwr.basketistics.UI.Fragments.SinglePlayerReportFragment;


public class ReportActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private final static String TAG = "ReportActivity";
    private static SharedPreferences sharedPreferences = null;
    private static String teamImageFilename;
    private static String teamName;
    ViewPager viewPager;
    int playerId;


    void inflateFragment(){
        List<Fragment> fragments = new Vector<Fragment>();

        Bundle page2 = new Bundle();
        page2.putString("url", "Player Stats");
        fragments.add(Fragment.instantiate(this, SinglePlayerReportFragment.class.getName(),page2));

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager = findViewById(R.id.reportsViewPager);
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

        inflateFragment();
    }


}
