package pet.lovers.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "vets")
@PrimaryKeyJoinColumn(name = "user_id")
public class Vet extends User {


    // TABLE COLUMNS
    @Column
    @NotBlank
    @Size(max = 60)
    private String fullName;

    @OneToMany(mappedBy = "vet",  cascade= {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    private List<Shelter> shelters;

    @OneToMany(mappedBy = "vet", cascade= CascadeType.ALL , orphanRemoval = true)
    private List<EmploymentRequest> employmentRequests;
    // END TABLE COLUMNS

    public Vet() {}

    public Vet(String username, String email, String password, String contactNumber, String location, String fullName,String documentUrl) {
        super(username, email, password, contactNumber, location, documentUrl);
        this.fullName = fullName;
    }

    // GETTERS AND SETTERS
    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public List<Shelter> getShelters() {
        return shelters;
    }
    public void setShelters(List<Shelter> shelters) {
        this.shelters = shelters;
    }

    public List<EmploymentRequest> getEmploymentRequests() {
        return employmentRequests;
    }
    public void setEmploymentRequests(List<EmploymentRequest> employmentRequests) {
        this.employmentRequests = employmentRequests;
    }
}