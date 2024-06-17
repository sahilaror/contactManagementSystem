package com.smartProject.allSpec.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.Optional;

import org.aspectj.weaver.tools.Trace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.smartProject.allSpec.businessOrServiceLogic.smartService;
import com.smartProject.allSpec.helper.message;
import com.smartProject.allSpec.model.contact;
import com.smartProject.allSpec.model.user;
import com.smartProject.allSpec.repositoryOrDao.contactRepo;
import com.smartProject.allSpec.repositoryOrDao.smartRepo;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.AssertFalse;



@Controller
@RequestMapping("/user")
public class userController {

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	smartRepo smartRop;
	
	@Autowired
	smartService smartSer;
	
	@Autowired
	contactRepo contactRepo;

	@GetMapping("/index")
	public ModelAndView userDashboard(Model model,Principal principal) {
		String name=principal.getName();   //help to fetch the email
		System.out.println("name"+name);
		user user=smartRop.getUserByUserName(name); //user help to fetch the name of user
		String uname=user.getName();
		System.out.println("user"+uname);
		model.addAttribute("user",uname);
		String view="normal/userDashboard";
		return new ModelAndView(view);
	}
	
	@GetMapping("/add-contact")
	public ModelAndView addContact(Model model, Principal principal) {
		String name=principal.getName();   //help to fetch the email
		System.out.println("name"+name);
		user user=smartRop.getUserByUserName(name); //user help to fetch the name of user
		System.out.println("user"+user);
		model.addAttribute("user",user);
		model.addAttribute("title","addContact");
		model.addAttribute("contact",new contact());
		String view="normal/addContact";
		return new ModelAndView(view);
	}
	@PostMapping("/user/process-contact")
	public ModelAndView postContact(@ModelAttribute contact contact,Principal principal,Model model,@RequestParam("profileImage") MultipartFile file ) throws IOException {
		try {
		String name=principal.getName();   //help to fetch the email
		user user=smartRop.getUserByUserName(name); //user help to fetch the name of user
		model.addAttribute("user",user);
		if (file.isEmpty()) {
			contact.setImage("contact.png");
		}
		else {
			contact.setImage(file.getOriginalFilename());
			File saveFile=new ClassPathResource("/static/image").getFile();
			java.nio.file.Path path=Paths.get(saveFile.getAbsolutePath()+File.separator+file.getOriginalFilename());
			Files.copy(file.getInputStream(),path,StandardCopyOption.REPLACE_EXISTING);
		}
		contact.setUsers(user);
		user.getContacts().add(contact);
		smartRop.save(user);
		System.out.println("contact "+contact);
		
		}
		catch (Exception e) {
			// TODO: handle exception
			System.out.println("error"+e.getMessage());
			e.printStackTrace();
		}
		String view="normal/addContact";
		return new ModelAndView(view);
	}
	@GetMapping("/show-contact/{page}")
	public ModelAndView showContact(@PathVariable("page") Integer page ,Model model,Principal principal) {
		String name= principal.getName();
		user user= smartRop.getUserByUserName(name);
		model.addAttribute("user",user);  // using for show the user name in title
		
			Pageable pageable=PageRequest.of(page,5);
		Page<contact> allcontacts=contactRepo.findContactsByUser(user.getId(),pageable);
		model.addAttribute("cont",allcontacts);
		model.addAttribute("currentpage",page);
		model.addAttribute("totalpage",allcontacts.getTotalPages());
		String view="normal/showcontact";
		return new ModelAndView(view);
	}
	
