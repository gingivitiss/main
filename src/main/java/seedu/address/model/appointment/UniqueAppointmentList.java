package seedu.address.model.appointment;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.analytics.Analytics;
import seedu.address.model.appointment.exceptions.AppointmentClashException;
import seedu.address.model.appointment.exceptions.AppointmentNotFoundException;
import seedu.address.model.appointment.exceptions.DuplicateAppointmentException;

/**
 * A list of appointments that enforces uniqueness between its elements and does not allow nulls.
 * An appointment is considered unique by comparing using {@code Appointment#isSameAppointment(Appointment)}.
 * As such, adding and updating of appointment uses Appointment#isSameAppointment(Appointment)
 * for equality to ensure that the appointment being added or updated is unique
 * in terms of identity in the UniqueAppointmentList. However, the removal of an appointment
 * uses Appointment#equals(Object) so as to ensure that the person with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Appointment#isSameAppointment(Appointment)
 */
public class UniqueAppointmentList implements Iterable<Appointment> {

    private final ObservableList<Appointment> internalList = FXCollections.observableArrayList();
    private final Analytics analytics;
    private final int numDoctors;

    /**
     * Initializes a {@code UniqueAppointmentList} object.
     * Updates an {@code Analytics} instance.
     * @@author arsalanc-v2
     */
    public UniqueAppointmentList(Analytics analytics, int numDoctors) {
        this.analytics = analytics;
        updateAppointmentAnalytics();
        this.numDoctors = numDoctors;
    }

    /**
     * Returns true if the list contains an equivalent appointment as the given argument.
     */
    public boolean contains(Appointment toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameAppointment);
    }

    /**
     * Returns true if the list contains a clashing appointment as the given argument.
     */
    public boolean clashes(Appointment toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isOverlapAppointment);
    }

    /**
     * Returns true if the list contains the same amount or more of 'same slot' appointments as there are doctors.
     *
    public boolean isFullyBooked(Appointment toCheck) { TODO
        requireNonNull(toCheck);
        return internalList.stream().filter(toCheck::isOverlapAppointment).count() >= numDoctors;
    }*/

    /**
     * Adds an appointment to the list. The appointment must not already exist in the list.
     * The appointment should not clash with another appointment.
     */
    public void add(Appointment toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateAppointmentException();
        }
        if (clashes(toAdd)) {
            throw new AppointmentClashException();
        }
        /*if (isFullyBooked(toAdd)) { TODO
            throw new AppointmentSlotFullyBookedException();
        }*/
        internalList.add(toAdd);
        updateAppointmentAnalytics();
    }

    /**
     * Replaces the appointment {@code target} in the list with {@code editedAppt}.
     * {@code target} must exist in the list.
     * The appointment {@code editedAppt} must not be the same as another existing appointment in the list.
     */
    public void setAppointment(Appointment target, Appointment editedAppt) {
        requireAllNonNull(target, editedAppt);
        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new AppointmentNotFoundException();
        }
        if (!target.isSameAppointment(editedAppt) && contains(editedAppt)) {
            throw new DuplicateAppointmentException();
        }
        if (clashes(editedAppt)) {
            throw new AppointmentClashException();
        }
        internalList.set(index, editedAppt);
        updateAppointmentAnalytics();
    }

    /**
     * Cancels the appointment {@code target}.
     * Replaces the {@code target} with updated status.
     * {@code target} must exist in the list.
     */
    public void cancelAppointment(Appointment target) {
        requireNonNull(target);
        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new AppointmentNotFoundException();
        }
        target.isCancelled();
        Appointment cancelledAppt = target;
        internalList.set(index, cancelledAppt);
        updateAppointmentAnalytics();
    }

    /**
     * Removes the equivalent appointment from the list.
     * The appointment must exist in the list.
     */
    public void remove(Appointment toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new AppointmentNotFoundException();
        }
        updateAppointmentAnalytics();
    }

    public void setAppointments(UniqueAppointmentList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
        updateAppointmentAnalytics();
    }

    /**
     * Replaces the contents of this list with {@code appts}.
     * {@code appts} must not contain duplicate appointments.
     */
    public void setAppointments(List<Appointment> appts) {
        requireAllNonNull(appts);
        if (!appointmentsAreUnique(appts)) {
            throw new DuplicateAppointmentException();
        }
        internalList.setAll(appts);
        updateAppointmentAnalytics();
    }

    /**
     * Sends a copy of appointments to {@code Analytics} for extracting data.
     * @@author arsalanc-v2
     */
    private void updateAppointmentAnalytics() {
        final ObservableList<Appointment> appointments = FXCollections.observableArrayList();
        appointments.setAll(internalList);
        analytics.setAppointments(appointments);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Appointment> asUnmodifiableObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public Iterator<Appointment> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueAppointmentList // instanceof handles nulls
                && internalList.equals(((UniqueAppointmentList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code appts} contains only unique appointments.
     */
    private boolean appointmentsAreUnique(List<Appointment> appts) {
        for (int i = 0; i < appts.size() - 1; i++) {
            for (int j = i + 1; j < appts.size(); j++) {
                if (appts.get(i).isSameAppointment(appts.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

}
