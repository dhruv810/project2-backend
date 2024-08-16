# Project2 Backend

Entities

## Class User

User can be PLAYER or MANAGER based on role.

### Player 
- A player is a user with role "PLAYER".
- A player can be part of only one team.
- Player can ACCEPT / REJECT team invite.
- Player can join a team only if manager sends a team invite.
- Player can see all investors and total invested amount of team they are part of.
- Player can see amount they are getting paid

### Manager
- A manager is User with role as "MANAGER".
- A manager can ACCEPT / REJECT team invite.
- Manager can see amount they are getting paid
- Manager can send TEAM INVITE to a player.
- Manager can CREATE and EDIT TEAM.
- Manager can ACCEPT / REJECT sponsor proposal.
- Each team can have up to 3 managers.

```
id: UUID
username: String
password: String
name: String
role: String
team: UUID (Team.id)
amount: Double
```

## Class Team

- Team stores list of all sponsors
- Only Manager can create team and change name

[//]: # (- Manager can delete team. &#40;Deleting team will set User.team to empty string&#41;)

```
id: UUID
name: String
sponsor: Array of Sponsor.id UUID
```

## Class Sponsor

- Sponsors are brands like Nike, Adidas etc
- Sponsors can create account / login 
- Sponsor can see and set Budget.
- Sponsor can SEND proposal and REMOVE sponsored teams.
- Sponsor cam see their remaining balance.
- Sponsor can see all teams they have invested in

```
id: UUID
username: String
password: String
category: String
name: String
budget: Double
```

## Class proposal

- Proposal can be sent by either Sponsor or Manager.
- If player invite the proposal, it changes amount that player is getting paid.
- If manager accepts sponsor proposal, it deducts amounts from Sponsor's budget.

```
id: UUID
Type: String (PlayerInvite or SponsorProposal)
Status: String (PENDING/ACCEPTED/REJECTED)
sender: UUID (Team.id if PlayerInvite else Sponsor.id)
amount: Double
```

# TODO:

- [ ] add text what each end point do in plain text
- [ ] store amount invested per Sponsor in Teams

# User Stories

---
## Create user

This will create new user.

### Request:

```url = POST : "/user/create"```
```
example body = {
  "firstName": "Mikel",
  "lastName": "Frausto",
  "username": "fraustom",
  "password": "Fraustom#123",
  "role": "player",
  "salary": 300000
}

*** Note that userID, Team, and
TeamInvite attributes do not need to be
passed as params ***
```

### Response
```
example body ={
    "userId": "b065e857-9770-4c9e-bbb9-90a4d3dbd048",
    "username": "fraustom",
    "password": "Fraustom#123",
    "firstName": "Mikel",
    "lastName": "Frausto",
    "role": "player",
    "team": null,
    "salary": 300000.0
}
```

---
## Login user

This will let you log in as a User.

### Request:

```url = POST : "/auth/login"```
```
body = {
    username: String
    password: String
}
```

### Response

```
body = {
    id: UUID
    username: String
    name: String
    role: String
    team: UUID (Team.id)
    amount: Double
}
```

---
## Change role

This will allow Manager to promote a Player to Manger or demote Manager to Player.

### Request:

```url = PATCH : "/role/" + {new role}```

### Response

```
status code: 200 for sucess 
Error otherwise
body = "Sucess or Error message."
```

---
## View sponsors -- Stretch goal

This will allow user to see all sponsors for their team. Only manager will be able to see amount of sponsorship.

### Request:

```url = GET : "/sponsors"```

### Response

For Player:
```
body = [{
    id: UUID
    username: String
    category: String
    name: String
}, ...]
```
For Manager:
```
body = [{
    id: UUID
    username: String
    category: String
    name: String
    amount: Double
}, ...]

```
---
## Accept/Reject team invite

This will update team and amount if user accepts invite.

### Request:

```url = PATCH : "/proposal/team/" + "ACCEPT" 0R "REJECT"```

### Response

```
body = {
    id: UUID
    username: String
    name: String
    role: String
    team: UUID (Team.id)
    amount: Double
}
```

---

# User/ Manager Stories

---
## Accept/Reject Sponsor proposals

Accepting Sponsor proposal will add sponsor to team's list of sponsors.

### Request:

```url = PATCH : "/proposal/sponsor/" + "ACCEPT" 0R "REJECT"```

### Response

```
Body = [{ 
    id: UUID
    username: String
    category: String
    name: String
}, ...]
```

---
## Send team invite to Player

This will send a team invite to Player/Manager. Only Manager should be able to access this feature.

### Request:

```url = POST : "/team/proposal"```
```
body = {
    Type: String (default value = "PlayerInvite")
    sender: UUID 
}
```

### Response

```
status code: 200 for sucess
Error otherwise
body = "Sucess or Error message."
```

---
## Remove team player

This will remove a User from Team and return new list of Users.

### Request:

```url = PATCH : "/team/player/" + {playerId}```

### Response

```
status code: 200 for sucess
Error otherwise
body = [{
    id: UUID
    username: String
    name: String
    role: String
    team: UUID (Team.id)
    amount: Double
}, ...]
```

---
## See Total Investment

This will allow managers to see total amount that Sponsors has invested in the Team.

### Request:

```url = GET : "/investment"```

### Response

```
status code: 200 for sucess
Error otherwise
body = {
    inventment: Double
}
```

---

# Sponsor Stories

---
## Create Sponsor -- Done

This will create am account as Sponsor.

### Request:

```url = POST : "/sponsor/create"```
```
body = {
    "username": String
    "password": String
    "category": String
    "name": String
    "budget": Double
}
```

### Response

```
body = {
    "sponsorId": UUID,
    "username": String,
    "category": String,
    "name": String,
    "budget": Double
}
```

---
## Login

This will let Sponsor login to the application.

### Request:

```url = POST: /sponsor/login```
```
body = {
    username: String
    password: String
}
```

### Response

```
body = {
    id: UUID
    username: String
    category: String
    name: String
    budget: Double
}
```

---
## Send proposal

This endpoint will allow Sponsor to send proposal to teams.

### Request:

```url = POST : "/sponsor/proposal"```
```
body = {
    sender: UUID 
}
```

### Response

```
status code: 200 for sucess
Error otherwise
body = "Sucess or Error message."
```

---
## Change budget --DONE

This will change Sponsor's budget to new budget.

### Request:

```url = PATCH : "/sponsor/budget/" + {new budget}```

### Response

```
status code: 200 for sucess
Error otherwise
body = "Sucess or Error message."
```

---
## See remaining balance -- No Longer user story but default feature

This will return remaining balance of Sponsor.

### Request:

```url = GET : "/sponsor/balance"```

### Response

```
status code: 200 for sucess
Error otherwise
body = {
    balance: Double
}
```

---
## See all sponsored team

This will allow sponsor to see all teams they have invested in.

### Request:

```url = GET : "/sponsor/teams"```

### Response

```
body = [{
    name: String,
    amount, Double
}, ...]
```

---
## Remove sponsored team

This will remove team sponsored team. It should be called when logged in as Sponsor.

### Request:

```url = PATCH : "/sponsor/" + {teamId}```

### Response

```
body = [{
    name: String,
}, ...]
```

---

# Team Stories

---
## Create team

This will create a new team. It can be called when logged In as a Manager and not currently part of any team.
This will return list of Player and list of Managers.

### Request:

```url = POST : "/team"```
```
body = {
    name: String
}
```

### Response

```
body = {
    id: UUID
    name: String
    players: []
    managers: []
}
```

---
## Edit team name

This will allow Manger to change name of the team

### Request:

```url = PATCH : "/team/name/" +  "{newTeamName}"```

### Response

```
status code: 200 for sucess.
Error otherwise.
No return body.
```
