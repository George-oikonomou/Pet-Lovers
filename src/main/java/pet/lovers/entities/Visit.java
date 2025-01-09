package pet.lovers.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.springframework.format.annotation.DateTimeFormat;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table
public class Visit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    @NotNull
    @DateTimeFormat(pattern = " D:yyyy-MM-dd' T:'HH:mm:ss")
    private LocalDateTime DateTime;

    @Column
    @NotBlank
    @Pattern(regexp = "^\\d{10}$", message = "The number must be exactly 10 digits.")
    private String contact_number;

    @Column
    @NotNull
    private boolean confirmedAdoption;

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
    private Pet pet; //TODO maybe instead of pet, use adoption request?

    public Visit(LocalDateTime dateTime, String contact_number, boolean confirmedAdoption, Shelter shelter, Adopter adopter, Pet pet) {
        DateTime = dateTime;
        this.contact_number = contact_number;
        this.confirmedAdoption = confirmedAdoption;
        this.shelter = shelter;
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
}
