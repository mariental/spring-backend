package kk.server.repositories;

import java.util.Optional;
import kk.server.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>{
    public Optional<User> findByEmail(String email);
}
