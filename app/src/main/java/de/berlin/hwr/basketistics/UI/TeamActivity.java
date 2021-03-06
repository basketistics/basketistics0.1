package de.berlin.hwr.basketistics.UI;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;
import java.io.File;
import java.util.List;

import de.berlin.hwr.basketistics.Persistency.Entities.PlayerEntity;
import de.berlin.hwr.basketistics.R;
import de.berlin.hwr.basketistics.UI.Fragments.Adapter.TeamAdapter;
import de.berlin.hwr.basketistics.ViewModel.TeamViewModel;
public class TeamActivity extends AppCompatActivity {

    private final static int ADD_PLAYER_ACTIVITY_REQUEST_CODE = 3;
    public static final String TAG = "TeamActivity";
    private static final int PICK_TEAM_IMAGE = 7;

    private TeamViewModel teamViewModel;
    private SharedPreferences sharedPreferences;

    private BottomNavigationView bottomNavigationView;
    private FloatingActionButton addPlayerButton;
    private RecyclerView teamRecyclerView;
    private TeamAdapter teamAdapter;
    private ImageView teamImageView;

    private Bitmap teamBitmap;
    private String teamImageFilename;
    private String teamName;

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.i(TAG, "reached onActivityResult().");

        if (requestCode == ADD_PLAYER_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            PlayerEntity playerEntity = (PlayerEntity) data.getExtras().get(AddPlayerActivity.EXTRA_REPLY);
            teamViewModel.insert(playerEntity);
            teamAdapter.setTeam(teamViewModel.getAllPlayers().getValue());

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

                sharedPreferences = getSharedPreferences(FirstRunActivity.PREFERENCES, MODE_PRIVATE);
                String imageFileName = "TEAM_IMAGE" + "_" + new Date();
                new SwapTeamImageAsyncTask(
                        imageFileName,
                        teamBitmap,
                        sharedPreferences,
                        TeamActivity.this
                ).execute();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team);

        bottomNavigationView = findViewById(R.id.teamBottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.team);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.team:
                        Log.i(TAG, "already in TeamActivity.");
                        break;
                    case R.id.matches:
                        Intent matchesIntent = new Intent(TeamActivity.this, MatchesActivity.class);
                        startActivity(matchesIntent);
                        overridePendingTransition(R.anim.from_right_in, R.anim.from_left_out);
                        break;
                    case R.id.reports:
                        Intent reportIntent = new Intent(TeamActivity.this, ReportActivity.class);
                        startActivity(reportIntent);
                        overridePendingTransition(R.anim.from_right_in, R.anim.from_left_out);
                        break;
                    default:
                        return false;
                }
                return true;
            }
        });

        // Get Strings from Intent
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (teamImageFilename == null) { teamImageFilename = extras.getString("team_image"); }
        if (teamName == null) { teamName = extras.getString("team_name"); }

        // Set Team Image
        teamImageView = findViewById(R.id.teamImageView);

        File directory = this.getDir("images", Context.MODE_PRIVATE);
        File image = new File(directory, teamImageFilename);
        Uri imageUri = Uri.fromFile(image);

        Glide.with(this)
                .load(imageUri)
                .centerCrop()
                .placeholder(R.drawable.avatar_icon)
                .into(teamImageView);

        // Set up RecyclerView
        teamRecyclerView = (RecyclerView) findViewById(R.id.teamRecyclerView);
        teamAdapter = new TeamAdapter(this);
        teamRecyclerView.setAdapter(teamAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        teamRecyclerView.setLayoutManager(linearLayoutManager);

        // get ViewModel
        teamViewModel = ViewModelProviders.of(this).get(TeamViewModel.class);

        // Observe ViewModel
        teamViewModel.getAllPlayers().observe(this, new Observer<List<PlayerEntity>>() {
            @Override
            public void onChanged(@Nullable List<PlayerEntity> playerEntities) {
                Log.e(TAG, "teamViewModel has changed.");
                // Update cached players
                teamAdapter.setTeam(teamViewModel.getAllPlayers().getValue());
            }
        });

        addPlayerButton = findViewById(R.id.addPlayerButton);
        addPlayerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addPlayerIntent = new Intent(TeamActivity.this, AddPlayerActivity.class);
                startActivityForResult(addPlayerIntent, ADD_PLAYER_ACTIVITY_REQUEST_CODE);
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
