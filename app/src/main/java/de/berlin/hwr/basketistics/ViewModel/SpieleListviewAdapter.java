package de.berlin.hwr.basketistics.ViewModel;

import android.app.LauncherActivity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import de.berlin.hwr.basketistics.R;

public class SpieleListviewAdapter extends ArrayAdapter<SpieleListViewItem> {
    private int resourceLayout;
    private Context kontext;
    public SpieleListviewAdapter(Context kontext, int resourceLayout, List<SpieleListViewItem> items){
        super(kontext, resourceLayout, items);
        this.resourceLayout = resourceLayout;
        this.kontext = kontext;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if(v == null){
            LayoutInflater lif = LayoutInflater.from(kontext);
            v = lif.inflate(resourceLayout, null);
        }

        SpieleListViewItem slvi = getItem(position);

        if(slvi != null){
            TextView date = v.findViewById(R.id.date);
            TextView time = v.findViewById(R.id.time);
            TextView heim = v.findViewById(R.id.heim);
            TextView heim_score = v.findViewById(R.id.heim_score);
            TextView gast = v.findViewById(R.id.gast);
            TextView gast_score = v.findViewById(R.id.gast_score);
            TextView ort = v.findViewById(R.id.ort);

            date.setText(slvi.getDate());
            if(time != null){time.setText(slvi.getZeit());}
            if(heim != null){heim.setText(slvi.getHeim());}
            if(heim_score != null){heim_score.setText(slvi.getPunkte_heim());}
            if(gast != null){gast.setText(slvi.getGast());}
            if(gast_score != null){gast_score.setText(slvi.getPunkte_gast());}
            if(ort != null){ort.setText(slvi.getOrt());}

        }

        return v;
    }

    @Override
    public SpieleListViewItem getItem(int position) {
        return super.getItem(position);
    }
}
