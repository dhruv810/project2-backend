Project2 Backend

Entities

Class User

id: UUID
username: String
password: String
role: String
team: Team.id :UUID

Class Team

id: UUID
name: String
sponsor: Array of Sponsor.id UUID

Class Sponsor

id: UUID
category: String
name: String
budget: Double

Class proposals

id: UUID
Type: String (PlayerInvite or SponsorProposal)
Status: String (PENDING/ACCEPTED/REJECTED)
sender: Team.id if PlayerInvite else Sponsor.id


User Stories

url = POST : "/auth/create"
body = {
    username: 
    password:
    name:
    role:
}

Create user
---------------------------------
url = POST : "/auth/login"
body = {
    username:
    password:
}

login user
---------------------------------
url = patch : "/role"
path parameter = new role

change role
---------------------------------
url = get : "/sponsors"

view sponsors
---------------------------------
Accept/Reject team invite

User/ Manager Stories

Accept/Reject Sponsor proposals
Send team invite to Player
Remove team player
See Total Investment

Sponsor Stories

create
login
Send proposal
Change budget
See remaining balance

---------------------------------
Admin user

create player/teams/sponsors