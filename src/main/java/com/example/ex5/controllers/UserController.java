package com.example.ex5.controllers;

import com.example.ex5.repo.User;
import com.example.ex5.repo.UserRepository;
import com.example.ex5.services.UserService;
import jakarta.validation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {

    /* example: injecting a default value from the application.properties  file */
    @Value( "${demo.coursename}" )
    private String someProperty;

    /* inject via its type the User repo bean - a singleton */
    @Autowired
    private UserRepository repository;

    @Autowired
    private UserService userService;

    @GetMapping("/userlog")
    public String main(User user, Model model) {
        model.addAttribute("course", someProperty);

        // the name "users"  is bound to the VIEW
        model.addAttribute("users", repository.findAll());
        return "index";
    }

    @GetMapping("/signuplog")
    public String showSignUpForm(User user, Model model) {
        //model.addAttribute("user", new User("noname","noemail"));
        return "add-user";
    }

    /**
     * because we declared a BindingResult parameter, Spring will pass it to the controller
     * and we MUST use it to check for validation errors and catch them
     * @param user
     * @param result
     * @param model
     * @return
     */
    @PostMapping("/adduser")
    public String addUser(@Valid User user, BindingResult result, Model model) {

        // validate the object and get the errors
        if (result.hasErrors()) {
            // errors MUST be displayed in the view and not just printed to the console
            System.out.println("validation errors: " + result.getAllErrors());
            return "add-user";
        }



        repository.save(user);

        // pass the list of users to the view
        model.addAttribute("users", repository.findAll());
        return "index";
    }

    /** we implemented a POST request for adding a user, as a result
     * if the user accesses the /adduser URL with a GET request, we must redirect him to the main page
     * @param user
     * @param model
     * @return
     */
    @GetMapping("/adduser")
    public String showAddUserForm(User user, Model model) {
        // redirect to main page
        return "redirect:/";
    }


    /**
     * USING GET HTTP METHODS FOR UPDATING AND DELETING IS NOT A GOOD PRACTICE
     * below you will find the same methods but using POST methods
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/edit/log/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {

        User user = repository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Invalid user Id:" + id));

        // the name "user"  is bound to the VIEW
        model.addAttribute("user", user);
        return "update-user";
    }

    /* same as above but PostMapping
    @PostMapping("/edit")
    public String editUser(@RequestParam("id") long id, Model model) {

        User user = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));

        // the name "user"  is bound to the VIEW
        model.addAttribute("user", user);
        return "update-user";
    }    */

    @PostMapping("/update/log/{id}")
    public String updateUser(@PathVariable("id") long id, @Valid User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            user.setId(id);
            return "update-user";
        }

        repository.save(user);
        model.addAttribute("users", repository.findAll());
        return "index";
    }

    @GetMapping("/update/log/{id}")
    public String updateUserGet() {
        return "redirect:/";
    }

    @PostMapping("/delete/log")
    public String deleteUser(@RequestParam("id") long id, Model model) {

        // we throw an exception if the user is not found
        // since we don't catch the exception here, it will fall back on an error page (see below)
        User user = repository
                .findById(id)
                .orElseThrow(
                        () -> new IllegalArgumentException("Invalid user Id:" + id)
                );
        repository.delete(user);
        model.addAttribute("users", repository.findAll());
        return "index";
    }

    /**
     * USING GET HTTP METHODS FOR UPDATING AND DELETING IS NOT A GOOD PRACTICE
     * @param id
     * @return
     */
    @GetMapping("/delete/log/{id}")
    public String deleteUserGet(@PathVariable long id) {
        return "redirect:/";
    }

    @GetMapping("/delete/log")
    public String deleteUserGetNoParams() {
        return "redirect:/";
    }

    /**
     * returns the json.html page
     * @param model
     * @return
     */
    @GetMapping(value="/json/log")
    public String json (Model model) {
        return "json";
    }
    /**
     * a sample controller return the content of the DB in JSON format
     * @return
     */

    /** handling IllegalArgumentException */
    @GetMapping("/error/log")
    public String error() {
        return "error";
    }

}