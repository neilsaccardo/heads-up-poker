# Blog: Heads Up Computer Poker

**Neil Saccardo**

## Blog Entry - 12/02/17

I have been working on the UI and server set up of the web app in the last few days. Im using the AngularJS framework for the UI, and using an ExpressJS server to host the app.
I have been working on creating components for the UI, in order to modulalarise my code. I have the following links about different aspects of AngularJS particularly helpful
 - [Angular Components] (https://docs.angularjs.org/guide/component) 
 - [Services/Factories/Providers] (https://docs.angularjs.org/guide/services). [This also] (http://stackoverflow.com/questions/15666048/angularjs-service-vs-provider-vs-factory).
 - [Data Binding] (http://www.w3schools.com/angular/angular_databinding.asp).
 
I have also been working on the server set up. I plan on using [socket io] (http://socket.io/) to communicate between the client browser and the ExpressJS web server. I then plan on using sockets to communicate between the Java Server where the poker ai agent will be running. The reason I have chosen to do it like this, is in order to help seperate the AI from the web app, so they are not dependent on each other. This work can be seen in the branch WIP-Express_Server_and_Sockets.

I am now working on logic on the client to set up a game of poker and hope to finish this off in the next couple of days. I also hope to use Karma to unit test and protractor to run end to end tests.



## Second Blog Entry - 09/02/17

Recently I have been working on processing the data from the University of Alberta computer research group. I have created classes to both automatically pull and unpack the the data (the data is stored in .tar.gz files).
I have also created classes to process the data and get the two player games from the data. I did this by using several classes to read across multiple relevant files in order to pull the data needed into one game record.

Further on from this, I still need to write tests for these classes - I plan to write unit tests using Junit and also some component tests.


## First Blog Entry
The last few weeks I have spent working on other course work as I put in a good bit of research nearer to the start of the year on this project.
I have decided to implement the poker AI using a neural network. I'll also be working with some of the sample data provided by the University of Alberta computer poker research group. Information on this data can be found [here] (http://poker.cs.ualberta.ca/irc_poker_database.html).
The data will be used for clustering, specifically to cluster types of player together. I'll be concentrating on two main attributes in a players playing type/style - their tightness/looseness and passiveness/aggressiveness. Definitions on these terms can be found [here] (http://www.pokerology.com/lessons/poker-playing-styles/).


Recently though, I have spent time recently working on my functional specification, which can found in this git repository. 
It helped me identify and focus on the functional requirements of this project, specifically focussing on the end project. It also gave me a good idea of what I should be researching and looking into now for the time being. I plan to start development of this project in Week '13', after all other assignments for this semester have been completed and submitted.
