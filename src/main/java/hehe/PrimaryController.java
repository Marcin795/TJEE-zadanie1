package hehe;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class PrimaryController {

    @FXML
    TableView<DbRow> dbTable;

    @FXML
    TableColumn<DbRow, Integer> columnId;

    @FXML
    TableColumn<DbRow, String> columnOznaczenie;

    @FXML
    TableColumn<DbRow, String> columnNazwa;

    @FXML
    TableColumn<DbRow, String> columnOpis;

    @FXML
    TableColumn<DbRow, String> columnRodzaj;

    @FXML
    TableColumn<DbRow, Boolean> columnPionowy;

    @FXML
    TableColumn<DbRow, Boolean> columnJednaStrona;

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }

    public void initialize() {

        ObservableList<DbRow> data = FXCollections.observableArrayList();

        columnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnOznaczenie.setCellValueFactory(new PropertyValueFactory<>("oznaczenie"));
        columnNazwa.setCellValueFactory(new PropertyValueFactory<>("nazwa"));
        columnOpis.setCellValueFactory(new PropertyValueFactory<>("opis"));
        columnRodzaj.setCellValueFactory(new PropertyValueFactory<>("rodzaj"));
        columnPionowy.setCellValueFactory(new PropertyValueFactory<>("czyPionowy"));
        columnPionowy.setCellFactory( tc -> new CheckBoxTableCell<>());
        columnJednaStrona.setCellValueFactory(new PropertyValueFactory<>("czyDotyczyJednejStrony"));
        columnJednaStrona.setCellFactory( tc -> new CheckBoxTableCell<>());
        DbConnection db = DbConnection.getInstance();
        db.open();

        try {
            db.statement = db.conn.createStatement();
            ResultSet rs = db.statement.executeQuery("select * from " + db.TABLE);
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            for(int i = 1; i <= columnCount; i++) {
                System.out.print(rsmd.getColumnLabel(i) + "\t\t");
            }

            System.out.println("");

            while(rs.next()) {

                DbRow row = new DbRow();
                row.setId(new SimpleIntegerProperty(rs.getInt(1)));
                row.setOznaczenie(new SimpleStringProperty(rs.getString(2)));
                row.setNazwa(new SimpleStringProperty(rs.getString(3)));
                row.setOpis(new SimpleStringProperty(rs.getString(4)));
                row.setRodzaj(new SimpleStringProperty(rs.getString(5)));
                row.setCzyPionowy(new SimpleBooleanProperty(rs.getBoolean(6)));
                row.setCzyDotyczyJednejStrony(new SimpleBooleanProperty(rs.getBoolean(7)));
                data.add(row);
                for(int i = 1; i <= columnCount; i++) {

                    System.out.print(rs.getString(i) + "\t\t");
                }
                System.out.println();
            }
            dbTable.setItems(data);
            dbTable.setEditable(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        db.close();
    }
}
