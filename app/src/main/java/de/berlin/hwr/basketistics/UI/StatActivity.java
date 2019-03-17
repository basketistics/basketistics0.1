package de.berlin.hwr.basketistics.UI;

import android.arch.lifecycle.ViewModelProviders;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import de.berlin.hwr.basketistics.R;
import de.berlin.hwr.basketistics.ViewModel.SingleGameReportViewModel;

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


        setContentView(R.layout.stat_single_player_and_game);

        //-------TextViews--------
        TextView pointsMade = findViewById(R.id.visu_points_val);
        TextView freeThrows = findViewById(R.id.visu_fts_val);
        TextView fieldGoals = findViewById(R.id.visu_fgs_val);
        TextView fieldGoals3 = findViewById(R.id.visu_fgs3_val);
        TextView rebounds = findViewById(R.id.visu_rebounds_val);
        TextView assists = findViewById(R.id.visu_assists_val);
        TextView blocks = findViewById(R.id.visu_blocks_val);
        TextView steals = findViewById(R.id.visu_steals_val);
        TextView turnover = findViewById(R.id.visu_tov_val);
        TextView fouls = findViewById(R.id.visu_fouls_val);

        gameViewModel = ViewModelProviders.of(this).get(SingleGameReportViewModel.class);


        int matchId = getIntent().getExtras().getInt("matchID");
        gameViewModel.setMatchId(matchId);

        SingleGameReportViewModel.PlayerReport reportPlayer1 = gameViewModel.getReportByPlayerId(1);

        Log.i(TAG, ""+reportPlayer1.threePointsAttempt);



        pointsMade.setText(reportPlayer1.onePoint + reportPlayer1.twoPoints*2 + reportPlayer1.threePoints*3+"");
        if(reportPlayer1.onePointAttempt==0)
            freeThrows.setText("0/ 0/ 0%");
        else
        freeThrows.setText(reportPlayer1.onePoint+ "/ " + reportPlayer1.onePointAttempt +"/ " + ((100/(float)reportPlayer1.onePointAttempt)*(float)reportPlayer1.onePoint+"%"));
        if(reportPlayer1.twoPointsAttempt==0)
            fieldGoals.setText("0/ 0/ 0%");
        else
            fieldGoals.setText(reportPlayer1.twoPoints+ "/ " + reportPlayer1.twoPointsAttempt +"/ " + ((100/(float)reportPlayer1.twoPointsAttempt)*(float)reportPlayer1.twoPoints+"%"));
        if(reportPlayer1.threePointsAttempt==0)
            fieldGoals3.setText("0/ 0/ 0%");
        else
            fieldGoals3.setText(reportPlayer1.threePoints+ "/ " + reportPlayer1.threePointsAttempt +"/ " + ((100/(float)reportPlayer1.threePointsAttempt)*(float)reportPlayer1.threePoints+"%"));
        rebounds.setText(reportPlayer1.rebound+"");
        assists.setText(reportPlayer1.assist+"");
        blocks.setText(reportPlayer1.block+"");
        steals.setText(reportPlayer1.steal+"");
        turnover.setText(reportPlayer1.turnover+"");
        fouls.setText(reportPlayer1.foul+"");
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
