package com.anas.springblog.service;

import com.anas.springblog.model.User;
import com.anas.springblog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;


    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public Long getUserId(String username) throws UsernameNotFoundException{
        User user = (User) loadUserByUsername(username);
        return user.getId();
    }

    public User loadUserByID(Long userId){
        return userRepository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id " + userId));
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }
}
