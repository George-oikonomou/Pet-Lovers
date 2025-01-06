package pet.lovers.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.time.Year;
import java.util.List;

@Entity
@Table
public class Pet {


    //TABLE COLUMNS
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    @NotEmpty(message = "Name is required")
    @Size(min = 2, max = 50)
    private String name;

    @Column
    @NotBlank
    @Size(min = 2000, max = 2025)
    private final int yearBirthed;

    @Column
    @NotBlank

    private final String type;

    @Column
    @NotBlank
    private final String breed;

    @Column
    @Size(min = 1, max = 90)
    private float weight;

    @Column
    @NotBlank
    private boolean adoptedStatus;

    @Enumerated
    @NotBlank
    private HealthStatus healthStatus;

    @OneToOne(mappedBy = "pet", cascade = CascadeType.ALL)
    private AdoptionRequest adoptionRequest;

//    private Shelter shelterHost;

    @Enumerated
    private PetStatus petStatus;
    //END TABLE COLUMNS


    //CONSTRUCTORS
    public Pet(String name, int yearBirthed, String type, String breed, float weight, boolean adoptedStatus, HealthStatus healthStatus, PetStatus petStatus) {
        this.name = name;
        this.yearBirthed = yearBirthed;
        this.type = type;
        this.breed = breed;
        this.weight = weight;
        this.adoptedStatus = adoptedStatus;
        this.healthStatus = healthStatus;
        this.petStatus = petStatus;
    }

    public Pet() {
        yearBirthed = 0;
        type = "null";
        breed = "null";
    }


    //GETTERS AND SETTERS
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

    public boolean isAdoptedStatus() {
        return adoptedStatus;
    }
    public void setAdoptedStatus(boolean adoptedStatus) {
        this.adoptedStatus = adoptedStatus;
    }

    public HealthStatus getHealthStatus() {
        return healthStatus;
    }
    public void setHealthStatus(HealthStatus healthStatus) {
        this.healthStatus = healthStatus;
    }

//    public Shelter getShelter() {
//        return shelter;
//    }
//
//    public void setShelter(Shelter shelter) {
//        this.shelter = shelter;
//    }

    public PetStatus getPetStatus() {
        return petStatus;
    }

    public void setPetStatus(PetStatus petStatus) {
        this.petStatus = petStatus;
    }

    public int getId() {
        return id;
    }

    public AdoptionRequest getAdoptionRequest() {
        return adoptionRequest;
    }

    public void setAdoptionRequest(AdoptionRequest adoptionRequest) {
        this.adoptionRequest = adoptionRequest;
    }

    @Override
    public String toString() {
        return "PET\nname: " + name + "\nage: " + (Year.now().getValue() - yearBirthed) + "\ntype: " + type + "\nbreed: " + breed + "\nweight: " + weight;
    }
}