package de.berlin.hwr.basketistics.UI;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import de.berlin.hwr.basketistics.Persistency.Entities.Player;
import de.berlin.hwr.basketistics.R;

// TODO: Set Flag FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS to Intent calling this Activity
public class AddPlayerActivity extends AppCompatActivity {

    // For Camera usage
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private String currentFotoPath;

    private final static String TAG = "AddPlayerActivity";

    private ImageView playerImageView;
    private Button takePictureButton;
    private EditText playerNameEditText;
    private EditText playerNumberEditText;
    private EditText playerDescriptionEditText;
    private Button addPlayerButton;

    // TODO: Properly extend OnClickListener so Intent does not have to be public
    public Intent teamActivityIntent;

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File fotoFile = null;
            try {
                fotoFile = createImageFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (fotoFile != null) {
                Uri fotoUri = FileProvider.getUriForFile(
                        this,
                        "de.berlin.hwr.basketistics.fileprovider",
                        fotoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, fotoUri);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,      /* prefix */
                ".jpg",      /* suffix */
                storageDir          /* directory */
        );
        // To be use by the Intent
        currentFotoPath = image.getAbsolutePath();
        return image;
    }

    private void bindViews() {
        playerImageView = (ImageView) findViewById(R.id.add_playerImageView);
        takePictureButton = (Button) findViewById(R.id.add_playerFotoButton);
        playerNameEditText = (EditText) findViewById(R.id.add_playerNamePlainText);
        playerNumberEditText = (EditText) findViewById(R.id.add_playerNumber);
        playerDescriptionEditText = (EditText) findViewById(R.id.add_playerDescription);
        addPlayerButton = (Button) findViewById(R.id.add_addPlayerButton);
    }

    // Coming back from Camera Intent
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            playerImageView.setImageBitmap(imageBitmap);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_player);

        bindViews();

        takePictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });

        // Prepare change to TeamActivity
        teamActivityIntent = new Intent(this, TeamActivity.class);
        addPlayerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // TODO: Write inserted data to ViewModel
                // TODO: Check whether user input is legit.
                Log.i(TAG, "addPlayerButton was clicked.");
                Log.i(TAG, "Player Name: " + playerNameEditText.getText());
                Log.i(TAG, "Player Number: " + playerNumberEditText.getText());
                Log.i(TAG, "Player Description: " + playerDescriptionEditText.getText());

                // Create player from user input and pass via intent to TeamActivity
                teamActivityIntent.putExtra("player", new Player(
                        playerNameEditText.getText().toString(),
                        Integer.parseInt(playerNumberEditText.getText().toString()),
                        playerDescriptionEditText.getText().toString()));

                // Start TeamActivity
                startActivity(teamActivityIntent);
            }
        });
    }
}
