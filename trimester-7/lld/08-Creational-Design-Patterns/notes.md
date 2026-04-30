# Creational Design Patterns (Class 8)

## Proxy Design Pattern

Imagine you have a `ITextParser` interface with methods such as:
- `getWordCount()`
- `getSentenceCount()`
- `searchWord()`
- `searchRootWord()`

The concrete implementation `BookParser` takes a book (string) in its constructor and 
immediately performs heavy work:
- Parsing the entire text
- POS tagging
- Storing structures in vectors and XML trees

This constructor is very expensive.

Client1 depends on an `ITextParser`, injected via its constructor. It has 5 methods,
but only 3 of them need the parser. The other 2 don’t use it at all.

Client2 needs to use Client1. However, even if Client2 never calls the methods
that rely on the parser, the `BookParser` is still eagerly constructed during Client1’s creation.
This wastes time and resources (unnecessary eager loading). Client2 is blocked waiting for the
heavy constructor.

### Naive Solution

This solution aims to achieve _lazy loading_ which it does.

```java
class Client1 {
    private ITextParser parser;
    private final String book;

    public Client1(String book) {
        this.book = book;
    }

    public int getWordCount() {
        if (parser == null) {
            parser = new BookParser(book);
        }
        return parser.getWordCount();
    }

    // ... similar use cases with if-checks in all of them ...
}
```

lazy loading is achieved but at the cost of:
- **Violation of SRP**: Client1 now does 2 things = its own core logic + managing lifecycle of the parser.
- **Violation OCP**: Every new parser-using method must repeat the “if null then create” logic.
- **Violation DIP**: The client is tied to the BookParser constructor; no easy swap of other ITextParser implementations.
- **Duplication and clutter**: Null-check logic is scattered across all parser-using methods.
- **Encapsulation leak**: Client code is burdened with decisions about when to construct dependencies — this should not be its responsibility.

### Proxy Solution

Instead of making the client write the lazy loading logic, we create a wrapper class which which will encapsulate
the parser class and will also contain the lazy loading logic. We then make the client use this wrapper class.

This wrapper class is known as the `Proxy` class and it also implements the same interface `ITextParser` that other
subclasses do.

```java
interface ITextParser {
    int getWordCount();
    int getSentenceCount();
    boolean searchWord(String w);
    boolean searchRootWord(String w);
}

class BookParser implements ITextParser {
    public BookParser ( String book ) {
        // heavy parsing , POS tagging , XML building ...
        System.out.println( "Heavy BookParser initialized");
    }
    public int getWordCount () { /* ... */ return 1000; }
    public int getSentenceCount () { /* ... */ return 80; }
    public boolean searchWord ( String w ) { return true ; }
    public boolean searchRootWord ( String w ) { return false ; }
}

// Proxy for lazy loading
// this contains the same lazy loading logic that the client had written in the previous example.
class LazyBookParserProxy implements ITextParser {
    private final String book;
    private BookParser parser;

    public LazyBookParserProxy(String book) {
        this.book = book;
        this.parser = null;
    }

    private BookParser getInstance() {
        if (parser == null) {
            // create the object lazily.
            parser = new BookParser(book);
        }
        return parser;
    }

    // This class implements all the methods which a BookParser does, so the client can access them directly
    // these methods internally call those same methods which are implemented in the original BookParser class.
    @Override
    public int getWordCount () { return getReal().getWordCount(); }
    @Override
    public int getSentenceCount () { return getReal().getSentenceCount(); }
    @Override
    public boolean searchWord ( String w ) { return getReal().searchWord(w); }
    @Override
    public boolean searchRootWord ( String w ) { return getReal().searchRootWord(w); }
}
```

Benefits:
- This design ensures that the client doesn't have to implement this logic.
- client isn't even aware of the `LazyBookParserProxy`, the only thing client knows is `ITextParser`.

> With a separate `Proxy` class we centralize the logic (lazy loading) in one class, keep client side code simple
> and still honour the same interface. This is the essence of the Proxy pattern: A surrogate object that controls
> the access to the real object.

### Use cases of Proxy

#### Lazy Loading

Scenario: A large image that should be only loaded when displayed.

Solution: Create a proxy class that contains the lazy loading logic, contains an `Image` and implements the same
interface as the `Image` class.

#### Remote Service Call

Scenario: Connecting to a database on another machine.

Solution: Create a proxy class that contains the class responsible for connecting to the database i.e. consists
of the `connect(String connectionString)` method. Inside that class implement the logic of retry mechanisms,
handling failures etc.

### Intuition Behind Proxy Solution
Create a separate class `Proxy` which implements the same interface as the real subject that it encapsulates.
The `Proxy` class decides how to forward calls to the real subject by adding custom logic like lazy loading,
security, optimizations etc.

### Protection Proxy (Proxy used for Access Control)

```java
public interface DoorLock {
    void unlock();
}

public class PhysicalDoorLock implements DoorLock {
    @Override
    public void unlock() {
        System.out.println("Engaging hardware... The door is now unlocked.");
    }
}

public class SecureDoorLockProxy implements DoorLock {
    private PhysicalDoorLock realLock;
    private String userRole;

    public SecureDoorLockProxy(String userRole) {
        this.userRole = userRole;
    }

    @Override
    public void unlock() {
        // Access control logic happens here
        if (checkAccess()) {
            System.out.println("[Proxy] Access granted for role: '" + userRole + "'. Delegating to physical lock...");
            
            // Lazy initialization: only create the real object if access is granted
            if (realLock == null) {
                realLock = new PhysicalDoorLock();
            }
            
            realLock.unlock();
            logAccess();
        } else {
            System.out.println("[Proxy] Access denied. Role '" + userRole + "' does not have permission.");
        }
    }

    private boolean checkAccess() {
        // Only admins and trusted family members can unlock the door
        return "admin".equalsIgnoreCase(userRole) || "family_member".equalsIgnoreCase(userRole);
    }

    private void logAccess() {
        System.out.println("[Proxy] Logging unlock event for security auditing.\n");
    }
}
```
