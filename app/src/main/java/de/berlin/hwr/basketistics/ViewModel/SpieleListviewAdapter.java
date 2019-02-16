package de.berlin.hwr.basketistics.ViewModel;

import android.app.LauncherActivity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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


        return super.getView(position, convertView, parent);
    }

    @Override
    public SpieleListViewItem getItem(int position) {
        return super.getItem(position);
    }
}
