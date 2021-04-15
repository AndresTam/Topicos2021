package sample.views;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Rompecabezas extends Stage implements EventHandler {
    private String[] arreglo1 = {"fila-1-col-1.jpg", "fila-1-col-2.jpg", "fila-1-col-3.jpg",
            "fila-2-col-1.jpg", "fila-2-col-2.jpg", "fila-2-col-3.jpg",
            "fila-3-col-1.jpg", "fila-3-col-2.jpg", "fila-3-col-3.jpg"};

    private String[] arreglo2 = {"fila-1-col-1.jpg", "fila-1-col-2.jpg", "fila-1-col-3.jpg",
            "fila-1-col-4.jpg", "fila-1-col-5.jpg", "fila-2-col-1.jpg", "fila-2-col-2.jpg",
            "fila-2-col-3.jpg", "fila-2-col-4.jpg", "fila-2-col-5.jpg", "fila-3-col-1.jpg",
            "fila-3-col-2.jpg", "fila-3-col-3.jpg", "fila-3-col-4.jpg", "fila-3-col-5.jpg",
            "fila-4-col-1.jpg", "fila-4-col-2.jpg", "fila-4-col-3.jpg", "fila-4-col-4.jpg",
            "fila-4-col-5.jpg", "fila-5-col-1.jpg", "fila-5-col-2.jpg", "fila-5-col-3.jpg",
            "fila-5-col-4.jpg", "fila-5-col-5.jpg"};

    private String[] arreglo3 = {"fila-1-col-1.jpg", "fila-1-col-2.jpg", "fila-1-col-3.jpg",
            "fila-1-col-4.jpg", "fila-1-col-5.jpg", "fila-1-col-6.jpg", "fila-2-col-1.jpg",
            "fila-2-col-2.jpg", "fila-2-col-3.jpg", "fila-2-col-4.jpg", "fila-2-col-5.jpg",
            "fila-2-col-6.jpg", "fila-3-col-1.jpg", "fila-3-col-2.jpg", "fila-3-col-3.jpg",
            "fila-3-col-4.jpg", "fila-3-col-5.jpg", "fila-3-col-6.jpg", "fila-4-col-1.jpg",
            "fila-4-col-2.jpg", "fila-4-col-3.jpg", "fila-4-col-4.jpg", "fila-4-col-5.jpg",
            "fila-4-col-6.jpg", "fila-5-col-1.jpg", "fila-5-col-2.jpg", "fila-5-col-3.jpg",
            "fila-5-col-4.jpg", "fila-5-col-5.jpg", "fila-5-col-6.jpg", "fila-6-col-1.jpg",
            "fila-6-col-2.jpg", "fila-6-col-3.jpg", "fila-6-col-4.jpg", "fila-6-col-5.jpg",
            "fila-6-col-6.jpg"};

    private String[] arImagenes;
    private String[][] arAsignacion;

    private BorderPane borderPane;
    private GridPane tablero;
    private Button[][] btnTarjetas;
    private HBox hBox;
    private VBox vBox;
    private Label lblTarjetas, lblOpciones;
    private TextField txtTarjetas;
    private Button btnMezclar;
    private Scene escena;
    private int noPiezas, tamañoBoton;
    private boolean bandera = false; //Nos indica si ya se seleccionó una carta para el intercambio
    private int xTemp, yTemp = 0;
    private String carpeta;

    public Rompecabezas(){
        CrearUI();
        this.setTitle("Rompecabezas");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        borderPane = new BorderPane();
        lblTarjetas = new Label("Ingresa una opcion de tamaño del rompezabezas: \nOpciones:\n -3 bloques.\n -5 bloques.\n -6 bloques");
        txtTarjetas = new TextField("");
        btnMezclar = new Button("Mezclar Tarjetas");
        btnMezclar.addEventHandler(MouseEvent.MOUSE_CLICKED,this);
        hBox = new HBox();
        hBox.getChildren().addAll(lblTarjetas, txtTarjetas,btnMezclar);
        borderPane.setTop(hBox);
        tablero = new GridPane();
        borderPane.setCenter(tablero);

        escena = new Scene(borderPane,950,700);
    }

    @Override
    public void handle(Event event) {
        lblTarjetas.setVisible(false);
        txtTarjetas.setVisible(false);
        btnMezclar.setVisible(false);

        noPiezas = Integer.parseInt(txtTarjetas.getText());
        switch (noPiezas){
            case 3:
                arImagenes=arreglo1;
                carpeta = "seccion1";
                tamañoBoton = 190;
                break;
            case 5:
                arImagenes=arreglo2;
                carpeta = "seccion2";
                tamañoBoton = 110;
                break;
            case 6:
                arImagenes=arreglo3;
                carpeta = "seccion3";
                tamañoBoton = 90;
                break;
        }
        btnTarjetas = new Button[noPiezas][noPiezas];
        arAsignacion = new String[noPiezas][noPiezas];
        revolver();
        for (int i = 0; i < noPiezas; i++) {
            for (int j = 0; j < noPiezas; j++) {
                Image img = new Image("sample/assets/"+ carpeta + "/" + arAsignacion[i][j]);
                ImageView imv = new ImageView(img);
                imv.setFitHeight(tamañoBoton);
                imv.setPreserveRatio(true);
                int finalI = i;
                int finalJ = j;
                btnTarjetas[i][j] = new Button();
                btnTarjetas[i][j].setOnAction(event1 -> intercambio(finalI,finalJ));
                btnTarjetas[i][j].setGraphic(imv);
                //btnTarjetas[i][j].setPrefSize(50,50);
                tablero.add(btnTarjetas[i][j],j,i);
            }
        }
    }

    private void intercambio(int i, int j){
        if(!bandera){
            bandera = !bandera;
            xTemp = i;
            yTemp = j;
        } else{
            Image img = new Image("sample/assets/"+ carpeta + "/" + arAsignacion[i][j]);
            ImageView imv = new ImageView(img);
            imv.setFitHeight(tamañoBoton);
            imv.setPreserveRatio(true);

            Image img2 = new Image("sample/assets/"+ carpeta + "/" + arAsignacion[xTemp][yTemp]);
            ImageView imv2 = new ImageView(img2);
            imv2.setFitHeight(tamañoBoton);
            imv2.setPreserveRatio(true);

            btnTarjetas[xTemp][yTemp].setGraphic(imv);
            btnTarjetas[i][j].setGraphic(imv2);

            String cad1 = arAsignacion[xTemp][yTemp];
            String cad2 = arAsignacion[i][j];
            arAsignacion[i][j] = cad1;
            arAsignacion[xTemp][yTemp] = cad2;
            validarFinal();

            bandera = false;
        }
    }

    public void validarFinal(){
        int x = 0, contador = 0;
        for (int i = 0; i < noPiezas; i++) {
            for (int j = 0; j < noPiezas; j++) {
                if(arAsignacion[i][j].equals(arImagenes[x])){
                    contador ++;
                }
                x++;
            }
        }
        if(contador == (noPiezas*noPiezas)){
            Alert dialAlert = new Alert(Alert.AlertType.CONFIRMATION);
            dialAlert.setTitle("¡¡HAS GANADO!!");
            dialAlert.setHeaderText(null);
            dialAlert.setContentText("Felicidades, completaste el rompecabezas");
            dialAlert.initStyle(StageStyle.UTILITY);
            dialAlert.showAndWait();
            close();
        }
    }

    private void revolver(){
        for (int i = 0; i < noPiezas; i++) {
            for (int j = 0; j < noPiezas; j++) {
                arAsignacion[i][j] = new String();
            }
        }
        int posx, posy = 0;
        for (int i = 0; i < arImagenes.length;) {
            posx = (int) (Math.random() * noPiezas);
            posy = (int) (Math.random() * noPiezas);
            if(arAsignacion[posx][posy].equals("")){
                arAsignacion[posx][posy] = arImagenes[i];
                i++;
            }
        }
    }
}