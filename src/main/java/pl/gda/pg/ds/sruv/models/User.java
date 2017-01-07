package pl.gda.pg.ds.sruv.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "login", nullable = false)
    @NotNull
    @Size(max = 128)
    private String login;

    public User(String login) {
        this.login = login;
    }

    public User() {}

    public String getLogin() {
        return login;
    }
}
