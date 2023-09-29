package ada.mod3.bookclub.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ada.mod3.bookclub.model.User;

public interface UserRepository extends JpaRepository<User, Integer> { //tipo de dado e de id que recebe

    @Override
    @Query(value = "SELECT * FROM users WHERE active = TRUE", nativeQuery = true)
    Page<User> findAll(Pageable pageable);
    
}
