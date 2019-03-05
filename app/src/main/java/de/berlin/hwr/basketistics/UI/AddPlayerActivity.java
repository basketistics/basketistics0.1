package de.berlin.hwr.basketistics.UI;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;


import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import de.berlin.hwr.basketistics.Persistency.Entities.PlayerEntity;
import de.berlin.hwr.basketistics.R;

// TODO: Set Flag FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS to Intent calling this Activity
public class AddPlayerActivity extends AppCompatActivity {

    public final static String EXTRA_REPLY = "de.hwr.basketistis.AddPlayerActivity.REPLY";

    // For image
    static final int PICK_IMAGE = 2;
    public static final String IMAGE_URI = "de.hwr.basketistis.AddPlayerActivity.IMAGE_URI";
    private Uri imageUri;

    private final static String TAG = "AddPlayerActivity";

    private ImageView playerImageView;
    private Button takePictureButton;
    private EditText playerLastNameEditText;
    private EditText playerFirstNameEditText;
    private EditText playerNumberEditText;
    private EditText playerDescriptionEditText;
    private Button addPlayerButton;

    private void dispatchChoosePictureIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == PICK_IMAGE) {
            playerImageView.setImageURI(data.getData());
            imageUri = data.getData();
        }
    }

    private void bindViews() {
        playerImageView = (ImageView) findViewById(R.id.add_playerImageView);
        takePictureButton = (Button) findViewById(R.id.add_playerFotoButton);
        playerLastNameEditText = (EditText) findViewById(R.id.add_playerLastNamePlainText);
        playerFirstNameEditText = (EditText) findViewById(R.id.add_playerFirstNamePlainText);
        playerNumberEditText = (EditText) findViewById(R.id.add_playerNumber);
        playerDescriptionEditText = (EditText) findViewById(R.id.add_playerDescription);
        addPlayerButton = (Button) findViewById(R.id.add_addPlayerButton);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_player);

        bindViews();

        takePictureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchChoosePictureIntent();
                // dispatchTakePictureIntent();
            }
        });

        addPlayerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // TODO: Check whether user input is legit.
                Log.i(TAG, "addPlayerButton was clicked.");
                Log.i(TAG, "Player Firstname: " + playerFirstNameEditText.getText());
                Log.i(TAG, "Player Lastname: " + playerLastNameEditText.getText());
                Log.i(TAG, "Player Number: " + playerNumberEditText.getText());
                Log.i(TAG, "Player Description: " + playerDescriptionEditText.getText());

                // Create player from user input and pass via intent to TeamActivity
                Intent teamActivityIntent = new Intent();
                teamActivityIntent.putExtra(EXTRA_REPLY, new PlayerEntity(
                        playerLastNameEditText.getText().toString(),
                        playerFirstNameEditText.getText().toString(),
                        Integer.parseInt(playerNumberEditText.getText().toString()),
                        playerDescriptionEditText.getText().toString()));
                teamActivityIntent.putExtra(IMAGE_URI, imageUri);
                setResult(RESULT_OK, teamActivityIntent);
                finish();
            }
        });
    }
}
