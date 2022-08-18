package lk.ijse.pos.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.pos.bo.BoFactory;
import lk.ijse.pos.bo.custom.ItemBo;
import lk.ijse.pos.dto.ItemDto;
import lk.ijse.pos.view.tm.ItemTM;

import java.io.IOException;
import java.sql.SQLException;

public class ItemFormController {

    public TextField txtSearch;
    public Button btnSaveUpdate;
    public AnchorPane itemContainer;
    public TextField txtDescription;
    public TextField txtUnitPrice;
    public TextField txtQtyAvailable;
    public TableView<ItemTM> tblItem;
    public TableColumn colCode;
    public TableColumn colDescription;
    public TableColumn colUnitPrice;
    public TableColumn colQtyAvailable;
    public TableColumn colOptions;

    private ItemBo itemBo = BoFactory.getInstance().getBo(BoFactory.BoType.ITEM);

    public void initialize(){


        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQtyAvailable.setCellValueFactory(new PropertyValueFactory<>("qtyAvailable"));
        colOptions.setCellValueFactory(new PropertyValueFactory<>("btn"));

        loadAllItems("");

        /*Listener*/
        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            loadAllItems(newValue);
        });

        tblItem.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue!=null){
                setData(newValue);
            }
        });

        /*Listener*/
    }

    private String code="";

    private void setData(ItemTM value) {
        btnSaveUpdate.setText("Update Item");
        code = value.getCode();
        txtDescription.setText(value.getDescription());
        txtUnitPrice.setText(String.valueOf(value.getUnitPrice()));
        txtQtyAvailable.setText(String.valueOf(value.getQtyAvailable()));
    }

    private void loadAllItems(String text) {
        ObservableList<ItemTM> tmList = FXCollections.observableArrayList();
        try {
            for (ItemDto dto : itemBo.searchItem(text)
            ) {
                Button btn = new Button("Delete");
                ItemTM tm = new ItemTM(
                        dto.getCode(), dto.getDescription(), dto.getUnitPrice(), dto.getQtyAvailable(), btn
                );

                tmList.add(tm);

                /*===============*/
                btn.setOnAction((e) -> {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure  want to delete this Item?", ButtonType.YES, ButtonType.NO);
                    alert.showAndWait();
                    if (alert.getResult() == ButtonType.YES) {
                        try {
                            if (itemBo.deleteItem(tm.getCode())) {
                                new Alert(Alert.AlertType.INFORMATION, "Deleted", ButtonType.OK).show();
                                loadAllItems("");
                            }
                        } catch (SQLException | ClassNotFoundException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                });
                /*===============*/
            }
            }catch(Exception e){
                System.out.println(e);
            }
            tblItem.setItems(tmList);
    }

    public void backToHomeOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) itemContainer.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader
                .load(getClass().getResource("../view/DashboardForm.fxml"))));
        stage.centerOnScreen();
        stage.setTitle("Dashboard");
    }

    public void newItemOnAction(ActionEvent actionEvent) {
        btnSaveUpdate.setText("Save Item");
        clearData();
    }

    public void saveItemOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if(btnSaveUpdate.getText().equalsIgnoreCase("Save Item")){
            ItemDto dto = new ItemDto("",txtDescription.getText(),Double.parseDouble(txtUnitPrice.getText()),
                   Integer.parseInt(txtQtyAvailable.getText())
            );

            if (itemBo.saveItem(dto)){
                loadAllItems("");
                new Alert(Alert.AlertType.CONFIRMATION,"Saved!", ButtonType.OK).show();
            }else {
                new Alert(Alert.AlertType.WARNING,"Try Again", ButtonType.OK).show();
            }

        }else{
            if(code.length()==0)return;
            ItemDto dto = new ItemDto(code,txtDescription.getText(),Double.parseDouble(txtUnitPrice.getText()),
                    Integer.parseInt(txtQtyAvailable.getText())
            );
            if (itemBo.updateItem(dto)){
                loadAllItems("");
                new Alert(Alert.AlertType.CONFIRMATION,"Updated!", ButtonType.OK).show();
            }else {
                new Alert(Alert.AlertType.WARNING,"Try Again", ButtonType.OK).show();
            }
        }
    }
    private void clearData(){
        txtDescription.clear();
        txtUnitPrice.clear();
        txtQtyAvailable.clear();

    }
}
