package de.berlin.hwr.basketistics.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import de.berlin.hwr.basketistics.R;

// TODO: Set Flag FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS to Intent calling this Activity
public class AddPlayerActivity extends AppCompatActivity {

    private final static String TAG = "AddPlayerActivity";

    private ImageView playerImageView;
    private Button takePictureButton;
    private EditText playerNameEditText;
    private EditText playerNumberEditText;
    private EditText playerDescriptionEditText;
    private Button addPlayerButton;

    // TODO: Properly extend OnClickListener so Intent does not have to be public
    public Intent teamActivityIntent;

    private void bindViews() {
        playerImageView = (ImageView) findViewById(R.id.add_playerImageView);
        takePictureButton = (Button) findViewById(R.id.add_playerFotoButton);
        playerNameEditText = (EditText) findViewById(R.id.add_playerNamePlainText);
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
                // TODO: Take Foto
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

                // Start TeamActivity
                startActivity(teamActivityIntent);
            }
        });
    }
}
