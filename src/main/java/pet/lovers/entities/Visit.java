package pet.lovers.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.springframework.format.annotation.DateTimeFormat;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table
@Inheritance(strategy = InheritanceType.JOINED)
public class Visit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime dateTime;

    @Column
    @NotBlank
    @Pattern(regexp = "^\\d{10}$", message = "The number must be exactly 10 digits.")
    private String contactNumber;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "shelter_id")
    private Shelter shelter;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "adopter_id")
    private Adopter adopter;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "pet_id")
    private Pet pet;

    public Visit(LocalDateTime dateTime, Shelter shelter, Adopter adopter, Pet pet) {
        this.dateTime = dateTime;
        this.shelter = shelter;
        this.adopter = adopter;
        this.pet = pet;
        this.contactNumber = adopter.getContactNumber();
    }

    public Visit(LocalDateTime dateTime, Shelter shelter, Adopter adopter ) {
        this.dateTime = dateTime;
        this.shelter = shelter;
        this.adopter = adopter;
        this.contactNumber = adopter.getContactNumber();
    }

    public Visit() {}

    // GETTERS AND SETTERS

    public int getId() {
        return id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getContactNumber() {
        return contactNumber;
    }
    public void setContactNumber (String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public Shelter getShelter() {
        return shelter;
    }
    public void setShelter(Shelter shelter) {
        this.shelter = shelter;
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

    public String getReadableDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm");
        return dateTime.format(formatter);
    }
}
