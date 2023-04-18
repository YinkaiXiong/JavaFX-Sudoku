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

  Timeline timeline = new Timeline(
      new KeyFrame(Duration.seconds(1),
          e ->{
            stopwatch.oneSecond();
            stopwatchText.setText(stopwatch.getTime());
          })
  );


  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    stopwatchText.setText(stopwatch.getTime());
    timeline.setCycleCount(Timeline.INDEFINITE);
    timeline.play();
  }

  @FXML
  void onHistoryBtnClick(ActionEvent event) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader();
    fxmlLoader.setLocation(getClass().getResource("history-view.fxml"));
    DialogPane historyDialogPane = fxmlLoader.load();


    Dialog<ButtonType> dialog = new Dialog<>();
    dialog.setDialogPane(historyDialogPane);
    dialog.setTitle("Game History");

    Optional<ButtonType> clickedButton = dialog.showAndWait();
  }

  @FXML
  void onCheckBtnClick(ActionEvent event) throws SQLException {
    int[][] solution;
    boolean noError = true;
    switch (difficulty){

      case "Medium": solution = mediumGameBoardSolution;
        break;

      case "Hard": solution = hardGameBoardSolution;
        break;

      default: solution = easyGameBoardSolution;
    }
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
      timeline.stop();
    }

  }


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

  public void setDifficulty(String value) {
    this.difficulty = value;
    gameSetUp();
  }


  public void setPlayerName(String value){
    this.playerName = value;
  }


  private void gameSetUp(){
    switch (difficulty){

      case "Medium": gameBoard = mediumGameBoard;
      break;

      case "Hard": gameBoard = hardGameBoard;
      break;

      default: gameBoard = easyGameBoard;
    }

    for(int i = 0; i < gameBoard.length; i++){
      for(int j = 0; j < gameBoard[i].length; j++){
        Button btn = (Button) getBtnFromGridPane(gameGridPane, i, j);
        btn.setText(Integer.toString(gameBoard[i][j]));
        btn.setStyle("");
      }
    }
  }


  //Check if the number exists in row
  private boolean checkInRow(int number, int row){
    for(int i = 0; i <gameBoard.length; i++){
      if(gameBoard[row][i] == number){
        return true;
      }
    }
    return false;
  }

  private boolean checkInCol(int number, int col){
    for (int i = 0; i < gameBoard.length; i++){
      if(gameBoard[i][col] == number){
        return true;
      }
    }
    return false;
  }

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
