package hehe;

import javafx.beans.property.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "LOGINGITLAB_ZNAKI_DROGOWE_TB", schema = "APP")
public class RowEntity {

    private IntegerProperty idZnaku = new SimpleIntegerProperty();
    private StringProperty oznaczenie = new SimpleStringProperty();
    private StringProperty nazwa = new SimpleStringProperty();
    private StringProperty opis = new SimpleStringProperty();
    private StringProperty rodzaj = new SimpleStringProperty();
    private BooleanProperty czyPionowy = new SimpleBooleanProperty();
    private BooleanProperty czyDotyczyJednejStrony = new SimpleBooleanProperty();

    @Id
    @GeneratedValue
    @Column(name = "ID_ZNAKU")
    public int getIdZnaku() {
        return idZnaku.getValue();
    }

    public void setIdZnaku(int idZnaku) {
        this.idZnaku.setValue(idZnaku);
    }

    public IntegerProperty idZnakuProperty() {
        return idZnaku;
    }

    @Basic
    @Column(name = "OZNACZENIE", length=5)
    public String getOznaczenie() {
        return oznaczenie.getValueSafe();
    }

    public void setOznaczenie(String oznaczenie) {
        this.oznaczenie.setValue(oznaczenie);
    }

    public StringProperty oznaczenieProperty() {
        return oznaczenie;
    }

    @Basic
    @Column(name = "NAZWA")
    public String getNazwa() {
        return nazwa.getValueSafe();
    }

    public void setNazwa(String nazwa) {
        this.nazwa.setValue(nazwa);
    }

    public StringProperty nazwaProperty() {
        return nazwa;
    }

    @Basic
    @Column(name = "OPIS")
    public String getOpis() {
        return opis.getValueSafe();
    }

    public void setOpis(String opis) {
        this.opis.setValue(opis);
    }

    public StringProperty opisProperty() {
        return opis;
    }

    @Basic
    @Column(name = "RODZAJ")
    public String getRodzaj() {
        return rodzaj.getValueSafe();
    }

    public void setRodzaj(String rodzaj) {
        this.rodzaj.setValue(rodzaj);
    }

    public StringProperty rodzajProperty() {
        return rodzaj;
    }

    @Basic
    @Column(name = "CZY_PIONOWY")
    public Boolean getCzyPionowy() {
        return czyPionowy.getValue();
    }

    public void setCzyPionowy(Boolean czyPionowy) {
        this.czyPionowy.setValue(czyPionowy);
    }

    public BooleanProperty czyPionowyProperty() {
        return czyPionowy;
    }

    @Basic
    @Column(name = "CZY_DOTYCZY_JEDNEJ_STRONY")
    public Boolean getCzyDotyczyJednejStrony() {
        return czyDotyczyJednejStrony.getValue();
    }

    public void setCzyDotyczyJednejStrony(Boolean czyDotyczyJednejStrony) {
        this.czyDotyczyJednejStrony.setValue(czyDotyczyJednejStrony);
    }

    public BooleanProperty czyDotyczyJednejStronyProperty() {
        return czyDotyczyJednejStrony;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RowEntity that = (RowEntity) o;
        return idZnaku == that.idZnaku &&
                Objects.equals(oznaczenie, that.oznaczenie) &&
                Objects.equals(nazwa, that.nazwa) &&
                Objects.equals(opis, that.opis) &&
                Objects.equals(rodzaj, that.rodzaj) &&
                Objects.equals(czyPionowy, that.czyPionowy) &&
                Objects.equals(czyDotyczyJednejStrony, that.czyDotyczyJednejStrony);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idZnaku, oznaczenie, nazwa, opis, rodzaj, czyPionowy, czyDotyczyJednejStrony);
    }
}
