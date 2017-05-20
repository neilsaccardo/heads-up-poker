
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

