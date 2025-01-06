package pet.lovers.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    @NotBlank
    private String path;

    @Column
    @Size(min = 1, max = 50)
    private String name;

    @Column
    @NotBlank
    private boolean approved;

    @Column
    @Size(max = 200)
    private String descriptor;

    public Document(String path, String name, boolean approved, String descriptor) {
        this.path = path;
        this.name = name;
        this.approved = approved;
        this.descriptor = descriptor;
    }

    public Document() {

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

    public boolean getApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public String getDescriptor() {
        return descriptor;
    }

    public void setDescriptor(String descriptor) {
        this.descriptor = descriptor;
    }
}
