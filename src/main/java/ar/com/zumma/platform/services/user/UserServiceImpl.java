package ar.com.zumma.platform.services.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import ar.com.zumma.platform.domain.User;
import ar.com.zumma.platform.layout.form.UserForm;
import ar.com.zumma.platform.repositories.UserRepository;

import java.util.Collection;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> getUserById(long id) {
        LOGGER.debug("Getting user={}", id);
        return Optional.ofNullable(userRepository.findOne(id));
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        LOGGER.debug("Getting user by email={}", email.replaceFirst("@.*", "@***"));
        return userRepository.findOneByEmail(email);
    }

    @Override
    public Collection<User> getAllUsers() {
        LOGGER.debug("Getting all users");
        return userRepository.findAll(new Sort("email"));
    }

    @Override
    public User create(UserForm form) {
    	LOGGER.debug("Creating user with emaill={}", form.getEmail().replaceFirst("@.*", "@***"));
    	User user = null;
        try {
			user = new User();
			user.setEmail(form.getEmail());
			user.setPasswordHash(new BCryptPasswordEncoder().encode(form.getPassword()));
			user.setRole(form.getRole());
			user = userRepository.save(user);
		} catch (Exception e) {
			LOGGER.error("User Creation Exception."+ e.getMessage());
			e.printStackTrace();
		}
        return user;
    }

	@Override
	public boolean delete(Long id) {
		try {
			userRepository.delete(id);
		} catch (Exception e) {
			LOGGER.error("Error deteling user by ID." + e.getMessage());
			return false;
		}
		return true;
	}

	@Override
	public User update(UserForm form) {
    	LOGGER.debug("Updating user with id={}", form.getId());
    	User user = null;
        try {
			user = new User();
			user.setId(form.getId());
			user.setEmail(form.getEmail());
			user.setPasswordHash(new BCryptPasswordEncoder().encode(form.getPassword()));
			user.setRole(form.getRole());
			user = userRepository.save(user);
		} catch (Exception e) {
			LOGGER.error("User Update Exception."+ e.getMessage());
			e.printStackTrace();
		}
        return user;
	}

}
