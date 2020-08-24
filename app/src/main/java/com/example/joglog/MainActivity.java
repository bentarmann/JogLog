package com.example.joglog;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

/**
 * Class represents the main activity of this app
 *
 * @author Benjamin Tarmann
 */
public class MainActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private PageAdapter pageAdapter;
    private TabItem logsTab;
    private TabItem statsTab;
    private TabItem shoesTab;

    /**
     * Method creates the MainActivity view whenever MainActivity is entered (on app start or when
     * returning from another activitiy)
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabFunctionality();
    }

    /**
     * Method implements the tab functionality to switch between the 3 tabs in the MainActivity (logs,
     * stats, and shoes)
     */
    public void tabFunctionality() {
        // tab functionality
        tabLayout = findViewById(R.id.tabLayout);
        logsTab = findViewById(R.id.logsTab);
        statsTab = findViewById(R.id.statsTab);
        shoesTab = findViewById(R.id.shoesTab);
        viewPager = findViewById(R.id.viewPager);

        pageAdapter = new PageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pageAdapter);

        // implement switching between tabs
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }

    public static void refresh() {

    }
}
