package de.berlin.hwr.basketistics.UI;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;

import de.berlin.hwr.basketistics.R;
import de.berlin.hwr.basketistics.UI.Fragments.MatchesFragment;
import de.berlin.hwr.basketistics.UI.Fragments.TeamFragment;

public class MainActivity extends AppCompatActivity
        implements MatchesFragment.OnFragmentInteractionListener, TeamFragment.OnFragmentInteractionListener {

    private static final String TAG = "MainActivity";
    private static final int PICK_TEAM_IMAGE = 13;

    private static SharedPreferences sharedPreferences = null;
    private static String teamImageFilename;
    private static String teamName;
    private Bitmap teamBitmap;

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
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                // TODO: Set up navigation for fragments
                switch (menuItem.getItemId()) {
                    case R.id.team:
                        setViewPager(0);
                        break;
                    case R.id.matches:
                        setViewPager(1);
                        break;
                    case R.id.reports:
                        Intent reportsIntent = new Intent(MainActivity.this, ReportActivity.class);
                        startActivity(reportsIntent);
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

        // Make TeamImage swappable
        teamImageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                dispatchChoosePictureIntent();
                return false;
            }
        });
    }

    private void dispatchChoosePictureIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_TEAM_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_TEAM_IMAGE && resultCode == RESULT_OK) {
            try {

                // Get Image
                Uri selectedImage = data.getData();
                InputStream inputStream =
                        getContentResolver().openInputStream(selectedImage);
                BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
                teamBitmap = BitmapFactory.decodeStream(bufferedInputStream);

                // Set ImageView
                Glide.with(this)
                        .load(selectedImage)
                        .centerCrop()
                        .placeholder(R.drawable.marcel_davis)
                        .into(teamImageView);

                String imageFileName = "TEAM_IMAGE" + "_" + new Date();
                new SwapTeamImageAsyncTask(
                        imageFileName,
                        teamBitmap,
                        sharedPreferences,
                        this
                ).execute();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void setupViewPager(ViewPager viewPager){
        SectionsStatePagerAdapter adapter = new SectionsStatePagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new TeamFragment(), "TeamFragment"); // item #0
        adapter.addFragment(new MatchesFragment(), "MatchesFragment"); // item #1
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
