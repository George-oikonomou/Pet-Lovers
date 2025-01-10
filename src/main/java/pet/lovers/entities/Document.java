package pet.lovers.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table
public class Document {


    //TABLE COLUMNS
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    @NotBlank
    private String path;

    @Column
    @NotNull
    private boolean approved;

    @OneToOne(mappedBy = "identification", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
    private Adopter adopter;

    @OneToOne(mappedBy = "documents", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
    private Shelter shelter;

    @OneToOne(mappedBy = "documents", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
    private Vet vet;
    //END TABLE COLUMNS



    //CONSTRUCTORS
    public Document(String path, boolean approved) {
        this.path = path;
        this.approved = approved;
    }

    public Document() {}


    //GETTERS AND SETTERS
    public int getId() {
        return id;
    }

    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }

    public Adopter getAdopter() {
        return adopter;
    }
    public void setAdopter(Adopter adopter) {
        this.adopter = adopter;
    }
    
    public boolean isApproved() {
        return approved;
    }
    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public Shelter getShelter() {
        return shelter;
    }
    public void setShelter(Shelter shelter) {
        this.shelter = shelter;
    }

    public Vet getVet() {
        return vet;
    }
    public void setVet(Vet vet) {
        this.vet = vet;
    }
}
