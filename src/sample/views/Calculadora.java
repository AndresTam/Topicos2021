package sample.views;
import com.sun.xml.internal.ws.commons.xmlutil.Converter;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Arrays;


public class Calculadora extends Stage implements EventHandler {
    private Scene escena;
    private TextField txtOperacion;
    private HBox[] hBoxes;
    private Button[] arBotones;
    private VBox vBox;
    private char [] arNumeros = {'7','8','9','/','4','5','6','+','1','2','3','*','0','.','=','-'};
    private String [] arOperandos = {"+","-",".","/"};
    private int pos;
    private int operaciones = 0;
    private String cadena ="";
    private String operando = "";
    private String numeros = "";

    public Calculadora(){
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
                        if(!String.valueOf(btnPresionado(caract)).equals("=")){
                            cadena += btnPresionado(caract);
                            if(!String.valueOf(btnPresionado(caract)).equals("+") && !String.valueOf(btnPresionado(caract)).equals("-")
                            && !String.valueOf(btnPresionado(caract)).equals("*") && !String.valueOf(btnPresionado(caract)).equals("/")){
                                numeros += btnPresionado(caract);
                                //txtOperacion.setText("" + btnPresionado(caract));
                                txtOperacion.setText(cadena);
                            } else if(String.valueOf(btnPresionado(caract)).equals("+") || String.valueOf(btnPresionado(caract)).equals("-")
                            || String.valueOf(btnPresionado(caract)).equals("*") || String.valueOf(btnPresionado(caract)).equals("/")){
                                txtOperacion.setText("");
                                cadena = "";
                                operaciones ++;
                                numeros += "-";
                                if(operaciones == 1){
                                    operando += btnPresionado(caract);
                                } else{
                                    operando += " " + btnPresionado(caract);
                                }
                            }
                        } else{
                            txtOperacion.setText(String.valueOf(jerarCalc(numeros,operando, operaciones)));
                            cadena = "";
                            operando = "";
                            numeros = "";
                            operaciones = 0;
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
    
    @Override
    public void handle(Event event) {
        System.out.println("Mi primer evento");
    }

    private char btnPresionado(String entrada){
        char caracter = Character.MIN_VALUE;
        caracter = entrada.charAt(entrada.length()-2);
        return caracter;
    }

    public double jerarCalc(String entrada, String operandos, int cantOperaciones){
        double resultado = 0;
        String partCadena[] = entrada.split("-");
        //System.out.println(partCadena.length);
        int sum = 0, rest = 0, mult = 0, div = 0;
        String tieneSum = "", tieneRest = "", tieneMult = "", tieneDiv = "";
        double suma = 0, resta = 0, multiplicacion = 0, division = 0;
        if(cantOperaciones > 1){
            String partOperandos[] = operandos.split("\\ ");
            for (int i = 0; i < cantOperaciones; i++) {
                if(partOperandos[i].equals("+")){
                    sum = i;
                    tieneSum = "si";
                    System.out.println("suma");
                } else if(partOperandos[i].equals("-")){
                    rest = i;
                    tieneRest = "si";
                    System.out.println("resta");
                } else if(partOperandos[i].equals("*")){
                    mult = i;
                    tieneMult = "si";
                    System.out.println("multi");
                } else if(partOperandos[i].equals("/")){
                    div = i;
                    tieneDiv = "si";
                    System.out.println("divi");
                }
            }
            if(tieneMult.equals("si") && mult < div){
                multiplicacion = Double.parseDouble(partCadena[mult]) * Double.parseDouble(partCadena[mult+1]);
                partCadena[mult] = "";
                partCadena[mult+1] = "";
                if(tieneDiv.equals("si")){
                    if(!partCadena[div].equals("") && partCadena[div+1].equals("")){
                        division = Double.parseDouble(partCadena[div]) / multiplicacion;
                        partCadena[div] = "";
                        if(tieneSum.equals("si") && sum < rest){
                            if(!partCadena[sum].equals("") && partCadena[sum+1].equals("")){
                                suma = Double.parseDouble(partCadena[sum]) + division;
                                partCadena[sum] = "";
                                if(tieneRest.equals("si")){
                                    if(!partCadena[rest].equals("") && partCadena[rest+1].equals("")){
                                        resta = Double.parseDouble(partCadena[rest]) - suma;
                                        partCadena[rest] = "";
                                        resultado = resta;
                                    } else if(partCadena[rest].equals("") && !partCadena[rest+1].equals("")){
                                        resta = suma - Double.parseDouble(partCadena[sum]);
                                        partCadena[rest+1] = "";
                                        resultado = resta;
                                    }
                                } else{
                                    resultado = suma;
                                }
                            } else if(partCadena[sum].equals("") && !partCadena[sum+1].equals("")){
                                suma = Double.parseDouble(partCadena[sum+1]) + division;
                                partCadena[sum+1] = "";
                                if(tieneRest.equals("si")){
                                    if(!partCadena[rest].equals("") && partCadena[rest+1].equals("")){
                                        resta = Double.parseDouble(partCadena[rest]) - suma;
                                        partCadena[rest] = "";
                                        resultado = resta;
                                    } else if(partCadena[rest].equals("") && !partCadena[rest+1].equals("")){
                                        resta = suma - Double.parseDouble(partCadena[rest+1]);
                                        partCadena[rest+1] = "";
                                        resultado = resta;
                                    }
                                } else{
                                    resultado = suma;
                                }
                            }
                        } else if(tieneRest.equals("si") && rest < sum){
                            if(!partCadena[rest].equals("") && partCadena[rest+1].equals("")) {
                                resta = Double.parseDouble(partCadena[rest]) - div;
                                partCadena[rest] = "";
                                if(tieneSum.equals("si")){
                                    if(!partCadena[sum].equals("") && partCadena[sum+1].equals("")){
                                        suma = Double.parseDouble(partCadena[sum]) + resta;
                                        partCadena[sum] = "";
                                        resultado = suma;
                                    } else if(partCadena[sum].equals("") && !partCadena[sum+1].equals("")){
                                        suma = Double.parseDouble(partCadena[sum+1]) + resta;
                                        partCadena[sum+1] = "";
                                        resultado = suma;
                                    }
                                }else{
                                    resultado = resta;
                                }
                            } else if(partCadena[rest].equals("") && !partCadena[rest+1].equals("")){
                                resta = div - Double.parseDouble(partCadena[rest+1]);
                                partCadena[rest+1] = "";
                                if(tieneSum.equals("si")){
                                    if(!partCadena[sum].equals("") && partCadena[sum+1].equals("")){
                                        suma = Double.parseDouble(partCadena[sum]) + resta;
                                        partCadena[sum] = "";
                                        resultado = suma;
                                    } else if(partCadena[sum].equals("") && !partCadena[sum+1].equals("")){
                                        suma = Double.parseDouble(partCadena[sum+1]) + resta;
                                        partCadena[sum+1] = "";
                                        resultado = suma;
                                    }
                                }else{
                                    resultado = resta;
                                }
                            }
                        } else{
                            resultado = division;
                        }
                    } else if(partCadena[div].equals("") && !partCadena[div+1].equals("")){
                        division = multiplicacion / Double.parseDouble(partCadena[div+1]);
                        partCadena[div+1] = "";
                        if(tieneSum.equals("si") && sum<rest){
                            if(!partCadena[sum].equals("") && partCadena[sum+1].equals("")){
                                suma = Double.parseDouble(partCadena[sum]) + division;
                                partCadena[sum] = "";
                                if(tieneRest.equals("si")){
                                    if(!partCadena[rest].equals("") && partCadena[rest+1].equals("")){
                                        resta = Double.parseDouble(partCadena[rest]) - suma;
                                        partCadena[rest] = "";
                                        resultado = resta;
                                    } else if(partCadena[rest].equals("") && !partCadena[rest+1].equals("")){
                                        resta = suma - Double.parseDouble(partCadena[sum]);
                                        partCadena[rest+1] = "";
                                        resultado = resta;
                                    }
                                } else{
                                    resultado = suma;
                                }
                            } else if(partCadena[sum].equals("") && !partCadena[sum+1].equals("")){
                                suma = Double.parseDouble(partCadena[sum+1]) + division;
                                partCadena[sum+1] = "";
                                if(tieneRest.equals("si")){
                                    if(!partCadena[rest].equals("") && partCadena[rest+1].equals("")){
                                        resta = Double.parseDouble(partCadena[sum]) - suma;
                                        partCadena[rest] = "";
                                        resultado = resta;
                                    } else if(partCadena[rest].equals("") && !partCadena[rest+1].equals("")){
                                        resta = suma - Double.parseDouble(partCadena[rest+1]);
                                        partCadena[rest+1] = "";
                                        resultado = resta;
                                    }
                                } else{
                                    resultado = suma;
                                }
                            }
                        } else if(tieneRest.equals("si") && rest < sum){
                            if(!partCadena[rest].equals("") && partCadena[rest+1].equals("")) {
                                resta = division - Double.parseDouble(partCadena[rest]);
                                partCadena[rest] = "";
                                if(tieneSum.equals("si")){
                                    if(!partCadena[sum].equals("") && partCadena[sum+1].equals("")){
                                        suma = Double.parseDouble(partCadena[sum]) + resta;
                                        partCadena[sum] = "";
                                        resultado = suma;
                                    } else if(partCadena[sum].equals("") && !partCadena[sum+1].equals("")){
                                        suma = Double.parseDouble(partCadena[sum+1]) + resta;
                                        partCadena[sum+1] = "";
                                        resultado = suma;
                                    }
                                }else{
                                    resultado = resta;
                                }
                            } else if(partCadena[rest].equals("") && !partCadena[rest+1].equals("")){
                                resta = division - Double.parseDouble(partCadena[rest+1]);
                                partCadena[rest+1] = "";
                                if(tieneSum.equals("si")){
                                    if(!partCadena[sum].equals("") && partCadena[sum+1].equals("")){
                                        suma = Double.parseDouble(partCadena[sum]) + resta;
                                        partCadena[sum] = "";
                                        resultado = suma;
                                    } else if(partCadena[sum].equals("") && !partCadena[sum+1].equals("")){
                                        suma = Double.parseDouble(partCadena[sum+1]) + resta;
                                        partCadena[sum+1] = "";
                                        resultado = suma;
                                    }
                                }else{
                                    resultado = resta;
                                }
                            }
                        } else{
                            resultado = division;
                        }
                    }
                }
            }// else if(tieneDiv.equals("si") && tieneMult.equals("")){

            //} else if(tieneSum.equals("si") && tieneMult.equals("")){

            //} else if(tieneRest.equals("si") && tieneMult.equals("")){

            //}
        } else{
            switch (operandos){
                case "+":
                    resultado = Double.parseDouble(partCadena[0]) + Double.parseDouble(partCadena[1]);
                    break;
                case "-":
                    resultado = Double.parseDouble(partCadena[0]) - Double.parseDouble(partCadena[1]);
                    break;
                case "*":
                    resultado = Double.parseDouble(partCadena[0]) * Double.parseDouble(partCadena[1]);
                    break;
                case "/":
                    resultado = Double.parseDouble(partCadena[0]) / Double.parseDouble(partCadena[1]);
                    break;
            }
        }
        return resultado;
    }
}

class EventoCalcu implements EventHandler{
    char tecla;
    public EventoCalcu(char tecla){
        this.tecla = tecla;
        System.out.println("Mi segundo evento");
    }

    @Override
    public void handle(Event event) {
        System.out.println("Mi segundo evento");
    }
}

