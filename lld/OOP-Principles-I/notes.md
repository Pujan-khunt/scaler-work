# Object Oriented Programming Principles I

## Design A Bird (SDE II Question Asked in Amazon)

## Requirements
- Bird should have certain features like fly, walk and eat

Follow Up Question From Interviewee: Can all birds fly/eat/walk based on the usecase of the design?

Expected Answer From Interviewer: Since some birds can't fly that part should be handled in the design.

## Updated Requirements
- Bird should have certain features like fly, walk and eat
- Different birds have different flying behavior and that also includes the behavior of not flying at all.

## Basic V0 Design
```java
class Bird {
    String name, type, color;
    double weight, height;
    
    // Since different types of birds have different behavior
    // We decide the behavior based on the attribute `type`
    void fly() {
        if (this.type == "Hen") {
            // fly low
        }
        else if (this.type == "Eagle") {
            // fly high
        }
    }
    
    void walk() {
        // walking behavior of a bird ...
    }
    
    void eat() {
        // eating behavior of a bird ...
    }
}
```

This is not a good design (as discussed in the previous class). The reason being that this one single method is going to have multiple types of behaviors which will make the code hard to maintain, understand and extend.

## Better V1 Design
```java
abstract class Bird {
    String name, type, color;
    double weight, height;
    
    void fly() {};
    void walk() {};
    void eat() {};
}

class Hen extends Bird {
    void fly() {
        // flying behavior of a hen ...
    }
    
    void walk() {
        // walking behavior of a hen ...
    }
    
    void eat() {
        // eating behavior of a hen ...
    }
}

class Eagle extends Bird {
    void fly() {
        // flying behavior of a eagle ...
    }
    
    void walk() {
        // walking behavior of a eagle ...
    }
    
    void eat() {
        // eating behavior of a eagle ...
    }
}
```

Instead of defining multiple behaviors in a single method it segregates the responsibilities in smaller individual classes.

By reading the requirements, we understand that any bird can't be a generic bird and will have specific attributes and methods which are unique like for a hen or an eagle, hence we can make the `Bird` method abstract.

## Problem Of Bird Not Being Able To Fly
None of these design solve the problem for birds which can't fly like a penguin or kiwi.

### Solution: Throw Exception
One such solution could be to throw an `Exception` from the fly method for the bird that can't fly.
```java
class Penguin {
   void fly() {
       throw new UnsupportedOperationException("Penguins cannot fly");
   } 
   
   void walk() {
       // walking behavior of a penguin...
   }
   
   void eat() {
       // eating behavior of a penguin...
   }
}
```

It might appear as a good solution since we are throwing an error with a clear message that "Penguins cannot fly" the client (user of our code) can understand that the bird can't fly. The user/client can then use try-catch to know whether the `Bird` can fly or not.

### Why It Shouldn't Be Used?
Throwing exceptions like this is considered an anti-pattern in Low Level Design.
The fundamental rule of error handling states that exceptions should be used for unexpected situations and not for control flow of your program.

If we provide a method like `canFly()` to the user which communicates with the client on whether a certain bird can fly or not, will still be a bad design. Since we would be breaking LSP (Liskov Substitution Principle) which states that a parent class should be replacable with any of its child class. Technically speaking it does replace it, but practically speaking it doesn't honour the implicitly created contract of "I am a bird and I can fly" for the `Bird` class that defines a `fly()` method because you need to use the `canFly()` method before using `fly()`. 

> A well designed object shouldn't have methods that it doesn't support.

How it would look client side?
```java
for (Bird bird : flock) {
    if (bird.canFly()) {
        bird.fly();
    }
}
```
> You should never force the client to manage the business logic of YOUR objects.

### Better V3 Design
The better approach is to use _Interfaces_ to define behavior of the `Bird` class instead of defining them in the base class.

A good question to think is why do need to use interface to define the behavior, why can't we use a separate class to define the behavior and then extend it?

The answer is that java doesn't allow multiple inheritance and avoids the diamond problem of inheritance.

> Thumb Rule in Inheritance: If you need to use more than 1 level of inheritance, its a bad design.

```java
interface Flyable {
    void fly();
}

interface Walkable {
    void walk();
}

interface Eatable {
    void eat();
}

abstract class Bird {
    String name, type, color;
    double weight, height;
}

class Penguin extends Bird implements Walkable, Eatable {
    // ... penguin specific attributes ...

    void walk() {
        // walking behavior of a penguin...
    }
    
    void eat() {
        // eating behavior of a penguin...
    }
    
    // ... penguin specific methods ...
}

class Eagle extends Bird implements Flyable, Walkable, Eatable {
    // ... eagle specific attributes ...
    
    void fly() {
        // flying behavior of a eagle ...
    }
    
    void walk() {
        // walking behavior of a eagle ...
    }
    
    void eat() {
        // eating behavior of a eagle ...
    }
    
    // ... eagle specific methods ...
}
```

This design will also have the problem where certain birds have exact same way of flying and we would repeat that same method in all types of birds.

The solution for this problem is the _Strategy Pattern_.
