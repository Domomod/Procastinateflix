package sample;

import Aktorzy.*;
import Produkt.Produkt;

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
    private Text popularnosc;
    @FXML
    private ImageView zdjecieProduktu;
    @FXML
    private TextArea opisProduktu;
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
    volatile private LineChart<String, Number> produktChart;
    @FXML
    private TextField sciezkaDoPliku;

    public Controller() {

    }

    public void nowyDystrybutorButton() {
        SimulationAPI.wygenerujNowegoDystrybutora();
    }

    public void testujDystrybutoraButton() {
        SimulationAPI.wygenerujNowyProdukt();
    }

    synchronized public void wyswietlProdukt(Produkt produkt) {
        nazwaProduktu.setText(produkt.getNazwa());
        //ToDo zmieniac zdjecie produktu;
        opisProduktu.setText(produkt.getOpis());
        produktChart.getData().clear();
        produktChart.getData().add(produkt.getWykresOgladalnosci());
    }

    public void wyswietlWlasciciela(WlascicielSerwisu gracz) {
        stanKonta.setText(gracz.getKontoBankowe().getStanKonta().toString());
        podsumowanieMiesiaca.setText(gracz.getWydatkiWOstanimMiesiacu().toString());
    }

    public void zapiszSymulacje(){
        String sciezka = sciezkaDoPliku.getText();
        SimulationAPI.serializacjiaDoXML(sciezka);
    }

    public void wczytajSymulacje() {
        String sciezka = sciezkaDoPliku.getText();
        SimulationAPI.deserializacjaXML(sciezka);

        listaUmowProperty.set(SimulationAPI.getUmowy());
        listaProduktowProperty.set(SimulationAPI.getProdukty());
        listaDystrybutorowProperty.set(SimulationAPI.getDystrybutorzy());
        listaKlientowProperty.set(SimulationAPI.getKlienci());
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
        });

        listaKlientow.setCellFactory(param -> new ListCell<Klient>() {
            @Override
            protected void updateItem(Klient item, boolean empty) {
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
        });

        listaDystrybutorow.setCellFactory(param -> new ListCell<Dystrybutor>() {
            @Override
            protected void updateItem(Dystrybutor item, boolean empty) {
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
        });

        /*Ustalam co ma sie dziac przy wybraniu pozycji z listy, robie to poniewaz nie wiem czy da sie wybrac uchwyt takiej funkcji
         * z poziomu SceneBuildera*/
        listaProduktow.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    wyswietlProdukt((Produkt) newValue);
                });
    }
}

