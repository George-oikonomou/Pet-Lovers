package pet.lovers.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
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
    @NotBlank(message = "Sex is required")
    @Pattern(regexp = "^(male|female)$", message = "Sex must be either 'male' or 'female'")
    private String sex;

    @Column
    @Min(value = 2000, message = "Year must be after 2000")
    @Max(value = 2025, message = "Year must be before 2025")
    private Integer yearBirthed;

    @Column
    @NotBlank(message = "Type is required")
    private String type;

    @Column
    @NotBlank
    private String breed;

    @Column
    @NotNull(message = "Weight is required.")
    @DecimalMin(value = "1.0", message = "Weight must be at least 1 kg.")
    @DecimalMax(value = "100.0", message = "Weight must be less than 100 kg.")
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
    public Pet(String name, String sex,Shelter shelter, int yearBirthed, String type, String breed, double weight) {
        this.name = name;
        this.sex = sex;
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
    public void setName (String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }

    public Shelter getShelter() {
        return shelter;
    }
    public void setShelter(Shelter shelter) {
        this.shelter = shelter;
    }

    public Integer getYearBirthed() {
        return yearBirthed;
    }
    public void setYearBirthed(Integer yearBirthed) {
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
    public void setBreed(String breed) {
        this.breed = breed;
    }

    public Double getWeight() {
        return weight != null ? weight : 0.0;
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

    public UserStatus getUserStatus() {
        return userStatus;
    }
    public void setUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }

    @Override
    public String toString() {
        return "PET\nname: " + name + "\nsex: " + sex + "\nage: " + (Year.now().getValue() - yearBirthed) + "\ntype: " + type + "\nbreed: " + breed + "\nweight: " + weight + "shelter: " + shelter;
    }
}