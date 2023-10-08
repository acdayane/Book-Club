package ada.mod3.bookclub.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ada.mod3.bookclub.model.User;

public interface UserRepository extends JpaRepository<User, Integer> { //tipo de dado e de id que recebe
    
    User findByEmail(String email);

    @Query("SELECT u FROM User u WHERE LOWER(u.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<User> findAllByNameIgnoreCase(String name);

}
