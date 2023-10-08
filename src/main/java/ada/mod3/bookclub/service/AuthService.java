package ada.mod3.bookclub.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ada.mod3.bookclub.repository.UserRepository;

@Service
public class AuthService implements UserDetailsService { //UserDetailService busca o usuário na db

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {       
      
        return userRepository.findByEmail(email);
        
    }
    
}
