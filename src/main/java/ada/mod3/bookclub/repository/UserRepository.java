package ada.mod3.bookclub.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ada.mod3.bookclub.model.User;

public interface UserRepository extends JpaRepository<User, Integer> { //tipo de dado e de id que recebe

    
    
}
