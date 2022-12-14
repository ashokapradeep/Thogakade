package lk.ijse.pos.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import lk.ijse.pos.bo.BoFactory;
import lk.ijse.pos.bo.custom.OrderBo;
import lk.ijse.pos.dto.OrderDto;
import lk.ijse.pos.view.tm.OrderTM;

import java.io.IOException;
import java.sql.SQLException;

public class OrderFormController {
    public TableView<OrderTM> tblOrder;
    public TableColumn colOrderId;
    public TableColumn colDate;
    public TableColumn colCost;

    private OrderBo bo = BoFactory.getInstance().getBo(BoFactory.BoType.ORDER);

    public void initialize() throws SQLException, ClassNotFoundException {
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colCost.setCellValueFactory(new PropertyValueFactory<>("cost"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
         loadAllOrders();

         /* ================== */
        tblOrder.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue==null) return;
            try {
                loadDetailsWindow(newValue);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
         /* ================== */

    }

    private void loadDetailsWindow(OrderTM orderData) throws IOException {
        FXMLLoader loader =new FXMLLoader(getClass().getResource("../view/OrderDetails.fxml"));
        Parent parent = loader.load();
        OrderDetailsController controller = loader.getController();

        controller.loadOrderId(orderData.getId());

        Stage stage = new Stage();
        stage.setScene(new Scene(parent));
        stage.show();
    }

    ObservableList<OrderTM> obList = FXCollections.observableArrayList();

    private void loadAllOrders() throws SQLException, ClassNotFoundException {

        for (OrderDto dto:bo.getAllOrders()
             ) {
            obList.add(
                   new OrderTM(dto.getId(), dto.getDate(), dto.getCost())
            );
        }

        tblOrder.setItems(obList);
    }


}
