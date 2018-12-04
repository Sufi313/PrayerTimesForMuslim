package net.vorson.muhammadsufwan.prayertimesformuslim;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

class VariableDemo {
    public int count=0;
    static int COUNT=0;
    public void increment()
    {
        count++;
    }
    public static void main(String args[])
    {
        //or in java 8
        HashMap<String, Double> productPrice = new HashMap<>();

        productPrice.put("Rice", 6.9);
        productPrice.put("Flour", 3.9);
        productPrice.put("Sugar", 4.9);
        productPrice.put("Milk", 3.9);
        productPrice.put("Egg", 1.9);

        Set<String> keys = productPrice.keySet();

        for (String key : keys) {
            System.out.println(key);
        }

        Collection<Double> values = productPrice.values();
        for (Double value : values){
            System.out.println(value);
        }

        Set<Map.Entry<String, Double>> entries = productPrice.entrySet();
        for (Map.Entry<String, Double> entry : entries) {
            System.out.print("key: "+ entry.getKey());
            System.out.println(", Value: "+ entry.getValue());
        }

        VariableDemo obj1=new VariableDemo();
        VariableDemo obj2=new VariableDemo();
        obj1.increment();
        obj2.increment();
        obj2.increment();
        System.out.println("Obj1: count is="+obj1.count);
        System.out.println("Obj2: count is="+obj2.count);

    }

}