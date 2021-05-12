package sample.views;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sample.models.CancionesDAO;

public class frmCancion extends Stage {
    private Scene escena;
    private VBox vBox;
    private TextField txtNombre, txtDuracion, txtPortada, txtAnio;
    private TextArea txaLetra;
    private Button btnGuardar;
    private CancionesDAO objDAO;
    private TableView<CancionesDAO> tbvCanciones;

    public frmCancion(TableView<CancionesDAO> tbvCanciones, CancionesDAO objDAO){
        this.tbvCanciones = tbvCanciones;
        if(objDAO != null){
            this.objDAO = objDAO;
        }else{
            this.objDAO = new CancionesDAO();
        }
        CrearUI();
        this.setTitle("Gestion de Canción");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        vBox = new VBox();
        vBox.setSpacing(10.0);
        vBox.setPadding(new Insets(10.0));
        txtNombre = new TextField();
        txtNombre.setText(objDAO.getNombre_cancion());
        txtNombre.setPromptText("Nombre de la cancion");
        txtDuracion = new TextField();
        txtDuracion.setText(String.valueOf(objDAO.getDuracion()));
        txtDuracion.setPromptText("Duracion de la cancion");
        txtPortada = new TextField();
        txtPortada.setText(objDAO.getPortada());
        txtPortada.setPromptText("Portada del disco");
        txtAnio = new TextField();
        txtAnio.setText(String.valueOf(objDAO.getAnio()));
        txtAnio.setPromptText("Año de lanzamiento");
        txaLetra = new TextArea();
        txaLetra.setText(objDAO.getLetra());
        txaLetra.setPromptText("Letra de la cancion");
        btnGuardar = new Button("Guardar");
        btnGuardar.setOnAction(event -> {
            objDAO.setNombre_cancion(txtNombre.getText());
            objDAO.setDuracion(Integer.parseInt(txtDuracion.getText()));
            objDAO.setPortada(txtPortada.getText());
            objDAO.setAnio(Integer.parseInt(txtAnio.getText()));
            objDAO.setLetra(txaLetra.getText());
            if(objDAO.getId_cancion() > 0){
                objDAO.UPDATE();
            }else{
                objDAO.INSERT();
            }

            tbvCanciones.setItems(objDAO.SELECT());
            tbvCanciones.refresh();

            this.close();
        });
        vBox.getChildren().addAll(txtNombre,txtDuracion,txtPortada,txtAnio,txaLetra,btnGuardar);
        escena = new Scene(vBox,250,300);
    }
}
