package ar.com.zumma.platform.controllers.sign;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ar.com.zumma.platform.layout.form.SignupForm;
import ar.com.zumma.platform.layout.support.AjaxUtils;
import ar.com.zumma.platform.layout.support.MessageHelper;
import ar.com.zumma.platform.services.user.UserService;

@Controller
public class SignupController {

    private static final String SIGNUP_VIEW_NAME = "signup/signup";

//	@Autowired
//	private AccountRepository accountRepository;
	
	@Autowired
	private UserService service;
	
//	@Autowired
//	private GroupService groupService;
	
	@RequestMapping(value = "signup")
	public String signup(Model model, @RequestHeader(value = "X-Requested-With", required = false) String requestedWith) {
		model.addAttribute(new SignupForm());
        if (AjaxUtils.isAjaxRequest(requestedWith)) {
            return SIGNUP_VIEW_NAME.concat(" :: signupForm");
        }
        return SIGNUP_VIEW_NAME;
	}
	
	@RequestMapping(value = "signup", method = RequestMethod.POST)
	public String signup(@Valid @ModelAttribute SignupForm signupForm, Errors errors, RedirectAttributes ra) {
		if (errors.hasErrors()) {
			return SIGNUP_VIEW_NAME;
		}
		
		ar.com.zumma.platform.domain.User user = signupForm.createAccount();
		//user.getGroups().add(groupService.findDefault());
		//user = service.create(signupForm);
		//accountService.signin(user);
		
        MessageHelper.addSuccessAttribute(ra, "signup.success");
		return "redirect:/";
	}
}
