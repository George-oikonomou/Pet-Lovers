package pet.lovers.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@PrimaryKeyJoinColumn(name = "visit_id")
public class AdoptionRequest extends Visit {
    // TABLE COLUMNS
    @Enumerated(EnumType.STRING)
    private UserStatus requestStatus = UserStatus.PENDING; // Default status

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "pet_id")
    private Pet pet;

    // CONSTRUCTORS
    public AdoptionRequest() {}

    public AdoptionRequest(LocalDateTime dateTime, Shelter shelter, Adopter adopter, Pet pet) {
        super(dateTime, shelter, adopter);
        this.pet = pet;
    }

    // GETTERS AND SETTERS
    public UserStatus getRequestStatus() {
        return requestStatus;
    }
    public void setRequestStatus(UserStatus requestStatus) {
        this.requestStatus = requestStatus;
    }

    public Pet getPet() {
        return pet;
    }
    public void setPet(Pet pet) {
        this.pet = pet;
    }
}