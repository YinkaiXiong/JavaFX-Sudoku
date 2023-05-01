package com.cs622.sudoku;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.util.Duration;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Class: SudokuController
 *
 * Description:
 *    This is the controller class for sudoku-view.fxml.Main controller
 *    of the Sudoku program. Include game logic and game operation functions.
 */

public class SudokuController implements Initializable {

  Button selectedGridBtn = null;

  public String difficulty;
  private String playerName;

  Stopwatch stopwatch = new Stopwatch();

  int[][] gameBoard;
  int[][] easyGameBoard =
      {{0,0,0,8,2,0,0,9,0},
      {0,5,7,6,0,9,0,1,3},
      {0,8,4,0,3,1,0,0,0},
      {0,7,8,0,6,0,4,5,0},
      {0,0,9,1,0,0,0,0,6},
      {5,6,0,3,0,0,9,8,0},
      {8,3,0,4,0,6,0,0,0},
      {0,0,5,0,1,8,0,0,0},
      {1,0,6,7,5,0,2,0,0}};

  int[][] easyGameBoardSolution =
      {{6,1,3,8,2,7,5,9,4},
      {2,5,7,6,4,9,8,1,3},
      {9,8,4,5,3,1,6,2,7},
      {3,7,8,9,6,2,4,5,1},
      {4,2,9,1,8,5,7,3,6},
      {5,6,1,3,7,4,9,8,2},
      {8,3,2,4,9,6,1,7,5},
      {7,4,5,2,1,8,3,6,9},
      {1,9,6,7,5,3,2,4,8}};

  int[][] mediumGameBoard =
      {{9,0,0,3,0,0,0,7,1},
      {4,3,7,8,0,0,2,5,0},
      {0,0,5,0,2,0,0,4,9},
      {0,5,8,4,0,9,0,3,0},
      {7,0,0,1,0,0,0,9,8},
      {2,9,0,0,3,0,0,0,4},
      {0,8,0,0,1,3,0,0,0},
      {3,0,4,6,8,7,0,0,0},
      {1,0,0,2,5,0,0,0,0}};

  int[][] mediumGameBoardSolution =
      {{9,6,2,3,4,5,8,7,1},
      {4,3,7,8,9,1,2,5,6},
      {8,1,5,7,2,6,3,4,9},
      {6,5,8,4,7,9,1,3,2},
      {7,4,3,1,6,2,5,9,8},
      {2,9,1,5,3,8,7,6,4},
      {5,8,6,9,1,3,4,2,7},
      {3,2,4,6,8,7,9,1,5},
      {1,7,9,2,5,4,6,8,3}};

  int[][] hardGameBoard =
      {{7,5,1,8,0,0,0,0,0},
      {0,0,3,0,4,5,0,2,1},
      {4,0,0,1,0,0,0,7,5},
      {0,0,0,0,7,8,0,0,0},
      {0,6,0,4,0,9,0,1,0},
      {0,2,0,5,0,0,0,0,0},
      {0,0,0,0,8,0,0,0,0},
      {1,0,0,0,0,7,0,6,4},
      {0,0,0,0,0,3,0,0,2}};

  int[][] hardGameBoardSolution =
      {{7,5,1,8,3,2,6,4,9},
      {6,9,3,7,4,5,8,2,1},
      {4,8,2,1,9,6,3,7,5},
      {5,1,4,3,7,8,2,9,6},
      {3,6,7,4,2,9,5,1,8},
      {8,2,9,5,6,1,4,3,7},
      {2,7,6,9,8,4,1,5,3},
      {1,3,8,2,5,7,9,6,4},
      {9,4,5,6,1,3,7,8,2}};

  @FXML
  private GridPane gameGridPane;

  @FXML
  private Text stopwatchText;

  @FXML
  private Text resultText;

  //Update stopwatch text one time per second and display on UI.
  Timeline timeline = new Timeline(
      new KeyFrame(Duration.seconds(1),
          e ->{
            stopwatch.oneSecond();
            stopwatchText.setText(stopwatch.getTime());
          })
  );

