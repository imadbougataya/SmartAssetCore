package com.ailab.smartasset.service.dto;

import com.ailab.smartasset.domain.User;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO representing a user, with only the public attributes.
 */
public class UserDTO implements Serializable {

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    private static final long serialVersionUID = 1L;

    private Long id;
    private String login;
    private String firstName;
    private String lastName;
    private String email;

    public UserDTO() {
        // Empty constructor needed for Jackson
    }

    public UserDTO(User user) {
        if (user != null) {
            this.id = user.getId();
            this.login = user.getLogin();
            this.firstName = user.getFirstName();
            this.lastName = user.getLastName();
        }
    }

    // --------------------
    // Getters & Setters
    // --------------------

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    // --------------------
    // equals / hashCode
    // --------------------

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserDTO)) return false;
        UserDTO userDTO = (UserDTO) o;
        return id != null && Objects.equals(id, userDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    // --------------------
    // toString
    // --------------------

    @Override
    public String toString() {
        return (
            "UserDTO{" +
            "id=" +
            id +
            ", login='" +
            login +
            '\'' +
            ", firstName='" +
            firstName +
            '\'' +
            ", lastName='" +
            lastName +
            '\'' +
            '}'
        );
    }
}
