package de.berlin.hwr.basketistics.UI;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;

import de.berlin.hwr.basketistics.ImageSaver;

public class SwapTeamImageAsyncTask extends AsyncTask {

    private static final String TAG = "SwapTeamImageAsyncTask";

    private Bitmap bitmap;
    private String fileName;
    private SharedPreferences sharedPreferences;
    private Context context;

    public SwapTeamImageAsyncTask(String fileName,
                               Bitmap bitmap,
                               SharedPreferences sharedPreferences,
                               Context context) {
        this.bitmap = bitmap;
        this.fileName = fileName;
        this.sharedPreferences = sharedPreferences;
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected Object doInBackground(Object[] objects) {

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("team_image");
        editor.putString("team_image", fileName);
        editor.commit();

        Log.e(TAG, "Editor committed.");

        ImageSaver imageSaver = new ImageSaver(context);
        imageSaver.setExternal(false)
                .setFileName(fileName)
                .setDirectoryName("images")
                .save(bitmap);

        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);

    }

}
