package ar.com.zumma.platform.services.user;

import java.util.Arrays;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import ar.com.zumma.platform.domain.User;
import ar.com.zumma.platform.layout.form.UserForm;
import ar.com.zumma.platform.repositories.RoleRepository;
import ar.com.zumma.platform.repositories.UserRepository;
import ar.com.zumma.platform.repositories.search.SearchDTO;
import ar.com.zumma.platform.repositories.search.SearchType;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;

    private final RoleRepository roleRepository;
    
    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
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
    public Page<User> getAllUsers(Pageable pageable) {
        LOGGER.debug("Getting all users");
        return userRepository.findAll(pageable);
    }

    @Override
    public User create(UserForm form) {
    	LOGGER.debug("Creating user with emaill={}", form.getEmail().replaceFirst("@.*", "@***"));
    	User user = null;
        try {
			user = new User();
			user.setEmail(form.getEmail());
			user.setPasswordHash(new BCryptPasswordEncoder().encode(form.getPassword()));
			user.setRoles(Arrays.asList(roleRepository.findByName("ROLE_USER")));
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
			user.setRoles(Arrays.asList(roleRepository.findByName("ROLE_USER")));
			user = userRepository.save(user);
		} catch (Exception e) {
			LOGGER.error("User Update Exception."+ e.getMessage());
			e.printStackTrace();
		}
        return user;
	}

	@Override
	public Page<User> getFilteredUsers(SearchDTO searchCriteria) {
		LOGGER.debug("Searching users with search criteria: " + searchCriteria);
        String searchTerm = searchCriteria.getSearchTerm();
        SearchType searchType = searchCriteria.getSearchType();
         
        if (searchType == null) {
            throw new IllegalArgumentException();
        }
        
        if(searchTerm == null || searchTerm.equalsIgnoreCase(""))
        	return getAllUsers(searchCriteria.getPageable());
        else
        	return userRepository.find(searchTerm, searchCriteria.getPageable());
	}
	
	/*
    private Page<User> findUsersBySearchType(String searchTerm, SearchType searchType) {
    	Page<User> users = null;
        if (searchType == SearchType.METHOD_NAME) {
            LOGGER.debug("Searching users by using method name query creation.");
            //users = userRepository.findOneByEmail(searchTerm);
        }
        else if (searchType == SearchType.NAMED_QUERY) {
            LOGGER.debug("Searching users by using named query");
           //users = personRepository.findByName(searchTerm);
        }
        else {
            LOGGER.debug("Searching users by using query annotation");
            users = userRepository.find(searchTerm);
            LOGGER.debug("users:" + users.size());
        }
        return users;
    }*/

	@Override
	public Page<User> getUsersPage(Pageable pageable) {
		LOGGER.trace("Page:"+pageable.getPageNumber());
		LOGGER.trace("Page-Size:"+pageable.getPageSize());
		return userRepository.findAll(pageable);
	}

}
