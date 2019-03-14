package de.berlin.hwr.basketistics.UI;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;

import de.berlin.hwr.basketistics.ImageSaver;
import de.berlin.hwr.basketistics.R;

public class FirstRunActivity extends AppCompatActivity implements OnTeamCreatedListener{

    public static final String PREFERENCES = "de.berlin.hwr.basketistics.PREFERENCES";
    private final static int PICK_IMAGE = 5;

    private EditText teamName;
    private EditText teamHome;
    private EditText teamWhatEver;
    private ImageView teamImageView;
    private Button takePictureButton;
    private Button startButton;
    private ProgressBar progressBar;

    private Bitmap teamBitmap;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_run);

        teamName = findViewById(R.id.firstTeamName);
        teamHome = findViewById(R.id.firstTeamHome);
        teamWhatEver = findViewById(R.id.firstTeamWhatever);
        startButton = findViewById(R.id.firstStartButton);
        teamImageView = findViewById(R.id.first_teamImageView);
        takePictureButton = findViewById(R.id.first_takePictureButton);

        progressBar = findViewById(R.id.firstStartProgressBar);

        sharedPreferences = getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);

        takePictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchChoosePictureIntent();
                // dispatchTakePictureIntent();
            }
        });

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!(teamName.getText().length() < 5 && teamHome.getText().length() < 5 && teamWhatEver.getText().length() < 5)) {

                    // Save Bitmap to app storage and return Uri
                    String imageFileName = "TEAM_IMAGE" + "_" + new Date();
                    new CreateTeamAsyncTask(imageFileName, teamBitmap, FirstRunActivity.this).execute();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == PICK_IMAGE) {
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

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
    }

    private void dispatchChoosePictureIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
    }

    @Override
    public void onTeamCreated() {
        Intent matchesIntent = new Intent(FirstRunActivity.this, MatchesActivity.class);
        startActivity(matchesIntent);
    }

    private class CreateTeamAsyncTask extends AsyncTask {

        private Bitmap bitmap;
        private String fileName;
        private OnTeamCreatedListener onTeamCreatedListener;

        public CreateTeamAsyncTask(String fileName, Bitmap bitmap, OnTeamCreatedListener onTeamCreatedListener) {
            this.bitmap = bitmap;
            this.fileName = fileName;
            this.onTeamCreatedListener = onTeamCreatedListener;
        }

        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Object doInBackground(Object[] objects) {

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("team_name", teamName.getText().toString());
            editor.putString("team_home", teamHome.getText().toString());
            editor.putString("team_whatever", teamWhatEver.getText().toString());
            editor.putString("team_image", fileName);
            editor.putBoolean("first_run", false);
            editor.commit();

            ImageSaver imageSaver = new ImageSaver(getApplicationContext());
            imageSaver.setExternal(false)
                    .setFileName(fileName)
                    .setDirectoryName("images")
                    .save(bitmap);

            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);

            progressBar.setVisibility(View.GONE);
            onTeamCreatedListener.onTeamCreated();
        }

    }
}
