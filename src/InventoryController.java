import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


// Singleton Class
public class InventoryController {
    private static InventoryController inventoryController = null;
    private static IOHandler csvhandler;

    
    protected InventoryController() {
        try {
            InventoryController.csvhandler = new CsvHandler(Consts.Medicine.FILE_NAME);
        } catch (IOException e) {
            System.err.println("Error reading the CSV file: " + e.getMessage());
            e.printStackTrace(); // Print stack trace for debugging
        }

    }

    public static InventoryController getInstance() {
       if (inventoryController == null) {
            inventoryController = new InventoryController();
        }
            return inventoryController;
        
    }

    public boolean reduceStock(String medicineName, int amount) {
        
        Medicine medicine = Medicine.getMedicine(medicineName, csvhandler);
        if(medicine == null){
            System.out.println("Medicine Not Found.");
            return false;
        }
        return medicine.reduceStock(amount);

    }
    

    // methods

    public int addMedication()
    {
        AttributeController getter = AttributeController.getInstance();
        String name = getter.inputString("Enter new medication name: ");
        int quantityNum = getter.inputInt("Enter new medication quantity: ");
        String quantity = Integer.toString(quantityNum);
        int lowAlertNum = getter.inputInt("Enter new medication low alert value: ");
        String lowAlert = Integer.toString(lowAlertNum);
        String[] temp = {name, quantity, lowAlert};
        //
        try{
            csvhandler.addRow(temp);
        }catch (IOException e){
            System.out.println("Error occured during adding medication!");
            return 0;
        }
        return 1;
    }

    public void viewMedicationInventory()
    {
        // read csv values
        Collection<String[]> rows = csvhandler.readCsvValues();
    
        // ArrayLists to store quantities
        ArrayList<String> medications = new ArrayList<>();
        ArrayList<String> quantities = new ArrayList<>();
        ArrayList<String> topup = new ArrayList<>();
    
        // add each row that has 3 or more length to the arrayList
        for (String[] row : rows) {
            if (row.length >= 3) { 
                medications.add(row[0]); 
                quantities.add(row[1]);  
                topup.add(row[2]); 
            }
        }
        
    
        // Print the medications and quantities
        System.out.println("Medication Inventory:");
        for (int i = 0; i < medications.size(); i++) {
            System.out.println(medications.get(i) + " : " + quantities.get(i) + ".");
            if (Integer.parseInt(quantities.get(i)) <= Integer.parseInt(topup.get(i)))
            {
                System.out.printf("Alert! %s amount under %s.\n", medications.get(i), topup.get(i));
            } 
        }
    
    }

    public int editMedication(String Medication)
    {
        AttributeController getter = AttributeController.getInstance();
        int change = getter.inputInt("Enter the top-up amount of " + Medication + ": ");
        String oldValue = csvhandler.getField(0, Medication, 1);
        int newValue = Integer.parseInt(oldValue) + change;

        //
        try{
            csvhandler.setField(0, Medication, 1, String.valueOf(newValue));
        }catch (IOException e){
            System.out.println("Error occured during setting value!");
            return 0;
        }
        return 1;
    }

    public int editMedicationList(List<String> Medications)
    {
        int x = 1;
        for (String medication:Medications)
        {   
            x &= editMedication(medication);
        }
        return x;
    }

    public int deleteMedication()
    {
        AttributeController getter = AttributeController.getInstance();
        String name = getter.inputString("Enter medication name to delete: ");
        try{
            csvhandler.removeRows(0,name);
        }catch (IOException e){
            System.out.println("Error occured during deleting medication!");
            return 0;
        }
        return 1;
    }

    





}