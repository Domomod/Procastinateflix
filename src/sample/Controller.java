package sample;

import Aktorzy.*;
import Produkt.Produkt;
import Produkt.Film;
import Produkt.Serial;
import Produkt.Sezon;
import Produkt.Odcinek;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.*;

import static java.lang.Thread.sleep;


public class Controller implements Initializable {

    @FXML
    private Text nazwaProduktu;
    @FXML
    private Text stanKonta;
    @FXML
    private Text podsumowanieMiesiaca;
    @FXML
    private Text jakoscProduktu;
    @FXML
    private Text odcinki;
    @FXML
    private Text sezony;
    @FXML
    private Text aktorzy;
    @FXML
    private Text krajProduktu;
    @FXML
    private Text dataProduktu;
    @FXML
    private ImageView zdjecieProduktu;
    @FXML
    private TextArea opisProduktu;
    @FXML
    private TextField sciezkaDoPliku;
    @FXML
    private TextField cenaProduktu;
    @FXML
    private TextField cenaAbonamentuZwyklego;
    @FXML
    private TextField cenaAbonamentuPremium;

    @FXML
    volatile private ListView listaProduktow = new ListView();
    volatile private ListProperty<Produkt> listaProduktowProperty = new SimpleListProperty<>();
    @FXML
    volatile private ListView listaDystrybutorow = new ListView();
    volatile private ListProperty<Dystrybutor> listaDystrybutorowProperty = new SimpleListProperty<>();
    @FXML
    volatile private ListView listaKlientow = new ListView();
    volatile private ListProperty<Klient> listaKlientowProperty = new SimpleListProperty<>();
    @FXML
    volatile private ListView listaUmow = new ListView();
    volatile private ListProperty<Umowa> listaUmowProperty = new SimpleListProperty<>();
    @FXML
    volatile private ListView sezonyListView = new ListView();
    volatile private ListProperty<Sezon> sezonyProperty = new SimpleListProperty<>();
    @FXML
    volatile private ListView odcinkiListView = new ListView();
    volatile private ListProperty<Odcinek> odcinkiProperty = new SimpleListProperty<>();
    @FXML
    volatile private ListView aktorzyListView = new ListView();
    volatile private ListProperty<String> aktorzyProperty = new SimpleListProperty<>();

    @FXML
    volatile private LineChart<String, Number> produktChart;

    Produkt wybranyProdukt = null;

    public Controller() {

    }

    public void nowyKlientButton() {
        SimulationAPI.wygenerujNowegoKlienta();
    }

    public void nowyDystrybutorButton() {
        SimulationAPI.wygenerujNowegoDystrybutora();
    }

    public void nowyProduktButton() {
        SimulationAPI.wygenerujNowyProdukt();
    }

    public void wycofajPozycjeButton() {
        if(wybranyProdukt != null){
            final int wybranyIdx = listaProduktow.getSelectionModel().getSelectedIndex();
            SimulationAPI.wycofajProduktZerwijUmowy(wybranyProdukt);
            listaProduktow.getItems().remove(wybranyIdx);

            //listaProduktow.refresh();
        }
    }

    public void ustawCeneProduktuButton() {
        if(wybranyProdukt != null){
            wybranyProdukt.setCena(Integer.parseInt( cenaProduktu.getText() ));
        }
    }

    public void ustawCeneAbonamentow() {
        Integer zwyklyCena = Integer.parseInt(cenaAbonamentuZwyklego.getText());
        Integer premiumCena = Integer.parseInt(cenaAbonamentuPremium.getText());
        SimulationAPI.ustawCeneAbonamentu(zwyklyCena, premiumCena);
    }

