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
import lk.ijse.pos.bo.custom.CustomerBo;
import lk.ijse.pos.dto.CustomerDto;
import lk.ijse.pos.view.tm.CustomerTM;

import java.io.IOException;
import java.sql.SQLException;

public class CustomerFormController {
    public TextField txtName;
    public TextField txtSalary;
    public TextField txtAddress;
    public TextField txtSearch;
    public TableView<CustomerTM> tblCustomer;
    public TableColumn colId;
    public TableColumn colName;
    public TableColumn colAddress;
    public TableColumn colSalary;
    public TableColumn colOptions;
    public AnchorPane customerContainer;
    public Button btnSaveUpdate;

    private CustomerBo customerBo = BoFactory.getInstance().getBo(BoFactory.BoType.CUSTOMER);

    public void initialize(){


        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colSalary.setCellValueFactory(new PropertyValueFactory<>("salary"));
        colOptions.setCellValueFactory(new PropertyValueFactory<>("btn"));

        loadAllCustomer("");

        /*Listener*/
        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            loadAllCustomer(newValue);
        });

        tblCustomer.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue!=null){
                setData(newValue);
            }
        });

        /*Listener*/
    }

    private String id="";

    private void setData(CustomerTM value) {
        btnSaveUpdate.setText("Update Customer");
        id = value.getId();

        txtName.setText(value.getName());
        txtSalary.setText(String.valueOf(value.getSalary()));
        txtAddress.setText(value.getAddress());
    }

    private void loadAllCustomer(String text) {
        ObservableList<CustomerTM> tmList = FXCollections.observableArrayList();
        try {
            for (CustomerDto dto : customerBo.searchCustomer(text)
            ) {
                Button btn = new Button("Delete");
                CustomerTM tm = new CustomerTM(
                        dto.getId(), dto.getName(), dto.getAddress(), dto.getSalary(), btn
                );

                tmList.add(tm);

                /*===============*/
                btn.setOnAction((e) -> {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure  want to delete this customer?", ButtonType.YES, ButtonType.NO);
                    alert.showAndWait();
                    if (alert.getResult() == ButtonType.YES) {
                        try {
                            if (customerBo.deleteCustomer(tm.getId())) {
                                new Alert(Alert.AlertType.INFORMATION, "Deleted", ButtonType.OK).show();
                                loadAllCustomer("");
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
            tblCustomer.setItems(tmList);
    }

    public void backToHomeOnAction(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage) customerContainer.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader
                .load(getClass().getResource("../view/DashboardForm.fxml"))));
        stage.centerOnScreen();
        stage.setTitle("Dashboard");
    }

    public void newCustomerOnAction(ActionEvent actionEvent) {
        btnSaveUpdate.setText("Save Customer");
        clearData();

    }


    public void saveCustomerOnAction(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if(btnSaveUpdate.getText().equalsIgnoreCase("Save Customer")){
            CustomerDto dto = new CustomerDto("",txtName.getText(),txtAddress.getText(),
                    Double.parseDouble(txtSalary.getText())
            );

            if (customerBo.saveCustomer(dto)){
                loadAllCustomer("");
                new Alert(Alert.AlertType.CONFIRMATION,"Saved!", ButtonType.OK).show();
            }else {
                new Alert(Alert.AlertType.WARNING,"Try Again", ButtonType.OK).show();
            }

        }else{
            if(id.length()==0)return;
            CustomerDto dto = new CustomerDto(id,txtName.getText(),txtAddress.getText(),
                    Double.parseDouble(txtSalary.getText())
            );
            if (customerBo.updateCustomer(dto)){
                loadAllCustomer("");
                new Alert(Alert.AlertType.CONFIRMATION,"Updated!", ButtonType.OK).show();
            }else {
                new Alert(Alert.AlertType.WARNING,"Try Again", ButtonType.OK).show();
            }
        }


    }
    private void clearData(){
        txtAddress.clear();
        txtName.clear();
        txtSalary.clear();

    }

}
