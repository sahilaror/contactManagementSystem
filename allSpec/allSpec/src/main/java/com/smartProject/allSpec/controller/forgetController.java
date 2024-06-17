package com.smartProject.allSpec.controller;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.smartProject.allSpec.businessOrServiceLogic.emailService;
import com.smartProject.allSpec.helper.message;
import com.smartProject.allSpec.model.user;
import com.smartProject.allSpec.repositoryOrDao.smartRepo;

import jakarta.servlet.http.HttpSession;


@RestController
public class forgetController {
	@Autowired
	private emailService emailServ;
	@Autowired
	smartRepo smartRop;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@GetMapping("/forgot")
	public ModelAndView forgot() {
		
		
		String view="normal/forgot";
		return new ModelAndView(view);
	}
	
	Random random=new Random(1000);
	
	@PostMapping("/send-otp")
	public ModelAndView otp(@RequestParam("email") String email,HttpSession session) {
		System.out.println(email);
		int value=random.nextInt(999999);
		System.out.println("otp"+value);
		String subject="otp from scm";
		String message=""+
						"<div style='border:1px solid #e2e2e2 padding:20px'>"+
						"<h1>"+
						"OTP is:-"+
						"<b>"+value+
						"</n>"+
						"<h1>"+
						"<div>";
		String to=email;
		boolean flag=emailServ.sendmail(subject,message,to);
		if(flag) {
			session.setAttribute("myotp",value);
			session.setAttribute("email",email);
			String view="normal/sendotp";
			return new ModelAndView(view);
		}else {
			session.setAttribute("message",new message("please check your email","alert-danger"));
			String view1="normal/forgot";
			return new ModelAndView(view1);
		}
	}
	@PostMapping("/changed-pass")
	public ModelAndView changepassword(@RequestParam("otp")int otp,HttpSession session) {
		int myotp=(int)session.getAttribute("myotp");
		String email=(String)session.getAttribute("email");
		System.out.println("email and otp is:-"+email+myotp);
		if (otp==myotp) {
			System.out.println("email checking "+email);
			user user=smartRop.getUserByUserName(email);
			System.out.println("user is:-"+user);
			if(user==null) {
				session.setAttribute("message",new message("seems like this email is not registered","alert-danger"));
				RedirectView rd=new RedirectView();
				rd.setUrl("/forgot");
				return new ModelAndView(rd);
			}else {
			
			}
			String view="normal/changed-pass";
			return new ModelAndView(view);
		}
		else {
			session.setAttribute("message",new message("otp doesn't match", "alert-danger"));
			String view1="normal/sendotp";
			return new ModelAndView(view1);
		}
		
	}
	@GetMapping("/new-pass")
	public ModelAndView newpass(@RequestParam("password") String newpassword,HttpSession session) {
		String email=(String)session.getAttribute("email");
		user user=smartRop.getUserByUserName(email);
		user.setPassword(bCryptPasswordEncoder.encode(newpassword));
		smartRop.save(user);
		session.setAttribute("message",new message("password successfully changed","alert-success"));
		String view="normal/changed-pass";
		return new ModelAndView(view);
	}
}
