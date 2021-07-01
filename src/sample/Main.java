package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import sample.views.*;
import sample.models.conector;

public class Main extends Application implements EventHandler<WindowEvent> {
    private VBox vBox;
    private MenuBar mnbPrincipal;
    private Menu menCompetencia1, menCompetencia2, menCerrar;
    private MenuItem mitCalcu, mitRompecabezas, mitEncriptar, mitBDCanciones, mitCorredores, mitSocket, mitSalir;
    private Scene escena;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));

        CrearMenu();

        primaryStage.addEventHandler(WindowEvent.WINDOW_CLOSE_REQUEST, this);
        primaryStage.setTitle("Proyecto de Clase TAP 2021");
        primaryStage.setScene(escena);
        primaryStage.setMaximized(true);
        primaryStage.show();
        //Abrimos la conexion de manera global
        conector.getConection();

        /*
        new Corredor("Homero").start();
        new Corredor("Flash").start();
        new Corredor("Quick Silver").start();
        new Corredor("Bob Esponja").start();
        new Corredor("Shrek").start();
         */
    }

    private void CrearMenu(){
        vBox = new VBox();
        mnbPrincipal    = new MenuBar();
        menCompetencia1 = new Menu("Competencia 1");
        menCompetencia2 = new Menu("Competencia 2");
        menCerrar       = new Menu("Cerrar");
        mnbPrincipal.getMenus().addAll(menCompetencia1,menCompetencia2,menCerrar);

        mitCalcu = new MenuItem("Calculadora");
        mitCalcu.setOnAction(event -> opcionesMenu(1));
        mitRompecabezas = new MenuItem("Rompecabezas");
        mitRompecabezas.setOnAction(event -> opcionesMenu(2));
        mitEncriptar = new MenuItem("Encriptador");
        mitEncriptar.setOnAction(event -> opcionesMenu(3));
        mitBDCanciones = new MenuItem(("BD Canciones"));
        mitBDCanciones.setOnAction(event -> opcionesMenu(4));
        menCompetencia1.getItems().addAll(mitCalcu, mitRompecabezas, mitEncriptar, mitBDCanciones);

        mitCorredores = new MenuItem("Ejecución de Hilos");
        mitCorredores.setOnAction(event -> opcionesMenu(5));
        mitSocket = new MenuItem("Manejo de Sockets");
        mitSocket.setOnAction(event -> opcionesMenu(6));
        menCompetencia2.getItems().addAll(mitCorredores, mitSocket);

        mitSalir = new MenuItem("Salir");
        mitSalir.setOnAction(event -> {System.exit(0);});
        menCerrar.getItems().add(mitSalir);
        vBox.getChildren().add(mnbPrincipal);

        escena = new Scene(vBox, 300, 70);
        escena.getStylesheets().add(getClass().getResource("css/styles.css").toExternalForm());
    }

    private void opcionesMenu(int opc){
        switch (opc){
            case 1: new Calculador().show();  break;
            case 2: new Rompecabezas().show(); break;
            case 3: new Encriptador().show();  break;
            case 4: new FrmCanciones().show(); break;
            case 5: new Pista().show();        break;
            case 6: new ClienteSocket().connectToServer(); break;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void handle(WindowEvent event) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Mensaje del Sistema");
        alerta.setHeaderText("Gracias por usar el programa :)");
        alerta.setContentText("Vuelva pronto");
        alerta.showAndWait();
    }
}
