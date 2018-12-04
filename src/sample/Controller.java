package sample;

import Aktorzy.Dystrybutor;
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
    private ImageView zdjecieProduktu;
    @FXML
    private TextArea opisProduktu;
    @FXML
    private ListView listaProduktow = new ListView();
    private ListProperty<Produkt> listProperty = new SimpleListProperty<>();

    //ToDo tymczasowe, do usuniecia
    @FXML
    private Button testujDystrybutoraButton;

    public Controller(){

    }

    public void testujDystrybutoraButton(){
        Dystrybutor dystrybutor = new Dystrybutor();
        dystrybutor.zaproponujUmowe();
    }

    public void wyswietlProdukt( Produkt produkt ){
        nazwaProduktu.setText( produkt.getNazwa() );
        //ToDo zmieniac zdjecie produktu;
        opisProduktu.setText( produkt.getOpis() );
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        listaProduktow.itemsProperty().bind(listProperty);
        listProperty.set(Main.getProdukty());

        /*Ustalam w jaki sposob ma sie wyswietlac Produkt na Liscie, uzywam CellFactory, zeby nie musiec
        * powiazywac metody toString z wyswietlaniem w GUI*/
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

        /*Ustalam co ma sie dziac przy wybraniu pozycji z listy, robie to poniewaz nie wiem czy da sie wybrac uchwyt takiej funkcji
        * z poziomu SceneBuildera*/
        listaProduktow.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    wyswietlProdukt((Produkt) newValue);
                });
    }
}

