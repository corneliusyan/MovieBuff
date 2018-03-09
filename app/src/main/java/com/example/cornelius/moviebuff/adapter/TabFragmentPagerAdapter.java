package com.example.cornelius.moviebuff.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.cornelius.moviebuff.fragment.ComingSoonFragment;
import com.example.cornelius.moviebuff.fragment.FavouriteFragment;
import com.example.cornelius.moviebuff.fragment.NowShowingFragment;
import com.example.cornelius.moviebuff.fragment.PopularFragment;

/**
 * Created by Cornelius on 24/02/2018.
 */

public class TabFragmentPagerAdapter extends FragmentPagerAdapter{

    String[] title = new String[]{
            "Now Showing", "Coming Soon","Most Popular","Favourite"
    };

    public TabFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position){
            case 0:
                fragment =  new NowShowingFragment();
                break;
            case 1:
                fragment = new ComingSoonFragment();
                break;
            case 2:
                fragment = new PopularFragment();
                break;
            case 3:
                fragment = new FavouriteFragment();
                break;
            default:
                fragment = null;
                break;
        }

        return fragment;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }
    @Override
    public int getCount() {
        return title.length;
    }

}
