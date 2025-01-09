package pet.lovers.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

@Entity
@Table
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    @NotBlank
    private String regionalUnit;

    @Column
    @NotBlank
    private String city;

    @Column
    @NotBlank
    private String address;

    @Column
    @NotBlank
    private String number;

    @Column
    @NotBlank
    private String postalCode;

    @OneToOne(mappedBy = "location", cascade = CascadeType.MERGE)
    private User user;

    public Location(String regionalUnit, String city, String address, String number, String postalCode) {
        this.regionalUnit = regionalUnit;
        this.city = city;
        this.address = address;
        this.number = number;
        this.postalCode = postalCode;
    }

    public Location() {

    }

    public int getId() {
        return id;
    }

    public String getRegionalUnit() {
        return regionalUnit;
    }

    public void setRegionalUnit(String regionalUnit) {
        this.regionalUnit = regionalUnit;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Location{" +
                "id=" + id +
                ", regionalUnit='" + regionalUnit + '\'' +
                ", city='" + city + '\'' +
                ", address='" + address + '\'' +
                ", number='" + number + '\'' +
                ", postalCode='" + postalCode + '\'' +
                '}';
    }
}
