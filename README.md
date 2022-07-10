# Company Collaboration Platform
## Esprit PIDEV: Company Collaboration Platform
### Design and architectural choice :
#### I. Choice and proposal of architecture
##### 1. Logical architecture : Layered architecture

- Data Access Layer - Model /Domain Objects : This contains the mapping of actual tables to classes.<br />
- Data Access Layer - Repository : This contains simple CRUD and quering to database.<br />
- Service Layer : This is the place where you should put your complex business logic which involved Multiple unconnected (not child - parent) domain models. These will be reused in Web Controllers and Rest API Controllers.<br />
- Controllers : Controllers can be categorized in two, namely: Web Controllers, and REST Controllers. No business logic should be implemented in this layer because the same logic can be required to call in Web as well as in API level.<br />

![Logical architecture](/images/layers-architecture.jpg)

##### 2. Physical architecture : Tiers Architecture
- Presentation tier : The presentation tier is the user interface and communication layer of the application.<br />
- Application tier : The application tier, also known as the logic tier or middle tier, is the heart of the application.<br />
- Data tier : The data tier, sometimes called database tier, data access tier or back-end, is where the information processed by the application is stored and managed.

<br />

![Physical architecture](/images/tiers-architecture.png)

<br />

#### II. Functional & non functional requirements

##### 1. functional requirements :

- Module - User Management :<br />
● Registration<br />
● Registration with linkedIn account<br />
● Authentication<br />
● Profile Completion<br />
● Synchronization of LinkedIn information<br />

- Module - Event/Activity Management :<br />
● CRUD events (create event, join event, rate an event).<br />
● Accept/deny a request to join an event.<br />
● Participate in events/activities.<br />
● Rate and comment on an event / activity.<br />
● Find colleagues who share the same interests.<br />
● Filter by apartment / office.<br />
● Management of subscriptions.<br />
● Add an activity to favorites.<br />

- Module - Forum :<br />
● Professional social network.<br />
● CRUD items.<br />
● Recommend topics.<br />
● Anonymously express your emotions within of the company.<br />
● Respond to QVT (Quality of Work Life).<br />
● Share your opinion on your well-being in your business.<br />
● Discuss with the community and your colleagues.<br />
● Personalized advice.<br />

- Module - Evaluation :<br />
● Find out what your colleagues think about you.<br />
● Creation of badges.<br />
● Earn points.<br />
● Promotion based on points.<br />
● View point rankings.<br />
● Vote for an idea of badge, trophy..<br />

- Module - Notification management :<br />
● Receive notifications.<br />
● Invitation of one or more people to join an event.<br />
● Notify the participants at the end of the event to validate the results.<br />

- Module - Management of collaboration(Partners) :<br />
● CRUD Collaboration<br />
● Reservation management<br />
● Management of offers(Happy Hour —Happy days– Black Friday)<br />
● Rating<br />
● Advertising<br />

- Module - News :<br />
● View general, company-wide information.<br />
● Individual at the level of his department / service.<br />
● Publication management.<br />
● Comments management (Like / DisLike).<br />

- Module - Chat :<br />
● Instant messaging.<br />

##### 2. Non functional requirements :

- Usability: The platform should be easy to use for even a non-technical user.<br />
- Security:<br />
  * The software must remain resilient in the face of attacks.<br />
  * The behavior of the software must be correct and predictable.<br />
  * The software must be available and behave reliably even under DOS attacks.<br />
  * The software must ensure the integrity of the customer account information.<br />
- Performance: The focus should be on loading the platform as fast as possible regardless of the number of integrations and traffic.<br />
- Maintainability<br />
- Scalability<br />
##### 3. Distribution of tasks by module:

![Class diagram](/images/tasks.png)

#### III. UML data model

##### 1. Class diagram :

![Class diagram](/images/class-diagram.png)

##### 1. Use case :
