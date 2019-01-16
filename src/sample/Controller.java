package sample;

import Aktorzy.Dystrybutor;
import Aktorzy.Symulacja;
import Aktorzy.WlascicielSerwisu;
import Produkt.Produkt;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.*;


public class Controller implements Initializable {

    @FXML
    private Text nazwaProduktu;
    @FXML
    private Text stanKonta;
    @FXML
    private Text wydatki;
    @FXML
    private Text zarobki;
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
    volatile private ListProperty<Produkt> listaKlientowProperty = new SimpleListProperty<>();

    public Controller() {

    }

    public void nowyDystrybutorButton() {
        Main.getDystrybutorzy().dodajDystrybutora();
    }

    public void testujDystrybutoraButton() {
        Main.getDystrybutorzy().wygenerujNowyProduktNaZlecenieUżytkownika();
    }

    public void wyswietlProdukt(Produkt produkt) {
        nazwaProduktu.setText(produkt.getNazwa());
        //ToDo zmieniac zdjecie produktu;
        opisProduktu.setText(produkt.getOpis());
    }

    public void wyswietlGracza(WlascicielSerwisu gracz) {
        stanKonta.setText(gracz.getKontoBankowe().getStanKonta().toString());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        listaProduktow.itemsProperty().bind(listaProduktowProperty);
        listaProduktowProperty.set(Main.getProdukty());

        listaDystrybutorow.itemsProperty().bind(listaDystrybutorowProperty);
        listaDystrybutorowProperty.set(Main.getDystrybutorzy().getDystrybutorzy());

        wyswietlGracza(Main.getGracz());

        Symulacja.setOnChangeListener(e -> {
            wyswietlGracza(e);
        });

        Symulacja.isControllerCreated = true;

        /*Ustalam w jaki sposob ma sie wyswietlac Produkt na Liscie, uzywam CellFactory, zeby nie musiec
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

