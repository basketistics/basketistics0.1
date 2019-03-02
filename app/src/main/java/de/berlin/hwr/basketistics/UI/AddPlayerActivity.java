package de.berlin.hwr.basketistics.UI;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import de.berlin.hwr.basketistics.Persistency.Entities.PlayerEntity;
import de.berlin.hwr.basketistics.R;

// TODO: Set Flag FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS to Intent calling this Activity
public class AddPlayerActivity extends AppCompatActivity {

    public final static String EXTRA_REPLY = "de.hwr.basketistis.AddPlayerActivity.REPLY";

    // For Camera usage
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int PICK_IMAGE = 2;
    private String currentFotoPath;

    private final static String TAG = "AddPlayerActivity";

    private ImageView playerImageView;
    private Button takePictureButton;
    private EditText playerLastNameEditText;
    private EditText playerFirstNameEditText;
    private EditText playerNumberEditText;
    private EditText playerDescriptionEditText;
    private Button addPlayerButton;

    // Not used right now
    // TODO: Implement taking actual pictures
    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(currentFotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }

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
        Log.i(TAG, currentFotoPath);
        return image;
    }

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
                InputStream inputStream = this.getContentResolver().openInputStream(data.getData());
                Bitmap bitmap;
                bitmap = BitmapFactory.decodeStream(inputStream);
                playerImageView.setImageBitmap(bitmap);
                // TODO: Save image to database (?).
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        /*
        Having trouble here...

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            super.onActivityResult(requestCode, resultCode, data);

            // https://github.com/Vydia/react-native-background-upload/issues/8
            String augmentedFilePath = "file://" + currentFotoPath;
            Log.i(TAG, augmentedFilePath);
            File file = new File(Environment.getExternalStorageDirectory().getPath(), augmentedFilePath);
            Uri fotoUri = Uri.fromFile(file);
            Bitmap bitmap;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), fotoUri);
                // bitmap = cropAndScale(bitmap, 300); // Maybe do some scaling?
                playerImageView.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        */
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
                setResult(RESULT_OK, teamActivityIntent);
                finish();
            }
        });
    }
}
