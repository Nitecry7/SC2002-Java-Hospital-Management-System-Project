/**
 * The AppointmentStatus enum represents the various states an appointment can be in.
 */
public enum AppointmentStatus {
    /**
     * The appointment is pending and has not yet been confirmed.
     */
    PENDING,

    /**
     * The appointment has been confirmed and is scheduled.
     */
    CONFIRMED,

    /**
     * The appointment has been canceled.
     */
    CANCELED,

    /**
     * The appointment has been completed successfully.
     */
    COMPLETED
}
