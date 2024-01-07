# Record

Java introduced the record feature as a preview in JDK 14 and made it standard in JDK 16. The record feature is part of Java's efforts to improve the language's expressiveness and reduce boilerplate code.

A record provides a compact syntax for declaring classes that are intended to be simple, transparent holders for shallowly immutable data. Essentially, they can simplify a lot of the code we often write for simple POJOs (Plain Old Java Objects).

###  Declaration
The simplest way to think of a record is as a named sequence of fields. Here's how you'd define a record:

```java
record Point(int x, int y) {}
```
With this definition, you get:

A final class named Point that extends java.lang.Record.

- Private final fields x and y.
- A public constructor with arguments for all fields.
- Implementations of hashCode(), equals(), and toString() that are derived from all the fields.
- Public accessor methods for all fields with the same name as the fields.

### Features and Characteristics of Records
#### Compact Constructor: 
If you want to validate fields or perform some action on fields, you can define a compact constructor
```java
record Point(int x, int y) {
   public Point {
     if (x < 0 || y < 0) {
       throw new IllegalArgumentException("Negative values not allowed.");
     }
   }
}
```
Notice that the compact constructor doesn't have any parameters. It uses the fields directly.

#### Static Members: 
You can define static fields, methods, or initializers inside a record. But instance variables that are not part of the record's state are disallowed.

#### Immutability: 
All fields in a record are final, which means they cannot be modified after they're set in the constructor.

#### No Inheritance: 
Records cannot extend another class. However, they can implement interfaces.

```java
public record Point(int x, int y) implements Comparable<Point> {
  @Override
  public int compareTo(Point other) {
    return Integer.compare(this.x, other.x);
  }
}
```
#### Annotations & Modifiers: 

You can use annotations and access modifiers with records as you would with other classes. For example, you can annotate a record or its fields with validation annotations from frameworks like Hibernate Validator.

### When to Use Records
Records are best suited for classes that are primarily used to encapsulate data—data carriers. These might include:

- Value objects in a domain-driven design.
- DTOs (Data Transfer Objects).
- Struct-like classes where the primary purpose is to group together related fields.

Limitations

- Records can't extend other classes.
- Fields in records are implicitly final.
- No field can have the same name as any record component.

### Summary
Records are an elegant way to reduce boilerplate for data-centric classes in Java. They come with automatically derived semantics for basic methods like equals(), hashCode(), and toString(). However, they are not a complete replacement for traditional classes. Use them where they make sense, and benefit from the concise syntax and automatic features they offer.

## Exercise

###  Simple Library System
Problem Statement:

You're implementing a simple library system. The primary entities in this system are Book and Author. Use Java records to model these entities.

Create a Author record with the following attributes:

- firstName (String)
- lastName (String)
- birthYear (int)

Create a Book record with the following attributes:

- title (String)
- author (Author)
- publicationYear (int)
- 
Implement a main method where you:

- Create a few Author and Book instances.
- Print these instances to see the auto-generated toString() in action.
- Compare two Book instances using the auto-generated equals() to see if they are the same.

Bonus Challenge:

- Implement a method getAuthorAgeAtPublication(Book book) that returns the age of the author when the book was published.

Once you've implemented the above, try exploring:

Using the auto-generated constructors for the records.
Accessing the attributes of the records using the auto-generated accessor methods (which have the same name as the attributes).


## Solution

### Define the Author record:
```java
   public record Author(String firstName, String lastName, int birthYear) {}
```
### Define the Book record:
```java
   public record Book(String title, Author author, int publicationYear) {}
```
### Implementing the main method:
```java
   public class LibrarySystem {

   public static void main(String[] args) {
   Author rowling = new Author("J.K.", "Rowling", 1965);
   Book harryPotter = new Book("Harry Potter and the Philosopher's Stone", rowling, 1997);

        Author tolkien = new Author("J.R.R.", "Tolkien", 1892);
        Book lordOfTheRings = new Book("The Lord of the Rings", tolkien, 1954);
        
        // Print the records
        System.out.println(rowling);
        System.out.println(harryPotter);
        
        // Check if two books are the same
        Book anotherHarryPotter = new Book("Harry Potter and the Philosopher's Stone", rowling, 1997);
        System.out.println(harryPotter.equals(anotherHarryPotter));  // This should print true

        // Bonus Challenge
        System.out.println(getAuthorAgeAtPublication(harryPotter));  // This should print 32
   }

   // Bonus Challenge Method
   public static int getAuthorAgeAtPublication(Book book) {
   return book.publicationYear() - book.author().birthYear();
   }
   }
```
The solution above uses the auto-generated constructors provided by the record keyword to create instances of Author and Book. It also showcases the auto-generated toString() method by printing the records and the equals() method by comparing two Book instances.

The getAuthorAgeAtPublication() method demonstrates how you can use the auto-generated accessor methods to fetch and operate on the attributes of a record. The accessors have the same names as the attributes.