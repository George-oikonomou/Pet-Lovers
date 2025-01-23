package pet.lovers.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class Role {

    public static final String ADOPTER = "ROLE_ADOPTER";
    public static final String ADMIN = "ROLE_ADMIN";
    public static final String VET = "ROLE_VET";
    public static final String SHELTER = "ROLE_SHELTER";


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 20)
    private String name;

    public Role() {}

    public Role(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name.substring(5, 6).toUpperCase() + name.substring(6).toLowerCase();
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}