    public void wyswietlProdukt(Produkt produkt) {
        if(produkt == null)
            return;
        nazwaProduktu.setText(produkt.getNazwa());
        //ToDo zmieniac zdjecie produktu;
        opisProduktu.setText(produkt.getOpis());
        produktChart.getData().clear();
        produktChart.getData().add(produkt.getWykresOgladalnosci());
        cenaProduktu.setText( produkt.getCena().toString() );

        if(produkt instanceof Film)
            wyswietlFilm((Film)produkt);
        else if(produkt instanceof Serial)
            wyswietlSerial((Serial)produkt);

        wybranyProdukt = produkt;
    }

    public void wyswietlFilm(Film film) {
        aktorzyProperty.set(film.getAktorzy());
    }

    public void wyswietlSerial(Serial serial){
        sezonyProperty.set(serial.getSezony());
    }

    public void wyswietlWlasciciela(WlascicielSerwisu gracz) {
        stanKonta.setText(gracz.getKontoBankowe().getStanKonta().toString());
        podsumowanieMiesiaca.setText(gracz.getWydatkiWOstanimMiesiacu().toString());
    }

    public void zapiszSymulacje(){
        String sciezka = sciezkaDoPliku.getText();
        SimulationAPI.serializacjia(sciezka);
    }

    public void wczytajSymulacje() {
        String sciezka = sciezkaDoPliku.getText();
        SimulationAPI.deserializacja(sciezka);

        listaUmowProperty.set(SimulationAPI.getUmowy());
        listaProduktowProperty.set(SimulationAPI.getProdukty());
        listaDystrybutorowProperty.set(SimulationAPI.getDystrybutorzy());
        listaKlientowProperty.set(SimulationAPI.getKlienci());

        listaProduktow.refresh();
        listaUmow.refresh();
        listaDystrybutorow.refresh();
        listaProduktow.refresh();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        listaProduktow.itemsProperty().bind(listaProduktowProperty);
        listaProduktowProperty.set(SimulationAPI.getProdukty());

        listaDystrybutorow.itemsProperty().bind(listaDystrybutorowProperty);
        listaDystrybutorowProperty.set(SimulationAPI.getDystrybutorzy());

        listaUmow.itemsProperty().bind(listaUmowProperty);
        listaUmowProperty.set((ObservableList<Umowa>) SimulationAPI.getUmowy());

        listaKlientow.itemsProperty().bind(listaKlientowProperty);
        listaKlientowProperty.set(SimulationAPI.getKlienci());

        aktorzyListView.itemsProperty().bind(aktorzyProperty);

        sezonyListView.itemsProperty().bind(sezonyProperty);

        odcinkiListView.itemsProperty().bind(odcinkiProperty);



        wyswietlWlasciciela(SimulationAPI.getWlascicielSerwisu());

        Symulacja.setOnWlascicielChangeListener(e -> {
            wyswietlWlasciciela(e);
        });

        Symulacja.isControllerCreated = true;

        /*Ustalam w jaki sposob mają się wyświetlać pozycja na Liscie, uzywam CellFactory, zeby nie musiec
         * powiazywac metody toString z wyswietlaniem w GUI, Java nie pozwala mi użyć wildcarda, nie wiem jak uniknąć powielenia kodu*/
        listaProduktow.setCellFactory(param -> new ListCell<Produkt>() {
            @Override
            protected void updateItem(Produkt item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null || item.getNazwa() == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    Text text = new Text();
                    text.wrappingWidthProperty().bind(listaProduktow.widthProperty().subtract(15));
                    text.setText(item.getNazwa());

                    setPrefWidth(0);
                    setGraphic(text);
                }
            }
        });

        listaUmow.setCellFactory(param -> new ListCell<Umowa>() {
            @Override
            protected void updateItem(Umowa item, boolean empty) {
                synchronized (this){
                    super.updateItem(item, empty);

                    if (empty || item == null || item.getNazwa() == null) {
                        setText(null);
                    } else {
                        Text text = new Text();
                        text.wrappingWidthProperty().bind(listaUmow.widthProperty().subtract(15));
                        text.setText(item.getNazwa());

                        setPrefWidth(0);
                        setGraphic(text);
                    }
                }

            }
        });

