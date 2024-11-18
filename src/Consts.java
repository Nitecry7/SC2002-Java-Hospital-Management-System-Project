/**
 * The Consts class holds constant values and nested classes for organizing and
 * managing application-wide constants, such as file names and column indices
 * for various entities.
 * 
 * <p>
 * This class is final and cannot be instantiated to ensure its usage as a
 * utility class for constants only.
 * </p>
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
        /**
         * File name for Availability.
         */
        public static final String FILE_NAME = "Availability.csv";
        /**
         * Index of the ID column for Availability.
         */
        public static final int ID_COLUMN = 0;
        /**
         * Index of the Doctor ID column for Availability.
         */
        public static final int DOCTOR_ID_COLUMN = 1;
        /**
         * Index of the Date column for Availability.
         */
        public static final int DATE_COLUMN = 2;
        /**
         * Index of the Time column for Availability.
         */
        public static final int TIME_COLUMN = 3;
    }

    /**
     * Constants related to patients.
     */
    public static final class Patient {
        /**
         * File name for Patient records.
         */
        public static final String FILE_NAME = "Patient_List.csv";
        /**
         * Index of the ID column for Patient.
         */
        public static final int ID_COLUMN = 0;
        /**
         * Index of the Password column for Patient.
         */
        public static final int PW_COLUMN = 1;
        /**
         * Index of the Name column for Patient.
         */
        public static final int NAME_COLUMN = 2;
        /**
         * Index of the Date of Birth column for Patient.
         */
        public static final int DOB_COLUMN = 3;
        /**
         * Index of the Gender column for Patient.
         */
        public static final int GENDER_COLUMN = 4;
        /**
         * Index of the Blood Type column for Patient.
         */
        public static final int BLOODTYPE_COLUMN = 5;
        /**
         * Index of the Email column for Patient.
         */
        public static final int EMAIL_COLUMN = 6;
        /**
         * Index of the Contact Number column for Patient.
         */
        public static final int CONTACTNUMBER_COLUMN = 7;
        /**
         * Index of the Appointment Order Record (AOR) ID column for Patient.
         */
        public static final int AOR_ID_COLUMN = 8;
        /**
         * Index of the Diagnosis and Treatment column for Patient.
         */
        public static final int DIAGNOSISTREATMENT_COLUMN = 9;
    }

    /**
     * Constants related to staff.
     */
    public static final class Staff {
        /**
         * File name for Staff records.
         */
        public static final String FILE_NAME = "Staff_List.csv";
        /**
         * Index of the ID column for Staff.
         */
        public static final int ID_COLUMN = 0;
        /**
         * Index of the Password column for Staff.
         */
        public static final int PW_COLUMN = 1;
        /**
         * Index of the Name column for Staff.
         */
        public static final int NAME_COLUMN = 2;
        /**
         * Index of the Role column for Staff.
         */
        public static final int ROLE_COLUMN = 3;
        /**
         * Index of the Gender column for Staff.
         */
        public static final int GENDER_COLUMN = 4;
        /**
         * Index of the Age column for Staff.
         */
        public static final int AGE_COLUMN = 5;
        /**
         * Index of the Email column for Staff.
         */
        public static final int EMAIL_COLUMN = 6;
        /**
         * Index of the Contact Number column for Staff.
         */
        public static final int CONTACTNUMBER_COLUMN = 7;
    }

    /**
     * Constants related to medicines.
     */
    public static final class Medicine {
        /**
         * File name for Medicine records.
         */
        public static final String FILE_NAME = "Medicine_List.csv";
        /**
         * Index of the Name column for Medicine.
         */
        public static final int NAME_COLUMN = 0;
        /**
         * Index of the Stock column for Medicine.
         */
        public static final int STOCK_COLUMN = 1;
        /**
         * Index of the Alert Line column for Medicine.
         */
        public static final int ALERT_COLUMN = 2;
    }

    /**
     * Constants related to replenishment requests.
     */
    public static final class Replenishment {
        /**
         * File name for Replenishment records.
         */
        public static final String FILE_NAME = "Replenishment_List.csv";
        /**
         * Index of the ID column for Replenishment.
         */
        public static final int ID_COLUMN = 0;
        /**
         * Index of the Names column for Replenishment.
         */
        public static final int NAMES_COLUMN = 1;
        /**
         * Index of the Status column for Replenishment.
         */
        public static final int STATUS_COLUMN = 2;
    }

    /**
     * Constants related to appointment order records (AOR).
     */
    public static final class AOR {
        /**
         * File name for Appointment Order Records.
         */
        public static final String FILE_NAME = "AppointmentAOR_List.csv";
        /**
         * Index of the ID column for AOR.
         */
        public static final int ID_COLUMN = 0;
        /**
         * Index of the Patient ID column for AOR.
         */
        public static final int PATIENT_ID_COLUMN = 1;
        /**
         * Index of the Patient Name column for AOR.
         */
        public static final int PATIENT_NAME_COLUMN = 2;
        /**
         * Index of the Doctor ID column for AOR.
         */
        public static final int DOCTOR_ID_COLUMN = 3;
        /**
         * Index of the Doctor Name column for AOR.
         */
        public static final int DOCTOR_NAME_COLUMN = 4;
        /**
         * Index of the Date column for AOR.
         */
        public static final int DATE_COLUMN = 5;
        /**
         * Index of the Time column for AOR.
         */
        public static final int TIME_COLUMN = 6;
        /**
         * Index of the Service column for AOR.
         */
        public static final int SERVICE_COLUMN = 7;
        /**
         * Index of the Prescription column for AOR.
         */
        public static final int PRESCRIPTION_COLUMN = 8;
        /**
         * Index of the Notes column for AOR.
         */
        public static final int NOTES_COLUMN = 9;
        /**
         * Index of the Appointment Status column for AOR.
         */
        public static final int APPOINTMENT_STATUS_COLUMN = 10;
    }

    /**
     * Private constructor to prevent instantiation of the Consts class.
     */
    private Consts() {
    }
}
