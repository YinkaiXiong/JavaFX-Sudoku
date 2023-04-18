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


public class HistoryController implements Initializable{
  public void viewHistory(){
    System.out.println("Check");
  }

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

  @FXML
  void onGetBtnClick(ActionEvent event) throws SQLException {
    ConnectionClass connectionClass = new ConnectionClass();
    Connection connection = connectionClass.getConnection();

    String sql = "SELECT * FROM sudoku.history;";
    Statement statement = connection.createStatement();

    ResultSet resultSet = statement.executeQuery(sql);

    while(resultSet.next()){
      String id = resultSet.getString(1);
      String playerName = resultSet.getString(2);
      String difficulty = resultSet.getString(3);
      String time = resultSet.getString(4);

      tableview.getItems().add(new Record(id,playerName,difficulty,time));

    }


  }


  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
    nameColumn.setCellValueFactory(new PropertyValueFactory<>("playerName"));
    difficultyColumn.setCellValueFactory(new PropertyValueFactory<>("difficulty"));
    timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
  }
}
