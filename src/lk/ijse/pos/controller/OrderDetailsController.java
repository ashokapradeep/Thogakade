package lk.ijse.pos.controller;

public class OrderDetailsController {
    public void findOrderId(String id){
        System.out.println(id);
        //load all order Details with id
        //load all Customer details associated with order
        // SELECT * FROM `order` o JOIN Customer c ON o.customerId = c.id ---> dao
        
    }

    public void loadOrderId(String id) {
    }
}
