package pet.lovers.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table
@PrimaryKeyJoinColumn(name = "user_id")
public class Admin extends User {

    public Admin() {
    }

    public Admin(String username, String email, String password) {
        super(username, email, password);
    }

}