	@GetMapping("/{cId}/contact")
	public ModelAndView showContactDetail(@PathVariable("cId") Integer cId,Model model,Principal principal){
		String name= principal.getName();
		user user= smartRop.getUserByUserName(name);
		model.addAttribute("user",user);  // using for show the user name in title
		
		Optional<contact> contactOptional=contactRepo.findById(cId);
		contact contact=contactOptional.get();
		
		model.addAttribute("contact",contact);
		
		String view="normal/contactdetail";
		return new ModelAndView(view);
	}
	
//	@GetMapping("/{cId}/delet")
//	public ModelAndView deleteContact(@PathVariable("{cId}") Integer cId,Model model,Principal principal) {
//		System.out.println(cId);
//		String name= principal.getName();
//		user user= smartRop.getUserByUserName(name);
//		model.addAttribute("user",user);  // using for show the user name in title
//		Optional<contact> contactoOptional1=contactRepo.findById(cId);
//		contact contact=contactoOptional1.get();
//		if (user.getId()==contact.getUsers().getId()) {
//		contact.setUsers(null);
//		contactRepo.delete(contact);
//		contactRepo.deleteById(cId);
//		}
//		String view="normal/contactdetail";
//		return new ModelAndView(view);
//	}
	@GetMapping("/deleting")
	public ModelAndView deletingContcts(@RequestParam(name = "id") Integer cId,Model model,Principal principal) {
		String name= principal.getName();
		user user= smartRop.getUserByUserName(name);
		model.addAttribute("user",user);
		Optional<contact> optionalcontactOptional2=contactRepo.findById(cId);
		contact contact=optionalcontactOptional2.get();
		contact.setUsers(null);
		contactRepo.deleteById(cId);
		RedirectView rd=new RedirectView();
		rd.setUrl("/user/show-contact/0");
		return new ModelAndView(rd);
	}
	@GetMapping("/updatecontact/{cid}")
	public ModelAndView updateContact(@PathVariable("cid") Integer cid, Model model,Principal principal) {
		String name= principal.getName();
		user user= smartRop.getUserByUserName(name);
		model.addAttribute("user",user);
		contact contact=contactRepo.findById(cid).get();
		model.addAttribute("contact",contact);
		String view="normal/updatecontact";
		return new ModelAndView(view);
	}
	@PostMapping("/updatecontacts")
	public ModelAndView updateContacts(@ModelAttribute("contact") contact contact,Model model,Principal principal) {
		String name= principal.getName();
		user user= smartRop.getUserByUserName(name);
		model.addAttribute("user",user);
		contact.setUsers(user);
		System.out.println("cid is:- "+contact.getcId());
		contactRepo.save(contact);
		model.addAttribute("contact",contact);
		RedirectView rd=new RedirectView();
		rd.setUrl("/user/show-contact/0");
		return new ModelAndView(rd);
	}
	@GetMapping("/setting")
	public ModelAndView setting(Model model,Principal principal) {
		String name= principal.getName();
		user user= smartRop.getUserByUserName(name);
		model.addAttribute("user",user);
		String view="normal/setting";
		return new ModelAndView(view);
		}
	
	@PostMapping("/change-setting")
	public ModelAndView changeSetting(@RequestParam("oldpassword") String oldpassword,@RequestParam("newpassword") String newpassword,Principal principal , HttpSession session) {
		System.out.println("old password"+oldpassword);
		System.out.println("newpassword"+newpassword);
		
		String name=principal.getName();
		user user=smartRop.getUserByUserName(name);
		
		if (bCryptPasswordEncoder.matches(oldpassword, user.getPassword())) {
			//change password
			user.setPassword(bCryptPasswordEncoder.encode(newpassword));
			smartRop.save(user);
			session.setAttribute("message",new message("successfully changed","alert-success"));
		}
		else {
			session.setAttribute("message",new message("password doesn't match","alert-danger"));
		}
		
		RedirectView rd=new RedirectView();
		rd.setUrl("/user/setting");
		return new ModelAndView(rd);
	}
	@GetMapping("/yourprofile")
	public ModelAndView profile(Model model,Principal principal) {
		String name=principal.getName();
		user user=smartRop.getUserByUserName(name);
		model.addAttribute("user",user);
		model.addAttribute("userprofile", user);
		String view="normal/profile";
		return new ModelAndView(view);
	}
}
