package de.berlin.hwr.basketistics.UI;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.File;

import de.berlin.hwr.basketistics.R;
import de.berlin.hwr.basketistics.UI.Fragments.MatchesFragment;

public class MainActivity extends AppCompatActivity
        implements MatchesFragment.OnFragmentInteractionListener{

    private static final String TAG = "MainActivity";

    private static SharedPreferences sharedPreferences = null;
    private static String teamImageFilename;
    private static String teamName;

    private ImageView teamImageView;
    private SectionsStatePagerAdapter sectionsStatePagerAdapter;
    private ViewPager viewPager;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.e(TAG, "onCreate: was passed.");

        // Fragments Adapter
        sectionsStatePagerAdapter = new SectionsStatePagerAdapter(getSupportFragmentManager());

        // Find and setup ViewPager
        viewPager = findViewById(R.id.mainViewPager);
        setupViewPager(viewPager);

        // Get preferences
        if (sharedPreferences == null) {
            sharedPreferences = getSharedPreferences(FirstRunActivity.PREFERENCES, MODE_PRIVATE);
            Log.e(TAG, "SharedPreferences was null");
        }

        // TODO: Make Fragment
        // First run?
        if (sharedPreferences.getBoolean("first_run", true)) {
            Intent firstRunIntent = new Intent(this, FirstRunActivity.class);
            startActivity(firstRunIntent);
        }

        Log.e(TAG, "Code after intent is entered.");

        // Get values from preferences
        if (teamImageFilename == null) {
            Log.e(TAG, "teamImageFilename was null");
            teamImageFilename = sharedPreferences.getString("team_image", ""); }
        if (teamName == null) { teamName = sharedPreferences.getString("team_name", ""); }

        // Set up navbar
        bottomNavigationView = findViewById(R.id.mainBottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.matches);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                // TODO: Set up navigation for fragments
                switch (menuItem.getItemId()) {
                    case R.id.team:
                        // Intent teamIntent = new Intent(MainActivity.this, TeamActivity.class);
                        // teamIntent.putExtra("team_name", teamName);
                        // teamIntent.putExtra("team_image", teamImageFilename);
                        // startActivity(teamIntent);
                        // overridePendingTransition(R.anim.from_left_in, R.anim.from_right_out);
                        break;
                    case R.id.matches:
                        Log.i(TAG, "already in MatchesActivity");
                        break;
                    case R.id.reports:
                        Log.i(TAG, "ReportsActivity not implemented yet.");
                        break;
                    default:
                        return false;
                }
                return true;
            }
        });
        Log.e(TAG, bottomNavigationView.getVisibility() + "");

        // Set Team Image
        teamImageView = findViewById(R.id.mainTeamImage);

        File directory = this.getDir("images", Context.MODE_PRIVATE);
        File image = new File(directory, teamImageFilename);
        Uri imageUri = Uri.fromFile(image);

        Glide.with(this)
                .load(imageUri)
                .centerCrop()
                .placeholder(R.drawable.marcel_davis)
                .into(teamImageView);
    }

    private void setupViewPager(ViewPager viewPager){
        SectionsStatePagerAdapter adapter = new SectionsStatePagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new MatchesFragment(), "MatchesFragment");
        viewPager.setAdapter(adapter);
    }

    public void setViewPager(int fragmentNumber){
        viewPager.setCurrentItem(fragmentNumber);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        Log.i(TAG, "onFragmentInteraction: was called.");
    }

    public String getTeamName() {
        return teamName;
    }
}
