package hehe;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.ArrayList;

public class PrimaryController {

    @FXML TableView<RowEntity> dbTable;
    @FXML TableColumn<RowEntity, Integer> columnId;
    @FXML TableColumn<RowEntity, String> columnOznaczenie;
    @FXML TableColumn<RowEntity, String> columnNazwa;
    @FXML TableColumn<RowEntity, String> columnOpis;
    @FXML TableColumn<RowEntity, String> columnRodzaj;
    @FXML TableColumn<RowEntity, Boolean> columnPionowy;
    @FXML TableColumn<RowEntity, Boolean> columnJednaStrona;
    @FXML TableColumn<RowEntity, RowEntity> columnDelete;

    @FXML TextField newOznaczenie;
    @FXML TextField newNazwa;
    @FXML TextField newOpis;
    @FXML TextField newRodzaj;
    @FXML CheckBox newCzyPionowy;
    @FXML CheckBox newCzyDotyczyJednejStrony;

    private ObservableList<RowEntity> data;

    private SessionFactory factory = new Configuration().configure().buildSessionFactory();
    private Session session = factory.openSession();
    private Transaction tx;

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

                entry.czyPionowyProperty().addListener( e -> {
                    tx = session.beginTransaction();
                    tx.commit();
                } );

                entry.czyDotyczyJednejStronyProperty().addListener( e -> {
                    tx = session.beginTransaction();
                    tx.commit();
                });

                data.add(entry);
            }
            dbTable.setItems(data);
            dbTable.setEditable(true);

        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void commitTransaction(Event e) {
        var event = (TableColumn.CellEditEvent) e;
        var edited = (RowEntity) event.getRowValue();
        var column = event.getTableColumn();
        var newValue = event.getNewValue().toString();

        if(column == columnOznaczenie) {
            if(newValue.length() > 5) {
                dbTable.refresh();
                return;
            }
            edited.setOznaczenie(newValue);
        } else if(column == columnNazwa) {
            if(newValue.length() > 15) {
                dbTable.refresh();
                return;
            }
            edited.setNazwa(newValue);
        } else if(column == columnOpis) {
            if(newValue.length() > 30) {
                dbTable.refresh();
                return;
            }
            edited.setOpis(newValue);
        } else if(column == columnRodzaj) {
            if(newValue.length() > 10) {
                dbTable.refresh();
                return;
            }
            edited.setRodzaj(newValue);
        }

        tx = session.beginTransaction();
        tx.commit();
    }

    @FXML
    private void submit() {
        boolean err = false;
        if(newOznaczenie.getText().length() > 5) {
            newOznaczenie.styleProperty().setValue("-fx-border-color: red;");
            err = true;
        } else {
            newOznaczenie.styleProperty().setValue("");
        }
        if(newNazwa.getText().length() > 15) {
            newNazwa.styleProperty().setValue("-fx-border-color: red;");
            err = true;
        } else {
            newNazwa.styleProperty().setValue("");
        }
        if(newOpis.getText().length() > 30) {
            newOpis.styleProperty().setValue("-fx-border-color: red;");
            err = true;
        } else {
            newOpis.styleProperty().setValue("");
        }
        if(newRodzaj.getText().length() > 10) {
            newRodzaj.styleProperty().setValue("-fx-border-color: red;");
            err = true;
        } else {
            newRodzaj.styleProperty().setValue("");
        }
        if(err) {
            return;
        }
        var newRow = new RowEntity(newOznaczenie.getText(), newNazwa.getText(), newOpis.getText(), newRodzaj.getText(), newCzyPionowy.isSelected(), newCzyDotyczyJednejStrony.isSelected());
        tx = session.beginTransaction();
        session.save(newRow);
        tx.commit();
        data.add(newRow);
    }

    private void setCellValueFactories() {
        columnId.setCellValueFactory(new PropertyValueFactory<>("idZnaku"));
        columnOznaczenie.setCellValueFactory(new PropertyValueFactory<>("oznaczenie"));
        columnOznaczenie.setCellFactory(TextFieldTableCell.forTableColumn());
        columnNazwa.setCellValueFactory(new PropertyValueFactory<>("nazwa"));
        columnNazwa.setCellFactory(TextFieldTableCell.forTableColumn());
        columnOpis.setCellValueFactory(new PropertyValueFactory<>("opis"));
        columnOpis.setCellFactory(TextFieldTableCell.forTableColumn());
        columnRodzaj.setCellValueFactory(new PropertyValueFactory<>("rodzaj"));
        columnRodzaj.setCellFactory(TextFieldTableCell.forTableColumn());
        columnPionowy.setCellValueFactory(new PropertyValueFactory<>("czyPionowy"));
        columnPionowy.setCellFactory( tc -> new CheckBoxTableCell<>());
        columnJednaStrona.setCellValueFactory(new PropertyValueFactory<>("czyDotyczyJednejStrony"));
        columnJednaStrona.setCellFactory( tc -> new CheckBoxTableCell<>());

        columnDelete.setCellValueFactory( p -> new ReadOnlyObjectWrapper<>(p.getValue()));
        columnDelete.setCellFactory( p -> new TableCell<>() {
            private final Button deleteButton = new Button("X");

            @Override
            protected void updateItem(RowEntity row, boolean empty) {
                super.updateItem(row, empty);

                if(row == null) {
                    setGraphic(null);
                    return;
                }

                setGraphic(deleteButton);
                deleteButton.setOnAction(
                        e -> {
//                            getTableView().getItems().remove(row);
                            tx = session.beginTransaction();
                            session.remove(row);
                            tx.commit();
                            data.remove(row);
                        }
                );
            }
        });
    }
}
