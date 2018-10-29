package seedu.address.storage;

import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.Date;
import seedu.address.model.appointment.Time;


/**
 * JAXB-friendly version of the Appointment.
 */
public class XmlAdaptedAppointment {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Appointment's %s field is missing!";

    @XmlElement(required = true)
    private String date;
    @XmlElement(required = true)
    private String time;
    @XmlElement(required = true)
    private String ic;
    @XmlElement(required = true)
    private int status;

    @XmlElement
    private int type;
    @XmlElement
    private String doctor;

    /**
     * Constructs an XmlAdaptedAppointment. This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedAppointment() {
    }

    /**
     * Constructs an {@code XmlAdaptedAppointment} with the given appointment details.
     */
    public XmlAdaptedAppointment(String date, String time, String ic, int status, int type, String doctor) {
        this.date = date;
        this.time = time;
        this.ic = ic;
        this.status = status;
        this.type = type;
        if (doctor != null) {
            this.doctor = doctor;
        }
    }

    /**
     * Converts a given Appointment into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedAppointment
     */
    public XmlAdaptedAppointment(Appointment source) {
        int day = source.getAppointmentDate().getDay();
        int month = source.getAppointmentDate().getMonth();
        int year = source.getAppointmentDate().getYear();

        date = String.valueOf(day) + " " + String.valueOf(month) + " " + String.valueOf(year);

        int hour = source.getAppointmentTime().getHour();
        int min = source.getAppointmentTime().getMinute();

        time = String.valueOf(hour) + " " + String.valueOf(min);
        ic = "TODO: Get ic string of patient";
        status = source.getAppointmentStatus();
        type = source.getAppointmentType();
        doctor = source.getAssignedDoctor().toString();
    }

    /**
     * Converts this jaxb-friendly adapted appointment object into the model's Appointment object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted appointment
     */
    public Appointment toModelType() throws IllegalValueException {
        if (date == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName()));
        }
        if (!Date.isValidDate(date)) {
            throw new IllegalValueException(String.format(Date.MESSAGE_DATE_CONSTRAINTS));
        }
        if (time == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Time.class.getSimpleName()));
        }
        if (!Time.isValidTime(time)) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Time.class.getSimpleName()));
        }
        //TODO: Add check for validity of ic
        if (ic == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "IC"));
        }
        //TODO: Add ic once appointment takes in ic
        return new Appointment(Date.newDate(date), Time.newTime(time), null, type);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedAppointment)) {
            return false;
        }

        XmlAdaptedAppointment otherAppt = (XmlAdaptedAppointment) other;
        return Objects.equals(date, otherAppt.date)
                && Objects.equals(time, otherAppt.time)
                && Objects.equals(ic, otherAppt.ic)
                && Objects.equals(status, otherAppt.ic)
                && Objects.equals(type, otherAppt.ic)
                && Objects.equals(doctor, otherAppt.doctor);
    }
}
