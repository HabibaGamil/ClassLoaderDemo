package org.example;

import java.lang.ref.WeakReference;
import java.lang.reflect.*;
import java.util.*;

public class AbstractCard {
    static private ClassLoader cl = null;
    static private String directory = null;
    static private Class implClass;
    static Set<WeakReference> instances = new HashSet<>();

    public static Card newInstance( )
            throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {

        Card card = (Card)implClass.getDeclaredConstructor().newInstance();
        Card cardProxy = (Card) CardIH.newInstance( card );
        instances.add( new WeakReference( cardProxy ));
        return cardProxy;
    }
    public static void reload( String dir )
            throws ClassNotFoundException,InstantiationException,
            IllegalAccessException,
            NoSuchMethodException,
            InvocationTargetException
    {
        cl = new CustomClassLoader(dir, ClassLoader.getSystemClassLoader());
        implClass = cl.loadClass( "ProductImpl" );
        Set<WeakReference> newInstances = new HashSet<>();

        Method evolve = implClass.getDeclaredMethod( "evolve", new Class[]{Object.class} );

        for ( WeakReference ref : instances ) {
            Proxy x =(Proxy) ref.get();
            CardIH cih = (CardIH)Proxy.getInvocationHandler(x);
            Card oldObject = cih.getTarget();
            Card replacement = (Card) evolve.invoke( null, new Object[]{ oldObject } );
            cih.setTarget( replacement );
            newInstances.add( new WeakReference( x ) );
        }
        instances = newInstances;
    }

}
