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

> Most Important Interview Question: What is the difference between Dependency Inversion, Dependency Injection and Inversion of Control?

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
**Answer**: Through a `static` getter method. The method should be `static` since you can't access a non-static method without an instance.

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

**Limitation**: The drawback of eager loading is memory wastage. Since the object
is created even before the `getInstance()` method is called, it will consume memory
even when not needed.
Even this design is susceptible to serialization attack and Java Reflection API.

### V6 Design
Since the Reflection API can make private fields accessible, it can directly access 
the constructor of the Singleton class. To prevent this we can add a check inside
the constructor to check if the instance has already been initialized. 
This will work with eager loading as well as lazy loading with double checked locking.

#### Eager Loading
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

#### Lazy Loading
```java
class Singleton {
    // This variable is supposed to contain the one and only instance of this class.
    // This also needs to be static, since it will be used in the static getInstance method.
    // Make it private to prevent modification.
    // volatile ensures its not cached locally in a thread.
    private volatile static Singleton instance;

    // Don't allow client access to the instance creator machine.
    // This can be achieved by privatizing the constructor.
    private Singleton() {
        if (instance != null) {
            throw new RuntimeException("use getInstance()");
        }
    }

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

**Limitation**: Eager loading will fail due to serialization attack and lazy loading will fail, when the reflection
API calls the private constructor before calling the `getInstance()` method first, since the constructor would provide
it the first instance, but it wouldn't assign the `instance` variable to that value and hence when later the `getInstance()`
method is used, it would check that the value of the `instance` variable is null and create another instance.
Lazy loading will also fail in serialization attack.

### V7 Design 

#### Serialization Attack
To understand the V7 design, we first need to understand how does a serialization attack work?

The serialization attack essentially serializes and then deserializes the object immediately. In that process
of deserialization, JVM doesn't call your `getInstance()` method nor does it call the private constructor of your
singleton class, it rather allocates a fresh block of memory and puts the complete serialized object onto that 
memory block. The freshly created memory address will obviously be different than where our `instance` variable
lives.

#### Verify Serialization Attack Breaks Singleton Behavior
```java
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        // path of the file containing serialized singleton instance.
        String filepath = "singleton.serialized";

        // Get singleton instance through valid method.
        Singleton instanceOne = Singleton.getInstance();

        // Serialize the singleton instance onto a file.
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filepath));
        out.writeObject(instanceOne);
        out.close();

        // Deserialize the singleton instance from the same file
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(filepath));
        // Here, in.readObject() will allocate memory for an Object class which will contain the exact
        // same data as our singleton instance, and then will be casted to the appropriate Singleton class.
        // Now since this new object has been allocated in a completely separate block of memory, it would be 
        // considered a separate instance, which can be verfied by comparing the hashcodes.
        Singleton instanceTwo = (Singleton) in.readObject();
        in.close();

        // Should print 'false' signifying that both objects are indeed different, proving violation to singleton behavior.
        System.out.println(instanceTwo.hashCode() == instanceOne.hashCode());
    }
}

class Singleton implements Serializable {
    private volatile static Singleton instance;

    private Singleton() {
        if (instance != null) {
            throw new RuntimeException("use getInstance()");
        }
    }

    public static Singleton getInstance() {
        if (instance == null) {
            synchronized (Singleton.class) {
                if (instance == null) {
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }
}
```

Now, the question lies in how to protect the method against a serialization attack?

JVM looks for a certain method definition inside your class (`protected Object readResolve()`) in the deserialization phase. 
If finds it, then it will execute it before returning the object to your application.

#### Eager Loading
```java
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        // path of the file containing serialized singleton instance.
        String filepath = "singleton.serialized";

        // Get singleton instance through valid method.
        Singleton instanceOne = Singleton.getInstance();

        // Serialize the singleton instance onto a file.
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filepath));
        out.writeObject(instanceOne);
        out.close();

        // Deserialize the singleton instance from the same file
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(filepath));
        // Here, in.readObject() will allocate memory for an Object class which will contain the exact
        // same data as our singleton instance, and then will be casted to the appropriate Singleton class.
        // Now since this new object has been allocated in a completely separate block of memory, it would be 
        // considered a separate instance, which can be verfied by comparing the hashcodes.
        Singleton instanceTwo = (Singleton) in.readObject();
        in.close();

        // Should print 'false' signifying that both objects are indeed different, proving violation to singleton behavior.
        System.out.println(instanceTwo.hashCode() == instanceOne.hashCode());
    }
}

