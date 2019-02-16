package de.berlin.hwr.basketistics.UI;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import de.berlin.hwr.basketistics.R;
import de.berlin.hwr.basketistics.ViewModel.SpieleListViewItem;
import de.berlin.hwr.basketistics.ViewModel.SpieleListviewAdapter;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.startactivity);
        ListView spiele = findViewById(R.id.lv_spiele);

        ArrayList<SpieleListViewItem> li = new ArrayList<SpieleListViewItem>();

        li.add(new SpieleListViewItem("01.01.2019", "22:00", "Boris", "99", "Welt", "00", "Berlin"));
        li.add(new SpieleListViewItem("01.01.2019", "22:00", "Boris", "99", "Welt", "00", "Berlin"));
        li.add(new SpieleListViewItem("01.01.2019", "22:00", "Boris", "99", "Welt", "00", "Berlin"));
        li.add(new SpieleListViewItem("01.01.2019", "22:00", "Boris", "99", "Welt", "00", "Berlin"));
        li.add(new SpieleListViewItem("01.01.2019", "22:00", "Boris", "99", "Welt", "00", "Berlin"));

        SpieleListviewAdapter adapter = new SpieleListviewAdapter(this, R.layout.spiele_listitem, li);
        spiele.setAdapter(adapter);

    }

}
