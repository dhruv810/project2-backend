# Project2 Backend

Entities

Class User

id: UUID
username: String
password: String
name: String
role: String
team: Team.id :UUID

Class Team

id: UUID
name: String
sponsor: Array of Sponsor.id UUID

Class Sponsor

id: UUID
username: String
password: String
category: String
name: String
budget: Double

Class proposals

id: UUID
Type: String (PlayerInvite or SponsorProposal)
Status: String (PENDING/ACCEPTED/REJECTED)
sender: Team.id if PlayerInvite else Sponsor.id


# User Stories

---------------------------------
Create user

```url = POST : "/auth/create"```
```
body = {
    username: String
    password: String
    name: String
    role: String
}
```
---------------------------------
login user

```url = POST : "/auth/login"```
```
body = {
    username:
    password:
}
```
---------------------------------
change role

```url = PATCH : "/role"```
```
path parameter = new role
```
---------------------------------
view sponsors

```url = GET : "/sponsors"```

---------------------------------
Accept/Reject team invite

```url = PATCH : "/proposal/team" + "ACCEPT" 0r "REJECT"```

---------------------------------

# User/ Manager Stories

---------------------------------
Accept/Reject Sponsor proposals

```url = PATCH : "/proposal/sponsor" + "ACCEPT" 0r "REJECT"```

---------------------------------
Send team invite to Player

```url = POST : "/team/proposal"```
```
body = {
    sender = UUID 
}
```
---------------------------------
Remove team player

```url = PATCH : "/team/player/" + {playerId}```

---------------------------------
See Total Investment

```url = GET : "/investment"```

---------------------------------

# Sponsor Stories

---------------------------------
create

```url = POST : "/sponsor"```
```
body = {
    username: String
    password: String
    category: String
    name: String
    budget: Double
}
```

---------------------------------
login

```url = POST: /sponsor/login```
```
body = {
    username: String
    password: String
}
```
---------------------------------
Send proposal

```url = POST : "/sponsor/proposal"```
```
body = {
    sender = UUID 
}
```
---------------------------------
Change budget

```url = PATCH : "/sponsor/budget/" + {new budget}```

---------------------------------
See remaining balance

```url = GET : "/sponsor/balance"```

---------------------------------
See all sponsored team

```url = GET : "/sponsor/teams"```

---------------------------------
Remove sponsored team

```url = PATCH : "/sponsor/" + {teamId}```

---------------------------------

# Team Stories

---------------------------------
Create team

```url = POST : "/team"```
```
body = {
    name: String
}
```
---------------------------------
Edit team name

```url = PATCH : "/team/name/" +  "{newTeamName}"```

---------------------------------




Admin user

create player/teams/sponsors