package de.berlin.hwr.basketistics.UI;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    List<Fragment> fragments;
    public static int pos = 0;


    public ViewPagerAdapter(FragmentManager fm, List<Fragment> myFragments) {
        super(fm);
        fragments = myFragments;
    }


    @Override
    public Fragment getItem(int position) {

        return fragments.get(position);

    }

    @Override
    public int getCount() {

        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {

        setPos(position);

        String PageTitle = "";

        switch(pos)
        {
            case 0:
                PageTitle = "Team Stats";
                break;
            case 1:
                PageTitle = "Player Stats";
                break;
        }
        return PageTitle;
    }

    public static int getPos() {
        return pos;
    }

    public static void setPos(int pos) {
        ViewPagerAdapter.pos = pos;
    }


}



