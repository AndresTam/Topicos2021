package sample.views;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sample.componentes.Corredor;

public class Pista extends Stage {

    Scene escena;
    private VBox vBox;
    private ProgressBar[] carriles;
    private Corredor[] corredores;
    private String[] nomCorredores = {"Bob Esponja", "Flash", "Quick Silver", "Homero", "Shrek"};

    public Pista(){
        CrearUI();
        this.setTitle("Pistad de atletismo");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        vBox = new VBox();
        vBox.setSpacing(10.0);
        vBox.setPadding(new Insets(10.0));
        carriles = new ProgressBar[5];
        corredores = new Corredor[5];
        for(int i = 0; i < carriles.length; i++){
            carriles[i] = new ProgressBar(0);
            carriles[i].setPrefWidth(180);
            corredores[i] = new Corredor(nomCorredores[i], carriles[i]);
            corredores[i].start();
            vBox.getChildren().add(carriles[i]);
        }
        escena = new Scene(vBox, 200,180);
    }
}
