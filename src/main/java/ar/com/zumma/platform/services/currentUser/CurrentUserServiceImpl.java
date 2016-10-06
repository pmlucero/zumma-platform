package ar.com.zumma.platform.services.currentUser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.zumma.platform.domain.CurrentUser;
import ar.com.zumma.platform.repositories.RoleRepository;

@Service
public class CurrentUserServiceImpl implements CurrentUserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CurrentUserDetailsService.class);

	@Autowired
	private RoleRepository roleRepository;
	
    @Override
    public boolean canAccessUser(CurrentUser currentUser, Long userId) {
        LOGGER.debug("Checking if user={} has access to user={}", currentUser, userId);
        return currentUser != null
                && (currentUser.getRoles().contains(roleRepository.findByName("ROLE_ADMIN")) || currentUser.getId().equals(userId));
    }

}
