package sample.componentes;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import sample.models.CancionesDAO;
import sample.views.frmCancion;

import java.util.Optional;

public class CellCustome extends TableCell<CancionesDAO, String> {
    private Button btnCelda;
    private CancionesDAO objDAO;

    public CellCustome(int opc){
        if(opc == 1){
            btnCelda = new Button("Editar");
            btnCelda.setOnAction(event ->{
                objDAO = CellCustome.this.getTableView().getItems().get(CellCustome.this.getIndex());
                new frmCancion(CellCustome.this.getTableView(), objDAO);
            });
        }
        else{
            btnCelda = new Button("Borrar");
            btnCelda.setOnAction(event -> {
                Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
                alerta.setTitle("Mensaje del Sistema");
                alerta.setHeaderText("Confirmación de la acción");
                alerta.setContentText("¿Realmente desea borrar el registro?");
                Optional<ButtonType> result = alerta.showAndWait();
                if(result.get() == ButtonType.OK){
                    objDAO = CellCustome.this.getTableView().getItems().get(CellCustome.this.getIndex());
                    objDAO.DELETE();
                    CellCustome.this.getTableView().setItems(objDAO.SELECT());
                    CellCustome.this.getTableView().refresh();
                }
            });
        }
    }

    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if(!empty){
            setGraphic(btnCelda);
        }
    }
}
