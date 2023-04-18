package com.cs622.sudoku;

public class Record {

  private String id;

  private String playerName;

  private String difficulty;

  private String time;

  public Record(){
    this("","","","");
  }

  public Record(String id, String playerName, String difficulty, String time){
    this.id = id;
    this.playerName = playerName;
    this.difficulty = difficulty;
    this.time = time;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getPlayerName() {
    return playerName;
  }

  public void setPlayerName(String playerName) {
    this.playerName = playerName;
  }

  public String getDifficulty() {
    return difficulty;
  }

  public void setDifficulty(String difficulty) {
    this.difficulty = difficulty;
  }

  public String getTime() {
    return time;
  }

  public void setTime(String time) {
    this.time = time;
  }




}
