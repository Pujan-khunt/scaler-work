# Getting Started With Object Oriented Modelling (Class 2)

## OOP Revision

Class is a blueprint which doesn't store any data.

You create objects out of this blueprint (Class) which contain properties and methods defined in the class.

```java
class Student {
    String email;
    String name;
    String rollno;
    HashMap<Subject, Grade> grades;
    double cgr;
}
```

To create a new object from a class, you use the `new` keyword.

```java
Student s = new Student();
```

Here the `Student()` is a call to a function, a special type of a function which is known as the _constructor_ of a class.

`new Student()` allocates memory in RAM (or more specifically the _heap memory_ of the process). You need a way to manipulate that memory when needed, hence you need the address where it is located.

That address is stored in the variable `s` which is called a _reference_ that lives in the _stack memory_ of the process.

You can also just create a reference without it pointing to any value in the RAM.
`s2 = s` will make the reference `s2` point to the location where `s1` is pointing.
Hence any modification in either of the references will update the memory in RAM for the student object.

```java
Student s2;
s2 = s
```

You can use the `.`(dot) operator to access the methods and properties defined in the class `Student` for any object of the `Student` class.

To create a student with customized defaults, you use a parameterized constructor by overriding the default constructor.

```java
// This constructor will be inside the `Student` class.
Student(String email, String rollno) {
    this.email = email;
    this.rollno = rollno;
}
```

We need to use the `this` keyword since the parameter has the same name as the attribute inside the class, hence we need to use `this` to reference the object being created or the object with which we are calling a method.

After overriding the default constructor, this line would give an compilation error.
Because the Student constructor is requiring 2 arguments.

```java
Student s = new Student();
```

You can define methods in the Student class which will be accessible from all objects created from it.

```java
void printDetails() {
    System.out.println("Student: " + this.name);
}

```

**Problem**: In practical scenarios like `Scaler` you might have type of students like those belonging to SSB, SST, Online CS, Online DSML etc. Then you need to define different behaviours of this `printDetail` method for different types of students.

### How can we achieve that?

One such approach could be to define logic of the function based on the attributes of the student.

```java
class Student {
    String type; // "SSB" / "SST" / ...
    String name;
    // ... other attributes ...

    void printDetails() {
        if (this.type == "SST") {
            System.out.println("SST Student: " + this.name);
        }
        else if (this.type == "SSB") {
            System.out.println("SSB student: " + this.name);
        }
        // ... 1 else if block for each type of student ...
    }
}
```

## Is The Above Approach a Good Way To Solve The Problem?

Is this a good way of solving the problem, not based on time or space complexity which will anyways be `O(1)` for printing details, but rather with respect to writing code that is maintainable, understandable and extensible.

**Answer**: No.

### Is The Code Maintainable?

**No**. Generally when writing production code, before you push it, you need to review and test it. You can't just test the code that is changed by you, but you need to test all the functions which use the updated function and also the entire updated function to ensure that all are working correctly.

So imagine a scenario where there are 9 types of students for which extensive testing is already done and you add another type of student, then you would have to test the functionality of those 9 types of students too, since you need to test out the entire updated function and by doing so you are wasting your time. Instead of testing out only one single behavior, you are wasting your time testing all 10 and all the APIs associated with them.

Also if there is a bug detected from this function, then you would have to test all the behaviors to ensure that similar bug is not present in other behaviors.

### Is The Code Understandable?

**No**. Inside one single method you have multiple different behaviors. Since currently the functionality is just printing a single line it seems simple, but in other usecases you would have to implement complicated algorithms for each else if block which will increase the cognitive complexity of the code. You shouldn't be writing functions which are very very lengthy.

### Is The Code Extensible?

**No**. Extensible means it easy to add new a type of student. It might seem that its easy since we only need to add a single else-if block. In reality its not just adding a single else-if block, but rather you will have to update all other methods like `getRollNo()`, `getCgr()` etc. and add an else-if block for all of them too and then test out everything again for all behaviors since you modified those other functions.

> NOTE: If you have a very small problem/usecase, this is actually a good solution/design, but if it is part of a larger codebase then its definitely not good.

> This design breaks the Single Responsibility Principle, since one function contains multiple behaviors and hence there are multiple reasons to change this code. For example to change the format of SST students you will have to change this function and for updating the format of SSB students you will also have to change this same function.

## What Is A Better Solution For This Problem?

Answer: **Inheritance**.

We can define a base class `Student` and define other classes which will inherit properties from this base class. For example `SSTStudent`, `SSBStudent`, ...

```java
class SSTStudent extends Student {
   Map<Term, PDC> pdc;
   // ... Attributes specifically for SST student ...

   void printDetails() {
       // one single behavior which corresponds to a SST student.
   }
}

class SSBStudent extends Student {
   // ... Attributes specifically for SSB student ...

   void printDetails() {
       // one single behavior which corresponds to a SST student.
   }
}
```

This will also solve the problem of wastage of space, since not all properties and method behaviors are common between a SST and a SSB student, like PDC(for SST but not for SSB) or the method which calculates the CGR for students.

### Is It Maintainable?
If you make changes in the `SSTstudent`, you don't need to test out the behaviors of `SSBStudent` or any other child classes of `Student` because their behavior is in a different function and you are not making changes in those functions. So you only need to test the updated function and all the functions which use this updated function.

### Is It Understandable?
Instead of having 10 different behaviors inside a single function, you have 10 separate functions showing different behaviors. At a time you will only be working with one function by either reviewing, making changes, understanding which reduces the cognitive load and you don't need to understand something that is irrelevant for your usecase.

### Is It Extensible?
If we want to add a new type of student, you don't need to make changes in code that is already written, well tested and working in production. You simply add a new class (e.g. `OnlineCSStudent`) and you will test out only that class.

## Why Should `Student` be Abstract?
Based on our requirements we know that a student can only belong to "SSB" or "SST", so having implementations of methods which are common in both inside the `Student` class doesn't make any sense, since they will never be used. Also if a student can never be of the generic type `Student` then you shouldn't be allowed to create an instance of the `Student` class. Hence for both of these reasons we should make the `Student` class abstract.

> NOTE: Always understand the requirement to make this kind of a change, since our requirement didn't allow for a generic `Student`, we can make that class abstract.

## The Problem With Inheritance Pattern
Imagine a scenario where we have an abstract class `Student` and 4 class which extend it, namely `SSTStudent`, `SSBStudent`, `OnlineCSStudent` and `OnlineDSMLStudent`. Now there must be some properties that are common between some types of these students but not all.
Example:
- `getCGR()` has same implementations in `OnlineCSStudent` and `OnlineDSMLStudent`

We are repeating the same method in both of the classes. Not a good design.

### How Can We Solve This Problem?
Using a design pattern.
