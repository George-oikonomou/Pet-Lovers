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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "document_id", referencedColumnName = "id")
    private Document documents;

    @OneToMany(mappedBy = "vet", cascade= CascadeType.ALL)
    private List<Shelter> shelters;
    // END TABLE COLUMNS

    public Vet() {}

    public Vet(String username, String email, String password, String contactNumber, String location, String fullName,Document documents) {
        super(username, email, password, contactNumber, location);
        this.fullName = fullName;
        this.documents = documents;
    }

    // GETTERS AND SETTERS
    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Document getDocuments() {
        return documents;
    }
    public void setDocuments(Document documents) {
        this.documents = documents;
    }

    public List<Shelter> getShelters() {
        return shelters;
    }
    public void setShelters(List<Shelter> shelters) {
        this.shelters = shelters;
    }
}