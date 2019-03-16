package de.berlin.hwr.basketistics.UI;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.berlin.hwr.basketistics.R;

public class PlayerReportsFragment extends ListFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.player_list_popup, container, false);


        View rootView = inflater.inflate(R.layout.fragment_colors, container, false);
        // 1. get a reference to recyclerView
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.list);

        // 2. set layoutManger
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // this is data fro recycler view
        ItemData itemsData[] = {
                new ItemData("Indigo", R.drawable.circle),
                new ItemData("Red", R.drawable.color_ic_launcher),
                new ItemData("Blue", R.drawable.indigo),
                new ItemData("Green", R.drawable.circle),
                new ItemData("Amber", R.drawable.color_ic_launcher),
                new ItemData("Deep Orange", R.drawable.indigo)
        };


        // 3. create an adapter
        MyAdapter mAdapter = new MyAdapter(itemsData);
        // 4. set adapter
        recyclerView.setAdapter(mAdapter);
        // 5. set item animator to DefaultAnimator
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        return rootView;
    }

}
