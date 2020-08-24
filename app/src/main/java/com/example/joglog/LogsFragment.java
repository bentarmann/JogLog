package com.example.joglog;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Class represents the LogsFragment which is the Logs tab in the MainActivity
 *
 * @author Benjamin Tarmann
 */
public class LogsFragment extends Fragment {
    /**
     * This private inner class extends the ArrayAdapter class and allows for changing the formatting
     * of the TextView inside of the ListView that contains the logs
     */
    private class LogAdapter extends ArrayAdapter {
        private LogAdapter(ArrayList<Log> logs) {
            super(getActivity(), android.R.layout.simple_list_item_1, logs);
        }

        @Override
        public View getView (int position, View convertView, ViewGroup parent) {
            View row = convertView;
            if (row == null) {
                LayoutInflater inflater = LogsFragment.this.getLayoutInflater();
                row = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
            }

            Log log = (Log) getItem(position);

            ((TextView) row.findViewById(android.R.id.text1)).setText(Html.fromHtml(log.toString()));
            return row;
        }
    }

    private static ArrayList<Log> jogLogs;
    private ListView listView;
    private ArrayAdapter adapter;
    private FloatingActionButton addLogButton;
    private View view;

    /**
     * Required empty public constructor
     */
    public LogsFragment() { }

    /**
     * Method creates the LogsFragment view
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return an instance of the created LogsFragment view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = (View) inflater.inflate(R.layout.fragment_logs, container, false);

        if (jogLogs == null) {
            jogLogs = new ArrayList<Log>();
        }

        // floating action button action
        addLogButton = view.findViewById(R.id.addLogButton);
        addLogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addLog();
            }
        });

        // list view
        if (jogLogs != null) {
            listView = (ListView) view.findViewById(R.id.logs);

            // sort jogLogs by date so that most recent log appears first
            Comparator<Log> compareByDate = new Comparator<Log>() {
                @Override
                public int compare(Log o1, Log o2) {
                    return -o1.getDate().compareTo(o2.getDate());
                }
            };
            Collections.sort(jogLogs, compareByDate);

            adapter = new LogsFragment.LogAdapter(jogLogs);
            listView.setAdapter(adapter);

            // remove log functionality
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView a, View v, int position, long id) {
                    removeLog(a, v, position, id);
                }
            });
        }

        // Inflate the layout for this fragment
        return view;
    }

    /**
     * Contains the action for the floating action button to add a log
     */
    public void addLog() {
        Intent intent = new Intent(getActivity(), AddLogActivity.class);
        startActivity(intent);
    }

    /**
     * Contains the functionality for removing a log when a user holds down on a log entry
     *
     * @param a the AdapterView to remove a log from
     * @param v the LogsFragment view
     * @param position the position in the list view to remove from
     * @param id
     */
    public void removeLog(AdapterView a, View v, final int position, long id) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle("Delete?");
        dialog.setMessage("Are you sure you want to delete this log?");

        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            public void onClick(DialogInterface dialog, int which) {
                jogLogs.remove(position);
                adapter.notifyDataSetChanged();
                StatsFragment.updateStats();
            }
        });

        dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }

        });

        dialog.show();
    }

    /**
     * Static method allows adding a new log from a different class/activity.
     *
     * @param log the log to add
     */
    public static void addToJogLog(Log log) {
        jogLogs.add(log);
    }

    /**
     * Static method allows getting all of the jog logs from a different class/activity
     *
     * @return the list of logs
     */
    public static ArrayList<Log> getJogLogs() {
        return jogLogs;
    }
}
