package com.app.stock.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
public class Users implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -5300888600362991691L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Email(message = "Enter the valid email address")
    @NotEmpty(message = "Email is required")
    @Column(nullable = false, unique = true)
    private String email;

    @NotEmpty(message = "Password is required.")
    private String password;

    @NotEmpty(message = "First name is required.")
    private String firstName;

    public Users() {
    }

    public Users(Users user) {
	this.id = user.id;
	this.firstName = user.firstName;
	this.lastName = user.lastName;
	this.email = user.email;
	this.password = user.password;
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

    @NotEmpty(message = "Last name is required.")
    private String lastName;

    public String getEmail() {
	return email;
    }

    public void setEmail(String email) {
	this.email = email;
    }

    public String getPassword() {
	return password;
    }

    public void setPassword(String password) {
	this.password = password;
    }

}
