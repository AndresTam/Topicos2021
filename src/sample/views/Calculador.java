package sample.views;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class Calculador extends Stage implements EventHandler{
    private Scene escena;
    private TextField txtOperacion;
    private HBox[] hBoxes;
    private Button[] arBotones;
    private VBox vBox;
    private char [] arNumeros = {'7','8','9','/','4','5','6','+','1','2','3','*','0','.','=','-'};
    private String [] arOperandos = {"+","-",".","/"};
    private int pos;
    private String valor1 = "";
    private String valor2 = "";
    private String operando = "";

    public Calculador(){
        CrearUI();
        this.setTitle("Calculadora");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        vBox = new VBox();
        hBoxes = new HBox[4];
        arBotones = new Button[16];
        txtOperacion = new TextField();
        txtOperacion.setEditable(false);
        txtOperacion.setPrefHeight(50);
        txtOperacion.setPromptText("0");
        for (int i = 0; i<hBoxes.length; i++){ //Iterarme sobre el arreglo de HBox
            hBoxes[i] = new HBox();
            hBoxes[i].setSpacing(10);
            hBoxes[i].setPadding(new Insets(5));
            for (int j = 0; j < 4; j++) { //Ciclo para crear y cargar 4 botones
                arBotones[pos] = new Button("" + arNumeros[pos]);
                arBotones[pos].setPrefSize(50,50);
                //arBotones[pos].addEventHandler(MouseEvent.MOUSE_CLICKED, new EventoCalcu());
                arBotones[pos].addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        String caract = "" + event.getSource();
                        System.out.println(btnPresionado(caract));
                        if(!String.valueOf(btnPresionado(caract)).equals("=")){
                            if(!String.valueOf(btnPresionado(caract)).equals("+") && !String.valueOf(btnPresionado(caract)).equals("-")
                               && !String.valueOf(btnPresionado(caract)).equals("*") && !String.valueOf(btnPresionado(caract)).equals("/")
                               && !String.valueOf(btnPresionado(caract)).equals(".") && operando.equals("")){
                                valor1 += btnPresionado(caract);
                                txtOperacion.setText(valor1);
                            } else if(String.valueOf(btnPresionado(caract)).equals("+") || String.valueOf(btnPresionado(caract)).equals("-")
                               || String.valueOf(btnPresionado(caract)).equals("*") || String.valueOf(btnPresionado(caract)).equals("/")){
                                operando = "" + btnPresionado(caract);
                            } else if(!String.valueOf(btnPresionado(caract)).equals("+") && !String.valueOf(btnPresionado(caract)).equals("-")
                                    && !String.valueOf(btnPresionado(caract)).equals("*") && !String.valueOf(btnPresionado(caract)).equals("/")
                                    && !String.valueOf(btnPresionado(caract)).equals(".") && !operando.equals("")){
                                valor2 += btnPresionado(caract);
                                txtOperacion.setText(valor2);
                            }
                        } else{
                            if(valor1.equals("") || valor2.equals("") || operando.equals("")){
                                Alert dialAlert = new Alert(Alert.AlertType.CONFIRMATION);
                                dialAlert.setTitle("Error");
                                dialAlert.setHeaderText(null);
                                dialAlert.setContentText("La entrada que has ingresado no es correcta");
                                dialAlert.initStyle(StageStyle.UTILITY);
                                dialAlert.showAndWait();
                                valor1 = "";
                                valor2 = "";
                                operando = "";
                            } else {
                                txtOperacion.setText(String.valueOf(resOperaciones(valor1, valor2, operando)));
                                valor1 = "";
                                valor2 = "";
                                operando = "";
                            }
                        }
                    }
                });
                hBoxes[i].getChildren().add(arBotones[pos]);
                pos++;
            }
        }
        arBotones[pos-1].setId("font-button");
        vBox.getChildren().addAll(txtOperacion, hBoxes[0],hBoxes[1],hBoxes[2],hBoxes[3]);
        vBox.setPadding(new Insets(8,5,5,5));
        escena = new Scene(vBox, 250,250);
        escena.getStylesheets().add(getClass().getResource("../css/estilosCalcu.css").toExternalForm());
    }

    private char btnPresionado(String entrada){
        char caracter = Character.MIN_VALUE;
        caracter = entrada.charAt(entrada.length()-2);
        return caracter;
    }

    private double resOperaciones(String valor1, String valor2, String operandos){
        double resultado = 0;
        switch (operandos){
            case "+":
                resultado = Double.parseDouble(valor1) + Double.parseDouble(valor2);
                break;
            case "-":
                resultado = Double.parseDouble(valor1) - Double.parseDouble(valor2);
                break;
            case "*":
                resultado = Double.parseDouble(valor1) * Double.parseDouble(valor2);
                break;
            case "/":
                resultado = Double.parseDouble(valor1) / Double.parseDouble(valor2);
                break;
        }
        return resultado;
    }

    @Override
    public void handle(Event event) {

    }
}
