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
    @NotBlank
    @Size(min = 1, max = 50)
    private String name;

    @Column
    @NotNull
    private boolean approved;

    @Column
    @NotBlank
    @Size(max = 200)
    private String descriptor;

    @OneToOne(mappedBy = "identification", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
    private Adopter adopter;

    @OneToOne(mappedBy = "documents", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
    private Shelter shelter;

    @OneToOne(mappedBy = "documents", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
    private Vet vet;
    //END TABLE COLUMNS



    //CONSTRUCTORS
    public Document(String path, String name, boolean approved, String descriptor) {
        this.path = path;
        this.name = name;
        this.approved = approved;
        this.descriptor = descriptor;
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

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getDescriptor() {
        return descriptor;
    }
    public void setDescriptor(String descriptor) {
        this.descriptor = descriptor;
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
