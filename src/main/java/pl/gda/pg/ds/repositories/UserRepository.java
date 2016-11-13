package pl.gda.pg.ds.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.gda.pg.ds.models.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
