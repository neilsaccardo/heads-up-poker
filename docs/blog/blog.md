# Blog: Heads Up Computer Poker

**Neil Saccardo**

Over the past few months I have been updating this file as my blog, with the oldest posts at the bottom, and the newest posts at the top.

However, its not the easiest way to read them, so I have also split each blog post into their own files just to make it a bit more convenient to read.

Links are as follows:

- [Blog 1](blog1.md)
- [Blog 2](blog2.md)
- [Blog 3](blog3.md)
- [Blog 4](blog4.md)
- [Blog 5](blog5.md)
- [Blog 6](blog6.md)
- [Blog 7](blog7.md)
- [Blog 8](blog8.md)
- [Blog 9](blog9.md)
- [Blog 10](blog10.md)
- [Blog 11](blog11.md)

## Blog 21/05/17

So over the past few days, I've been working mainly on my documentation, however I have been spending most of my time studying in the past few weeks.


I've managed to piece together and finish off 'final' version of the technical guide and user manual. This documentation took me quite a while to do, and Im happy enough with the result.


### Video
Today I have also been working on the video walkthrough. I used [OBS Studio](https://obsproject.com/) to record the screen and microphone, and used [iMovie](https://www.apple.com/lae/imovie/) on my brothers mac to piece it together and publish it. The video walkthrough includes an introduction to the project and a walkthrough on some scenarios a typical user might undertake such as:

- Logging in
- Determining what different objects are on the board (stacks and cards etc.)
- Folding
- Betting
- Calling, Checking
- Showdown scenario

As a side note, yesterday I also decided to split the blogs into their own individual files, as well as have them in the way they are presented in this file here. I think that the individual files are probably easier to read.

### Onwards
I'm now looking forward and preparing for both the Expo on Tuesday and the presentation on Wednesday!


## Blog Entry - 15/05/17

Over the past couple of weeks I have been working on the following :

- Validation (particularly of parameters)
- Evaluation
- Documentation


### Validation

I have implemented a validation module as part of the project. This module allows two AI_Agent objects play against each other. Different parameters can be specified in the two agents.

The main Harness class allows two agents to play 20,000 hands against each other. The average earnings of each players is recorded and calculated in terms of the value of the big blind. The variance is also caculated and recorded. From these figures, it can be seen which player plays 'better' (i.e wins).

This part of the project makes use of Steve Brechers hand evaluation module, which I found [here](http://www.poker-ai.org/archive/www.pokerai.org/pf3/viewtopic4bef.html?f=3&t=16). The reason I chose Brechers project is because of the overall low memory usage, compared to other hand evaluators. It is used when the two agents go to show down, and hand evaluation is needed to determine the winner.

So far I have attempted to examine and validate the following :
 - Bet/Pass values
 - Pre Flop values
 - Hand Potential in terms of Straight/Flush 
 - Common hand values
 - Default opponent model vs other opponent models.

 
The exact tests can be found and ran in the corresponding packages in the validation module. All tests that have been ran have had their results documented and will be included as an appendix to the final documentation, as an explanation to why certain values were chosen.

### Evaluation

I have also been doing some formal documented user testing. I have taken inspiration from ISO 9126-4 for this. My user testing activities measure the following:
 - The effectiveness of the system - can a user complete a task.
 - The effieciency of a the system - how quickly can a user complete a task.
 - The overall satisfaction - how comfortable does a user feel using the system.
 
I have attempted to do this by having defined tasks for a user to complete. I measure the effectiveness by whether or not they complete the tasks, and the efficiency by the time it takes to complete the tasks.

I measure the user satisfaction by having them complete a 10 question survey - [the system usability scale] (https://hell.meiert.org/core/pdf/sus.pdf).
This will all be documented as part of the test documentation of the project. 


### Documentation

I have also been working on the technical guide, user manual and test documentation. 

The test documentation so far consists of testing activities carried out as part of the project (unit tests, integration tests, acceptance tests, user evaluation testing) and a test plan. This documentation will be included as an appendix to the final documentation.
 
## Blog Entry - 16/04/17

Over the past number of days I have been working on the following:

- Implementing Mongoose to track player actions
- Improving the AI effectiveness/aggressiveness
- Some user testing

### Mongoose

I have used [Mongoose](http://mongoosejs.com/) as part of the web app to act as an interface and read and write to the MongoDB instance running. I use it mainly to track certain user statistics, all together make up an opponent model. The opponent model can then be used as an input to the AI system, rather than using one of the default opponent models obtained from clustering. I found Mongoose pretty easy to use and have attempted to adopt a micro-service like architecture with it in its design - all reading/writing function is stored in a seperate file to the main app.js file.

### Improving the AI

From my own experience and observations of playing against the AI, one thing in particular stood out to me - I found that it was easily  'bullied' off a pot, and would often fold too easily. This, in general, is not a good ploy to win a game in Heads Up poker. As such I decided to examine the code I had written which determines the action to take- in particular the function which  determines whether to fold or pass(continue playing). I actually noticed an error here in implementation of the function, which was part of the reason as to why the AI was being so 'tight' in its play. I fixed it up and noticed an immediate improvement in its play.

I have also changed the way the betting function has been implemented, with a particular focus on what amount to bet. I wanted a more aggresive AI, something that would try and bully another user off a pot, but would not make ridiculous calls that would make zero sense. Once again, when I implemented my changes I noticed an immediate change in its play. It is now much more aggresive and makes for a much more interesting game to play against. 

### User testing

Once I implemented these changes, I decided that I wanted to let a couple of my friends who play poker (online) to see how the AI would cope. I'm happy to report it actually won some games!

Against one of the two users which tested the system, who plays in a very 'tight' way, the aggressiveness of the AI allowed it to win a  majority of hands and often bluffed its own hand strength, even more so when the system used the user opponent model rather than the default model. It also often correctly predicted when to fold and not continue with the hand - I attribute this to the opponent modelling part of the system. The AI played notably well against this user and I was thrilled with its performance.

Against the user, who could be described as more loose and aggressive the AI held its own but did not manage to win any games. It did manage to bluff regularly enough but was called out on it more, as the user was more aggressive. Im still happy enough with its performance though, and hope to make improvements in order to further challenge this kind of opponent.

Overall I'm pretty satisfied with the peroformance of the AI - I was impressed by its action selection(folding at correct points), bluffing, and also on occasion its [Sandbagging] (http://www.poker-king.com/dictionary/sandbagging/) the opponent.



## Blog Entry - 09/04/17

Over the last number of days I have been working on the following things:
 - Unit tests
 - Test documentation
 - AI improvement (straight and flush potential)

### Unit tests
I spent a few days writing unit tests (using JUnit) for my main AI module. During this process, it also allowed me to fix up a few bugs that the tests help me pick up. I have been using the Intellij junit runner to run the tests with class, method and line coverage. I use this coverage as an indicator to see if there are specific tests I have missed out and I feel its proved to be a useful enough tool.

### Test Documentation
All my unit tests written in JUnit have a message associated with each one, explaining what the test should and giving an indicator of why it might fail (if it does fail).

I have also spent some time writing up and documentating test cases for the UI of the game. The pdf with this info can be found in the docs/documentation folder of this repo. Each feature of the game/gameplay has been broken down and scenarios(test cases) have been identifed within each of these. Each scenario has the following three attributes:
 - Preconditions
 - Action
 - Expected Result
 
The way I have decided to document these test cases is inspired by the [Gherkin Syntax](https://github.com/cucumber/cucumber/wiki/Given-When-Then) (i.e. Preconditions=Given, Action=When, Expected Result=Then). I found this method of documentation to be particularly clear to read and analyse.


### AI Improvement

Finally I have also been working on improving my AI, I have added a partial flush and partial straight identifier. This means that the AI can now add belief into winning the hand at show down, depending on how lucky it thinks it will be. Currently, luck is determined by a randomly generated value. I will need to evaluate the effect of this partial straight/flush identification, and see if it does indeed improve the AI's performance.


## Blog Entry - 03/04/17

Over the past week I've been fixing up defects in terms of my UI and over all user experience.

I've added a couple of components to the UI in the last few days:
 - Contributions to pot
 - Ai Action Message

The contributions to pot keeps track to what the user and the AI player have contributed so far to the pot. This helps keep it clear to the user how much is required to perform certain actions i.e. call, raise.

The AI action message also clears up what action the AI has performed. I may need to add some timeout/delays in order to smooth out the gameplay.

I've also been debugging the actual gameplay, with a particular focus on the pre flop stage, and also raising. I had some minor issues involving raising that were causing me headaches, however I have sorted this issue out. It was a simple issue to do with parsing the input as an integer rather than as a string.

I've also added some modal screens that appear when a user makes an error. Examples of these errors include:
 - Invalid username (e.g. no username).
 - Negative amount to raise or bet.
 - Amount to raise or bet greater than the users current stack.
 - Amount to bet or raise less than the minimum bet.

## Blog Entry - 27/03/17

Over the past three weeks I have spent some time further developing the following areas in my project: 
 - 1. The A.I. component
 - 2. Communication between the ExpressJS web server and the Java Server (running AI).
 - 3. Further refining U.I. and user experience of front end.

I'll discuss the work done in each part in the following sections.

### A.I. 
At the present moment, the AI can be broken down in two parts:
 - Preflop
 - Postflop (flop, turn, river)

Preflop implements a rule based system that utilises opponent modelling and EV for cards based the values found for card pairs [here](https://www.tightpoker.com/poker_hands.html).
It takes in the following inputs:
 - Hole Cards
 - Opponent Model
 - Stack Size
 - Minimum Bet
 - Position

The rules here ensure that the AI will always try to play as many hands as possible, while taking into account the opponent model and what their hand is worth taking into account their hand EV. This is important as in heads up poker, it is vital that players play as many hands as possible in order to make the AI hard to predict, and thefore trickier to play against.


For the post flop stages, I have decided to implement a bayesian decision network. In addition to the inputs the preflop part, it also takes:
 - Specific round (flop, turn, river)
 - Board Cards/Community Cards

This part involves estimating what the final pot will be / what the future contribution will be from each player depending on the action decided, and also calculate a value representing a belief in winning.

The final pot and future contributions are calculated using techniques specified in [Carlton, 2000](http://citeseerx.ist.psu.edu/viewdoc/download?doi=10.1.1.146.3280&rep=rep1&type=pdf) unpublished thesis.
The belief value takes into account two factors:
 - Current hole and board cards - Probability of winning at showdown
 - Opponent Model

All possibilities for hole and board cards are split into buckets or hand types, along with the probability of a bucket occuring. The values for these have been obtained form Carlton, 2000 thesis also (see table 2). Currently I am using 17 buckets to represent all possible hands. A belief in winning is partially calculated using this.

Belief is also partially calculated by taking into account the opponent model - it takes into account how often the opponent ususally gets to the current stage of play (i.e. a player who gets to this stage of play with a low value, then the belief in winning would be reduced also.). Further work could be done here by taking account and modelling typical betting patterns/ratios.

The best action to carry out is worked out using the final pot/future contribution and belief value. This is done in a similar way to how Carlton describes in his thesis - using a fold/pass curve and a pass/bet curve to determine an action(see sections 6.2.1 Deterministic Strategy and 6.2.2 Mixed Strategy). This introduces an element of randomness/unpredicatability into the way the AI will play against opponents. Evaluation will need to be carried out to examine the effectiveness of the AI.
 
### Server Communication

Communicating between the ExpressJS web server and java server(which runs the AI) has been achieved using Sockets.
In the NodeJS environment this has been achieved using the [net module](https://nodejs.org/api/net.html), and on the java side, this has been done using the [Socket](https://docs.oracle.com/javase/7/docs/api/java/net/Socket.html) and [ServerSocket](https://docs.oracle.com/javase/7/docs/api/java/net/ServerSocket.html) classes. Communication between the client (front end) and the ExpressJS using the [SocketIO](https://github.com/socketio/socket.io) - which is much more high level and easier to use than sockets :).


### UI 
(added 28-03-'17)

In terms of UI advancements the major change I have made is I have decided to use this [set of cards, made using HTMl5 and CSS3](https://github.com/selfthinker/CSS-Playing-Cards). I have found that they scale much nicer to different screen sizes - I also prefer how they look in general. 
I've made use of existing [Bootstrap](http://getbootstrap.com/) CSS classes and also [Angular UIBootstrap](https://angular-ui.github.io/bootstrap/) modules as part of my UI.

Here's a screen shot of what my UI currently looks like:

![Current UI] (https://gitlab.computing.dcu.ie/saccarn2/2017-ca400-saccarn2/raw/master/docs/blog/images/updateUI.PNG)


I've also added a simple log in screen to the front end. This helps me keep track of users playing agains the AI.

![Log in screen] (https://gitlab.computing.dcu.ie/saccarn2/2017-ca400-saccarn2/raw/master/docs/blog/images/loginUI.PNG)

There is still some work to be done on the front end, mainly to do with augmenting the overall UX, and making it more clear precisely what action the AI has taken against them.

## Blog Entry - 05/03/17
Over the past two weeks I have using data mining techniques to analyse the data from University of Alberta IRC poker database. I have used a clustering technique in order to cluster together types of player based on how often they raise or bet in each stage of a hand (preflop, flop, turn and river). I have used [MongoDB] (https://www.mongodb.com) to store the information for each player from IRC db files. The primary reason I used MongoDB rather a traditional relational database was that it allowed me to be flexible. If I had used relational dbs such MySQL or SQL Server I would have specify exact attributes, while MongoDB allowed me to be flexible in that regard. The other primary reason is because I inted to use Mongo to store user information when a user would play a game with my system. This is because Mongo is simple and easy to use with NodeJS.

The clustering algorithm I used cluster types of player together is a hierarchical method. The steps to the algorithm include:
 - 1. Assigning each point (a player) its own cluster
 - 2. Computing the distance between each cluster
 - 3. Merging the two closest cluster together forming their own cluster
 - 4. Using the smallest distance between the other clusters and the most recently merged cluster to compute the new distances
 - 5. Repeat steps 2-4 until a certain number of clusters determined.
 - 6. Calculate the centroid of each cluster.

The reason I used this hierachical algorithm instead of k means or other clustering algorithms was because while researching this I found that if using k means the points chosen to be the 'centroid' points affect the output of the clustering drastically. Hierarchical clustering does not suffer this particular drawback, however the main disadvantage to it is the fact it runs slower than k means - however the time taken for clustering is not too much of a concern for me (as long as its not unreasonable), hence the reason I went with hierarchical clustering method.

In terms of the actual poker AI I think I will change my approach and go with a Bayesian model instead of a neural network which I had initially planned to do.  The main reason I have decided to drop the neural network is because I do not feel the data I have would really fit for a neural network. [Source](https://www.ijarcsse.com/docs/papers/Volume_3/7_July2013/V3I7-0565.pdf).

My plan for the AI will be for it take a set of inputs including:

 - Pocket cards
 - Game Stage (State of Board/community cards)
 - Stack Size
 - Big Blind Amount/Minimum Bet 
 - Opponent model (cluster)
 
The AI will output a set an action:
- Fold
- Check
- Call
- Bet (min bet amount)
- Bet (25% of pot)
- Bet (50% of pot)
- Bet (75% of pot)
- Bet (100% of pot) 
- Raise (same options as bet)
- Bet/Raise All in

I have been reading and studying some papers on using different techniques I plan on using in my AI, some of which are listed here:
- [Bayesian decision network in poker] (http://www.csse.monash.edu.au/bai/poker/2006paper.pdf)
- [Decision Trees in poker] (http://www-personal.umich.edu/~liy/Project_Report.pdf)
- [Introdunction to bayesian networks] (http://www.cacs.louisiana.edu/~logan/BioInformatics/spring04/bayesTutorial.pdf)
- [Case based reasoning] (http://www.eecs.qmul.ac.uk/~pp305/pub/bosilj11mipro.pdf)
- [Analysis of Machline learning techniques in poker] (http://www.cs.northwestern.edu/~ddowney/courses/349_Fall2008/projects/poker/Poker.pdf)

## Blog Entry  - 14/02/17

I have continued working on the front end side of things, fixing up little bugs and making slight refactors. Here is what the UI currently looks like: 
![Early UI] (https://gitlab.computing.dcu.ie/saccarn2/2017-ca400-saccarn2/raw/master/docs/blog/images/earlyui.png)
For the time being, I am happy enough with how the UI looks although there still are some issues, the key one being the lack of an input method when wanting to bet or raise a certain amount of chips. I dont view this as particularly pressing for the moment though.

I have also decided to use a node module called [Poker-Evaluator] (https://www.npmjs.com/package/poker-evaluator) to evaluate a hand in poker, and to help determine a winner of a hand should it go down to the showdown stage.

I also have continued working on the express server set up and socket communication with it and the client. To acheive this I am using the socketio node module server side, and have wrapped the socketio events with an angular service to provide functionality client side.
I have set up some communication between them to dummy responses to each other - these responses will have no real meaning until the AI component to the project is complete. This is what I intend to focus on from now, with the UI and node stuff taking a back seat for the moment. In saying that, there are some key issues I would like to iron out with the javascript stuff:
 -  I would like to write unit tests and end to end tests as part of the final version of the UI.
 -  I would also like to clean up the controller in the main Table angular component - I feel there are far too many functions which could be abstracted into angular services or factories.

In the last few days the following links have also helped me out in developing with JavaScript:
- [When having to deal with arrays in JSON] (https://www.w3schools.com/js/js_json_arrays.asp)
- [Concatinating arrays in JavaScript] (http://www.w3schools.com/jsref/jsref_concat_array.asp)
- [Learning further about Angular Services] (https://www.w3schools.com/angular/angular_services.asp)


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
