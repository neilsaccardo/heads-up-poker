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



