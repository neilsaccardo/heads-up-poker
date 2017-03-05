# Blog: Heads Up Computer Poker

**Neil Saccardo**

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
