package hehe;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import lombok.Setter;

@Setter
public class DbRow {

    IntegerProperty id;
    StringProperty oznaczenie;
    StringProperty nazwa;
    StringProperty opis;
    StringProperty rodzaj;
    BooleanProperty czyPionowy;
    BooleanProperty czyDotyczyJednejStrony;

    public IntegerProperty idProperty() {
        return id;
    }

    public StringProperty oznaczenieProperty() {
        return oznaczenie;
    }

    public StringProperty nazwaPropert() {
        return nazwa;
    }

    public StringProperty opisProperty() {
        return opis;
    }

    public StringProperty rodzajProperty() {
        return rodzaj;
    }

    public BooleanProperty czyPionowyProperty() {
        return czyPionowy;
    }

    public BooleanProperty czyDotyczyJednejStronyProperty() {
        return czyDotyczyJednejStrony;
    };

}
