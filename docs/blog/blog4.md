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
