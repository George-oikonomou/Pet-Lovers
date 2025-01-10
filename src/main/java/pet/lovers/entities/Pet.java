package pet.lovers.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

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
    @Min(2000)
    @Max(2025)
    private int yearBirthed;

    @Column
    @NotBlank
    private String type; //cat, dog, etc

    @Column
    @NotBlank
    private String breed;

    @Column
    @DecimalMin(value = "0.0", inclusive = false, message = "Weight must be greater than 0")
    @DecimalMax(value = "100.0", message = "Weight must be less than or equal to 100")
    private Double weight;

    @Enumerated(EnumType.STRING)
    private HealthStatus healthStatus = HealthStatus.UNKNOWN; // Default health status

    @OneToMany(mappedBy = "pet", cascade = CascadeType.ALL)
    private List<AdoptionRequest> adoptionRequests;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name="shelter_id")
    private Shelter shelter;

    @Enumerated(EnumType.STRING)
    private PetStatus petStatus = PetStatus.AVAILABLE; // Default status

    @Enumerated(EnumType.STRING)
    private UserStatus userStatus = UserStatus.PENDING; // Default status
    //END TABLE COLUMNS


    //CONSTRUCTORS
    public Pet(String name,Shelter shelter, int yearBirthed, String type, String breed, double weight) {
        this.name = name;
        this.shelter = shelter;
        this.yearBirthed = yearBirthed;
        this.type = type;
        this.breed = breed;
        this.weight = weight;
    }

    public Pet() {}

    //GETTERS AND SETTERS
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
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

    public List<AdoptionRequest> getAdoptionRequests() {
        return adoptionRequests;
    }

    public void setAdoptionRequests(List<AdoptionRequest> adoptionRequests) {
        this.adoptionRequests = adoptionRequests;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public UserStatus getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }

    @Override
    public String toString() {
        return "PET\nname: " + name + "\nage: " + (Year.now().getValue() - yearBirthed) + "\ntype: " + type + "\nbreed: " + breed + "\nweight: " + weight;
    }
}