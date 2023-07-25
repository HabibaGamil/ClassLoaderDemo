package org.example;
import java.lang.reflect.*;

public class CardIH implements InvocationHandler {

    private Card target = null;
    static private Class[] productAInterfaces = { Card.class };

    private CardIH( Card c ) {
        target = c;
    }
    public static Card newInstance( Card c ) {
        return (Card) Proxy.newProxyInstance(
                        c.getClass().getClassLoader(),
                        productAInterfaces,
                        new CardIH(c) );
    }
    public void setTarget( Card c ){
        target = c;
    }
    public Card getTarget(){
        return target;
    }
    public Object invoke( Object t, Method m, Object[] args ) throws Throwable
    {
        Object result = null;
        try {
            result = m.invoke( target, args );
        } catch (InvocationTargetException e) {
            throw e.getTargetException();
        }
        return result;
    }
}
