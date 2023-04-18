package com.cs622.sudoku;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Sudoku Game
 *    Unique number-placement board puzzle game.
 *
 * Author:    Yinkai Xiong
 * Date:      05/03/2023
 * Class:     MET CS622
 * Issues:    None known
 *
 * Description:
 *    This program is a unique number-placement 9 X 9 grid board puzzle game.
 *    In the 9 X 9 grid, each row, column, and box cannot have repeated number
 *    from 1 to 9. Which box means 3 X 3 sub-grid in the entire game board.
 *
 * Assumptions:
 *    Player know the basic rules to play Sudoku.
 */

public class SudokuApplication extends Application {

  /**
   * General javafx start up method.
   * @param stage
   * @throws IOException
   */
  @Override
  public void start(Stage stage) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(SudokuApplication.class.getResource("welcome-view.fxml"));
    Scene scene = new Scene(fxmlLoader.load(), 600, 400);
    stage.setTitle("Sudoku");
    stage.setScene(scene);
    stage.show();
  }

  public static void main(String[] args) {
    launch();
  }
}
