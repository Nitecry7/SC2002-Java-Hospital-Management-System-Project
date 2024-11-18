/**
 * The Consts class holds constant values and nested classes for organizing and
 * managing application-wide constants such as file names and column indices
 * for various entities.
 */
public final class Consts {

    /**
     * Array of user types used in the application.
     */
    public static final String[] USER_TYPES = new String[] { "Patient", "Staff" };

    /**
     * Constants related to availability.
     */
    public static final class Availability {
        public static final String FILE_NAME = "Availability.csv";
        public static final int ID_COLUMN = 0;
        public static final int DOCTOR_ID_COLUMN = 1;
        public static final int DATE_COLUMN = 2;
        public static final int TIME_COLUMN = 3;
    }

    /**
     * Constants related to patients.
     */
    public static final class Patient {
        public static final String FILE_NAME = "Patient_List.csv";
        public static final int ID_COLUMN = 0;
        public static final int PW_COLUMN = 1;
        public static final int NAME_COLUMN = 2;
        public static final int DOB_COLUMN = 3;
        public static final int GENDER_COLUMN = 4;
        public static final int BLOODTYPE_COLUMN = 5;
        public static final int EMAIL_COLUMN = 6;
        public static final int CONTACTNUMBER_COLUMN = 7;
        public static final int AOR_ID_COLUMN = 8;
        public static final int DIAGNOSISTREATMENT_COLUMN = 9;
    }

    /**
     * Constants related to staff.
     */
    public static final class Staff {
        public static final String FILE_NAME = "Staff_List.csv";
        public static final int ID_COLUMN = 0;
        public static final int PW_COLUMN = 1;
        public static final int NAME_COLUMN = 2;
        public static final int ROLE_COLUMN = 3;
        public static final int GENDER_COLUMN = 4;
        public static final int AGE_COLUMN = 5;
        public static final int EMAIL_COLUMN = 6;
        public static final int CONTACTNUMBER_COLUMN = 7;
    }

    /**
     * Constants related to medicines.
     */
    public static final class Medicine {
        public static final String FILE_NAME = "Medicine_List.csv";
        public static final int NAME_COLUMN = 0;
        public static final int STOCK_COLUMN = 1;
        public static final int ALERT_COLUMN = 2;
    }

    /**
     * Constants related to replenishment requests.
     */
    public static final class Replenishment {
        public static final String FILE_NAME = "Replenishment_List.csv";
        public static final int ID_COLUMN = 0;
        public static final int NAMES_COLUMN = 1;
        public static final int STATUS_COLUMN = 2;
    }

    /**
     * Constants related to appointment order records (AOR).
     */
    public static final class AOR {
        public static final String FILE_NAME = "AppointmentAOR_List.csv";
        public static final int ID_COLUMN = 0;
        public static final int PATIENT_ID_COLUMN = 1;
        public static final int PATIENT_NAME_COLUMN = 2;
        public static final int DOCTOR_ID_COLUMN = 3;
        public static final int DOCTOR_NAME_COLUMN = 4;
        public static final int DATE_COLUMN = 5;
        public static final int TIME_COLUMN = 6;
        public static final int SERVICE_COLUMN = 7;
        public static final int PRESCRIPTION_COLUMN = 8;
        public static final int NOTES_COLUMN = 9;
        public static final int APPOINTMENT_STATUS_COLUMN = 10;
    }

    /**
     * Private constructor to prevent instantiation of the Consts class.
     */
    private Consts() {
    }
}