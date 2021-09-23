# paystation--tdd-02-hardison-rodriguez
paystation--tdd-02-hardison-rodriguez created by GitHub Classroom

#### UML

![image](https://user-images.githubusercontent.com/56310133/134098703-362689e7-3087-4219-a6ac-88b377a254c5.png)
#### Requirements
---
Our goal for this assignment was to create a main class that based as an interface for the PayStation. The PayStation interface would allow a user to select from a menu what they wanted to to. These included Deposit Coins, Display screen, Buy Ticket, Cancel, Empty (Admin) Change Rate Strategy (Admin), Change Time (Admin), and Exit. Changing rate strategy would change the hourly rates for a variety of different towns whether it was linear 1, linear 2, prgroessive rates, and alternating rates.

### Testing
---
Testing was done in a very similar way to the first lab where it was automated unit testing. There are prints in our code but those are more for sanity checks due to the weirdness of how the Java Calendar object works, namely that we were unable to get the calendars to line up with the GMT time zone to ease our calculations. As a result, our testing has some workarounds for that chief among them is the addition of 12 hours to any desired time to get the expected time. For example if we desire to test at 4 PM (16:00), we have to do 16+12 = 28 for the hour. We tried to TDD to the best we could so we didn't have to change the program much at all to make it compatible with tests, they were made in parallel. While doing testing I uncovered a bug where a switch statement would break completely and give us garbage output if something unexpected happen, I had to refactor it completely to correctly step through cases and default to an error if something goes wrong. Tests were mostly written before code was put down and were also mostly written by the person implementing RateStrategy.

### Team
---
#### Erik A. Rodriguez

Implemented rateStrategy interface and its corresponding implementation (RateStrategyImpl) and test suite (RateStrategyImplTest). Utilized code from lab slides as basis for some calculations like weekend/weekday rollover and used the given calendar object initializations. These were significantly modified to work with our implementation. Logic for rollover was made with guidance from the comments included in the slides and some re-used code from the slides although in the end my implementation is heavily altered. The rollover logic was probably the most challenging part of this project because it required a lot of thinking about how these moving parts to come together.

TDD proved invaluable as it was how I drove my development, I tried to do commits where I refactored code to get one test working but added a new test that did not pass at the same time. In this way it was like climbing a ladder one rung at a time with the next one in sight. Tests were come up with on the fly with input from Ryan to help clear my blind spots. As it is right now, if we had more time I would separate out most of the methods in the implementation into their own objects but to be completely honest I forgot how to do that as I haven't used Java in years and I was running into trouble figuring out how to scope the objects correctly. I would have also seen if I could separate out more of the rollover logic out of the gammatown and omegatown methods, but I couldn't figure out how to get it to be re-usable in time. I think if enough time remained to do those 2 things this project would have turned out perfect.

#### Ryan Hardison

Implemented the main class and all of the functionality within such as the user interface and their methods as well as all admin features such as changing time, emptying paystation and changing city for rate strategy. All methods used within the main were written by me (Ryan) as well as the README file. Some methods such as changing time and ratestrategy were also written by me and are located within the PayStationImpl class. Some bugs we came across that required a work around is that the rates were set as linear by default in the addPayment method in PayStationImpl and we had to relocate the rateStrategy method from main into PayStationImpl to accomodate for this. Change time was added simply for ease of testing and did not take much added work.
