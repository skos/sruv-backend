package pl.gda.pg.ds.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name", nullable = false)
    @NotNull
    @Size(max = 128)
    private String name;

    public User(String name) {
        this.name = name;
    }

    public User() {}

    public String getName() {
        return name;
    }
}
