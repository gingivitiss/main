package seedu.clinicio.testutil;

import seedu.clinicio.model.doctor.Doctor;
import seedu.clinicio.model.doctor.Id;
import seedu.clinicio.model.doctor.Password;
import seedu.clinicio.model.person.Name;

//@@author jjlee050
/**
 * A utility class to help with building Doctor objects.
 */
public class DoctorBuilder {

    public static final int DEFAULT_ID = 1;
    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_PASSWORD = "alicepaul";

    private Id id;
    private Name name;
    private Password password;

    public DoctorBuilder() {
        id = new Id(DEFAULT_ID);
        name = new Name(DEFAULT_NAME);
        password = new Password(DEFAULT_PASSWORD, false);
    }

    /**
     * Initializes the DoctorBuilder with the data of {@code personToCopy}.
     */
    public DoctorBuilder(Doctor doctorToCopy) {
        id = doctorToCopy.getId();
        name = doctorToCopy.getName();
        password = doctorToCopy.getPassword();
    }

    /**
     * Sets the {@code Id} of the {@code Doctor} that we are building.
     */
    public DoctorBuilder withId(int id) {
        this.id = new Id(id);
        return this;
    }

    /**
     * Sets the {@code Name} of the {@code Doctor} that we are building.
     */
    public DoctorBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the {@code Password} of the {@code Doctor} that we are building.
     *
     * @param isHashed Check if the password is hashed password.
     *
     */
    public DoctorBuilder withPassword(String password, boolean isHashed) {
        this.password = new Password(password, isHashed);
        return this;
    }

    public Doctor build() {
        return new Doctor(id, name, password);
    }

}