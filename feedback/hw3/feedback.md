### Total Score: 24/25

### Answers Score: 14/15
- Fibonacci: 5/5
- Ball: 1/1
- BallContainer: 4/4
- Box: 4/5

* Failing to note the advantages of one of the approaches (-1)

The second way is better when the client calls getBallsFromSmallest often, since
repeat calls will be more efficient.

### Style Score: 10/10

RandomHello:

* Not passing the length of the array of greetings into Random.nextInt (-0)

When selecting a greeting in `RandomHello`, the best style would use the length
of the array to specify the maximum value for the random integer generation:
```
String nextGreeting = greetings[rand.nextInt(greetings.length)];
```
Notice how this benefits us later on if we wanted to change the number of
possible greetings in the array.

Ball Comparator:

A more neat way to write ball comparison is to use `Double.compare`.  Check out
the Java documentation of these methods to see how you might use them. (-0)
