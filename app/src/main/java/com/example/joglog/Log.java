package com.example.joglog;

import java.util.Date;

/**
 * Log class represents a log that contains all of the information for a single run
 *
 * @author Benjamin Tarmann
 */
public class Log {
    private Date date;
    private Time time;
    private double distance;
    private double milePace;
    private String notes;

    /**
     * Parameterized constructor initializes a new Log object with a date, time, distance, and notes
     *
     * @param date the date of this new log
     * @param time the time of this new log
     * @param distance the distance of this new log
     * @param notes the notes of this new log
     */
    public Log(Date date, Time time, Double distance, String notes) {
        this.date = date;
        this.time = time;
        this.distance = distance;
        double totalTime = ((double) time.getMinutes()) + (((double) time.getSeconds()) / 60.0);
        this.milePace = totalTime / distance;
        this.notes = notes;
    }

    /**
     * Accessor method for the date field of this log
     *
     * @return the date of this log
     */
    public Date getDate() {
        return this.date;
    }

    /**
     * Accessor method for the time field of this log
     *
     * @return the time of this log
     */
    public Time getTime() {
        return this.time;
    }

    /**
     * Accessor method for the distance field of this log
     *
     * @return the distance of this log in miles
     */
    public double getDistance() {
        return this.distance;
    }

    /**
     * Accessor method for the mile pace of this log
     *
     * @return the mile pace of this log in minutes/mile
     */
    public double getMilePace() {
        return this.milePace;
    }

    /**
     * Accessor method for the notes of this log
     *
     * @return the notes of this log
     */
    public String getNotes() {
        return this.notes;
    }

    /**
     * Overridden toString method for the Log class creates a string in HTML of how this log is to
     * be formatted when it is output.
     *
     * @return HTML formatting of the output of this log
     */
    @Override
    public String toString() {
        return "<h1>" + (date.getMonth() + 1) + "/" + date.getDate() + "/" + date.getYear() + "<\\h1>" +
                "<p>" + "Time: " + time.getMinutes() + ":" + String.format("%02d", time.getSeconds()) + "<\\p>" +
                "<p>" + "Distance: " + distance + " miles<\\p>" +
                "<p>" + "Pace: " + (int) milePace + ":" + String.format("%02d" , Math.round(((milePace - (int) milePace) * 60))) + "/mile<\\p>" +
                "<p>" + notes + "<\\p>";
    }


}
