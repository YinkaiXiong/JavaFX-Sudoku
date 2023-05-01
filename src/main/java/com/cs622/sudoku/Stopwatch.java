package com.cs622.sudoku;

/**
 * Class: Stopwatch
 *
 * Description:
 *    Stopwatch class is a time counter that display on UI.
 *
 * Source: https://www.youtube.com/watch?v=zJZ-ogqin2o
 */

public class Stopwatch{
  private int hour;
  private int minute;
  private int second;

  /**
   * Stopwatch  (constructor)
   * Initial all variables to 0.
   */
  public Stopwatch() {
    this.hour = 0;
    this.minute = 0;
    this.second = 0;
  }

  /**
   * getTime    (Output formatted time)
   *  Input: None
   *  Output: None
   *  Return: formatted time.
   */
  public String getTime() {
    return hour + " : " + minute + " : " + second;
  }

  /**
   * oneSecond     (Increase value of minute, second, and hours based on the rate
   * of the calling method specified.)
   *  Input: None
   *  Output: None
   *  Return: None
   */
  public void oneSecond() {
    second++;
    if(second == 60){
      minute++;
      second = 0;
      if(minute == 60){
        hour++;
        minute = 0;
        if(hour == 24){
          hour = 0;
        }
      }
    }
  }
}