        listaKlientow.setCellFactory(param -> new ListCell<Klient>() {
            @Override
            protected void updateItem(Klient item, boolean empty) {
                synchronized (this){
                    super.updateItem(item, empty);

                    if (empty || item == null || item.getNazwa() == null) {
                        setText(null);
                    } else {
                        Text text = new Text();
                        text.wrappingWidthProperty().bind(listaKlientow.widthProperty().subtract(15));
                        text.setText(item.getNazwa() + " zadowolenie: " + item.getZadowolenie());

                        setPrefWidth(0);
                        setGraphic(text);
                    }
                }

            }
        });

        listaDystrybutorow.setCellFactory(param -> new ListCell<Dystrybutor>() {
            @Override
            protected void updateItem(Dystrybutor item, boolean empty) {
                synchronized (this){
                    super.updateItem(item, empty);

                    if (empty || item == null || item.getNazwa() == null) {
                        setText(null);
                    } else {
                        Text text = new Text();
                        text.wrappingWidthProperty().bind(listaDystrybutorow.widthProperty().subtract(15));
                        text.setText(item.getNazwa());

                        setPrefWidth(0);
                        setGraphic(text);
                    }
                }

            }
        });

        sezonyListView.setCellFactory(param -> new ListCell<Sezon>() {
            @Override
            protected void updateItem(Sezon item, boolean empty) {
                synchronized (this){
                    super.updateItem(item, empty);

                    if (empty || item == null || item.getNazwa() == null) {
                        setText(null);
                    } else {
                        Text text = new Text();
                        text.wrappingWidthProperty().bind(listaDystrybutorow.widthProperty().subtract(15));
                        text.setText(item.getNazwa());

                        setPrefWidth(0);
                        setGraphic(text);
                    }
                }

            }
        });

        odcinkiListView.setCellFactory(param -> new ListCell<Odcinek>() {
            @Override
            protected void updateItem(Odcinek item, boolean empty) {
                synchronized (this){
                    super.updateItem(item, empty);

                    if (empty || item == null || item.getNazwa() == null) {
                        setText(null);
                    } else {
                        Text text = new Text();
                        text.wrappingWidthProperty().bind(listaDystrybutorow.widthProperty().subtract(15));
                        text.setText(item.getNazwa());

                        setPrefWidth(0);
                        setGraphic(text);
                    }
                }

            }
        });

        /*Ustalam co ma sie dziac przy wybraniu pozycji z listy, robie to poniewaz nie wiem czy da sie wybrac uchwyt takiej funkcji
         * z poziomu SceneBuildera*/
        listaProduktow.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {

                    if(newValue instanceof Film){
                        aktorzy.setVisible(true);
                        aktorzy.setManaged(true);
                        aktorzyListView.setVisible(true);
                        aktorzyListView.setManaged(true);

                        odcinki.setVisible(false);
                        odcinki.setManaged(false);
                        odcinkiListView.setVisible(false);
                        odcinkiListView.setManaged(false);

                        sezony.setVisible(false);
                        sezony.setManaged(false);
                        sezonyListView.setVisible(false);
                        sezonyListView.setManaged(false);
                    }
                    else if (newValue instanceof Serial){
                        aktorzy.setVisible(false);
                        aktorzy.setManaged(false);
                        aktorzyListView.setVisible(false);
                        aktorzyListView.setManaged(false);

                        odcinki.setVisible(true);
                        odcinki.setManaged(true);
                        odcinkiListView.setVisible(true);
                        odcinkiListView.setManaged(true);

                        sezony.setVisible(true);
                        sezony.setManaged(true);
                        sezonyListView.setVisible(true);
                        sezonyListView.setManaged(true);
                    }

//                    else
//                        Main.wyswietlScenaGlowna();

                    wyswietlProdukt((Produkt) newValue);
                });

        sezonyListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    odcinkiProperty.set(((Sezon)newValue).getOdcinki());
                });
    }
}

