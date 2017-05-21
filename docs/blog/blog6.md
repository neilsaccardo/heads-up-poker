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

![Current UI] (https://gitlab.computing.dcu.ie/saccarn2/2017-ca400-saccarn2/raw/WIP_ai/docs/blog/images/updateUI.PNG)


I've also added a simple log in screen to the front end. This helps me keep track of users playing agains the AI.

![Log in screen] (https://gitlab.computing.dcu.ie/saccarn2/2017-ca400-saccarn2/raw/WIP_ai/docs/blog/images/loginUI.PNG)

There is still some work to be done on the front end, mainly to do with augmenting the overall UX, and making it more clear precisely what action the AI has taken against them.

