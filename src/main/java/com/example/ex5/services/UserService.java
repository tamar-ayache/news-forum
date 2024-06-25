package com.example.ex5.services;

import com.example.ex5.repo.User;
import com.example.ex5.repo.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Service layer used to implement business logic.
 * It is a good practice to separate the business logic from the controller
 * and the repository. This way the controller is only responsible for handling
 * the request and response, the repository is only responsible for the database operations,
 * and the service is responsible for the business logic.
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Finds users with duplicate usernames.
     *
     * @param userName the username to search for duplicates
     * @return a list of users with the specified username
     */
    public List<User> findDuplicateUserNames(String userName) {
        List<User> users = (List<User>) userRepository.findByUsername(userName);
        return users;
    }
}
