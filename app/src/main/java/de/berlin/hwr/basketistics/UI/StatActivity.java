package de.berlin.hwr.basketistics.UI;

import android.arch.lifecycle.ViewModelProviders;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.github.barteksc.pdfviewer.PDFView;
import java.io.*;
import java.io.IOException;

import de.berlin.hwr.basketistics.R;
import de.berlin.hwr.basketistics.ViewModel.SingleGameReportViewModel;
import de.berlin.hwr.basketistics.ViewModel.TeamViewModel;

public class StatActivity extends AppCompatActivity {

    private final static String TAG = "StatActivity";

    //PDFView pdfView;
    PdfDocument doc = new PdfDocument();
    PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(2250, 1400, 1).create();

    PdfDocument.Page page = doc.startPage(pageInfo);
    SingleGameReportViewModel gameViewModel;




    int measureWidth = View.MeasureSpec.makeMeasureSpec(page.getCanvas().getWidth(), View.MeasureSpec.EXACTLY);
    int measuredHeight = View.MeasureSpec.makeMeasureSpec(page.getCanvas().getHeight(), View.MeasureSpec.EXACTLY);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.stat_single_game_and_player);

        gameViewModel = ViewModelProviders.of(this).get(SingleGameReportViewModel.class);

        gameViewModel.setMatchId(1);
        gameViewModel.fet
        //pdfView = (PDFView) findViewById(R.id.pdfView);
        //pdfView.fromAsset("Ergebnisdokument Basketistics.pdf").load();


        /*File pdfDir = new File(this.getFilesDir(), "Basketistics.pdf");
        Log.i(TAG, pdfDir.toString());


            if (!pdfDir.exists()) {
                pdfDir.mkdir();
            }
        */

      /*  View content = findViewById(R.id.pdfView);
        content.draw(page.getCanvas());
        doc.finishPage(page);

        FileOutputStream outputStream;

        try {
            outputStream = openFileOutput("test.pdf", Context.MODE_PRIVATE);
            //doc.writeTo(outputStream);
            //doc.close();
        }  catch (FileNotFoundException e) {
        Log.e(TAG, "[createInternalFile]" + e.getMessage());
    } catch (IOException e) {
        Log.e(TAG, "[createInternalFile]" + e.getMessage());
    }
*/
/*
        try {
            pdfView.recycle();
            Log.i(TAG, "View Recycled");
            FileInputStream inputStream = openFileInput("test.pdf");
            Log.i(TAG, "Inputstream initialized");
            pdfView.fromStream(inputStream).load();
            Log.i(TAG, "pdf reloaded");
        }catch(IOException e)
        {
            Log.e(TAG, e.getStackTrace().toString());
        }



*/




    }
}
