package pet.lovers.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Set;

@Entity
@Table
@PrimaryKeyJoinColumn(name = "user_id")
public class Shelter extends User{

    @Column
    @NotBlank
    @Size(min = 2, max = 50)
    private String name;

//    @OneToMany(mappedBy = "shelter", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Pet> pets;

//    @Column
//    @NotBlank
//    private Vet  vet;


    public Shelter(Integer id, String username, String email, String password, long contact_number, String location, String role, Set<Role> roles, String name) {
        super(id, username, email, password, contact_number, location, role, roles);
        this.name = name;
    }

    public Shelter() {
        super();
    }


    public @NotBlank @Size(min = 2, max = 50) String getName() {
        return name;
    }

    public void setName(@NotBlank @Size(min = 2, max = 50) String name) {
        this.name = name;
    }
}