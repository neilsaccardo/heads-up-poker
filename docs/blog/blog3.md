
## Blog Entry - 12/02/17

I have been working on the UI and server set up of the web app in the last few days. Im using the AngularJS framework for the UI, and using an ExpressJS server to host the app.
I have been working on creating components for the UI, in order to modulalarise my code. I have the following links about different aspects of AngularJS particularly helpful
 - [Angular Components] (https://docs.angularjs.org/guide/component) 
 - [Services/Factories/Providers] (https://docs.angularjs.org/guide/services). [This also] (http://stackoverflow.com/questions/15666048/angularjs-service-vs-provider-vs-factory).
 - [Data Binding] (http://www.w3schools.com/angular/angular_databinding.asp).
 
I have also been working on the server set up. I plan on using [socket io] (http://socket.io/) to communicate between the client browser and the ExpressJS web server. I then plan on using sockets to communicate between the Java Server where the poker ai agent will be running. The reason I have chosen to do it like this, is in order to help seperate the AI from the web app, so they are not dependent on each other. This work can be seen in the branch WIP-Express_Server_and_Sockets.

I am now working on logic on the client to set up a game of poker and hope to finish this off in the next couple of days. I also hope to use Karma to unit test and protractor to run end to end tests.
