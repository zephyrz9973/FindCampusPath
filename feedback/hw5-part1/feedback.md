### Total Score: 12/25

### Answers Score: 12/20
- Problem 1a: 3/7
Your AFs should only describe a mapping from a concrete value to an abstract representation, and not include new abstract representations after the concrete mapping has changed.
The representation invariant for `IntQueue2` ought to be:
```
entries != null && 0 <= front < entries.length && 0 <= size <= entries.length
```
- Problem 1b: 0/1
The answer is [A, D, G] [B, H] [C, F] [E]
- Problem 1c: 4/6
3. The method is private, so a client wouldn't be able to call it
5. Iterators have a remove() method that can be used to modify the underlying structure
- Problem 2: 2/3
Need to explain why you have any additional classes
- Problem 3: 3/3

### Following Directions Score: 0/5
You included some implementation details.  These may include private methods,
private inner classes, private fields, abstraction functions, and representation
invariants.


The below scores are separate:

### Documentation Score: 2/3
What are Departure and Destination?
What given name for your constructor?
Need to specify behavior of addEdge for duplicate edges


### Design Score: 0/3
Fields should be private
Edge labels are Strings, not ints
If you have a public Edge class, your addEdge should take in an Edge, not 3 Strings
You should not return the nodes and children/edges as Strings in the format needed for
the Driver - that's a specific representation that should be unique to the Driver. Rather,
you should return Collections of the data that clients can then convert to the specific
String representation they need.

### Testing Score: 1/3
Your implementation tests are incomplete. Make sure you have tests for each interesting case for each method in your classes. You are missing tests for interesting cases in your specTest like alphabetical ordering, self edges, cyclic Graphs, etc.

#### Code Review Feedback

None.
