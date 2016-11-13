package pl.gda.pg.ds.sruv.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.gda.pg.ds.sruv.models.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
