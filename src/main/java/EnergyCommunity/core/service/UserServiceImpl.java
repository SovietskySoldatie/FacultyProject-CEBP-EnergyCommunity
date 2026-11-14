package EnergyCommunity.core.service;

import EnergyCommunity.core.model.House;
import EnergyCommunity.core.model.User;
import EnergyCommunity.core.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "User not found with id: " + id));
    }

    @Override
    public User saveUser(User user) {
        if (user.getEmail() != null && userRepository.existsByEmail(user.getEmail())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "A user with email " + user.getEmail() + " already exists");
        }
        if (user.getHouses() != null) {
            for (House house : user.getHouses()) {
                house.setUser(user);
                if (house.getBatteries() != null) {
                    house.getBatteries().forEach(b -> b.setHouse(house));
                }
                if (house.getSolarPanels() != null) {
                    house.getSolarPanels().forEach(sp -> sp.setHouse(house));
                }
            }
        }
        return userRepository.save(user);
    }

    @Override
    public User updateUser(Long id, User updatedUser) {
        User existing = findUserById(id);
        if (updatedUser.getName() != null) {
            existing.setName(updatedUser.getName());
        }
        if (updatedUser.getEmail() != null) {
            if (!updatedUser.getEmail().equals(existing.getEmail())
                    && userRepository.existsByEmail(updatedUser.getEmail())) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "A user with email " + updatedUser.getEmail() + " already exists");
            }
            existing.setEmail(updatedUser.getEmail());
        }
        if (updatedUser.getPhone() != null) {
            existing.setPhone(updatedUser.getPhone());
        }
        return userRepository.save(existing);
    }

    @Override
    public void deleteUser(Long id) {
        User existing = findUserById(id);
        userRepository.delete(existing);
    }
}
