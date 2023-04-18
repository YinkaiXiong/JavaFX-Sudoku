module com.cs622.sudoku {
  requires javafx.controls;
  requires javafx.fxml;
  requires java.sql;


  opens com.cs622.sudoku to javafx.fxml;
  exports com.cs622.sudoku;
}