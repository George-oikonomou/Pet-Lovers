package pet.lovers.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Entity
@Table
public class Visit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    @NotBlank
    @DateTimeFormat(pattern = " D:yyyy-MM-dd' T:'HH:mm:ss")
    private LocalDateTime DateTime;

    @Column
    @NotBlank
    @Pattern(regexp = "^\\d{10}$", message = "The number must be exactly 10 digits.")
    private String contact_number;

    @Column
    @NotBlank
    private boolean confirmedAdoption;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id")
    private Location location;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "adopter_id")
    private Adopter adopter;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "pet_id")
    private Pet pet;

    public Visit(LocalDateTime dateTime, String contact_number, boolean confirmedAdoption, Location location, Adopter adopter, Pet pet) {
        DateTime = dateTime;
        this.contact_number = contact_number;
        this.confirmedAdoption = confirmedAdoption;
        this.location = location;
        this.adopter = adopter;
        this.pet = pet;
    }

    public Visit() {

    }

    public LocalDateTime getDateTime() {
        return DateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        DateTime = dateTime;
    }

    public String getContact_number() {
        return contact_number;
    }

    public void setContact_number(String contact_number) {
        this.contact_number = contact_number;
    }

    public boolean getConfirmedAdoption() {
        return confirmedAdoption;
    }

    public void setConfirmedAdoption() {
        this.confirmedAdoption = !this.confirmedAdoption;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Adopter getAdopter() {
        return adopter;
    }

    public void setAdopter(Adopter adopter) {
        this.adopter = adopter;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }
}
