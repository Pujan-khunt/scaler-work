# Introduction to Design Patterns (Class 7)

## Simple Factory Design Pattern

Imagine a Game where there is a player at the bottom of the screen, which can only move horizontally.
The player needs to dodge the incoming stones from the top of the screen. The stones are going to fly
from the top to the bottom of the screen.

There are 3 types of stones:
1. Large
2. Medium
3. Small

The client's code currently looks like the following:
```java
while(true) {
    Stone smallStone = new SmallStone();
    attackPlayer(smallStone);
    Stone mediumStone = new MediumStone();
    attackPlayer(mediumStone);
    Stone largeStone = new LargeStone();
    attackPlayer(largeStone);
}
```

Drawbacks of this method:
- **Tight Coupling**: Your client code is tightly coupled to all of the concrete
stone classes.
- **Maintainence Issue**: If later its decided that all stones will be created from
an initial set of coordinates (`SmallStone(initialX, initialY)`), then the client's
code will break.
- **Violation of SRP**: The client's code which is responsible for the game loop
shoudln't handle object creation, it needs to be segregated.

### Simple Factory Implementation
```java
class StoneFactory {
    // Made static so client doesn't need to create the object of factory to use the method.
    static createStone(String type) {
        if (type == "small") {
            return new SmallStone();
        }
        else if (type == "medium") {
            return new MediumStone();
        }
        else if (type == "large") {
            return new LargeStone();
        }
    }
}

// Client side code
Stone stone = StoneFactory.createStone(type)
attackPlayer(stone)
```

Now if you want to introduce a new type of stone like `FlamingStone`
then the client doesn't need to change the game loop, they only need
to change the `type` variable.

It might seem that this `if-else` pattern in the `StoneFactory` class
violates SRP. It does not. The only responsibility of this `createStone()`
method is creation of objects, which will get changed on adding/removing
stone types.

## Factory Method Design Pattern

Now the requirements change. Instead of just generating a stone from `type`
the client now wants different types of algorithms/strategies for generating stones.
Strategies could be random generation or uniform generation etc.

One way to achieve this by making the factory class abstract with an abstract
generate method and have other implementation classes like `UniformStoneSpawner` or
`RandomStoneSpawner` extend the `StoneSpawner` class with their custom implementations.

To implement this we need a way such that the user provides which stone spawner they
would like to use, but without creating an object of that spawner.

To achieve this, we can make use of the simple factory design pattern. Now the concrete
spawner classes become the objects that are going to be generated based on a string.

```java
// Simple factory which generates objects of stone spawners
class StoneSpawnerFactory {
    static StoneSpawner createStoneSpawner(String type) {
        if (type == "Uniform") {
            return new UniformStoneSpawner();
        }
        else if (type == "Random") {
            return new RandomStoneSpawner();
        }
        else if (type == "OnlyLarge") {
            return new OnlyLargeStoneSpawner();
        }
    }
}

interface StoneSpawner {
    Stone generate();
}

class UniformStoneSpawner implements StoneSpawner {
    private int count = 0;

    @Override
    Stone generate() {
        if (count == 0) {
            return new SmallStone();
        }
        else if(count == 1) {
            return new MediumStone();
        }
        else if (count == 2) {
            return new LargeStone();
        }

        count = (count + 1) % 3;
    }
}

class RandomStoneSpawner implements StoneSpawner {
    private final Random random = new Random();

    @Override
    Stone generate() {
        int chance = random.nextInt(100);

        if (chance <= 33) return new SmallStone();
        else if (chance <= 66) return new MediumStone();
        else return new LargeStone();
    }
}

class OnlyLargeStoneSpawner extends StoneSpawner {
    @Override
    Stone generate() {
        return new LargeStone();
    }
}
```

### Client Side Code
```java
StoneSpawner factory = StoneSpawnerFactory.createStoneSpawner("Uniform")
factory.generate()
```
