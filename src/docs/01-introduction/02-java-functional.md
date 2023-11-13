# Functional Programming

Functional programming (FP) is a paradigm that treats computation as the evaluation of mathematical functions. Rather than changing state and manipulating data, FP emphasizes immutability, first-class functions, and the use of pure functions. Here's an overview of some core functional programming features:

- `First-Class and Higher-Order Functions`:   
Functions are first-class citizens, meaning they can be passed as arguments, returned from other functions, and assigned to variables.
Higher-order functions are functions that take other functions as parameters and/or return functions as results.
- `Pure Functions`:   
A function is pure if its output value is determined solely by its input values, without observable side effects. This means, for the same input, the output will always be the same.
- `Immutability`:   
Once data is created, it can't be changed. If you want to make a "change", you create a new version instead. This leads to safer and more predictable code.
- `Recursion`:   
In FP, recursion is the primary mechanism for performing repetitive tasks, in contrast to the typical iterative constructs found in imperative programming.
- `Referential Transparency`:    
An expression is called referentially transparent if it can be replaced with its value without changing the program's behavior. This closely ties with the concept of pure functions.
- `Function Composition`:    
FP emphasizes the chaining or composition of functions to produce more complex operations. This leads to modular and readable code.
- `Lazy Evaluation`:   
Evaluation of expressions is deferred until their results are actually needed. This can optimize performance and allow the creation of infinite data structures.
- `Pattern Matching`:    
A way to access the data in data structures. It's a mechanism for checking a value against a pattern.