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

