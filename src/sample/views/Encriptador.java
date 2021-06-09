package sample.views;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.control.TextArea;
import javafx.stage.StageStyle;

import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Encriptador extends Stage implements EventHandler<KeyEvent> {

    private Scene escena;
    private HBox hBox;
    private VBox vBox;
    private ToolBar tolMenu;
    private TextArea txtEntrada;
    private TextArea txtSalida;
    private Button btnEncriptar;
    private Button btnTolAbrir;
    private FileChooser fileChooser;
    private FileReader fileReader;
    private BufferedReader bufferedReader;

    public Encriptador(){
        CrearUI();

        this.setTitle("Función de encriptacion con HASH");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI(){
        vBox        = new VBox();
        tolMenu     = new ToolBar();
        btnTolAbrir = new Button();
        ImageView imv = new ImageView("sample/assets/iconfolder_open.png");
        imv.setFitWidth(35);
        imv.setPreserveRatio(true);
        btnTolAbrir.setGraphic(imv);
        btnTolAbrir.setOnAction(event -> abrirArchivo());

        hBox       = new HBox();
        txtEntrada = new TextArea();
        txtEntrada.setWrapText(true);
        txtEntrada.setOnKeyPressed(this);

        txtSalida  = new TextArea();
        txtSalida.setWrapText(true);
        txtSalida.setEditable(false);

        btnEncriptar = new Button();
        tolMenu.getItems().add(btnTolAbrir);

        hBox.getChildren().addAll(txtEntrada, txtSalida);
        btnEncriptar = new Button("Encriptar entrada");
        btnEncriptar.setOnAction(event -> encriptarEntrada());
        vBox.getChildren().addAll(tolMenu, hBox, btnEncriptar);

        escena = new Scene(vBox,600,300);
    }

    private void encriptarEntrada(){
        String codigo = "";
        String entrada = txtEntrada.getText();
        if(!entrada.equals("")){
            for (int i = 0; i<entrada.length(); i++){
                codigo += (int)entrada.charAt(i) +" ";
                txtSalida.setText(codigo);
            }
        } else{
            Alert dialAlert = new Alert(Alert.AlertType.CONFIRMATION);
            dialAlert.setTitle("Error");
            dialAlert.setHeaderText(null);
            dialAlert.setContentText("El campo de entrada no debe estar vacio");
            dialAlert.initStyle(StageStyle.UTILITY);
            dialAlert.showAndWait();
        }
    }

    private void abrirArchivo(){
        fileChooser = new FileChooser();
        fileChooser.setTitle("Buscar archivo a encriptar: ");
        File file = fileChooser.showOpenDialog(this);
        if(file != null){
            fileReader = null;
            bufferedReader = null;
            String texto = "";
            try{
                fileReader = new FileReader(file);
                bufferedReader = new BufferedReader(fileReader);
                String st = bufferedReader.readLine();
                while(st != null){
                    texto += st + "\n";
                    st = bufferedReader.readLine();
                    System.out.println(texto);
                }
            } catch(Exception e){
                System.out.println(e);
            } finally {
                try{
                    fileReader.close();
                } catch(Exception e2){
                    System.out.println(e2);
                }
            }
            txtEntrada.setText(texto);
        }
    }

    @Override
    public void handle(KeyEvent event) {

    }
}
