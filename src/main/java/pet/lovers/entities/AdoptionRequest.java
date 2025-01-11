package pet.lovers.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

@Entity
public class AdoptionRequest {

    // TABLE COLUMNS
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    private UserStatus requestStatus = UserStatus.PENDING; // Default status

    @Column
    @NotBlank
    private LocalDateTime dateTime;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "adopter_id")
    private Adopter adopter;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "pet_id", referencedColumnName = "id")
    private Pet pet;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "shelter_id")
    private Shelter shelter;

    // CONSTRUCTORS
    public AdoptionRequest() {
    }

    public AdoptionRequest(LocalDateTime dateTime, Adopter adopter, Pet pet, Shelter shelter) {
        this.dateTime = dateTime;
        this.adopter = adopter;
        this.pet = pet;
        this.shelter = shelter;
    }

    // GETTERS AND SETTERS
    public int getId() {
        return id;
    }

    public UserStatus getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(UserStatus requestStatus) {
        this.requestStatus = requestStatus;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
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

    public Shelter getShelter() {
        return shelter;
    }

    public void setShelter(Shelter shelter) {
        this.shelter = shelter;
    }

    @Override
    public String toString() {
        return "AdoptionRequest{" +
                "id=" + id +
                ", requestStatus=" + requestStatus +
                ", dateTime=" + dateTime +
                ", adopter=" + adopter +
                ", pet=" + pet +
                ", shelter=" + shelter +
                '}';
    }
}
