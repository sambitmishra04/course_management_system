package inheritance;

//Hierarchical Inheritance Example

class Shape {
 void display() {
     System.out.println("This is a shape.");
 }
}

class Circle extends Shape {
 void drawCircle() {
     System.out.println("Drawing a circle.");
 }
}

class Rectangle extends Shape {
 void drawRectangle() {
     System.out.println("Drawing a rectangle.");
 }
}

public class HierarchicalInheritanceDemo {
 public static void main(String[] args) {
     Circle c = new Circle();
     Rectangle r = new Rectangle();
     
     c.display();
     c.drawCircle();

     r.display();
     r.drawRectangle();
 }
}
