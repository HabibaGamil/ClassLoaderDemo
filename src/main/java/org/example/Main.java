package org.example;

import java.lang.reflect.*;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {

        //initialize class loader with the path where the .class files will reside
        CustomClassLoader classLoader = new CustomClassLoader("example", ClassLoader.getSystemClassLoader());
        Class c =  classLoader.loadClass("ConcreteFoo");

        if(c==null) {
            System.out.println("class not found");
            return;
        }

        //Invoking doSomething method using reflection
        c = Class.forName("org.example.ConcreteFoo");
        Object obj = c.getDeclaredConstructor().newInstance();
        Method doSomething = c.getDeclaredMethod("doSomething");
        doSomething.invoke(obj);

        //Invoking doSomething method using Interface
//        Foo foo = (Foo) c.getDeclaredConstructor().newInstance();
//        foo.doSomething();






    }
}