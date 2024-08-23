package com.reveture.project2.controller;

import com.reveture.project2.DTO.TeamProposalDTO;
import com.reveture.project2.DTO.UserDTO;
import com.reveture.project2.entities.Team;
import com.reveture.project2.entities.TeamProposal;
import com.reveture.project2.entities.User;
import com.reveture.project2.exception.CustomException;
import com.reveture.project2.service.TeamProposalService;
import com.reveture.project2.service.TeamService;
import com.reveture.project2.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final TeamService teamService;
    private final TeamProposalService teamProposalService;
    private static final Logger logger = LoggerFactory.getLogger(TeamService.class);

    @Autowired
    public UserController(UserService userService, TeamService teamService, TeamProposalService teamProposalService) {
        this.userService = userService;
        this.teamService = teamService;
        this.teamProposalService = teamProposalService;
    }

    @GetMapping("/sponsors/{status}")
    public ResponseEntity<?> seeSponsorships(HttpSession session, @PathVariable String status){
        try {
            User user = (User) session.getAttribute("user");
            if (user == null) {
                return ResponseEntity.status(400).body("Login first");
            }
            if (user.getRole().equals("Player") && (! status.equals("Accepted"))) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You don't have authorization to access this information");
            }

            User u = userService.getUserByUUID(user.getUserId());
            Team t = userService.getTeamFromUser(u);
            List<TeamProposal> proposalList = userService.getTeamProposals(t, status);
            boolean userIsPlayer = userService.userTypeIsPlayer(u);

            logger.info("Viewing sponsorship for {} team", user.getTeam().getTeamName());

            return ResponseEntity.ok(
                    userIsPlayer?
                     userService.getTeamProposalsForPlayer(proposalList)
                    : userService.getTeamProposalsForManager(proposalList)
            );
        } catch (CustomException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @GetMapping("/user")
    public ResponseEntity<?> viewUsers(HttpSession session) {
        try {
            User user = (User) session.getAttribute("user");

            //user checks
            if (user == null) {
                return ResponseEntity.status(400).body("Login first");
            } else if (!user.getRole().equals("Manager")) {
                return ResponseEntity.status(400).body("You are not a manager. Only managers can view all team members");
            }

            List<User> users = this.userService.getAllUsersByTeam(user.getTeam());
            List<UserDTO> res = new ArrayList<>();
            users.forEach(u -> {
                res.add(new UserDTO(u));
            });
            logger.info("Viewing all User of {} team", user.getTeam().getTeamName());
            return ResponseEntity.ok().body(res);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody User u ){
        try{
            User user = userService.addNewUser(u);
            logger.info("Create new user with username: {}", u.getUsername());
            return ResponseEntity.ok().body(new UserDTO(user));
        } catch (CustomException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }

    }

    @PatchMapping("/role/{newRole}")
    public ResponseEntity<String> updateUserRole(@PathVariable String newRole, @RequestParam UUID player_id, HttpSession session){
        try {
            User user = (User) session.getAttribute("user");
            if (user == null) {
                return ResponseEntity.status(400).body("Login first");
            }
            else if (user.getRole().equals("Player")) {
                return ResponseEntity.status(400).body("You are not manager. Only manager can change role");
            }
            else if (user.getUserId().equals(player_id)) {
                return ResponseEntity.status(400).body("You cannot change your own role");
            }

            userService.updateRole(player_id, newRole, user.getUserId());
            String s = String.format("User with ID %s role has been updated to role %s", player_id, newRole);
            logger.info(s);
            return ResponseEntity.ok(s);
        } catch(CustomException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    //remove user from team
    @PatchMapping("/users/{userId}")
    public ResponseEntity<?> removeUser(@PathVariable UUID userId, HttpSession session) {
        try {
            User user = (User) session.getAttribute("user");

            //user checks
            if (user == null) {
                return ResponseEntity.status(400).body("Login first");
            } else if (!user.getRole().equals("Manager")) {
                return ResponseEntity.status(400).body("You are not a manager. Only managers can remove team members");
            }

            userService.removeUser(user.getTeam(), userId);
            List<User> usersInTeam = userService.getAllUsersByTeam(user.getTeam());
            List<User> managers = usersInTeam.stream().filter(user1 -> user1.getRole().equals("Manager")).toList();
            if (managers.isEmpty()) {
                this.teamService.deleteTeam(user.getTeam());
            }
            String s = String.format("User with ID %s has been removed from your team", userId);
            logger.info(s);
            return ResponseEntity.ok(s);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PatchMapping("/proposal/sponsor/{newStatus}")
    public ResponseEntity<?> acceptOrRejectSponsorProposal(@PathVariable String newStatus, @RequestParam UUID proposal_ID, HttpSession session){
        try {
            User user = (User) session.getAttribute("user");
            if (user == null) {
                return ResponseEntity.status(400).body("Login first");
            }if (user.getRole().equals("Player")) {
                return ResponseEntity.status(400).body("You are not manager. Only manager can accept or reject sponsorship proposals");
            }if (user.getTeam() == null){
                return ResponseEntity.status(400).body("Manager must be member of a team in order to accept or deny proposals");
            }
            TeamProposal proposal = this.teamProposalService.getProposalByID(proposal_ID);
            if ( proposal.getReceiverTeam() == null){
                return ResponseEntity.status(400).body("proposal must be sent to a team in order to be accepted");
            }if ( ! proposal.getReceiverTeam().equals(user.getTeam())){
                return ResponseEntity.status(400).body("Manager must be a member of the same team in order to approve said team's sponsorship offers");
            } if (! proposal.getStatus().equalsIgnoreCase("Pending")) {
                return ResponseEntity.status(400).body("Accepted/Rejected proposals cannot be edited");
            }

            System.out.println("new status: " + newStatus);
            this.teamService.changeTeamBalance(proposal.getReceiverTeam().getTeamId(), proposal.getAmount());
            this.teamProposalService.changeProposalStatus(proposal, newStatus);
            logger.info("{} team just {} {}'s proposal", user.getTeam().getTeamName(), newStatus, proposal.getSenderSponsor().getName());
            return ResponseEntity.ok(new TeamProposalDTO(proposal));

        } catch (CustomException e){
            return ResponseEntity.status(400).body(e.getMessage());
        } catch (Exception ex){
            return ResponseEntity.status(400).body(ex.getMessage());
        }
    }
}
