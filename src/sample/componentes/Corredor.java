package sample.componentes;

import javafx.scene.control.ProgressBar;

public class Corredor extends Thread{

    private ProgressBar progressBar;
    public Corredor(String nombre, ProgressBar progressBar){
        super(nombre);
        this.progressBar = progressBar;
        this.setName(nombre);
    }

    /*@Override
    public void run(){
        super.run();
        System.out.println("Inicia el corredor"+getName());
        for(int i = 1; i <= 5; i++){
            try{
                sleep((long)(Math.random()*2000));
            }catch (InterruptedException ex){
                ex.printStackTrace();
            }
            System.out.println("Corredor " + getName() +" completó el km " + i);
        }
        System.out.println("LLego a la meta el corredor "+ getName());
    }*/

    @Override
    public void run(){
        super.run();
        double avance = 0;
        while(avance < 1){
            avance += Math.random()/10;
            this.progressBar.setProgress(avance);
            try{
                sleep((long)(Math.random()*5000));
            }catch (InterruptedException ex){
                ex.printStackTrace();
            }
        }
    }
}
