package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Objects;

import javafx.collections.ObservableList;

import seedu.address.model.analytics.Analytics;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.UniqueAppointmentList;
import seedu.address.model.doctor.Doctor;
import seedu.address.model.doctor.UniqueDoctorList;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson and .isSameDoctor comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    //@@author arsalanc-v2
    private final Analytics analytics;
    private final UniquePersonList persons;
    //@@author jjlee050
    private final UniqueDoctorList doctors;
    //@@author gingivitiss
    private final UniqueAppointmentList appointments;


    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        //@@author arsalanc-v2
        analytics = new Analytics();
        persons = new UniquePersonList();
        //@@author jjlee050
        doctors = new UniqueDoctorList();
        //@@author gingivitiss
        appointments = new UniqueAppointmentList(analytics, doctors.getSize());
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Persons in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //========== List overwrite operations ===================================================================

    /**
     * Replaces the contents of the person list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setPersons(List<Person> persons) {
        this.persons.setPersons(persons);
    }

    //@@author jjlee050
    /**
     * Replaces the contents of the doctor list with {@code doctors}.
     * {@code doctors} must not contain duplicate doctors.
     */
    public void setDoctors(List<Doctor> doctors) {
        this.doctors.setDoctors(doctors);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);
        setPersons(newData.getPersonList());
        setDoctors(newData.getDoctorList());
    }

    //========== Person-level operations =====================================================================

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return persons.contains(person);
    }

    //@@author jjlee050
    /**
     * Returns true if a doctor with the same identity as {@code doctor} exists in the address book.
     */
    public boolean hasDoctor(Doctor doctor) {
        requireNonNull(doctor);
        return doctors.contains(doctor);
    }

    //@@author gingivitiss
    /**
     * Returns true if an appointment with the same identity as {@code appt} exists in the address book.
     */
    public boolean hasAppointment(Appointment appt) {
        requireNonNull(appt);
        return appointments.contains(appt);
    }

    /**
     * Returns true if an appointment has the same slot as {@code appt} in the address book.
     */
    public boolean hasAppointmentClash(Appointment appt) {
        requireNonNull(appt);
        return appointments.clashes(appt);
    }

    /**
     * Adds a person to the address book.
     * The person must not already exist in the address book.
     */
    public void addPerson(Person p) {
        persons.add(p);
    }

    //author jjlee050
    /**
     * Adds a doctor to the ClinicIO.
     * The doctor must not already exist in the ClinicIO.
     */
    public void addDoctor(Doctor d) {
        doctors.add(d);
    }

    //@@author gingivitiss
    /**
     * Adds an appointment to the address book.
     * The appointment must not already exist in the address book.
     */
    public void addAppointment(Appointment appt) {
        appointments.add(appt);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    public void updatePerson(Person target, Person editedPerson) {
        requireNonNull(editedPerson);
        persons.setPerson(target, editedPerson);
    }

    //@@author jjlee050
    /**
     * Replaces the given doctor {@code target} in the list with {@code editedDoctor}.
     * {@code target} must exist in the address book.
     * The doctor identity of {@code editedDoctor} must not be the same as another existing doctor in the address book.
     */
    public void updateDoctor(Doctor target, Doctor editedDoctor) {
        requireNonNull(editedDoctor);
        doctors.setDoctor(target, editedDoctor);
    }

    //@@author gingivitiss
    /**
     * Replaces the given appointment {@code target} in the list with {@code editedAppt}.
     * {@code target} must exist in the address book.
     * The appointment identity of {@code editedAppt} must not be the same as another existing appointment
     * in the address book.
     */
    public void updateAppointment(Appointment target, Appointment editedAppt) {
        requireNonNull(editedAppt);
        appointments.setAppointment(target, editedAppt);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removePerson(Person key) {
        persons.remove(key);
    }

    //@@author jjlee050
    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeDoctor(Doctor key) {
        doctors.remove(key);
    }

    //@@author gingivitiss
    /**
     * Removes {@code key} from this {@code AddressBook}. Not to be used to cancel appointments.
     * {@code key} must exist in the address book.
     */
    public void removeAppointment(Appointment key) {
        appointments.remove(key);
    }

    /**
     * Cancels {@code key} by updating appointment with cancel status.
     * Calls {@code updateAppointment} to replace {@code key} with cancelled one.
     * {@code key} must exist in the address book.
     */
    public void cancelAppointment(Appointment key) {
        requireNonNull(key);
        appointments.cancelAppointment(key);
    }

    //========== Util methods ================================================================================

    @Override
    public String toString() {
        //@@author jjlee050
        return persons.asUnmodifiableObservableList().size() + " persons & "
                + doctors.asUnmodifiableObservableList().size() + " doctors &"
                + appointments.asUnmodifiableObservableList().size() + "appointments ";
        // TODO: refine later
    }

    @Override
    public ObservableList<Person> getPersonList() {
        return persons.asUnmodifiableObservableList();
    }

    //@@author jjlee050
    @Override
    public ObservableList<Doctor> getDoctorList() {
        return doctors.asUnmodifiableObservableList();
    }

    //@@author gingivitiss
    @Override
    public ObservableList<Appointment> getAppointmentList() {
        return appointments.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        //@@author jjlee050
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && persons.equals(((AddressBook) other).persons)
                && doctors.equals(((AddressBook) other).doctors)
                && appointments.equals(((AddressBook) other).appointments));
    }

    @Override
    public int hashCode() {
        //@@author jjlee050
        return Objects.hash(persons.hashCode(), doctors.hashCode(), appointments.hashCode());
    }
}
