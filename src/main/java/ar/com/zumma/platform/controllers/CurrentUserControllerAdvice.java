package ar.com.zumma.platform.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import ar.com.zumma.platform.domain.CurrentUser;

@ControllerAdvice
public class CurrentUserControllerAdvice {

    private static final Logger LOGGER = LoggerFactory.getLogger(CurrentUserControllerAdvice.class);

    @ModelAttribute("currentUser")
    public CurrentUser getCurrentUser(Authentication authentication) {
    	LOGGER.debug("getCurrentUser: {0}", (authentication != null ? authentication.getPrincipal() : null));
        return (authentication == null) ? null : (CurrentUser) authentication.getPrincipal();
    }


}
