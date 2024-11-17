import hmsystem.controllers.AvailabilityController;

public class Test {
    public static void main(String[] args) {
        AvailabilityController ava = AvailabilityController.getInstance();
        ava.deleteBlockedSlot("D003");
    }
}
