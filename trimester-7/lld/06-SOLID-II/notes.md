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

Immutability improves thread-safety since immutable objects don't change
we don't need any synchronization to overcome race conditions.

Immutable objects work well as keys in hash data structures, since their
hash value won't change because the underlying object is immutable, the
hashcodes are cached for performance.

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
    // Set default values
    private final double A = 4.2;
    private final String B = "hi";
    private final List<Integer> C = new LinkedList<>();

    public static class Builder {
        // The fields should be exactly the same as the ones outside
        // this class, along with the default values.
        public double A = 4.2;
        public String B = "hi";
        public List<Integer> C = new LinkedList<>();

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
            // Validate required fields before proceeding to create the object.
            // E.g. don't create object unless the id is null and rather
            // throw an exception.
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

### Client Side Code
```java
Immutable obj = new Immutable.Builder()
                            .setA(4.1)
                            .setB("Hello world")
                            .setC(new ArrayList<>())
                            .build()
```

### Copy-Builder using `toBuilder()`

A problem that can be faced with current builder design. Imagine client uses this pattern for creating
a server configuration which has 10+ parameters but are simplified because of the builder patter.

```java
ServerConfiguration devConfig = new ServerConfiguration.Builder()
    .host("127.0.0.1")
    .timeout(8000)
    .maxConnections(10)
    .retryCount(5)
    .sslEnabled(true)
    .port(8080)
    .build();
```

Now if the user wants to create another server configuration derived from the `devConfig`, with only change
in `port` they would have to create the entire object again, something like this:

```java
ServerConfiguration prodConfig = new ServerConfiguration.Builder()
    .host(devConfig.getHost())
    .timeout(devConfig.getTimeout())
    .maxConnections(devConfig.getMaxConnections())
    .retryCount(devConfig.getRetryCount())
    .sslEnabled(devConfig.isSslEnabled())
    .port(443) // The ONLY thing we actually wanted to change
    .build();
```

This causes maintainence issues too. If later the `devConfig` is changed, then you would also need
to update the `prodConfig` too.

The solution for this problem is very simple, give the client access to the `Builder` object used
for creating any object using the `toBuilder()` method.

```java
class Immutable {
    // Set default values
    private final double A = 4.2;
    private final String B = "hi";
    private final List<Integer> C = new LinkedList<>();

    public static class Builder {
        // The fields should be exactly the same as the ones outside
        // this class, along with the default values.
        public double A = 4.2;
        public String B = "hi";
        public List<Integer> C = new LinkedList<>();

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
            // Validate required fields before proceeding to create the object.
            // E.g. don't create object unless the id is null and rather
            // throw an exception.
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

    // Copy Builder Method
    // Creates the builder out of which the current object was created
    public Builder toBuilder() {
        return new Builder()
            .setA(this.A)
            .setB(this.B)
            .setC(this.C)
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

The client side code after the update looks like the following:

```java
// Create the initial immutable object
ServerConfiguration prodConfig = new ServerConfiguration.Builder()
    .host("api.production.com")
    .port(443)
    .sslEnabled(true)
    .build();

//  Derive a new configuration seamlessly
ServerConfiguration devConfig = prodConfig.toBuilder()
    .host("localhost") // Overwrite just the host
    .sslEnabled(false) // Overwrite just the SSL flag
    .build();          // The port (443) is safely carried over!
```

`Lombok` a Java library automatically creates this builder pattern along with the copy builder
using the `@Builder` annotation.
