package seedu.address.model.appointment;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalPersons.ADAM;
import static seedu.address.testutil.TypicalPersons.ALICE_AS_PATIENT;
import static seedu.address.testutil.TypicalPersons.BEN;
import static seedu.address.testutil.TypicalPersons.BENSON_AS_PATIENT;

import org.junit.Test;

import seedu.address.testutil.Assert;

import java.util.Optional;

public class AppointmentTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Appointment(null, null, null, 0));
    }

    @Test
    public void cancelAppointment() {
        Date validDate = new Date(2, 2, 2002);
        Time validTime = new Time(12, 20);

        Appointment appt = new Appointment(validDate, validTime, ALICE_AS_PATIENT, 1);
        Appointment appt2 = new Appointment(validDate, validTime, ALICE_AS_PATIENT, 1);

        appt.cancelAppointment();

        //0 means cancelled
        assertTrue(appt.getAppointmentStatus() == 0);
        assertFalse(appt.getAppointmentStatus() == appt2.getAppointmentStatus());
    }

    @Test
    public void isSameSlot() {
        Date validDate = new Date(2, 2, 2002);
        Date otherDate = new Date(3, 12, 2002);

        Time validTime = new Time(12, 20);
        Time otherTime = new Time(23, 10);

        Appointment appt = new Appointment(validDate, validTime, ALICE_AS_PATIENT, 1);
        Appointment appt2 = new Appointment(validDate, validTime, BENSON_AS_PATIENT, 1);
        Appointment appt3 = new Appointment(otherDate, validTime, ALICE_AS_PATIENT, 1);
        Appointment appt4 = new Appointment(otherDate, otherTime, ALICE_AS_PATIENT, 1);

        //same obj
        assertTrue(appt.isSameSlot(appt));

        //same date and time
        assertTrue(appt.isSameSlot(appt2));

        //different date
        assertFalse(appt.isSameSlot(appt3));

        //different time
        assertFalse(appt3.isSameSlot(appt4));

        //different date and time
        assertFalse(appt.isSameSlot(appt4));
    }

    @Test
    public void isSamePatient() {
        Date validDate = new Date(2, 2, 2222);

        Time validTime = new Time(12, 0);

        Appointment appt = new Appointment(validDate, validTime, ALICE_AS_PATIENT, 0);
        Appointment appt2 = new Appointment(validDate, validTime, BENSON_AS_PATIENT, 0);
        Appointment appt3 = new Appointment(validDate, validTime, BENSON_AS_PATIENT, 0);

        //same obj
        assertTrue(appt.isSamePatient(appt));

        //same patient
        assertTrue(appt3.isSamePatient(appt2));

        //diff patient
        assertFalse(appt.isSamePatient(appt2));
    }

    @Test
    public void isSameDoctor() {
        Date validDate = new Date(2, 2, 2222);
        Time validTime = new Time(12, 0);

        Appointment appt = new Appointment(validDate, validTime, ALICE_AS_PATIENT, 0);
        Appointment appt2 = new Appointment(validDate, validTime, BENSON_AS_PATIENT, 1);

        //same obj - both null
        assertTrue(appt.isSameDoctor(appt));

        //diff fields, same doctor - both null
        assertTrue(appt.isSameDoctor(appt2));

        //same obj - not null
        appt.setAssignedDoctor(ADAM);
        assertTrue(appt.isSameDoctor(appt));

        //diff doctor - 1 null
        assertFalse(appt2.isSameDoctor(appt));

        //diff fields, same doctor - not null
        appt2.setAssignedDoctor(ADAM);
        assertTrue(appt.isSameDoctor(appt2));

        //diff doctor - not null
        appt2.setAssignedDoctor(BEN);
        assertFalse(appt.isSameDoctor(appt2));
    }

    @Test
    public void isSameAppointment() {
        Date validDate = new Date(2, 2, 2222);
        Date otherDate = new Date(2, 3, 2222);

        Time validTime = new Time(12, 0);
        Time otherTime = new Time(13, 0);

        Appointment appt = new Appointment(validDate, validTime, ALICE_AS_PATIENT, 0);
        Appointment appt2 = new Appointment(validDate, validTime, BENSON_AS_PATIENT, 0);
        Appointment appt3 = new Appointment(validDate, validTime, BENSON_AS_PATIENT, 0);
        Appointment appt4 = new Appointment(validDate, otherTime, ALICE_AS_PATIENT, 0);
        Appointment appt5 = new Appointment(otherDate, otherTime, ALICE_AS_PATIENT, 0);
        Appointment appt6 = new Appointment(otherDate, otherTime, ALICE_AS_PATIENT, 0);

        //same obj
        assertTrue(appt.isSameAppointment(appt));

        //same fields
        assertTrue(appt2.equals(appt3));

        //diff patient
        assertFalse(appt.isSameAppointment(appt2));

        //diff time
        assertFalse(appt.isSameAppointment(appt4));

        //diff date
        assertFalse(appt5.isSameAppointment(appt4));

        //diff doctors - 1 null
        appt6.setAssignedDoctor(ADAM);
        assertFalse(appt6.isSameAppointment(appt5));

        //diff doctors
        appt5.setAssignedDoctor(BEN);
        assertFalse(appt6.isSameAppointment(appt5));

        //diff status
        //status change has no effect
        appt3.cancelAppointment();
        assertTrue(appt3.isSameAppointment(appt2));
    }

    @Test
    public void isOverlapAppointment() {
        Date validDate = new Date(2, 2, 2222);

        Time validTime = new Time(12, 0);
        Time otherTime = new Time(13, 0);
        Time otherTime2 = new Time(12, 30);
        Time otherTime3 = new Time(13, 30);

        Appointment appt = new Appointment(validDate, validTime, ALICE_AS_PATIENT, 0);
        Appointment appt2 = new Appointment(validDate, validTime, BENSON_AS_PATIENT, 0);
        Appointment appt3 = new Appointment(validDate, validTime, BENSON_AS_PATIENT, 0);
        Appointment appt4 = new Appointment(validDate, otherTime, ALICE_AS_PATIENT, 0);
        Appointment appt5 = new Appointment(validDate, otherTime2, ALICE_AS_PATIENT, 0);
        Appointment appt6 = new Appointment(validDate, otherTime3, ALICE_AS_PATIENT, 0);

        //same obj
        assertTrue(appt.isOverlapAppointment(appt));

        //same timing
        assertTrue(appt2.isOverlapAppointment(appt3));
        assertTrue(appt.isOverlapAppointment(appt2));

        //this is later than appt but within overlap
        assertTrue(appt4.isOverlapAppointment(appt5));

        //this is earlier than appt but within overlap
        assertTrue(appt5.isOverlapAppointment(appt4));

        //1 hour from prev appt
        assertFalse(appt5.isOverlapAppointment(appt6));

        //diff doctor - 1 null
        appt2.setAssignedDoctor(ADAM);
        assertFalse(appt2.isOverlapAppointment(appt3));

        //diff doctor
        appt3.setAssignedDoctor(BEN);
        assertFalse(appt2.isOverlapAppointment(appt3));

        //same doctor
        appt3.setAssignedDoctor(ADAM);
        assertTrue(appt3.isOverlapAppointment(appt2));
    }

    @Test
    public void isCancelled() {
        Date validDate = new Date(2, 2, 2222);
        Time validTime = new Time(12, 0);

        Appointment appt = new Appointment(validDate, validTime, ALICE_AS_PATIENT, 0);

        appt.cancelAppointment();
        assertTrue(appt.isCancelled());
    }

    @Test
    public void statusToString() {
        Date validDate = new Date(2, 2, 2222);
        Time validTime = new Time(12, 0);
        Appointment appt = new Appointment(validDate, validTime, ALICE_AS_PATIENT, 0);

        assertEquals(appt.statusToString(), "APPROVED");

        appt.cancelAppointment();

        assertEquals(appt.statusToString(), "CANCELLED");

    }

    @Test
    public void typeToString() {
        Date validDate = new Date(2, 2, 2222);
        Time validTime = new Time(12, 0);

        Appointment appt = new Appointment(validDate, validTime, ALICE_AS_PATIENT, 0);
        Appointment appt2 = new Appointment(validDate, validTime, ALICE_AS_PATIENT, 1);

        assertEquals(appt.typeToString(), "NEW");
        assertEquals(appt2.typeToString(), "FOLLOW-UP");
    }

    @Test
    public void equals() {
        Date validDate = new Date(2, 2, 2222);
        Date otherDate = new Date(2, 3, 2222);

        Time validTime = new Time(12, 0);
        Time otherTime = new Time(13, 0);

        Appointment appt = new Appointment(validDate, validTime, ALICE_AS_PATIENT, 0);
        Appointment appt2 = new Appointment(validDate, validTime, BENSON_AS_PATIENT, 0);
        Appointment appt3 = new Appointment(validDate, validTime, BENSON_AS_PATIENT, 0);
        Appointment appt4 = new Appointment(validDate, otherTime, ALICE_AS_PATIENT, 0);
        Appointment appt5 = new Appointment(otherDate, otherTime, ALICE_AS_PATIENT, 0);

        //same obj
        assertTrue(appt.equals(appt));

        //same fields
        assertTrue(appt3.equals(appt2));

        //diff patient
        assertFalse(appt.equals(appt2));

        //diff date
        assertFalse(appt4.equals(appt5));

        //diff time
        assertFalse(appt.equals(appt4));

        //diff status
        appt2.cancelAppointment();
        assertFalse(appt2.equals(appt3));

        //diff doctor - 1 null
        appt2.setAssignedDoctor(ADAM);
        assertFalse(appt2.equals(appt3));

        //diff doctor
        appt3.setAssignedDoctor(BEN);
        assertFalse(appt2.equals(appt3));
    }
}