class Singleton {
    private static Singleton INSTANCE = new Singleton();

    private Singleton() {
        if (instance != null) {
            throw new RuntimeException("use getInstance()");
        }
    }

    public static Singleton getInstance() {
        return INSTANCE;
    }

    protected Object readResolve() {
        return instance;
    }
}
```

#### Lazy Loading
```java
class Singleton {
    private volatile static Singleton instance;

    private Singleton() {
        if (instance != null) {
            throw new RuntimeException("use getInstance()");
        }
    }

    public static Singleton getInstance() {
        if (instance == null) {
            synchronized (Singleton.class) {
                if (instance == null) {
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }

    protected Object readResolve() {
        return instance;
    }
}
```

**Limitation**: Now the design is immune to serialization attacks, eager loading is truly a bulletproof singleton.
But as discussed previously, the lazy loading solution due to the Reflection API is not truly bulletproof.

One thought that came into my mind, was that can't we always throw an error out of the constructor so it becomes 
completely inaccessible through the reflection API?

This wouldn't work, because we also need to use the constructor inside the `getInstance()` method, which needs
a valid constructor.

Another approach that one can think of is to use a variable to track whether the instance has already been created
by us and based on that we can throw an exception from the constructor. It might seem that it works, but unfortunately it doesn't.
```java
private static boolean isCreated = false;

private Singleton() {
    if (isCreated) {
        throw new RuntimeException("Already created!");
    }
    isCreated = true;
}
```

We underestimate the power of the Reflection API. We can use the reflection API to alter the value of the `isCreated` variable
and control the logic inside the constructor to violate singleton behavior.

### V8 Design

#### Eager Loading
Enums are the solution. They work because now instead of writing code to protect the singleton behavior, in case of
enums this protection is guaranteed by the JVM's internal architecture itself.

Enum under the hood is a standard Java class which is extended using `java.lang.Enum`.

Hence the enum which looks like the following:
```java
public enum Singleton { INSTANCE; }
```

gets translated by the compiler as:
```java
public final class Singleton extends java.lang.Enum<Singleton> {
    public static final Singleton INSTANCE = new Singleton("INSTANCE", 0);

    // Private constructor
    private Singleton(/*...*/) {/*...*/}
}
```

So the enum method also internally uses `eager loading` but it simplifies the code drastically.
Hence both enum method and the previously mentioned one are both equivalent in terms of preserving 
singleton behavior.

When using enums which internally a class also have a constructor. The Java Reflection API cannot access this
constructor, since the code inside the `java.lang.reflect.Constructor` doesn't allow to create an object of 
an enum class.

The following is the source code for `java.lang.reflect.Constructor` proving the same.
```java
/**
 * Uses the constructor represented by this {@code Constructor} object to
 * create and initialize a new instance of the constructor's
 * declaring class, with the specified initialization parameters.
 * Individual parameters are automatically unwrapped to match
 * primitive formal parameters, and both primitive and reference
 * parameters are subject to method invocation conversions as necessary.
 *
 * <p>If the number of formal parameters required by the underlying constructor
 * is 0, the supplied {@code initargs} array may be of length 0 or null.
 *
 * <p>If the constructor's declaring class is an inner class in a
 * non-static context, the first argument to the constructor needs
 * to be the enclosing instance; see section {@jls 15.9.3} of
 * <cite>The Java Language Specification</cite>.
 *
 * <p>If the required access and argument checks succeed and the
 * instantiation will proceed, the constructor's declaring class
 * is initialized if it has not already been initialized.
 *
 * <p>If the constructor completes normally, returns the newly
 * created and initialized instance.
 *
 * @param initargs array of objects to be passed as arguments to
 * the constructor call; values of primitive types are wrapped in
 * a wrapper object of the appropriate type (e.g. a {@code float}
 * in a {@link java.lang.Float Float})
 *
 * @return a new object created by calling the constructor
 * this object represents
 *
 * @throws    IllegalAccessException    if this {@code Constructor} object
 *              is enforcing Java language access control and the underlying
 *              constructor is inaccessible.
 * @throws    IllegalArgumentException  if the number of actual
 *              and formal parameters differ; if an unwrapping
 *              conversion for primitive arguments fails; or if,
 *              after possible unwrapping, a parameter value
 *              cannot be converted to the corresponding formal
 *              parameter type by a method invocation conversion; if
 *              this constructor pertains to an enum class.
 * @throws    InstantiationException    if the class that declares the
 *              underlying constructor represents an abstract class.
 * @throws    InvocationTargetException if the underlying constructor
 *              throws an exception.
 * @throws    ExceptionInInitializerError if the initialization provoked
 *              by this method fails.
 */
@CallerSensitive
@ForceInline // to ensure Reflection.getCallerClass optimization
public T newInstance(Object ... initargs)
    throws InstantiationException, IllegalAccessException,
           IllegalArgumentException, InvocationTargetException
{
    Class<?> caller = override ? null : Reflection.getCallerClass();
    return newInstanceWithCaller(initargs, !override, caller);
}

/* package-private */
T newInstanceWithCaller(Object[] args, boolean checkAccess, Class<?> caller)
    throws InstantiationException, IllegalAccessException,
           InvocationTargetException
{
    if (checkAccess)
        checkAccess(caller, clazz, clazz, modifiers);

    ConstructorAccessor ca = constructorAccessor;   // read @Stable
    if (ca == null) {
        ca = acquireConstructorAccessor();
    }
    @SuppressWarnings("unchecked")
    T inst = (T) ca.newInstance(args);
    return inst;
}

// NOTE that there is no synchronization used here. It is correct
// (though not efficient) to generate more than one
// ConstructorAccessor for a given Constructor. However, avoiding
// synchronization will probably make the implementation more
// scalable.
private ConstructorAccessor acquireConstructorAccessor() {

    // First check to see if one has been created yet, and take it
    // if so.
    Constructor<?> root = this.root;
    ConstructorAccessor tmp = root == null ? null : root.getConstructorAccessor();
    if (tmp != null) {
        constructorAccessor = tmp;
    } else {
        // Otherwise fabricate one and propagate it up to the root
        // Ensure the declaring class is not an Enum class.
        if ((clazz.getModifiers() & Modifier.ENUM) != 0)
            throw new IllegalArgumentException("Cannot reflectively create enum objects");

        tmp = reflectionFactory.newConstructorAccessor(this);
        // set the constructor accessor only if it's not using native implementation
        if (VM.isJavaLangInvokeInited())
            setConstructorAccessor(tmp);
    }

    return tmp;
}
```

Implementation of the enum design.

```java
public enum Singleton {
    // This is the single instance. It is globally accessible and thread-safe.
    INSTANCE;

    private int connectionCount = 0;

    public String ping() {
        return "pong";
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

**Limitation**: You can't extend another base class when using enums.

Interesting [resource](https://stackoverflow.com/questions/64051855/why-enum-singleton-is-lazy) on whether enums are lazy-loaded or eager-loaded.

#### Lazy Loading (Initialization-on-demand holder idiom aka Bill Pugh Singleton)

It works by exploiting the JVM's lazy class loading using a nested `private static class`.

```java
import java.io.Serializable;

public class BillPughSingleton implements Serializable {

    private BillPughSingleton() {
        // You still need a boolean flag or a null check here to try and fight Reflection,
        // but as discussed, Reflection can bypass boolean flags.
    }

    // 1. The Nested Static Class (The Holder)
    // The JVM will NOT load this inner class when BillPughSingleton is loaded.
    private static class SingletonHelper {
        // 2. This is only executed when SingletonHelper is explicitly referenced.
        private static final BillPughSingleton INSTANCE = new BillPughSingleton();
    }

    public static BillPughSingleton getInstance() {
        // 3. This is the exact moment the JVM loads the inner class and creates the instance.
        // The JVM natively guarantees this process is 100% thread-safe.
        return SingletonHelper.INSTANCE;
    }

    // 4. Protect against Serialization attacks
    protected Object readResolve() {
        return getInstance();
    }
}
```
