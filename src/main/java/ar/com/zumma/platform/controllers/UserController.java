package ar.com.zumma.platform.controllers;

import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ar.com.zumma.platform.domain.User;
import ar.com.zumma.platform.layout.form.UserForm;
import ar.com.zumma.platform.layout.form.validator.UserCreateFormValidator;
import ar.com.zumma.platform.layout.support.PageWrapper;
import ar.com.zumma.platform.repositories.search.SearchDTO;
import ar.com.zumma.platform.repositories.search.SearchType;
import ar.com.zumma.platform.services.user.UserService;

@Controller
public class UserController extends AbstractBaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;
    private final UserCreateFormValidator userCreateFormValidator;

    @Autowired
    public UserController(UserService userService, UserCreateFormValidator userCreateFormValidator) {
        this.userService = userService;
        this.userCreateFormValidator = userCreateFormValidator;
    }

    @PreAuthorize("hasAuthority('READ_PRIVILEGE')")
    @RequestMapping(value = "/users", method = RequestMethod.GET)
	public ModelAndView getUsersList(@RequestParam(value = "find", required = false) String criteriaSearch,
			@RequestParam(value = "page.page", required = false) String page) {
		LOGGER.debug("Getting users filtered page by criteriaSearch={} ", criteriaSearch);
		PageWrapper<User> pageWrapper = new PageWrapper<User>(userService.getFilteredUsers(new SearchDTO(criteriaSearch, SearchType.QUERY_ANNOTATION, getPageable(page))), "/users");
		ModelAndView mv = new ModelAndView("in/users/users", "page", pageWrapper);
		if(criteriaSearch != null) {
			mv = new ModelAndView("in/users/users :: #results", "page", pageWrapper);
		}
		return mv;
	}
    
    @InitBinder("form")
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(userCreateFormValidator);
    }

    //@PreAuthorize("@currentUserServiceImpl.canAccessUser(principal, #id)")
    @RequestMapping("/user/{id}")
    public ModelAndView getUserPage(@PathVariable Long id) {
        LOGGER.debug("Getting user page for user={}", id);
        return new ModelAndView("user", "user_view", userService.getUserById(id));
    }

    //@PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @RequestMapping(value = "/user/create", method = RequestMethod.GET)
    public ModelAndView getUserCreatePage() {
        LOGGER.debug("Getting user create form");
        ModelAndView mv = new ModelAndView("in/users/user_create");
        mv.addObject("edit", false);
        mv.addObject("form", new UserForm());
        return mv;
    }

    //@PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @RequestMapping(value = "/user/create", method = RequestMethod.POST)
    public String handleUserCreateForm(@Valid @ModelAttribute("form") UserForm form, BindingResult bindingResult) {
        LOGGER.debug("Processing user create form={}, bindingResult={}", form, bindingResult);
        if (bindingResult.hasErrors()) {
            // failed validation
            return "in/users/user_create";
        }
        try {
            userService.create(form);
        } catch (DataIntegrityViolationException e) {
            // probably email already exists - very rare case when multiple admins are adding same user
            // at the same time and form validation has passed for more than one of them.
            LOGGER.warn("Exception occurred when trying to save the user, assuming duplicate email", e);
            bindingResult.reject("email.exists", "Email already exists");
            return "in/users/user_create";
        }
        // ok, redirect
        return "redirect:/users";
    }
    
    //@PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable Long id) {
        LOGGER.debug("Deleting user={}", id);
        userService.delete(id);
        return "redirect:/users";
    }

    //@PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @RequestMapping(value = "/user/{id}/edit", method = RequestMethod.GET)
    public ModelAndView getUserEdit(@PathVariable String id) {
        LOGGER.debug("Getting user edit form {}", id);
        Optional<User> user = userService.getUserById(Long.parseLong(id));
        UserForm uf = null;
        ModelAndView mv = new ModelAndView("in/users/user_create");
        if(user.isPresent()) {
        	uf = new UserForm(user.get());
        	mv.addObject("edit", true);
        }
        else {
        	uf = new UserForm();
        	mv.addObject("edit", false);
        }
        mv.addObject("form", uf);
        return mv;
    }
    
    //@PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @RequestMapping(value = "/user/{id}/edit", method = RequestMethod.POST)
    public String handleUserUpdateForm(@Valid @ModelAttribute("form") UserForm form, BindingResult bindingResult) {
        LOGGER.debug("Processing user update form={}, bindingResult={}", form, bindingResult);
        if (bindingResult.hasErrors()) {
            // failed validation
            return "in/users/user_create";
        }
        try {
            userService.update(form);
        } catch (Exception e) {
            LOGGER.warn("Exception occurred when trying to updating the user.", e);
            return "in/users/user_create";
        }
    	return "redirect:/users";
    }

	@Override
	public String getSortField() {
		return "email";
	}
}
