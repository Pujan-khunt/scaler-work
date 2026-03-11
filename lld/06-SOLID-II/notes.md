# SOLID - II (Class 6)

## Immutable Behavior
The challenge is to design a class which is immutable once created.

### V0 Design
- Make all variables `private` and `final`.
- Remove all setters.
- Create deep copy of objects inside constructor and getters.

```java
class Immutable {
    // Make fields private and final to prevent access and modification internally
    private final double A;
    private final String B;
    private final List<Integer> C;

    Immutable(double A, String B, List<Integer> C) {
        this.A = A;
        this.B = B;
        // We need to store the deep copy, since the client will
        // have access to the reference passed.
        this.C = C.clone();
    }

    public double getA() {
        return this.A;
    }

    public String getB() {
        return this.B;
    }

    // returning a deep copy ensures the client won't be able
    // to modify the list after retrieving it.
    public List<Integer> getC() {
        return this.C.clone();
    }
}
```

**Limitation**: If we want the fields like `List` to be internally immutable as well, then we need
to take help from the internal implementation of the `List` class 

### V1 Design (Specifically for making lists internally immutable as well)
```java
class Immutable {
    // Make fields private and final to prevent access and modification internally
    private final double A;
    private final String B;
    private final List<Integer> C;

    Immutable(double A, String B, List<Integer> C) {
        this.A = A;
        this.B = B;
        // Copying the mutable list into a new immutable list
        this.C = List.copyOf(C);
    }

    public double getA() {
        return this.A;
    }

    public String getB() {
        return this.B;
    }

    // returning an immutable copy.
    public List<Integer> getC() {
        return C;
    }
}
```

**Limitation**: The class can be extended and the getters can be overriden to
return some other value than they are intended to, breaking the immutable behavior.

### V2 Design
This design ensures that the immutable class cannot be extended, by making the class `final`.

Finally, the class is now immutable. (technically, reflection API can break anything and everything)

```java
// Ensure class cannot be extended by sub-classes.
final class Immutable {
    // Make fields private and final to prevent access and modification internally
    private final double A;
    private final String B;
    private final List<Integer> C;

    Immutable(double A, String B, List<Integer> C) {
        this.A = A;
        this.B = B;
        // We need to store the deep copy, since the client will
        // have access to the reference passed.
        this.C = C.clone();
    }

    public double getA() {
        return this.A;
    }

    public String getB() {
        return this.B;
    }

    // returning a deep copy ensures the client won't be able
    // to modify the list after retrieving it.
    public List<Integer> getC() {
        return this.C.clone();
    }
}
```

## Easier Creation Of Objects Of Immutable Class (Builder Pattern)
The problem is that if there are 10 or more parameters in the Immutable class' constructor
then the client would have to remember the ordering for all of them.

We can create a separate mutable class which will have the same variables (not methods).
The client can create an instance of this class and pass it to the constructor of the 
immutable class to create the final immutable instance.

The problem now lies in maintaining both the classes with same fields, also if the client
needs to know about this new random class that our logic needs, it breaks abstraction.

We can place this new class inside the immutable class which will make it easier to maintain
the fields of both the classes. We need to also make it static so that its accessible without
the object of its parent immutable class.

If we call this new class X, then the client side code would look like the following:
```java
Immutable.X intermediateObj = new Immutable.X();
Immutable immutableObj = new Immutable(intermediateObj);
```

Since we know we would have to carry out this procedure every time we want to create an instance
we can have a method inside this new class itself which returns an instance of the parent immutable
class.

If we call the method in new class returning instance of the parent immutable class 'f', then the
client side code would look like the following:
```java
Immutable immutableObj = new Immutable.X.f();
```

Now the way we solve the original problem of remembering the order is by creating chainable setters
inside the new class 'X'. We make them chainable by having them return the new class 'X'.

Finally, by doing all of this we get:
```java
class Immutable {
    private final double A;
    private final String B;
    private final List<Integer> C;

    public static class Builder {
        // The fields should be exactly the same as the ones outside
        // this class.
        public double A;
        public String B;
        public List<Integer> C;

        // Chainable setters all return the Builder class
        public Builder setA(double A) {
            this.A = A;
        }

        public Builder setB(String B) {
            this.B = B;
        }

        public Builder setC(List<Integer> C) {
            this.C = C;
        }

        // This is the final method that the client calls to get the
        // final immutable object
        public Immutable build() {
            return new Immutable(this);
        }
    }

    // The constructor accepts the builder class which can be modified
    // by the client however they see fit, until the build method is called
    Immutable(Builder b) {
        this.A = b.A;
        this.B = b.B;
        this.C = List.of(b.C);
    }

    public double getA() {
        return this.A;
    }

    public String getB() {
        return this.B;
    }

    // returning a deep copy ensures the client won't be able
    // to modify the list after retrieving it.
    public List<Integer> getC() {
        return this.C.clone();
    }
}
```
