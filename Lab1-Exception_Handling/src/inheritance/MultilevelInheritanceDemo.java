package inheritance;

//Multilevel Inheritance Example

class Vehicle {
 void start() {
     System.out.println("Vehicle starts.");
 }
}

class Car extends Vehicle {
 void drive() {
     System.out.println("Car is driving.");
 }
}

class SportsCar extends Car {
 void boost() {
     System.out.println("SportsCar uses turbo boost!");
 }
}

public class MultilevelInheritanceDemo {
 public static void main(String[] args) {
     SportsCar sc = new SportsCar();
     sc.start();
     sc.drive();
     sc.boost();
 }
}
