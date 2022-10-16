package com.example.springSecurity.person;

import com.example.springSecurity.user.UserEntity;

import javax.persistence.*;

@Entity
@Table(name = "persons",
        uniqueConstraints ={
        @UniqueConstraint(name  = "identificationId",
        columnNames = "ID_Number")
        } )
public class PersonEntity {
    @Id
    @SequenceGenerator(name = "person_seq",
            sequenceName = "person_seq",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "person_seq")
    @Column(name = "person_Id")
    private Long personID;
    @Column(name = "firstName", nullable = false)
    private String firstName;
    @Column(name = "lastName", nullable = false)
    private String lastName;
    @Column(name = "ID_Number", nullable = true)
    private String identificationID;
    @Column(name = "address")
    private String address;
    @Column(name = "email")
    private String email;
    @Column(name = "phone")
    private String phoneNumber;


    @Embedded
    private UserEntity user;


    public PersonEntity(Long personID,
                        String firstName,
                        String lastName,
                        String identificationID,
                        String address,
                        String email,
                        UserEntity user,
                        String phoneNumber) {
        this.personID = personID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.identificationID = identificationID;
        this.address = address;
        this.email = email;
        this.user = user;
        this.phoneNumber = phoneNumber;
    }

    public PersonEntity() {
    }

    public long getPersonID() {
        return personID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getIdentificationID() {
        return identificationID;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setIdentificationID(String identificationID) {
        this.identificationID = identificationID;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "PersonEntity{" +
                "personID=" + personID +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", identificationID='" + identificationID + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
