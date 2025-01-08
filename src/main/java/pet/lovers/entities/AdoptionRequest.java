package pet.lovers.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

@Entity
public class AdoptionRequest {    //TODO REQUESTED VISIT

    //TABLE COLUMNS
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    @NotBlank
    private Boolean Status;

    @Column
    @NotBlank
    private LocalDateTime DateTime;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name="adopter_id")
    private Adopter adopter;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "pet_id", referencedColumnName = "id")
    private Pet pet;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name="shelter_id")
    private Shelter shelter;
    //END TABLE COLUMNS

    //CONSTRUCTORS
    public AdoptionRequest() {
    }

    public AdoptionRequest(Boolean status, LocalDateTime dateTime, Adopter adopter, Pet pet, Shelter shelter) {
        Status = status;
        DateTime = dateTime;
        this.adopter = adopter;
        this.pet = pet;
        this.shelter = shelter;
    }

    //GETTERS AND SETTERS
    public int getId() {
        return id;
    }

    public Boolean getStatus() {
        return Status;
    }

    public void setStatus(Boolean status) {
        Status = status;
    }

    public LocalDateTime getDateTime() {
        return DateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        DateTime = dateTime;
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
                ", Status=" + Status +
                ", DateTime=" + DateTime +
                ", adopter=" + adopter +
                ", pet=" + pet +
                ", shelter=" + shelter +
                '}';
    }
}