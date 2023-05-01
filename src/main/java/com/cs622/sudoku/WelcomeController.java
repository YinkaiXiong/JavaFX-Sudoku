package com.cs622.sudoku;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Class: WelcomeController.java
 *
 * Description:
 *    This is the controller class for welcome-view.fxml. This class will provide
 *    the Welcome scene when program start.
 */

public class WelcomeController implements Initializable {
  @FXML
  private ChoiceBox<String> difficultyBox;

  @FXML
  private TextField playerName;

  private String[] difficulties = {"Easy", "Medium", "Hard"};

  private Stage stage;
  private Scene scene;

  private Parent root;

  /**
   * onStartBtnClick   (When start button clicked, load the sudoku view and
   * pass the player entered value(player name and difficulty level) to sudoku
   * controller.
   *  Input:
   *  @param event
   *  @throws IOException
   *  Output: None
   *  Return: void
   */
  @FXML
  void onStartBtnClick(ActionEvent event) throws IOException {

    String name = playerName.getText().equals("") ? "Anonymity" : playerName.getText();
    String level = difficultyBox.getSelectionModel().getSelectedItem();

    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("sudoku-view.fxml"));
    root = fxmlLoader.load();

    //Pass value by calling the methods in sudokuController.
    SudokuController sudokuController = fxmlLoader.getController();
    sudokuController.setPlayerName(name);
    sudokuController.setDifficulty(level);

    stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();
  }

  /**
   * initialize      (Method from Initializable interface. Invoke when stage view loaded.)
   * Initialize the selection box with difficulty array. And set the default selection
   * to first element of the difficulty array.
   *  Input:
   *  @param url
   *  @param resourceBundle
   *  Output: None.
   *  Return: void
   */
  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    difficultyBox.getItems().addAll(difficulties);
    difficultyBox.setValue(difficulties[0]);
  }
}
