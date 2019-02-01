package de.berlin.hwr.basketistics.UI;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import de.berlin.hwr.basketistics.R;
import de.berlin.hwr.basketistics.ViewModel.BasketisticsViewModel;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private Button shot_1_button;
    private BasketisticsViewModel basketisticsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);

        basketisticsViewModel = ViewModelProviders.of(this).get(BasketisticsViewModel.class);

        shot_1_button = findViewById(R.id.points_1);
        shot_1_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                basketisticsViewModel.incPoints(3);
            }
        });
    }


}
