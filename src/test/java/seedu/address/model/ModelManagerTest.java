package seedu.address.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_DOCTORS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.testutil.TypicalPersons.ADAM;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.ALICE_AS_PATIENT;
import static seedu.address.testutil.TypicalPersons.BEN;
import static seedu.address.testutil.TypicalPersons.BENSON;

import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.Date;
import seedu.address.model.appointment.Time;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.testutil.AddressBookBuilder;

public class ModelManagerTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private ModelManager modelManager = new ModelManager();

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.hasPerson(null);
    }

    //@@author jjlee050
    @Test
    public void hasDoctor_nullDoctor_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.hasDoctor(null);
    }

    //@@author gingivitiss
    @Test
    public void hasAppointment_nullAppointment_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.hasAppointment(null);
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasPerson(ALICE));
    }

    //@@author jjlee050
    @Test
    public void hasDoctor_doctorNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasDoctor(ADAM));
    }

    //@@author gingivitiss
    @Test
    public void hasAppointment_appointmentNotInAddressBook_returnsFalse() {
        Date date = new Date(1, 1, 2018);
        Time time = new Time(5, 30);
        Appointment appt = new Appointment(date, time, ALICE_AS_PATIENT, 0);
        assertFalse(modelManager.hasAppointment(appt));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        modelManager.addPerson(ALICE);
        assertTrue(modelManager.hasPerson(ALICE));
    }

    //@@author jjlee050
    @Test
    public void hasDoctor_doctorInAddressBook_returnsTrue() {
        modelManager.addDoctor(ADAM);
        assertTrue(modelManager.hasDoctor(ADAM));
    }

    //@@author gingivitiss
    @Test
    public void hasAppointment_appointmentInAddressBook_returnsTrue() {
        Date date = new Date(1, 1, 2018);
        Time time = new Time(5, 30);
        Appointment appt = new Appointment(date, time, ALICE_AS_PATIENT, 0);
        modelManager.addAppointment(appt);
        assertTrue(modelManager.hasAppointment(appt));
    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredPersonList().remove(0);
    }

    //@@author jjlee050
    @Test
    public void getFilteredDoctorList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredDoctorList().remove(0);
    }

    //@@author gingivitiss
    @Test
    public void getFilteredAppointmentList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredAppointmentList().remove(0);
    }

    @Test
    public void equals() {
        AddressBook addressBook = new AddressBookBuilder().withPerson(ALICE).withPerson(BENSON)
                .withDoctor(ADAM).withDoctor(BEN).build();
        AddressBook differentAddressBook = new AddressBook();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(addressBook, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(addressBook, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different addressBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentAddressBook, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(addressBook, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        //@@author jjlee050
        modelManager.updateFilteredDoctorList(PREDICATE_SHOW_ALL_DOCTORS);

        // different userPrefs -> returns true
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookFilePath(Paths.get("differentFilePath"));
        assertTrue(modelManager.equals(new ModelManager(addressBook, differentUserPrefs)));
    }
}
