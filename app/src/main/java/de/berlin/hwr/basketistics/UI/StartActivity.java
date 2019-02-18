package de.berlin.hwr.basketistics.UI;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;

import de.berlin.hwr.basketistics.R;
import de.berlin.hwr.basketistics.ViewModel.BasketisticsViewModel;

public class StartActivity extends AppCompatActivity {

    public static final String TAG = "StartActivity";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navbar);

    }
}
