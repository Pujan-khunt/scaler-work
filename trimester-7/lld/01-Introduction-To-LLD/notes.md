# Intro to Low Level Design (Class 1)

NOTE: This course is not programming language agnostic and will specifically use Java and its Object Oriented Paradigm.

## What is Low Level Design?
When working in a software based company with a role of SDE II or above you would spend very less time writing the code.
When I say writing the code that includes the time in figuring out:
- What to write?
- Creating modules
- Designing the APIs
- Which controller to use?
- Schema design
- and much more similar tasks

So basically you would spend very less time in writing the actual code, roughly 10-12%.

The majority of the part would be spent around the following:
| Maintainability | Understandability | Extensibility |
| :-- | :-- | :-- | 
| Refactoring | Understanding Codebase | Regression bugs |
| Testing | Code Review | Merge Conflicts |
| Finding and Fixing Bugs | Knowledge Transfer Sessions | |

> Introduction to LLD is all about learning how to write code that is:
> - **Maintainable**: Changes in the code doesn't take a lot of time.
> - **Understandable**: If anyone else or you are going through your code, it should not take a lot of time to understand it.
> - **Extensible**: Adding any new feature that should take as less time as possible, which will only happen if the code is _loosely coupled_. When adding a new feature if you are not supposed to test other things only then will it take less time.

Low Level Design is a field that can never be fully learnt since every system will have its own unique design according to its usecases and complications. Hence, in this course we are only going
to learn the important design patterns which solve the majority of the issues, but you can never get the answers for all of the problems, that comes with practice.

## Overview of the Course
- **OOP/OOD** (Object Oriented Design)
  - classes and objects
  - inheritance
  - abstract class
  - interface
- **SOLID principles**: These are the principles which if followed can help achieve MUE (Maintainability, Understandability and Extensibility)
- **Common Design Patterns**: These are the solutions to the most common problems, so you shouldn't reinvent the wheel and just use the optimal answer.
  - Most imporant thing to learn about how to use them for our specific usecase.
  - When to not use them.
  - Types of design patterns:
    - creational
    - structural
    - behavioral
- **UML**: How to communicate the design with others
- **Case Studies**: 
  - LLD of a real world product/problem like BookMyShow
  - Coding
  - Schema Design

If you are able to learn all of the above topics and implement them and understand why do we need a specific design pattern, when to use it, when not to use it, then you are ready for a SDE II or above role at companies like Amazon or Microsoft.