  /**
   * initialize      (Method from Initializable interface. Invoke when stage view loaded.)
   * Set timeline update indefinitely and start to play.
   *  Input:
   *  @param url
   *  @param resourceBundle
   *  Output: None.
   *  Return: void
   */
  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    stopwatchText.setText(stopwatch.getTime());
    timeline.setCycleCount(Timeline.INDEFINITE);
    timeline.play();
  }

  /**
   * onHistoryBtnClick     (When a button get clicked, load history-view.fxml as
   * a dialog that let player check game history.)
   *  Input:
   *  @param event
   *  @throws IOException
   *  Output: None.
   *  Return: void
   */
  @FXML
  void onHistoryBtnClick(ActionEvent event) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader();
    fxmlLoader.setLocation(getClass().getResource("history-view.fxml"));
    DialogPane historyDialogPane = fxmlLoader.load();

    Dialog<ButtonType> dialog = new Dialog<>();
    dialog.setDialogPane(historyDialogPane);
    dialog.setTitle("Game History");

    //Optional<ButtonType> clickedButton = dialog.showAndWait();
  }

  /**
   * onCheckBtnClick  (When button clicked, check every grid. By compare with the
   * solution, if the number is not match, mark it as red text. Else insert game
   * information into database.)
   *  Input:
   *  @param event
   *  @throws SQLException
   *  Output: None.
   *  Return: void
   */
  @FXML
  void onCheckBtnClick(ActionEvent event) throws SQLException {
    int[][] solution;
    boolean noError = true;
    solution = switch (difficulty) {
      case "Medium" -> mediumGameBoardSolution;
      case "Hard" -> hardGameBoardSolution;
      default -> easyGameBoardSolution;
    };
    //Loop game board to check number in each grid
    for(int i = 0; i < gameBoard.length; i++){
      for(int j = 0; j < gameBoard[i].length; j++){
        Button btn = (Button) getBtnFromGridPane(gameGridPane, i, j);
        int numInGrid = Integer.parseInt(btn.getText());
        if(numInGrid != solution[i][j]){
          btn.setStyle("-fx-text-fill: #cb0000");
          noError = false;
        }
      }
    }

    if(noError){
      //Perform update record to db
      ConnectionClass connectionClass = new ConnectionClass();
      Connection connection = connectionClass.getConnection();

      String sql = "INSERT INTO sudoku.history (`player_name`, `difficulty`, `time`) " +
          "VALUES ('" + playerName + "', '" + difficulty + "', '" + stopwatch.getTime() + "');\n";
      Statement statement = connection.createStatement();
      statement.executeUpdate(sql);

      resultText.setText("Congratulation! You win!");
      //Stop the stopwatch counter
      timeline.stop();
    }

  }

  /**
   * onEraseBtnClick   (When button clicked, it will set the text in selected
   * grid to 0 which means no valid number(1-9. If no selected grid, no action perform.)
   *  Input:
   *  @param event
   *  Output: None.
   *  Return: void
   */
  @FXML
  void onEraseBtnClick(ActionEvent event) {
    if(selectedGridBtn != null){
      //Change the text displayed on UI to 0
      selectedGridBtn.setText("0");

      //Change the value stored in gameBoard to 0
      int row = GridPane.getRowIndex(selectedGridBtn);
      int col = GridPane.getColumnIndex(selectedGridBtn);
      gameBoard[row][col] = 0;
    }

  }

  /**
   * onNumberPadBtnClick     (Set selected grid text to the number which number
   * button is clicked)
   *  Input:
   *  @param event
   *  Output: None.
   *  Return: void
   */
  @FXML
  void onNumberPadBtnClick(ActionEvent event) {
    //Get button text to indicate which button is clicked.
    Button btn = (Button) event.getTarget();
    int numberPad = Integer.parseInt(btn.getText());

    if(selectedGridBtn != null){
      selectedGridBtn.setText(btn.getText());

      int row = GridPane.getRowIndex(selectedGridBtn);
      int col = GridPane.getColumnIndex(selectedGridBtn);
      //If this number already exits in row, col, or box, mark the text to red.
      if(checkInRow(numberPad, row) || checkInCol(numberPad, col) || checkInBox(numberPad, row, col)){
        selectedGridBtn.setStyle("-fx-text-fill: #cb0000");
      }else {
        selectedGridBtn.setStyle("");
      }
      gameBoard[row][col] = numberPad;
    }
  }

  /**
   * onGridBtnClick  (When clicked a grid, mark it as selected grid and add darker
   * background color help player to know which grid is selected.)
   *  Input:
   *  @param event
   *  Output: None.
   *  Return: void
   */
  @FXML
  void onGridBtnClick(ActionEvent event){
    //Remove previous grid btn selected effect.
    if(selectedGridBtn != null){
      selectedGridBtn.setStyle("");
    }
    Button btn = (Button) event.getTarget();
    selectedGridBtn = btn;
    btn.setStyle("-fx-background-color: #979797;");
  }

  /**
   * setDifficulty  (Called from WelcomeController.java, to get the difficulty value
   * that selected by player. Then calling gameSetup() to initialize game board.
   * The reason of call gameSetup here is initialize method cannot invoke the gameSetup()
   * due to the methods are not been created yet at that phase.)
   *  Input:
   *  @param value
   *  Output: None.
   *  Return: void
   */
  public void setDifficulty(String value) {
    this.difficulty = value;
    gameSetUp();
  }

  /**
   * setPlayerName  (Called from WelcomeController.java, to get player input value.)
   * @param value
   */
  public void setPlayerName(String value){
    this.playerName = value;
  }

  /**
   * gameSetUp   (Game board initialize method, set default number in some grid
   * depend on the level of difficulty.)
   *  Input: None
   *  Output: None
   *  Return: void
   */
  private void gameSetUp(){
    switch (difficulty) {
      case "Medium" -> gameBoard = mediumGameBoard;
      case "Hard" -> gameBoard = hardGameBoard;
      default -> gameBoard = easyGameBoard;
    }

    for(int i = 0; i < gameBoard.length; i++){
      for(int j = 0; j < gameBoard[i].length; j++){
        Button btn = (Button) getBtnFromGridPane(gameGridPane, i, j);
        if (btn != null) {
          btn.setText(Integer.toString(gameBoard[i][j]));
        }
        btn.setStyle("");
      }
    }
  }

  /**
   * checkInRow  (Check if a number has the same one in rows.)
   * @param number
   * @param row
   * @return ture if same numbers appear in same row.
   */
  private boolean checkInRow(int number, int row){
    for(int i = 0; i <gameBoard.length; i++){
      if(gameBoard[row][i] == number){
        return true;
      }
    }
    return false;
  }

  /**
   * checkInCol   (Check if a number has the same one in columns.)
   * @param number
   * @param col
   * @return ture if same numbers appear in same column.
   */
  private boolean checkInCol(int number, int col){
    for (int[] ints : gameBoard) {
      if (ints[col] == number) {
        return true;
      }
    }
    return false;
  }

  /**
   * checkInCol   (Check if a number has the same one in boxes.)
   * @param number
   * @param row
   * @param col
   * @return ture if same numbers appear in same box.
   */
  private boolean checkInBox(int number, int row, int col){
    int currentBoxRow = row - row % 3;
    int currentBoxCol = col - col % 3;

    for(int i = currentBoxRow; i < currentBoxRow + 3; i++){
      for(int j = currentBoxCol; j < currentBoxCol + 3; j++){
        if(gameBoard[i][j] == number){
          return true;
        }
      }
    }
    return false;

  }

  /**
   * getBtnFromGridPane   (Loop through a grid pane and return a node.)
   * @param gp
   * @param row
   * @param col
   * @return node Will be cast to button for further operations.
   */
  //get node from grid pane
  private Node getBtnFromGridPane(GridPane gp, int row, int col){
    for(Node node : gp.getChildren()){
      if((GridPane.getRowIndex(node) != null && GridPane.getRowIndex(node) == row)
          && (GridPane.getColumnIndex(node) != null && GridPane.getColumnIndex(node) == col)){
        return node;
      }
    }
    return null;
  }


}
