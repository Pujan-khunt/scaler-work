# Object Oriented Programming Principles II (Class 4)

## Solid Principles
SOLID is a set of 5 principles, which makes the code easier to maintain, understand and extend.

### 1. Single Responsiblity Principle (SRP)
The textbook definition of SRP states that a function or class should have only 1 reason to change.

However, it is going to be hard to constraint something to a single responsiblity. The reason for that is
that is **granularity of abstraction**.

It states that the definition of "one reason" scales up depending upon the level of system you are looking into.
At the smallest level (function) the reason is a single technical action like "validate a string email" and an entire business capability at the largest level (microservices) like "handle entire lifecycle for user registration and authentication".

### 2. Open Closed Principle (OCP)
functions and classes should be open for extension and closed for modification.

It means that if you have a piece of code that is well written and tested; if a change is not expected in that part of code, that it shouldn't be modified.

### 3. Liskov Substitution Principle (LSP)
All child classes should be replacable by their parent class.

In other words, everything that is present in the child class should also be present in the parent class, only then would they become entirely replacable.

## Building an HR Management System
Requirements:
- Should support full-time employees as well as interns 
- save() method should be implemented to store the data of the employee in a file.

### V0 design
Until now the design demands all types of employees to have the exact same implementation for the save method.

```java
class Employee {
    String name;
    int age;
    String deparment;

    // Same saving behavior for all types of employee is a requirement of the design.
    void save() {
        // open file
        // convert attributes to string
        // write final string to file
        // close fie
    }
}

class FullTimeEmployee extends Employee {
    int salary;
}

class InternEmployee extends Employee {
    int stipend;
}

// There should only be one instance of this class
class EmployeeRepo {
    List<Employee> employees;
}
```

In this design the client would use the `EmployeeRepo` class to retrieve the list of all employees.

### Is This Design Bad?
As of now all types of employees have the same behavior of saving.
If the behavior of saving changes for different employees, then the design would need to change.

The main thing to notice here is that the `save()` method voilates SRP. The method is a monster method which is resopnsible for multiple things like:
- Writing to a file
- format of saving

Hence there are multiple reasons to change this method like:
- Changing the format of an employee
- Changing the file in which the data is getting saved.

### Better Design
```java
class Employee {
    String name;
    int age;
    String deparment;
}

class FullTimeEmployee extends Employee {
    int salary;
}

class InternEmployee extends Employee {
    int stipend;
}

// There should only be one instance of this class
class EmployeeRepo {
    List<Employee> employees;
    Serializer serializer;
    FileStorageService fss;

    EmployeeRepo(Serializer serializer, FileStorageService fss) {
        this.serializer = serializer;
        this.fss = fss;
    }

    void save(Employee e) {
        String str = serializer.serialize(e);
        fss.save(str);
    }
}

// Only one instance of this class is required.
class Serializer {
    public String serialize(Employee e) {
        return "serialized employee";
    }
}

// Only one instance of this class is required.
class FileStorageService {
    void save(String s) {
        // write to file
    }
}
```

We break down the save method into separate classes which are responsible solely for a single task that they were carrying it out in the save method.

There is still a problem in this design. If in future if you decide to use a SQL database service
for saving data, then you would still need to change the `save()` method, since it uses the hardcoded file storage service dependency.

### V1 Updated Requirements
- Add tax calculation functionality
- Income tax is 20% and an additional 2% professional tax.

> Should the new functionality be added to the `Employee` class?
> No. Since this would violate the SRP of the Employee class. There will be 2 reasons to change the employee class. Tax rule changes and employee design changes.

The new functionality should be in its separate class whose sole purpose is to manage tax calculation based on the current rules.

```java
class TaxCalculationUtility {
    double calculateTax(Employee e) {
        return 0.0;
    }
}
```


### V2 Updated Requirements
Full time employees pay 30% income tax + 2% professional tax.
Intern employees pay 20% income tax.

One intuitive option could be to check using the employee parameter inside the `calculateTax()` method whether the employee is full-time or employee and return the tax accordingly. This is not a good design as discussed multiple times previously.

```java
interface TaxCalculationUtil {
    double calculateTax(Employee e);
}

class FullTimeEmployeeTaxCalculator implements TaxCalculationUtil {
    @Override
    public void calculateTax(Employee e) {
        return 0.3 * e.income + 0.02 * e.income;
    }
}

class InternEmployeeTaxCalculator implements TaxCalculationUtil {
    @Override
    public void calculateTax(Employee e) {
        return 0.2 * e.income;
    }
}
```

This works, but now how does the client know which tax calculator to use?
There needs to be a mapping present somewhere on which tax calculator to use.

This can be achieved via the following design.

```java
abstract class Employee {
    String name;
    int age;
    String deparment;
    TaxCalculationUtil tcu;

    abstract double calculateTax();
}

class FullTimeEmployee extends Employee {
    int salary;

    double calculateTax() {
        tcu.calculateTax(this);
    }
}

class InternEmployee extends Employee {
    int stipend;

    double calculateTax() {
        tcu.calculateTax(this);
    }
}
```

The client would use the design in the following way:
```java
Employee e = new FullTimeEmployee(new FullTimeEmployeeTaxCalculator());
```

This offloads the problem of deciding which tax calculator to use to the client.
