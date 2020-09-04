package com.example.geobike.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.geobike.fragments.BikeFragment;
import com.example.geobike.fragments.MapFragment;
import com.example.geobike.fragments.RideFragment;
import com.example.geobike.fragments.SettingsFragment;

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new MapFragment();
                break;
            case 1:
                fragment = new RideFragment();
                break;
            case 2:
                fragment = new BikeFragment();
                break;
            case 3:
                fragment = new SettingsFragment();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Map";
            case 1:
                return "Ride";
            case 2:
                return "Bike";
            case 3:
                return "Settings";
        }
        return null;
    }
}