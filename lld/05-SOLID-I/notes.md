# SOLID - I (Class 5)

## 4. Interface Segregation Principle (ISP)
Interfaces shouldn't be generic in nature, they should be very specific and thin.

If they are made generic, then any class implementing them must also implement all the methods
which might not make sense to have.

### Example NOT Following Interface Segregation Principle
```java
// The "fat" interface violating ISP
interface Worker {
    void work();
    void eat();
    void takeBreak();
}

class HumanWorker implements Worker {
    @Override
    public void work() {
        System.out.println("Working on tasks");
    }

    @Override
    public void eat() {
        System.out.println("Eating lunch");
    }

    @Override
    public void takeBreak() {
        System.out.println("Taking a short break");
    }
}

class RobotWorker implements Worker {
    @Override
    public void work() {
        System.out.println("Processing tasks continuously");
    }

    @Override
    public void eat() {
        // Violation: Robots don't eat, but the interface forces this implementation.
        throw new UnsupportedOperationException("Robots do not consume food.");
    }

    @Override
    public void takeBreak() {
        // Violation: Robots don't need breaks, but must implement the method anyway.
        throw new UnsupportedOperationException("Robots do not need breaks.");
    }
}
```

The `RobotWorker` does not need to have `eat()` or `takeBreak()` methods, yet it still does because the interface forces it.

The better pattern would be to make thin interfaces which are only responsible for a single task/behavior.

### Example Following Interface Segregation Principle
```java
// Segregated interfaces based on specific capabilities
interface Workable {
    void work();
}

interface Eatable {
    void eat();
}

interface Restable {
    void takeBreak();
}

// HumanWorker implements all the interfaces that apply to a human
class HumanWorker implements Workable, Eatable, Restable {
    @Override
    public void work() {
        System.out.println("Working on tasks");
    }

    @Override
    public void eat() {
        System.out.println("Eating lunch");
    }

    @Override
    public void takeBreak() {
        System.out.println("Taking a short break");
    }
}

// RobotWorker only implements Workable, avoiding forced, useless methods
class RobotWorker implements Workable {
    @Override
    public void work() {
        System.out.println("Processing tasks continuously");
    }
}
```

If you just follow the Single Responsiblity Principle, which wasn't being followed in the earlier case where the generic interface was responsible for defining multiple behaviors, then you would notice that all other principles will get satisfied.

## 5. Dependency Inversion Principle (DIP)
In the design of the HR management system there was a flaw. The `save()` method violated SRP.
```java
class EmployeeRepo {
    List<Employee> employees;

    void save(Employee e) {
        Serializer serializer = new Serializer(/*...*/);
        FileStorageService fss = new FileStorageService(/*...*/);
        String str = serializer.serialize(e);
        fss.save(str);
    }
}
```

Since the `save()` method creates an object of another class, whenever you need to upgrade it to a separate storage service, you would also have to update the `save()` method too.

The `save()` method should be agnostic of which type of database is being used, which is not the case here, thereby breaking the illusion of abstraction.

To create the abstraction we need to create a layer of interface.

Replace `FileStorageService` with an interface like `IDatabaseService`.
```java
interface IDatabaseService {
    void save(Employee e);
}

class FileStorageService implements IDatabaseService {
    void save(String s) {
        // open file
        // write 's' to file
        // close file
    }
}

class SQLStorageService implements IDatabaseService {
    void save(String s) {
        // open db connection
        // write 's' to db
        // close db connection
    }
}
```

You remove the line of `FileStorageService fss = new FileStorageService()` since no function should directly create the object of any concrete class, since in future if you want to update to another implementation, then you would have to update all the methods using that hardcoded dependency.

Instead use the reference of type `IDatabaseService`. But you can't have an object of an interface and hence you need an object of a concrete class which implements the interface. 
So somewhere the object must be created and it must reach the `save()` method for use.

How does it reach the `save()` method? <br/>
Answer: Dependency Injection via a constructor

```java
class EmployeeRepo {
    List<Employee> employees;
    Serializer serializer;
    IDatabaseService db;

    EmployeeRepo(Serializer serializer, IDatabaseService db) {
        this.serializer = serializer;
        this.db = db;
    }

    void save(Employee e) {
        String str = serializer.serialize(e);
        db.save(str);
    }
}
```

It is not necessary to inject the dependency via the constructor, it can also be done via directly from the `save()` method, but ideally this dependency would be used at multiple places throughout the class it is ideal via the constructor.
Now the `EmployeeRepo` has no clue on the internals of the database service, abstraction maintains.

> Most Important Interview Question: What is the difference between Dependency Inversion, Dependency Injection and Inversion of Control

This process of injecting dependencies via the constructor or setters or any other methods is known as _Dependency Injection_.

Dependency Inversion Principle states that 2 classes shouldn't be directly dependent on each other, rather they should be dependent on an abstraction.

When using DIP it often leads to this question of how to manage the creation of objects, the answer to this lies in the framework. In Java the Spring framework does the hardwork of managing the lifecycles of the objects which includes object creation. This behavior of handing the control of object management to the framework is known as _Inversion of Control_.

