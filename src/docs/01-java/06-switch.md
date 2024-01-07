# Switch

#### Traditional Switch Statement
Before we dive into the new features, here's a quick recap of the traditional switch statement:

```java
String dayType;
switch (dayOfWeek) {
  case "Monday":
  case "Tuesday":
  case "Wednesday":
  case "Thursday":
  case "Friday":
    dayType = "Weekday";
    break;
  case "Saturday":
  case "Sunday":
    dayType = "Weekend";
    break;
  default:
    throw new IllegalArgumentException("Invalid day: " + dayOfWeek);
}
```
#### Switch Expression (from JDK 12)
With the enhanced switch, the above can be rewritten as:

```java
String dayType = switch (dayOfWeek) {
  case "Monday", "Tuesday", "Wednesday", "Thursday", "Friday" -> "Weekday";
  case "Saturday", "Sunday" -> "Weekend";
  default -> throw new IllegalArgumentException("Invalid day: " + dayOfWeek);
};
```
#### Yield Statement (from JDK 12)
You can use a block of code with the yield keyword to return a value from the switch expression:

```java
String dayType = switch (dayOfWeek) {
  case "Monday", "Tuesday", "Wednesday", "Thursday", "Friday" -> {
    System.out.println("It's a working day.");
    yield "Weekday";
  }
  case "Saturday", "Sunday" -> "Weekend";
  default -> throw new IllegalArgumentException("Invalid day: " + dayOfWeek);
};
```
#### Pattern Matching for Switch (from JDK 17)
With JDK 17, pattern matching has been introduced for switch as a preview feature. This allows for powerful matching and deconstruction.

Imagine you have a hierarchy of geometric shapes:

```java
sealed interface Shape permits Circle, Rectangle;
record Circle(double radius) implements Shape {}
record Rectangle(double width, double height) implements Shape {}
```
You can now switch on these shapes and extract their properties:

```java
double area(Shape shape) {
  return switch (shape) {
    case Circle c -> Math.PI * c.radius() * c.radius();
    case Rectangle r -> r.width() * r.height();
    default -> throw new AssertionError("Unknown shape: " + shape);
  };
}
```
Here, the Circle c and Rectangle r are pattern variables that hold the matched object. You can then use them in the switch branch to compute the desired value.

#### Record Pattern (java 21)

```java
double area(Shape shape) {
  return switch (shape) {
    case Circle(double radius) -> Math.PI * radius * radius
    case Rectangle(double w, double h) -> w * h
    default -> throw new AssertionError("Unknown shape: " + shape);
  };
}
```
The Circle and Rectangle als destructured into their construction variables.

#### When

```java
double area(Shape shape) {
  return switch (shape) {
    case Circle(double radius) when radius > 3 -> Math.PI * radius * radius;
    case Rectangle(double w, double h) when w > 0 && h > 0 -> w * h;
    default -> throw new AssertionError("Unknown shape or size.");
  };
}
```
We can add a guard to a case statement with the new `when` keyword.

#### Run 

```java
public static void main(String[] args)  {
    double result = area(new Circle(10));
    System.out.println(result);

    double result2 = area(new Rectangle(3, 4));
    System.out.println(result2);
    
  }
```

## Exercises


#### Basic Enhanced switch Usage
Create a method that takes an int day number as a parameter and returns the name of the day as a String. Use the new switch expression to handle each day number.

#### Using switch to Return Values
Create a method that takes a String representing a month and returns the number of days in that month using switch. Use yield to return values.

#### Handling Multiple Labels in a switch
Create a method that takes an int representing a day of the week and returns whether it is a weekday or weekend using switch. Handle multiple labels with a single case.

#### Using switch with Enums
Define an enum for the seasons. Create a method that takes a season as a parameter and returns a String describing the weather during that season using switch.

#### Handling Default Case
Create a method that takes a String representing a traffic light color and returns instructions for a driver using switch. Ensure you handle unexpected input with a default case.

## Solutions
#### Solution 1

```java
public String getDayName(int dayNumber) {
  return switch(dayNumber) {
    case 1 -> "Monday";
    case 2 -> "Tuesday";
    case 3 -> "Wednesday";
    case 4 -> "Thursday";
    case 5 -> "Friday";
    case 6 -> "Saturday";
    case 7 -> "Sunday";
    default -> throw new IllegalArgumentException("Invalid day number");
  };
}
```
#### Solution 2

```java
public int getDaysInMonth(String month) {
  return switch(month) {
    case "February" -> 28; // Simplifying by not considering leap years
    case "April", "June", "September", "November" -> 30;
    case "January", "March", "May", "July", "August", "October", "December" -> 31;
    default -> throw new IllegalArgumentException("Invalid month");
  };
}
```
#### Solution 3

```java
public String getDayType(int day) {
  return switch(day) {
    case 1, 2, 3, 4, 5 -> "Weekday";
    case 6, 7 -> "Weekend";
    default -> throw new IllegalArgumentException("Invalid day");
  };
}
```
#### Solution 4



```java
enum Season {
WINTER, SPRING, SUMMER, FALL
}

public String getWeather(Season season) {
  return switch(season) {
    case WINTER -> "Cold";
    case SPRING -> "Mild";
    case SUMMER -> "Hot";
    case FALL -> "Cool";
  };
}
```
#### Solution 5

```java
public String getDrivingInstruction(String lightColor) {
  return switch(lightColor) {
    case "Red" -> "Stop";
    case "Yellow" -> "Prepare to Stop";
    case "Green" -> "Go";
    default -> "Proceed with Caution";
  };
}
```


