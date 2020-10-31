package c.bawp.learnjava;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MyClass {

    public static void main(String[] args) {
        //key: value pair
        HashMap<String, String> gamePlayer = new HashMap<>();

        gamePlayer.put("firstName", "James");
        gamePlayer.put("first", "Bonni");
        gamePlayer.put("second", "George");
        gamePlayer.put("third", "Gloria");
        gamePlayer.put("fourth", "Gina");
        gamePlayer.put("fifth", "Adelina");


        //Iterate through HashMap
        Iterator iterator = gamePlayer.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry pair = (Map.Entry) iterator.next();

            System.out.println("Key: " + pair.getKey() + " Value: " + pair.getValue());
        }


//        System.out.println(gamePlayer.entrySet());
//        System.out.println(gamePlayer.get("first"));







    }
}







//ArrayList


   // int[] myArray =  {1, 33, 4, 12, 89, 98, 90, 12, 43, 1234}; // length = 6
//        String[] names = {"James", "Anthony", "Rodrigo", "Niraj"};
//
//        int[] newArray = new int[4];
//        String[] newNames = new String[5];
//
//
//
//        newArray[0] = 12;
//        newArray[1] = 13;
//        newArray[2] = 133;
//        newArray[3] = 113;
//
////        [12, 13, 133, 113]
//
//
//        for (int i = 0; i < newArray.length; i++) {
//            System.out.println("Items: " + newArray[i]);
//        }
//
//
//        //Array Lists
////        ArrayList<String> name = new ArrayList<>();
//        Person newPerson = new Person("James", "Bond", 34);
//        ArrayList name = new ArrayList();
//        name.add(newPerson);
//        name.add("Bond");
//        name.add("James");
//        name.add("Bonni");
//        name.add("Arnold");
//
////
////        for (int i = 0; i < name.size(); i++) {
////            System.out.println("Names: " + name.get(i));
////        }
//
//        if (name.contains("James")) {
//             name.remove("James");
//
//        }else {
//            System.out.println("Nope");
//        }
//        //Clever for loop
//        for (Object n : name) {
//
//            System.out.println("Names: " + n);
//
//        }
//
////        System.out.println(name.get(3));
//
//
//
//
//


//    int a = 12;
//    String myString = "Hello";
//    boolean isEmpty = myString.isEmpty();
//
//
//    //StringBuilder
//    StringBuilder builder = new StringBuilder();
////[h,e,l,l,o]
//        builder.append("Hello ");
//                builder.append(23);
//                builder.append(" ");
//                builder.append(1.2f);
//                builder.deleteCharAt(0);
//
//
//                System.out.println(builder);
//
//
//                if (!isEmpty) {
//                System.out.println(myString);
//                } else {
//                System.out.println("Empty");
//                }
//
//
//                Employee employee = new Employee();
//
//
//                employee.setFirstName("James");
//                employee.setLastName("Bond");
//                employee.setAge(45);
//                employee.setId(123544);
//                employee.setAnnualSalary(100000);
//
//                // System.out.println(employee.getAnnualSalary());
//
//
//                Manager manager = new Manager();
//                manager.setFirstName("Manager George");
//                manager.setLastName("Kilos");
//                manager.setAge(35);
//                manager.setId(456);
//                manager.setAnnualSalary(45000);


// System.out.println(manager);


//        System.out.println(manager.getAnnualSalary() + " , "
//        + manager.getFirstName() + " Id: " + manager.getId());


// Microphone microphone = new Microphone("Blue Yeti", "Blue", 364663); //instantiating our object
//        microphone.setModel(87);
//        microphone.setName("New Blue Yeti");
//        microphone.setColor("Red blue");
//
//        System.out.println("New Mic: " + microphone.getModel());
//
//
//        microphone.turnOn();
//        microphone.setVolume();
//        microphone.turnOff();
//
//
//        System.out.println(microphone.showDescription());
//
//        Microphone newMic = new Microphone("NewMic", "Green", 123);
//        Microphone otherMic = new Microphone();
//        otherMic.setColor("Other Mic");
//
//        System.out.println(otherMic.getColor());
//
//        Microphone grandMic = new Microphone("Grand", "Yellow");

//
//
//
//    String bio =  showBio("George", "I was born in Nebraska", 75);
//
//        System.out.println(bio);
////         double item = divideNumbers(3, 5);
////         item += 1;
////
////        System.out.println("Result: " + item);
//                // multiplyNumbers(3, 5);
//                //addNumbers(12, 12);
//                //anotherMethod("Hey", 91);
//                //anotherMethod("Hello");
//                //showName();
//
//
//                }
//
////Todo: create a multiplyNumbers(), divideNumbers()...
//public static void divideNumberss(double first, int second) {
//        System.out.println("Result: " + first/second);
//        }
//
//public static double divideNumbers(double first, int second) {
//
//        return (first/second);
//        }
//
//public static String showBio(String name, String bio, int age){
//
//        return " My name is "
//        + name + " and I am " + age + " years old. "
//        + bio;
//        }
//
//
//
//
//
//
//
//
//public static void multiplyNumbers(int first, int second) {
//        System.out.println("Result: " + first*second);
//        }
//public static void addNumbers(int a, int b) {
//        int sum = a + b;
//        System.out.println("Sum is: " + sum);
//        }
//public static void anotherMethod(String something, int age) {
//        if (age > 90) {
//        System.out.println("I am still young");
//        }
//
//        System.out.println("Another Method " + something + " " + age);
//        }
//public static void showName() {
//
//        for (int i = 0; i < 20; i++) {
//        //will run this code here
//        if (i % 3 == 0){
//        System.out.println(i + " is multiple of 3");
//        }
//
//        }
//
//        System.out.println("From ShowName");
//
//
//        }


//Control flow - Introduction
//if statements
        /*
         -- Relational Operators ---
           = --> assignment
           == --> comparison
           != --> NOT

           > --> Greater than
           < --> Less than
           >= --> Greater than of equal
           <= --> Less than or equal

           ===Logical Operators ==
            AND --> && : returns false if one is false
            OR  --> || : returns true if one side is true
            ! --> Negation

         */

//        double firstAge = 21;
//        double secondAge = 17;
//        boolean isAged = true;
//        boolean isNotAged = false;
//
//        if ((isAged || isNotAged) && (firstAge > 12)) {
//            System.out.println("Can consume beer!");
//        }else {
//            System.out.println("Not allowed to consume beer!");
//        }


//        double a = 12;
//        double b = 7;// 12.0
//
//
//        // Subtraction and addition (-, +)
//         //double total = (b / a); //casting
//
//        // total = total + 100;
//           //total +=  100;
//
//           //Remainder % == "what remains..."
//        double remainder = a%b; // divide 12 by 6, and see if there's a remainder
//        System.out.println("Remainder is: " + remainder);




        /*
            Long comment area...
            adadf
            adfadf
            adfafadfaf
         */

//        //Variable == bucket in memory
//        int age = 239090909;
//        long weightOfPlanet = 944342543;
//        byte b = 127;  // 127 max
//        short s = 156; // 2x byte
//
//        // 1.3, 4.4, 0.2... decimal numbers
//        float pi = 3.14f;
//        double pid = 3.14; // bigger with more precision
//
//        String name = "Georgina";
//        char a = '?';
//
//        //Boolean = true or false, 1, 0
//        boolean isTrue;
//        isTrue = true;

// System.out.println("My name is " + isTrue + " and I'm " + age + " years old.");