## `static` keyword
`static` can be applied to either a:
- variable
- function
- class (inner)

`static` variable belong to the class itself and is not tied to any instance of the class. The same belongs to when `static` is used with a function and inner class as well.

The special property of static members is that since they are not tied to any instance, they can be accesses without needing an instance, directly using the class name itself.
```java
class  TestStatic {
    static String variable = "accessible";

    static double calculate() {
        return 0.0;
    }

    static class Inner {}
}

class Main {
    public static void main() {
        System.out.println(TestStatic.variable);
        System.out.println(TestStatic.calculate());
        TestStatic.Inner inner = new TestStatic.Inner();
    }
}
```

> NOTE: Inside a `static` context, you can't access non-static members. However the opposite is true.
```java
class TestStatic {
    double nonStaticVar;

    static double test() {
        // Will throw error. non-static variable used in static context.
        return nonStaticVar
    }
}
```

## Singleton or Static?
In our HR management system design, we had classes where it made sense to only have a single instance of.
So the question is whether to only have a single instance or have the method as a static method?

There is a disadvantage in using a `static` method. Imagine you have a class which only requires a single instance like `FileStorageService` and the `save()` method is static. Now to use the `save()` method you would need to write `FileStorageService.save()`, by doing this you break the Dependency Inversion Principle, since you have hardcoded the dependency. 
You won't be able to have an abstract layer in between which supports multiple implementations.

## Achieving Singleton Behavior
Goal: We want a class to only have 1 instance and no more.

To disallow creation of more than 1 instances, we need to completely disable the functionality through which instances can be created.
That means the constructor should be hidden i.e. make the constructor private.

If the constructor is private, how would the client get an instance? <br/>
**Answer**: Through a static getter method. The method should be static since you can't access a non-static method without an instance.

### V0 design
This design ensures that at max there can be only 1 instance of this class.
```java
class Singleton {
    // This variable is supposed to contain the one and only instance of this class.
    // This also needs to be static, since it will be used in the static getInstance method.
    // Make it private to prevent modification.
    private static Singleton instance;

    // Don't allow client access to the instance creator machine.
    // This can be achieved by privatizing the constructor.
    private Singleton() {}

    // publically accessible method to retrieve the single instance.
    // if the instance doesn't exist, it will be created on-demand.
    // This method needs to be static, hence it should be available to call
    // when no instance is set.
    public static Singleton getInstance() {
        if (instance == null) {
            // Accessing constructor within the class itself and only when 
            // the instance has not yet been initialized.
            instance = new Singleton();
        }
        return instance;
    }
}
```

**Limitation**: Singleton behavior breaks in multi-threaded environment.
When the two threads call the `getInstance` method at the same time, they both
will bypass the null check and both would create an instance which would break the
intended behavior.

### V1 Design
We fix the previous limitation by making the entire method to be `synchronized`. This means
that when 2 threads call the `getInstance()` method at the same time, only 1 will be allowed
the access and the other thread will have to wait until the first thread has completed the 
function execution completely.

```java
class Singleton {
    // This variable is supposed to contain the one and only instance of this class.
    // This also needs to be static, since it will be used in the static getInstance method.
    // Make it private to prevent modification.
    private static Singleton instance;

    // Don't allow client access to the instance creator machine.
    // This can be achieved by privatizing the constructor.
    private Singleton() {}

    // publically accessible method to retrieve the single instance.
    // if the instance doesn't exist, it will be created on-demand.
    // This method needs to be static, hence it should be available to call
    // when no instance is set.
    // synchronized ensures only one thread can execute the method at a time.
    public static synchronized Singleton getInstance() {
        if (instance == null) {
            // Accessing constructor within the class itself and only when 
            // the instance has not yet been initialized.
            instance = new Singleton();
        }
        return instance;
    }
}
```
**Limitation**: Since the entire method is synchronized, it has become a single threaded function
and it will slow down the application, because of the locking overhead. Ideally you should only
lock the critical section, but here we are locking the entire function.

### V2 Design
We fix the previous limitation by synchronizing only the critical section.

```java
class Singleton {
    // This variable is supposed to contain the one and only instance of this class.
    // This also needs to be static, since it will be used in the static getInstance method.
    // Make it private to prevent modification.
    private static Singleton instance;

    // Don't allow client access to the instance creator machine.
    // This can be achieved by privatizing the constructor.
    private Singleton() {}

    // publically accessible method to retrieve the single instance.
    // if the instance doesn't exist, it will be created on-demand.
    // This method needs to be static, hence it should be available to call
    // when no instance is set.
    public static Singleton getInstance() {
        if (instance == null) {
            synchronized(Singleton.class) {
                // Accessing constructor within the class itself and only when 
                // the instance has not yet been initialized.
                instance = new Singleton();
            }
        }
        return instance;
    }
}
```

**Limitation**: This will also fail in a multi-threaded environment, since two threads trying
to get the instance at the same time would both pass the null check and then both threads will
create an instance but only one at a time, because of the synchronized section, which doesn't
preserve the singleton behavior.

### V3 Design (Double Checked Locking)
We introduce another null check inside the critical section to ensure singleton behavior is preserved.

