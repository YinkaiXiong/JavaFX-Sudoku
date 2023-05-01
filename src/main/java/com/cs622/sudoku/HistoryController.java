package com.cs622.sudoku;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

/**
 * Class: HistoryController.java
 *
 * Description:
 *    This is the controller class for history-view.fxml. This class provided
 *    basic operation to let player view the game winner history that stored
 *    in local database.
 */
public class HistoryController implements Initializable{

  @FXML
  private TableView<Record> tableview;

  @FXML
  private TableColumn<Record, String> difficultyColumn;

  @FXML
  private TableColumn<Record, String> idColumn;

  @FXML
  private TableColumn<Record, String> nameColumn;

  @FXML
  private TableColumn<Record, String> timeColumn;

  /**
   * onGetBtnClick         (Invoke when get information button clicked)
   *  Input: @param event
   *  Output: None
   *  @throws SQLException
   *  Return: void
   */
  @FXML
  void onGetBtnClick(ActionEvent event) throws SQLException {
    //Connect database
    ConnectionClass connectionClass = new ConnectionClass();
    Connection connection = connectionClass.getConnection();

    //Execute sql query
    String sql = "SELECT * FROM sudoku.history;";
    Statement statement = connection.createStatement();

    ResultSet resultSet = statement.executeQuery(sql);

    //Display database information to UI table view
    while(resultSet.next()){
      String id = resultSet.getString(1);
      String playerName = resultSet.getString(2);
      String difficulty = resultSet.getString(3);
      String time = resultSet.getString(4);

      tableview.getItems().add(new Record(id,playerName,difficulty,time));
    }
  }

  /**
   * initialize      (Method from Initializable interface. Invoke when stage view loaded.)
   * Associate the tableview column with the database field.
   *  Input:
   *  @param url
   *  @param resourceBundle
   *  Output: None.
   *  Return: void
   */
  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
    nameColumn.setCellValueFactory(new PropertyValueFactory<>("playerName"));
    difficultyColumn.setCellValueFactory(new PropertyValueFactory<>("difficulty"));
    timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
  }
}
