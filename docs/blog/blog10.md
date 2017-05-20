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