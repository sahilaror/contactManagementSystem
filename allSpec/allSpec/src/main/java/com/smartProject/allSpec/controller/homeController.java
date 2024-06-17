package com.smartProject.allSpec.controller;

import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.smartProject.allSpec.businessOrServiceLogic.smartService;
import com.smartProject.allSpec.helper.message;
import com.smartProject.allSpec.model.user;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
public class homeController {
	@Autowired
   smartService service;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@GetMapping("/")
	public ModelAndView home(Model model) {
		String view="home";
		model.addAttribute("title","home - smartcontact");
		return new ModelAndView(view);
	}
	

	
	@GetMapping("/signup")
	public ModelAndView signUp(Model model) {
		HashMap<String, Object> hm=new HashMap<>();
		hm.put("sign",new user());
		String view="signup";
		model.addAttribute("title","signUp - smartcontact");
		return new ModelAndView(view,hm);
	}
	
	@PostMapping("/do_register")
	public ModelAndView signupCompl(@Valid @ModelAttribute("sign") user user, BindingResult result,@RequestParam(value="agreement",defaultValue ="false" ) boolean agreement,Model model, HttpSession session) {
		try {
			if (!agreement) {
				throw new Exception("terms and condition not applied");
				}
			if (result.hasErrors()) {
				return new ModelAndView("/signup");
			}
			user.setRole("Role_USER");
			user.setEnabled(true);
			user.setImageUrl("default.png");
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			System.out.println("user"+user);
			service.save(user);
			model.addAttribute("sign", new user());
			session.setAttribute("message", new message("Successfully Registered!!","alert-success"));
			RedirectView rd=new RedirectView();
			rd.setUrl("/signup");
			return new ModelAndView(rd);
			
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("sign",user);
			session.setAttribute("message", new message("something went wrong!!"+e.getMessage(),"alert-danger"));
			RedirectView rd=new RedirectView();
			rd.setUrl("/signup");
			return new ModelAndView(rd);
		}
		
	}
	@GetMapping("/signin")
	public ModelAndView login() {
		
		String view="/login";
		return new ModelAndView(view);
	}
	@GetMapping("/admin")
	public ModelAndView admin() {
		
		return new ModelAndView("about");
	}
}
