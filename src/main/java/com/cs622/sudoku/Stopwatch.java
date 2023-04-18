package com.cs622.sudoku;

import java.util.Timer;

public class Stopwatch{
  private int hour;

  private int minute;

  private int second;

  public Stopwatch(){
    this.hour = 0;
    this.minute = 0;
    this.second = 0;
  }

  public String getTime(){
    return hour + " : " + minute + " : " + second;
  }

  public void oneSecond(){
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
