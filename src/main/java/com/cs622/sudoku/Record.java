package com.cs622.sudoku;

/**
 * Class: Record
 * Description:
 *    Each Record object holds one row information from database. Record object
 *    can be added into UI table view.
 */

public class Record {
  private String id;
  private String playerName;
  private String difficulty;
  private String time;

  /**
   * Record           (Default constructor)
   */
  public Record(){
    this("","","","");
  }

  /**
   * Record       (Constructor takes param)
   * @param id
   * @param playerName
   * @param difficulty
   * @param time
   */
  public Record(String id, String playerName, String difficulty, String time){
    this.id = id;
    this.playerName = playerName;
    this.difficulty = difficulty;
    this.time = time;
  }

  /**
   * getId     (id Getter method)
   * @return id field
   */
  public String getId() {
    return id;
  }

  /**
   * setId     (id Setter method)
   */
  public void setId(String id) {
    this.id = id;
  }

  /**
   * getPlayerName   (playername Getter method)
   * @return playerName field
   */
  public String getPlayerName() {
    return playerName;
  }

  /**
   * setPlayerName     (playerName Setter method)
   */
  public void setPlayerName(String playerName) {
    this.playerName = playerName;
  }

  /**
   * getDifficulty   (difficulty Getter method)
   * @return difficulty field
   */
  public String getDifficulty() {
    return difficulty;
  }

  /**
   * setDifficulty     (difficulty Setter method)
   */
  public void setDifficulty(String difficulty) {
    this.difficulty = difficulty;
  }

  /**
   * getTime   (time Getter method)
   * @return time field
   */
  public String getTime() {
    return time;
  }

  /**
   * setTime     (time Setter method)
   */
  public void setTime(String time) {
    this.time = time;
  }


}