```java
class Singleton {
    // This variable is supposed to contain the one and only instance of this class.
    // This also needs to be static, since it will be used in the static getInstance method.
    // Make it private to prevent modification.
    private static Singleton instance;

    // Don't allow client access to the instance creator machine.
    // This can be achieved by privatizing the constructor.
    private Singleton() {}

    // publically accessible method to retrieve the single instance.
    // if the instance doesn't exist, it will be created on-demand.
    // This method needs to be static, hence it should be available to call
    // when no instance is set.
    public static Singleton getInstance() {
        if (instance == null) {
            synchronized(Singleton.class) {
                if (instance == null) {
                    // Accessing constructor within the class itself and only when 
                    // the instance has not yet been initialized.
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }
}
```

**Limitation**: The singleton behavior will be violated due to the thread-local cache.
Each OS thread has its own cache for speeding up things, When one thread initializes the
value of the `instance` variable, it would NOT update the value in RAM directly, rather it 
updates its cache first and at a later point in time flushes to RAM. So when one thread 
initalizes the `instance` variable the other thread won't immediately know that an instance
has been allocated/created and would be under the assumption that no instance has yet been
created.

### V4 Design (Double Checked Locking with Lazy Loading)
To fix the previous limitation we need to make the `instance` variable `volatile`, meaning
all changes to this variable will immediately be flushed to the RAM.

```java
class Singleton {
    // This variable is supposed to contain the one and only instance of this class.
    // This also needs to be static, since it will be used in the static getInstance method.
    // Make it private to prevent modification.
    // volatile ensures its not cached locally in a thread.
    private volatile static Singleton instance;

    // Don't allow client access to the instance creator machine.
    // This can be achieved by privatizing the constructor.
    private Singleton() {}

    // publically accessible method to retrieve the single instance.
    // if the instance doesn't exist, it will be created on-demand.
    // This method needs to be static, hence it should be available to call
    // when no instance is set.
    public static Singleton getInstance() {
        if (instance == null) {
            synchronized(Singleton.class) {
                if (instance == null) {
                    // Accessing constructor within the class itself and only when 
                    // the instance has not yet been initialized.
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }
}
```
**Limitation**: Singleton behavior can be violated via Java Reflection API or via Serialization Attacks.

Proof that serialization attack breaks singletone behavior by getting 2 instances of the singleton class.
```java
    Singleton instanceOne = Singleton.getInstance();

    ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("singleton.ser"));
    out.writeObject(instanceOne);
    out.close();

    ObjectInputStream in = new ObjectInputStream(new FileInputStream("singleton.ser"));
    // When reading from input stream, JVM doesn't call getInstance or Singleton()
    // rather it assigns it a fresh block of memory and the data is forced onto it.
    Singleton instanceTwo = (Singleton) in.readObject();
    in.close();

    System.out.println("Instance 1 hash: " + instanceOne.hashCode());
    System.out.println("Instance 2 hash: " + instanceTwo.hashCode());
```

### V5 Design (Eager Loading)
This design ensures that the instance is initialized at class loading phase and 
is guaranteed to be thread-safe (guarantee provided by JVM).
```java
class Singleton {
    private static final INSTANCE = Singleton();

    private Singleton() {}

    public static Singleton getInstance() {
        return INSTANCE;
    }
}
```

**Limitation**: The only drawback of this method is memory wastage. Since the object
is created even before the `getInstance()` method is called, it will consume memory
even when not needed.

### V6 Design
Since the Reflection API can make private fields accessible, it can directly access 
the constructor of the Singleton class. To prevent this we can add a check inside
the constructor to check if the instance has already been initialized.

```java
class Singleton {
    private static final INSTANCE = Singleton();

    private Singleton() {
        if (INSTANCE != null) {
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
    }

    public static Singleton getInstance() {
        return INSTANCE;
    }
}
```

### V7 Design 
This design doesn't violate the singleton behavior against a serialization attack.

JVM looks for a certain method (`readResolve`) defined in the class in the deserialization phase. 
If it does find it, then it will execute it before returning the object to your application.

```java
class Singleton implements Serializable {
    private static final Singleton INSTANCE = new Singleton();

    private Singleton() {}

    public static Singleton getInstance() {
        return INSTANCE;
    }

    // This method is called internally by the JVM during deserialization
    protected Object readResolve() {
        // Discard the newly deserialized duplicate and return the true Singleton
        return INSTANCE;
    }
}
```

**Limitation**: Java Reflection API can break anything and everything (except Enums)

### V8 Design (Best as of now)
Since Java treats Enums differently than standard classes, the Reflection API
cannot work with enums, it will throw an exception if done so.
Also JVM internally handles the serialization protection logic as well.

```java
public enum EnumSingleton {
    // This is the single instance. It is globally accessible and thread-safe.
    INSTANCE;

    private int connectionCount = 0;

    public void doSomething() {
        System.out.println("Doing something...");
    }
    
    public int getConnectionCount() {
        return connectionCount;
    }
}

public class Main {
    public static void main() {
        // Client side usage
        EnumSingleton.INSTANCE.doSomething();
    }
}
```
