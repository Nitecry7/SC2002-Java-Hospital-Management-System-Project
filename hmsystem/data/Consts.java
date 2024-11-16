package hmsystem.data;

public final class Consts {

    public static final String[] USER_TYPES = new String[] { "Patient", "Staff" };

    public static final class Patient {
        public static final String FILE_NAME = "hmsystem/data/Patient_List.csv";
        public static final int ID_COLUMN = 0;
        public static final int PW_COLUMN = 1;
        public static final int NAME_COLUMN = 2;
        public static final int DOB_COLUMN = 3;
        public static final int GENDER_COLUMN = 4;
        public static final int BLOODTYPE_COLUMN = 5;
        public static final int EMAIL_COLUMN = 6;
        public static final int CONTACTNUMBER_COUMN = 7;
        public static final int AOR_ID_COLUMN = 8;
        public static final int DIAGNOSISTREATMENT_COLUMN = 9;
    }

    public static final class Staff {
        public static final String FILE_NAME = "hmsystem/data/Staff_List.csv";
        public static final int ID_COLUMN = 0;
        public static final int PW_COLUMN = 1;
        public static final int NAME_COLUMN = 2;
        public static final int ROLE_COLUMN = 3;
        public static final int GENDER_COLUMN = 4;
        public static final int AGE_COLUMN = 5;
        public static final int EMAIL_COLUMN = 6;
        public static final int CONTACTNUMBER_COUMN = 7;
    }

    public static final class Medicine {
        public static final String FILE_NAME = "hmsystem/data/Medicine_List.csv";
        public static final int NAME_COLUMN = 0;
        public static final int STOCK_COLUMN = 1;
        public static final int ALERT_COLUMN = 2;
    }

    // public static final class AORList {
    //     public static final String FILE_NAME = "hmsystem/data/AOR_List.csv";
    //     public static final int ID_COLUMN = 0;
    //     public static final int DATE_COLUMN = 1;
    //     public static final int SERVICE_COLUMN = 2;
    //     public static final int PRESCRIPTION_COLUMN = 3;
    //     public static final int NOTES_COLUMN = 4;
    // }

    // public static final class AppointmentList {
    //     public static final String FILE_NAME = "hmsystem/data/Appointment_List.csv";
    //     public static final int ID_COLUMN = 0;
    //     public static final int PATIENT_ID_COLUMN = 1;
    //     public static final int DOCTOR_ID_COLUMN = 2;
    //     public static final int DATE_COLUMN = 3;
    //     public static final int TIME_COLUMN = 4;
    //     public static final int APPOINTMENT_STATUS_COLUMN = 5;
    // }

    public static final class AOR {
        public static final String FILE_NAME = "hmsystem/data/AppointmentAOR_List.csv";
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

    private Consts() {
    }
}
