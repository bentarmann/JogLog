package com.example.joglog;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/**
 * PageAdapter class extends FragmentPagerAdapter and enables the swiping between tabs to see different
 * fragment views
 *
 * @author Benjamin Tarmann
 */
public class PageAdapter extends FragmentPagerAdapter {

    private int numOfTabs;

    /**
     * Parameterized constructor creates the new PageAdapter and sets the number of tabs
     *
     * @param fm
     * @param numOfTabs
     */
    public PageAdapter(@NonNull FragmentManager fm, int numOfTabs) {
        super(fm);
        this.numOfTabs = numOfTabs;
    }

    /**
     * Method returns the fragment to be viewed based on which tab is selected or swiped to
     *
     * @param position the position of the selected tab, indicating which fragment is to be shown
     * @return the fragment to be viewed based on the position
     */
    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch(position) {
            case 0:
                return new LogsFragment();
            case 1:
                return new StatsFragment();
            case 2:
                return new ShoesFragment();
            default:
                return null;
        }
    }

    /**
     * Accessor method for the number of tabs
     *
     * @return number of tabs
     */
    @Override
    public int getCount() {
        return numOfTabs;
    }
}
