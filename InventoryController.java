package oop.controllers;

//Singleton
public class InventoryController {

    private  static final InventoryController inventoryController = new InventoryController();

    protected InventoryController() {

    }

    public static InventoryController getInstance() {
            return inventoryController;
        
    }

}
