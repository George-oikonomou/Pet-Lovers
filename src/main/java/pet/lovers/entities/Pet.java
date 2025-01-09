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
    private int yearBirthed;

    @Column
    @NotBlank
    private String type;

    @Column
    @NotBlank
    private String breed;

    @Column
    @Size(min = 1, max = 90)
    private double weight;

    @Enumerated
    @NotBlank
    private HealthStatus healthStatus;

    @OneToOne(mappedBy = "pet", cascade = CascadeType.ALL)
    private AdoptionRequest adoptionRequest;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name="shelter_id")
    private Shelter shelter;

    @Enumerated
    private PetStatus petStatus;
    //END TABLE COLUMNS


    //CONSTRUCTORS
    public Pet(String name,Shelter shelter, int yearBirthed, String type, String breed, double weight, HealthStatus healthStatus, PetStatus petStatus) {
        this.name = name;
        this.shelter = shelter;
        this.yearBirthed = yearBirthed;
        this.type = type;
        this.breed = breed;
        this.weight = weight;
        this.healthStatus = healthStatus;
        this.petStatus = petStatus;
    }

    public Pet() {}

    //GETTERS AND SETTERS
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    public void setName (String name) {
        this.name = name;
    }

    public Shelter getShelter() {
        return shelter;
    }
    public void setShelter(Shelter shelter) {
        this.shelter = shelter;
    }

    public int getYearBirthed() {
        return yearBirthed;
    }
    public void setYearBirthed(int yearBirthed) {
        this.yearBirthed = yearBirthed;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public String getBreed() {
        return breed;
    }

    public double getWeight() {
        return weight;
    }
    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public HealthStatus getHealthStatus() {
        return healthStatus;
    }
    public void setHealthStatus(HealthStatus healthStatus) {
        this.healthStatus = healthStatus;
    }

    public PetStatus getPetStatus() {
        return petStatus;
    }
    public void setPetStatus(PetStatus petStatus) {
        this.petStatus = petStatus;
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