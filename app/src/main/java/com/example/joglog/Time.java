package com.example.joglog;

/**
 * Time class represents a time with minutes and seconds
 *
 * @author Benjamin Tarmann
 */
public class Time {
    private int minutes;
    private int seconds;

    /**
     * Parameterized constructor initializes a new Time object with minutes and seconds
     *
     * @param minutes the minutes of this time
     * @param seconds the seconds of this time
     */
    public Time(int minutes, int seconds) {
        this.minutes = minutes;
        this.seconds = seconds;
    }
    /**
     * Accessor method for the minutes field of this time
     *
     * @return the minutes of this time
     */
    public int getMinutes() {
        return this.minutes;
    }

    /**
     * Accessor method for the seconds field of this time
     *
     * @return the seconds of this time
     */
    public int getSeconds() {
        return this.seconds;
    }
}
