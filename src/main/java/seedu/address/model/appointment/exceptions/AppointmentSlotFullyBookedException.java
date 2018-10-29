package seedu.address.model.appointment.exceptions;

/**
 * Signals that all appointment slots at the same date and timing are all booked.
 */
public class AppointmentSlotFullyBookedException extends RuntimeException{
    public AppointmentSlotFullyBookedException() {
        super("Operation would result in overbooking Appointments");
    }
}
