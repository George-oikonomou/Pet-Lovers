package pet.lovers.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.time.Year;

@Entity
@Table
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int petID;

    @Column
    @NotEmpty(message = "Name is required")
    @Size(min = 2, max = 50)
    private String name;

    @Column
    @NotBlank
    @Size(min = 2000, max = 2025)
    private int yearBirthed;

    @Column
    @NotBlank

    private String type;

    @Column
    @NotBlank
    private String breed;

    @Column
    @Size(min = 1, max = 90)
    private float weight;

    @Enumerated
    @NotBlank
    private HealthStatus healthStatus;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH},
            fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "shelter_id", nullable = false)
    private Shelter shelter;
    @Enumerated
    private PetStatus petStatus;


    public Pet(int petID, String name, int yearBirthed, String type, String breed, float weight, HealthStatus healthStatus, PetStatus petStatus) {
        this.petID = petID;
        this.name = name;
        this.yearBirthed = yearBirthed;
        this.type = type;
        this.breed = breed;
        this.weight = weight;
        this.healthStatus = healthStatus;
        this.petStatus = petStatus;
    }

    public Pet() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBirthdate() {
        return yearBirthed;
    }

    public String getType() {
        return type;
    }

    public String getBreed() {
        return breed;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public HealthStatus getHealthStatus() {
        return healthStatus;
    }

    public void setHealthStatus(HealthStatus healthStatus) {
        this.healthStatus = healthStatus;
    }

    public Shelter getShelter() {
        return shelter;
    }

    public void setShelter(Shelter shelter) {
        this.shelter = shelter;
    }

    public PetStatus getPetStatus() {
        return petStatus;
    }

    public void setPetStatus(PetStatus petStatus) {
        this.petStatus = petStatus;
    }

    public int getPetID() {
        return petID;
    }

    @Override
    public String toString() {
        return "PET\nid: " + petID + "\nname: " + name + "\nage: " + (Year.now().getValue() - yearBirthed) + "\ntype: " + type + "\nbreed: " + breed + "\nweight: " + weight;
    }
}