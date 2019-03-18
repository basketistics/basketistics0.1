package de.berlin.hwr.basketistics.UI;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;
import java.io.File;
import java.util.List;

import de.berlin.hwr.basketistics.Persistency.Entities.MatchEntity;
import de.berlin.hwr.basketistics.R;
import de.berlin.hwr.basketistics.UI.Fragments.Adapter.MatchesAdapter;
import de.berlin.hwr.basketistics.ViewModel.MatchesViewModel;

public class MatchesActivity extends AppCompatActivity{

    private final static int ADD_MATCH_ACTIVITY_REQUEST_CODE = 4;
    public static final String TAG = "MatchesActivity";
    private static final int PICK_TEAM_IMAGE = 9;

    private static SharedPreferences sharedPreferences = null;
    private static String teamImageFilename;
    private static String teamName;

    private MatchesViewModel matchesViewModel;

    private FloatingActionButton addMatchButton;
    private BottomNavigationView bottomNavigationView;
    private RecyclerView matchesRecyclerView;
    private MatchesAdapter matchesAdapter;
    private ImageView teamImageView;

    private ProgressBar progressBar;

    private Bitmap teamBitmap;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.i(TAG, "reached onActivityResult().");

        if (requestCode == ADD_MATCH_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            MatchEntity matchEntity = (MatchEntity) data.getExtras().get(AddMatchActivity.EXTRA_REPLY);
            matchesViewModel.insert(matchEntity);
            matchesAdapter.setMatches(matchesViewModel.getAllMatches().getValue());

            // Test
            for (MatchEntity match : matchesViewModel.getAllMatches().getValue()) {
                Log.e(TAG, "MatchID: " + match.getId());
            }
        } else if (requestCode == PICK_TEAM_IMAGE && resultCode == RESULT_OK) {
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
                        .placeholder(R.drawable.avatar_icon)
                        .into(teamImageView);

                String imageFileName = "TEAM_IMAGE" + "_" + new Date();
                new SwapTeamImageAsyncTask(
                        imageFileName,
                        teamBitmap,
                        sharedPreferences,
                        MatchesActivity.this
                ).execute();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matches);

        // Get preferences
        if (sharedPreferences == null) {
            sharedPreferences = getSharedPreferences(FirstRunActivity.PREFERENCES, MODE_PRIVATE);
            Log.e(TAG, "SharedPreferences was null");
        }

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
        bottomNavigationView = findViewById(R.id.matchesBottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.matches);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.team:
                        Intent teamIntent = new Intent(MatchesActivity.this, TeamActivity.class);
                        teamIntent.putExtra("team_name", teamName);
                        teamIntent.putExtra("team_image", teamImageFilename);
                        startActivity(teamIntent);
                        overridePendingTransition(R.anim.from_left_in, R.anim.from_right_out);
                        break;
                    case R.id.matches:
                        Log.i(TAG, "already in MatchesActivity");
                        break;
                    case R.id.reports:
                        Intent reportIntent = new Intent(MatchesActivity.this, ReportActivity.class);
                        startActivity(reportIntent);
                        overridePendingTransition(R.anim.from_right_in, R.anim.from_left_out);
                        break;
                    default:
                        return false;
                }
                return true;
            }
        });

        // Set Team Image
        teamImageView = findViewById(R.id.matchImageView);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        File directory = this.getDir("images", Context.MODE_PRIVATE);
        File image = new File(directory, teamImageFilename);
        Uri imageUri = Uri.fromFile(image);

        Glide.with(this)
                .load(imageUri)
                .centerCrop()
                .placeholder(R.drawable.avatar_icon)
                .into(teamImageView);

        // Set up RecyclerView
        matchesRecyclerView = (RecyclerView) findViewById(R.id.matchesRecyclerView);
        matchesAdapter = new MatchesAdapter(this, teamName, null);
        matchesRecyclerView.setAdapter(matchesAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        matchesRecyclerView.setLayoutManager(linearLayoutManager);

        // get ViewModel
        matchesViewModel = ViewModelProviders.of(this).get(MatchesViewModel.class);

        // Observe ViewModel
        matchesViewModel.getAllMatches().observe(this, new Observer<List<MatchEntity>>() {
            @Override
            public void onChanged(@Nullable List<MatchEntity> playerEntities) {
                Log.e(TAG, "matchesViewModel has changed.");
                // Update cached players
                matchesAdapter.setMatches(matchesViewModel.getAllMatches().getValue());
            }
        });

        // Start MatchesActivity on button click
        addMatchButton = findViewById(R.id.addMatchButton);
        addMatchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addPlayerIntent = new Intent(MatchesActivity.this, AddMatchActivity.class);
                startActivityForResult(addPlayerIntent, ADD_MATCH_ACTIVITY_REQUEST_CODE);
            }
        });

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
}
