package com.example.learnjava;

import java.util.Arrays;

public class MyClass {
    public static void main(String[] args) {
        Person a=new Person();
        Person b=new Person("Saksham","Sachdeva",20);
        System.out.println(b.getFirstName());
        Employee c=new Employee();
        c.setFirstName("Saku");
        System.out.println(c.getFirstName());

//        Microphone m=new Microphone();
//        m.color="Black";
//        m.name="Saksham";
//        m.model=123;
//
//
//        //variable is bucket in memory
//        String name2;
//        String name;
//        int age=20;
//        long var=1232321232;
//        float var2=2.21f;//we have to use f as double is also other king of float
//        double var3=3.14;
//        byte b=127; //can store upto -127 to +127
//        short s=156; //lies between b and integer twice as bigger as byte
//        char a='a';
//        boolean x=true;
//        double r=13%12;
//        System.out.println("Remainder is: "+r);
//        name="Hi Buddy";
//        name2="Saksham Sachdeva";
//        System.out.println("Hey my"+var2+"age is: "+age);
//        System.out.println(name+" "+name2+" "+age);
//        System.out.println("Hi my name is Saksham Sachdeva");
    }
}
