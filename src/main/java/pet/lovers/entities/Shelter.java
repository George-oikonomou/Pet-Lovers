package pet.lovers.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class Shelter extends User{

    @Column
    @NotBlank
    @Size(min = 2, max = 50)
    private String name;

    @OneToMany(mappedBy = "shelter", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pet> pets;

//    @Column
//    @NotBlank
//    private Vet  vet;


    public Shelter(String username, String email, String password, String name) {
        super(username, email, password);
        this.name = name;
    }

    public Shelter() {}

    public @NotBlank @Size(min = 2, max = 50) String getName() {
        return name;
    }

    public void setName(@NotBlank @Size(min = 2, max = 50) String name) {
        this.name = name;
    }
}