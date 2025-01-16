package pet.lovers.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class AdoptionRequest extends Visit {

    // TABLE COLUMNS
    @Column
    @NotNull
    @PrimaryKeyJoinColumn(name = "visit_id")
    private boolean confirmedAdoption;
    // CONSTRUCTORS
    public AdoptionRequest() {
    }


    @Enumerated(EnumType.STRING)
    private UserStatus requestStatus = UserStatus.PENDING; // Default status

    public AdoptionRequest(LocalDateTime dateTime, Shelter shelter, Adopter adopter, Pet pet) {
        super(dateTime, shelter, adopter, pet);
    }

    // GETTERS AND SETTERS

    public boolean isConfirmedAdoption() {
        return confirmedAdoption;
    }
    public void setConfirmedAdoption(boolean confirmedAdoption) {
        this.confirmedAdoption = confirmedAdoption;
    }

    public UserStatus getRequestStatus() {
        return requestStatus;
    }
    public void setRequestStatus(UserStatus requestStatus) {
        this.requestStatus = requestStatus;
    }
}