package hehe;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import org.apache.derby.iapi.sql.Row;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import javax.persistence.metamodel.EntityType;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PrimaryController {

    @FXML
    TableView<RowEntity> dbTable;

    @FXML
    TableColumn<RowEntity, Integer> columnId;

    @FXML
    TableColumn<RowEntity, String> columnOznaczenie;

    @FXML
    TableColumn<RowEntity, String> columnNazwa;

    @FXML
    TableColumn<RowEntity, String> columnOpis;

    @FXML
    TableColumn<RowEntity, String> columnRodzaj;

    @FXML
    TableColumn<RowEntity, Boolean> columnPionowy;

    @FXML
    TableColumn<RowEntity, Boolean> columnJednaStrona;

    ObservableList<RowEntity> data;

    private SessionFactory factory = new Configuration().configure().buildSessionFactory();
    private Session session = factory.openSession();
    private Transaction tx;

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }

    @FXML
    private void test() {
        dbTable.getItems().get(0).setNazwa("dupa1234");
        session.save(dbTable.getItems().get(0));

    }

    public void initialize() {
        setCellValueFactories();

        fillTable();
    }

    private void fillDb() {
        if(session.createQuery("from " + RowEntity.class.getName()).list().size() == 0) {
            var rows = new ArrayList<RowEntity>();
            rows.add(new RowEntity("haha", "nazwaHaha", "opis znaku haha", "a", true, true));
            rows.add(new RowEntity("hehe", "nazwaHehe", "opis znaku hehe", "e", true, false));
            rows.add(new RowEntity("hihi", "nazwaHiHi", "opis znaku hihi", "i", false, true));
            rows.add(new RowEntity("huhu", "nazwaHuHu", "opis znaku huhu", "u", false, false));
            rows.add(new RowEntity("hoho", "nazwaHoHo", "opis znaku hoho", "o", true, true));
            rows.add(new RowEntity("ha", "nazwaHa", "opis znaku ha", "a", true, false));
            rows.add(new RowEntity("he", "nazwaHe", "opis znaku he", "e", false, true));
            rows.add(new RowEntity("hi", "nazwaHi", "opis znaku hi", "i", true, true));
            rows.add(new RowEntity("hu", "nazwaHu", "opis znaku hu", "u", false, false));
            rows.add(new RowEntity("ho", "nazwaHo", "opis znaku ho", "o", true, false));
            rows.add(new RowEntity("hue", "nazwaHue", "opis znaku hue", "ue", false, true));

            session.beginTransaction();
            for(var row : rows) {
                session.save(row);
            }
            session.getTransaction().commit();
        }
    }

    private void fillTable() {
        fillDb();

        try {
            final Query query = session.createQuery("from " + RowEntity.class.getName());

            data = FXCollections.observableArrayList();
            for(Object row : query.list()) {
                RowEntity entry = (RowEntity) row;
                System.out.println(entry + " " + entry.getOznaczenie());

                data.add(entry);
            }
            dbTable.setItems(data);
            dbTable.setEditable(true);

        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void createTransaction() {
        System.out.println("henlo");
        tx = session.beginTransaction();
    }

    @FXML
    private void commitTransaction(Event e) {
//        System.out.println(e.getTarget() + " " + e.getSource());

        System.out.println(dbTable.getEditingCell().getColumn());
        RowEntity edited = data.get(dbTable.getEditingCell().getRow());
        edited.setOznaczenie((String)((TableColumn.CellEditEvent)e).getNewValue());
//        System.out.println(columnOznaczenie.getCellData(edited));
//        System.out.println(edited + " " + edited.getOznaczenie() + edited.oznaczenieProperty().getValueSafe());
        session.saveOrUpdate(edited);
        tx.commit();
    }

    private void setCellValueFactories() {
        columnId.setCellValueFactory(new PropertyValueFactory<>("idZnaku"));
//        columnId.setCellValueFactory(x -> x.get);
        columnOznaczenie.setCellFactory(TextFieldTableCell.forTableColumn());
        columnOznaczenie.setCellValueFactory(new PropertyValueFactory<>("oznaczenie"));
        columnNazwa.setCellValueFactory(new PropertyValueFactory<>("nazwa"));
        columnOpis.setCellValueFactory(new PropertyValueFactory<>("opis"));
        columnRodzaj.setCellValueFactory(new PropertyValueFactory<>("rodzaj"));
        columnPionowy.setCellValueFactory(new PropertyValueFactory<>("czyPionowy"));
        columnPionowy.setCellFactory( tc -> new CheckBoxTableCell<>());
        columnJednaStrona.setCellValueFactory(new PropertyValueFactory<>("czyDotyczyJednejStrony"));
        columnJednaStrona.setCellFactory( tc -> new CheckBoxTableCell<>());
    }

//    private void useDBConnection() {
//        ObservableList<DbRow> data = FXCollections.observableArrayList();
//
//        setCellValueFactories();
//
//        DbConnection db = DbConnection.getInstance();
//        db.open();
//
//        try {
//            db.statement = db.conn.createStatement();
//            ResultSet rs = db.statement.executeQuery("select * from " + db.TABLE);
//            ResultSetMetaData rsmd = rs.getMetaData();
//            int columnCount = rsmd.getColumnCount();
//            for(int i = 1; i <= columnCount; i++) {
//                System.out.print(rsmd.getColumnLabel(i) + "\t\t");
//            }
//
//            System.out.println("");
//
//            while(rs.next()) {
//
//                DbRow row = new DbRow();
//                row.setId(new SimpleIntegerProperty(rs.getInt(1)));
//                row.setOznaczenie(new SimpleStringProperty(rs.getString(2)));
//                row.setNazwa(new SimpleStringProperty(rs.getString(3)));
//                row.setOpis(new SimpleStringProperty(rs.getString(4)));
//                row.setRodzaj(new SimpleStringProperty(rs.getString(5)));
//                row.setCzyPionowy(new SimpleBooleanProperty(rs.getBoolean(6)));
//                row.setCzyDotyczyJednejStrony(new SimpleBooleanProperty(rs.getBoolean(7)));
//                data.add(row);
//                for(int i = 1; i <= columnCount; i++) {
//
//                    System.out.print(rs.getString(i) + "\t\t");
//                }
//                System.out.println();
//            }
////            dbTable.setItems(data);
//            dbTable.setEditable(true);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        db.close();
//    }
}
