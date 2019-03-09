package de.berlin.hwr.basketistics.UI;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

import de.berlin.hwr.basketistics.ImageSaver;
import de.berlin.hwr.basketistics.Persistency.Entities.PlayerEntity;
import de.berlin.hwr.basketistics.R;

// TODO: Set Flag FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS to Intent calling this Activity
public class AddPlayerActivity extends AppCompatActivity {

    public final static String EXTRA_REPLY = "de.hwr.basketistis.AddPlayerActivity.REPLY";

    // For image
    static final int PICK_IMAGE = 2;
    public static final String IMAGE_FILENAME = "de.hwr.basketistis.AddPlayerActivity.IMAGE_URI";
    Bitmap playerBitmap;

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
            try {

                // Get Image
                Uri selectedImage = data.getData();
                InputStream inputStream =
                        getContentResolver().openInputStream(selectedImage);
                BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
                playerBitmap = BitmapFactory.decodeStream(bufferedInputStream);

                // Set ImageView
                playerImageView.setImageBitmap(playerBitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

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

                // Save Bitmap to app storage and return Uri
                String imageFileName = playerFirstNameEditText.getText().toString() + "_" + playerLastNameEditText.getText().toString() + "_" + new Date();
                new SaveBitmpAsynchTask(imageFileName, playerBitmap).execute();

                // Create player from user input and pass via intent to TeamActivity
                Intent teamActivityIntent = new Intent();
                teamActivityIntent.putExtra(EXTRA_REPLY, new PlayerEntity(
                        playerLastNameEditText.getText().toString(),
                        playerFirstNameEditText.getText().toString(),
                        Integer.parseInt(playerNumberEditText.getText().toString()),
                        playerDescriptionEditText.getText().toString()));
                teamActivityIntent.putExtra(IMAGE_FILENAME, imageFileName);
                setResult(RESULT_OK, teamActivityIntent);
                finish();
            }
        });
    }

    private class SaveBitmpAsynchTask extends AsyncTask {

        private Bitmap bitmap;
        private String fileName;

        public SaveBitmpAsynchTask(String fileName, Bitmap bitmap) {
            this.bitmap = bitmap;
            this.fileName = fileName;
        }

        @Override
        protected Object doInBackground(Object[] objects) {

            ImageSaver imageSaver = new ImageSaver(getApplicationContext());
            imageSaver.setExternal(false)
                    .setFileName(fileName)
                    .setDirectoryName("images")
                    .save(bitmap);

            return null;
        }
    }
}
