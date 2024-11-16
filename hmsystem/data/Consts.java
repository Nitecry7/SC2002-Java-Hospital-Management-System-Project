package hmsystem.data;

public final class Consts {

    public static final String[] USER_TYPES = new String[]{"Patient", "Staff"};

    public static final class Patient {
        public static final String FILE_NAME = "Patient_List.csv";
        public static final int ID_COLUMN = 0;
        public static final int PW_COLUMN = 1;
        public static final int NAME_COLUMN = 2;
        public static final int DOB_COLUMN = 3;
        public static final int GENDER_COLUMN = 4;
        public static final int BLOODTYPE_COLUMN = 2;
        public static final int EMAIL_COLUMN = 2;
    }

    public static final class Staff {
        public static final String FILE_NAME = "Staff_List.csv";
        public static final int ID_COLUMN = 0;
        public static final int PW_COLUMN = 1;
        public static final int NAME_COLUMN = 2;
        public static final int ROLE_COLUMN = 3;
        public static final int GENDER_COLUMN = 4;
        public static final int AGE_COLUMN = 5;
    }

  public static final class Medicine {
        public static final String FILE_NAME = "Medicine_List.csv";
        public static final int NAME_COLUMN = 0;
        public static final int STOCK_COLUMN = 1;
        public static final int ALERT_COLUMN = 2;
  }

  //appointmentID,patientName,doctorName,serviceType,dateObject,prescription[],notes
    public static final class AOR {
        public static final String FILE_NAME = "AOR_List.csv";
        public static final int ID_COLUMN = 0;
        public static final int PATIENT_COLUMN = 1;
        public static final int DOCTOR_COLUMN = 2;
        public static final int SERVICETYPE_COLUMN = 3;
        public static final int DATE_COLUMN = 4;
        public static final int PRESCRIPTIONS_COLUMN = 5;
        public static final int NOTES_COLUMN = 4;
  }

    private Consts() {}
}
