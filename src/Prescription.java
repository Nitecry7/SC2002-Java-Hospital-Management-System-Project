/**
 * Represents a Prescription with details such as prescription ID, amount, frequency,
 * medicine name, and dispensed status.
 * This class provides mechanisms to create, manage, and display prescription information.
 */

 import java.io.IOException;
 import java.io.Serializable;
 import java.util.*;
 
 public class Prescription implements Serializable {
 
     private int prescriptionID;
     private int amount;
     private Frequency frequency;
     private String medicineName;
     private boolean dispensed = false;
 
     /**
      * Constructs a Prescription object and initializes it by gathering input data.
      */
     public Prescription() {
         build();
     }
 
     /**
      * Builds a new prescription by collecting required data and saving it to a CSV file.
      * Uses an {@link AttributeController} for user input.
      */
     private void build() {
         try {
             IOHandler prescriptionHandler = new CsvHandler("Prescription_List.csv");
             System.out.println("Please enter information for prescription");
             Collection<String[]> data = prescriptionHandler.readCsvValues();
             this.prescriptionID = data.size();
 
             AttributeController ac = AttributeController.getInstance();
             this.medicineName = ac.inputString("Please choose medicine name to use");
             this.amount = ac.inputInt("Input amount to prescribe");
             this.frequency = ac.inputFrequency("Please enter the frequency (PRN/BD/TDS/QDS/MANE/NOCTE):");
 
             String[] row = new String[5];
             row[0] = String.valueOf(prescriptionID);
             row[1] = String.valueOf(amount);
             row[2] = String.valueOf(frequency);
             row[3] = medicineName;
             row[4] = String.valueOf(dispensed);
 
             prescriptionHandler.addRow(row);
         } catch (IOException e) {
             System.out.println("Something bad happened :(");
         }
     }
 
     /**
      * Displays the prescription details in a formatted manner.
      */
     public void display() {
         System.out.println("Medicine Name: " + medicineName);
         System.out.println("Amount prescribed: " + amount);
         System.out.println("Frequency: " + frequency);
         System.out.println("Dispensed: " + (dispensed ? "Yes" : "No"));
     }
 
     /**
      * Retrieves the frequency of the prescription.
      *
      * @return The frequency of the prescription.
      */
     public Frequency getFrequency() {
         return frequency;
     }
 
     /**
      * Sets the frequency of the prescription.
      *
      * @param frequency The frequency to set.
      */
     public void setFrequency(Frequency frequency) {
         this.frequency = frequency;
     }
 
     /**
      * Retrieves the name of the medicine prescribed.
      *
      * @return The name of the medicine.
      */
     public String getMedicineName() {
         return medicineName;
     }
 
     /**
      * Sets the name of the medicine for the prescription.
      *
      * @param medicineName The name of the medicine to set.
      */
     public void setMedicineName(String medicineName) {
         this.medicineName = medicineName;
     }
 
     /**
      * Checks whether the prescription has been dispensed.
      *
      * @return True if the prescription is dispensed, false otherwise.
      */
     public boolean isDispensed() {
         return dispensed;
     }
 
     /**
      * Sets the dispensed status of the prescription.
      *
      * @param dispensed The dispensed status to set.
      */
     public void setDispensed(boolean dispensed) {
         this.dispensed = dispensed;
     }
 
     /**
      * Retrieves the amount prescribed.
      *
      * @return The amount prescribed.
      */
     public int getAmount() {
         return amount;
     }
 
     /**
      * Sets the amount prescribed.
      *
      * @param amount The amount to set.
      */
     public void setAmount(int amount) {
         this.amount = amount;
     }
 }
 