package com.reveture.project2.service;

import com.reveture.project2.DTO.UserDTO;
import com.reveture.project2.entities.Team;
import com.reveture.project2.entities.User;
import com.reveture.project2.exception.CustomException;
import com.reveture.project2.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;

import java.util.Optional;
import java.util.UUID;


@Service
@AllArgsConstructor
public class UserService {

    @Autowired
    private final UserRepository userRepository;


    //TODO: this works fine when a Team is not passed as param, perhaps we should check if
    // it still works when a team is passed as param in HTTP request.
    // it is a little tricky to consider passing param w Team object as Team object
    // requires info such as "balance", which is unrealistic for client to have
    // thus, we encounter more complications if a team isn' passed. Something to discuss
    // as a group
    public User addNewUser(User u) throws CustomException{
        validateUser(u);
            try {
                u.setSalary(0.0);
                return userRepository.saveAndFlush(u);
            }
            catch (DataIntegrityViolationException dataException){
                throw new CustomException("Data Integrity Exception. Likely, trying to insert duplicate key into DB");
            }
            catch (Exception e){
                throw new CustomException("Failed to insert user: " + e.getMessage());
            }
    }
    public User getUserByUUID(UUID id) throws CustomException{
        String stringID = id.toString();
        Optional<User> u =  userRepository.findById(id);
        if(u.isEmpty()){
            String s = String.format("User doesn't exist: No UUID matching '%s' could be found", stringID);
            throw new CustomException(s);
        }
        return u.get();

    }

    public User updateRole(UUID u, String newRole, UUID manager_id) throws CustomException{
        if (!newRole.equals("Player") && !newRole.equals("Manager") ){
            throw new CustomException("Please ensure that updated role is either 'Player' or 'Manager'");
        }
        User manager = this.getUserByUUID(manager_id);
        User user = this.getUserByUUID(u);
        if (manager.getTeam() == null || user.getUserId() == null) {
            throw new CustomException("You or Player are not part of any team, so you cannot change anyone's role");
        }
        if (!manager.getTeam().getTeamId().equals(user.getTeam().getTeamId())) {
            throw new CustomException("You cannot change role of User that are not part of your team");
        }
        if (newRole.equals(user.getRole())){
            String errorMessage = String.format("User role could not be updated: user role is already set to '%s'", newRole);
            throw new CustomException(errorMessage);
        }
        user.setRole(newRole);
        return userRepository.saveAndFlush(user);
    }

    private void validateUser(User u) throws CustomException {
        if (u.getUsername() == null) {
            throw new CustomException("Username for user cannot be null.");
        } else if (u.getLastName() == null) {
            throw new CustomException("Lastname for user cannot be null.");
        } else if (u.getPassword() == null) {
            throw new CustomException("Password for user cannot be null.");
        } else if (u.getUsername().length() < 5) {
            throw new CustomException("Username length must be at least 5 characters.");
        } else if (u.getPassword().length() < 5) {
            throw new CustomException("Password length must be at least 5 characters.");
        }
        else if (!u.getRole().equals("Player") && !u.getRole().equals("Manager") ){
            throw new CustomException("Please ensure that updated role is either 'Player' or 'Manager'");
        }
    }

    public List<User> getAllUsersByTeam(Team team) {
        return this.userRepository.findByTeam(team);
    }

    public User getUserByUsernameAndPassword(String username, String password) throws CustomException {
        Optional<User> user = this.userRepository.findByUsernameAndPassword(username, password);
        if (user.isEmpty()) {
            throw new CustomException("Enter Valid username and Password");
        }
        return user.get();
    }

    public User updateTeam(User u, Team newTeam) throws CustomException {
        User user = getUserByUUID(u.getUserId());
        user.setTeam(newTeam);
        return userRepository.saveAndFlush(user);
    }

    public User removeUser(Team manager_team, UUID uuid) throws CustomException {
        //user check happen in the method
        User user = getUserByUUID(uuid);

        if (!manager_team.getTeamId().equals(user.getTeam().getTeamId())) {
            throw new CustomException("You are not part of same team, you cannot kick this player out");
        }

        //set team to null and salary to 0
        user.setTeam(null);
        user.setSalary(0.0);
        return userRepository.saveAndFlush(user);
    }

    public void updatePlayerTeamAndSalary(User player) {
        this.userRepository.save(player);
    }
}
