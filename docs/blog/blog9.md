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


