package com.example.joglog;

import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Class represents the StatsFragment which is the Stats tab in the MainActivity
 *
 * @author Benjamin Tarmann
 */
public class StatsFragment extends Fragment {

    private TextView textView;
    private static View view;

    /**
     * Required empty public constructor for fragment
     */
    public StatsFragment() { }

    /**
     * Method creates the StatsFragment view
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return and instance of the StatsFragment view
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_stats, container, false);

        updateStats();

        return view;
    }

    /**
     * This method computes the weekly mile total for the current week
     *
     * @param logs the list of logs to compute stats from
     * @return the weekly mile total for the current week
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    private static double computeWeeklyMileTotal(ArrayList<Log> logs) {
        // compute weekly mile total
        double weeklyMileTotal = 0;
        if (logs != null) {
            Calendar today = Calendar.getInstance();
            for (int i = logs.size() - 1; i >= 0; i--) {
                Calendar comparedDate = Calendar.getInstance();
                comparedDate.setTime(logs.get(i).getDate());
                // offset year for comparable year across Date and Calendar class
                comparedDate.set(Calendar.YEAR, comparedDate.get(Calendar.YEAR) - 1900);

                int currentYear = today.getWeekYear();
                int currentWeek = today.get(today.WEEK_OF_YEAR);
                int comparedYear = comparedDate.getWeekYear();
                int comparedWeek = comparedDate.get(comparedDate.WEEK_OF_YEAR);

                // check if currentLog is in this week
                if (currentYear == comparedYear && currentWeek == comparedWeek) {
                    weeklyMileTotal += logs.get(i).getDistance();
                } else {
                    continue;
                }
            }
        }

        return weeklyMileTotal;
    }

    /**
     * This method computes the lifetime mile total
     *
     * @param logs the list of logs to compute stats from
     * @return the lifetime mile total
     */
    private static double computeLifetimeMileTotal(ArrayList<Log> logs) {
        double lifetimeMileTotal = 0;

        for (Log currentLog : logs) {
            lifetimeMileTotal += currentLog.getDistance();
        }

        return lifetimeMileTotal;
    }

    /**
     * This method computes the distance of the longest run
     *
     * @param logs the list of logs to compute stats from
     * @return the distance of the longest run
     */
    private static double computeLongestRun(ArrayList<Log> logs) {
        double longestRun = 0;

        for (Log currentLog : logs) {
            if (currentLog.getDistance() > longestRun) {
                longestRun = currentLog.getDistance();
            }
        }

        return longestRun;
    }

    /**
     * This method computes the fastest pace over the course of a run. The pace is stored in
     * fastestPace[0], while the mileage of the log with the fastest pace is stored in fastestPace[1]
     *
     * @param logs the list of logs to compute stats from
     * @return the fastest pace as well as the distance this fastest pace covered
     */
    private static double[] computeFastestPace(ArrayList<Log> logs) {
        double[] fastestPace = new double[2];
        fastestPace[0] = Double.POSITIVE_INFINITY;

        for (Log currentLog : logs) {
            if (currentLog.getMilePace() < fastestPace[0]) {
                fastestPace[0] = currentLog.getMilePace();
                fastestPace[1] = currentLog.getDistance();
            }
        }

        // for case where no fastest pace is found, set the pace to 0.0 for display purposes
        if (Double.compare(fastestPace[0], Double.POSITIVE_INFINITY) == 0) {
            fastestPace[0] = 0.0;
        }

        return fastestPace;
     }

    /**
     * Method updates the stats and displays them in this fragment's TextView
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
     public static void updateStats() {
         ArrayList<Log> logs = LogsFragment.getJogLogs();

         // compute stats
         double weeklyMileTotal = computeWeeklyMileTotal(logs);
         double lifetimeMileTotal = computeLifetimeMileTotal(logs);
         double longestRun = computeLongestRun(logs);
         double[] fastestPace = computeFastestPace(logs);

         // set stats text
         TextView textView = view.findViewById(R.id.textView);
         String stats = "<h1>Stats<\\h1>" +
                 "<p>Weekly mile total: " + weeklyMileTotal + " miles<\\p>" +
                 "<p>Lifetime mile total: " + lifetimeMileTotal + " miles<\\p>" +
                 "<p>Longest run: " + longestRun + " miles<\\p>" +
                 "<p>Fastest pace: " + (int) fastestPace[0] + ":" + String.format("%02d", Math.round(((fastestPace[0] - (int) fastestPace[0]) * 60))) +
                 "/mile (for " + fastestPace[1] + " miles)<\\p>";
         textView.setText(Html.fromHtml(stats));
     }
}
