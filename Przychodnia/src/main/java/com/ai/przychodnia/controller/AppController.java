package com.ai.przychodnia.controller;


import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ai.przychodnia.model.User;
import com.ai.przychodnia.service.UserService;

@Controller
@RequestMapping("/")
public class AppController {

   @Autowired
   UserService service;
    
   @Autowired
   MessageSource messageSource;

   /*
    * This method will list all existing users.
    */
   @RequestMapping(value = { "/", "/list" }, method = RequestMethod.GET)
   public String listUsers(ModelMap model) {
       List<User> users = service.findAllUsers();
       model.addAttribute("user", users);
       return "allusers";
   }

   /*
    * This method will provide the medium to add a new user.
    */
   @RequestMapping(value = { "/new" }, method = RequestMethod.GET)
   public String newUser(ModelMap model) {
       User user = new User();
       model.addAttribute("user", user);
       model.addAttribute("edit", false);
       return "registration";
   }

   /*
    * This method will be called on form submission, handling POST request for
    * saving user in database. It also validates the user input
    */
   @RequestMapping(value = { "/new" }, method = RequestMethod.POST)
   public String saveUser(@Valid User user, BindingResult result,
           ModelMap model) {

       if (result.hasErrors()) {
           return "registration";
       }
       
       service.saveUser(user);

       model.addAttribute("success", "User " + user.getName() + " " + user.getSurname() + " registered successfully");
       return "success";
   }


   /*
    * This method will provide the medium to update an existing user.
    */
   @RequestMapping(value = { "/edit-{pesel}-employee" }, method = RequestMethod.GET)
   public String editEmployee(@PathVariable String pesel, ModelMap model) {
       User user = service.findUserByPesel(pesel);
       model.addAttribute("user", user);
       model.addAttribute("edit", true);
       return "registration";
   }
    
   /*
    * This method will be called on form submission, handling POST request for
    * updating user in database. It also validates the user input
    */
   @RequestMapping(value = { "/edit-{pesel}-employee" }, method = RequestMethod.POST)
   public String updateEmployee(@Valid User user, BindingResult result,
           ModelMap model, @PathVariable String ssn) {

       if (result.hasErrors()) {
           return "registration";
       }
       
       service.updateUser(user);

       model.addAttribute("success", "User " + user.getName() + " " + user.getSurname()  + " updated successfully");
       return "success";
   }

    
   /*
    * This method will delete an user by it's pesel value.
    */
   @RequestMapping(value = { "/delete-{pesel}-employee" }, method = RequestMethod.GET)
   public String deleteUser(@PathVariable String pesel) {
       service.deleteUserByPesel(pesel);
       return "redirect:/list";
   }

}