package pet.lovers.entities;


import jakarta.persistence.*;

@Entity
public class ShelterRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "vet_id")
    private Vet vet;

    @ManyToOne
    @JoinColumn(name = "shelter_id")
    private Shelter shelter;

    @Enumerated(EnumType.STRING)
    private UserStatus status = UserStatus.PENDING;

    // Constructor
    public ShelterRequest(Vet vet, Shelter shelter) {
        this.vet = vet;
        this.shelter = shelter;
    }

    public ShelterRequest() {}

    // Getters and Setters
    public int getId() {
        return id;
    }


    public Vet getVet() {
        return vet;
    }
    public void setVet(Vet vet) {
        this.vet = vet;
    }

    public Shelter getShelter() {
        return shelter;
    }
    public void setShelter(Shelter shelter) {
        this.shelter = shelter;
    }

    public UserStatus getStatus() {
        return status;
    }
    public void setStatus(UserStatus status) {
        this.status = status;
    }
}