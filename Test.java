import hmsystem.controllers.AvailabilityController;

public class Test {
    public static void main(String[] args) {
        AvailabilityController ava = AvailabilityController.getInstance();
        System.out.println(ava.checkSlot("D003", "2024-11-20","12:00"));
    }
}
