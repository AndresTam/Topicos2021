package sample.views;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import sample.models.CancionesDAO;

public class FrmCanciones extends Stage {
    private TableView<CancionesDAO> tbvCanciones;
    private Scene escena;

    public FrmCanciones(){
        CrearUI();
        this.setTitle("Gestion de Canciones");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        tbvCanciones = new TableView<>();
        escena = new Scene(tbvCanciones, 300, 250);
    }
}
