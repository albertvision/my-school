package bg.nbuteam4.myschool.repository;

import bg.nbuteam4.myschool.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsManager implements org.springframework.security.provisioning.UserDetailsManager {
    private UserRepository userRepository;

    public UserDetailsManager(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void createUser(UserDetails user) {
        userRepository.save((User) user);
    }

    @Override
    public void updateUser(UserDetails user) {
        userRepository.save((User) user);
    }

    @Override
    @Transactional
    public void deleteUser(String username) {
        UserDetails user = loadUserByUsername(username);

        userRepository.delete((User) user);
    }

    @Override
    @Transactional
    public void changePassword(String oldPassword, String newPassword) {
        User user = userRepository
                .findByPassword(oldPassword)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid password."));

        user.setPassword(newPassword);
        userRepository.save(user);
    }

    @Override
    public boolean userExists(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid username."));
    }
}
