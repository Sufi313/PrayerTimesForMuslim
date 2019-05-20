package net.vorson.muhammadsufwan.prayertimesformuslim;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class TestClass{

    transient volatile int modCount;
    public static void main(String[] args) {

        try{
            Student st = new Student(101,"John",10);
            FileOutputStream fos = new FileOutputStream("student.info");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(st);
            oos.close();
            fos.close();
        }catch(Exception e){
            System.out.println(e);
        }

        Student st = null;
        try{
            FileInputStream fis = new FileInputStream("student.info");
            ObjectInputStream ois = new ObjectInputStream(fis);
            st = (Student)ois.readObject();
        }catch(Exception e){
            System.out.println(e);
        }
        System.out.println(st.id);
        System.out. println(st.name);
        System.out. println(st.age);


        //ConcurrentHashMap
        Map<String, String> myMap = new ConcurrentHashMap<>();
        myMap.put("1", "1");
        myMap.put("2", "1");
        myMap.put("3", "1");
        myMap.put("4", "1");
        myMap.put("5", "1");
        myMap.put("6", "1");
        System.out.println("ConcurrentHashMap before iterator: " + myMap);
        Iterator<String> it = myMap.keySet().iterator();

        while (it.hasNext()) {
            String key = it.next();
            if (key.equals("3")) myMap.put(key + "new", "new3");
        }
        System.out.println("ConcurrentHashMap after iterator: " + myMap);

        //HashMap
        myMap = new HashMap<>();
        myMap.put("1", "1");
        myMap.put("2", "1");
        myMap.put("3", "1");
        myMap.put("4", "1");
        myMap.put("5", "1");
        myMap.put("6", "1");
        System.out.println("HashMap before iterator: " + myMap);
        Iterator<String> it1 = myMap.keySet().iterator();

        while (it1.hasNext()) {
            String key = it1.next();
            if(key.equals("3")){
                myMap.put(key+"new", "new3");
                break;
            }
        }
        System.out.println("HashMap after iterator: " + myMap);

        System.gc();
        long start=new GregorianCalendar().getTimeInMillis();
        long startMemory=Runtime.getRuntime().freeMemory();
        //StringBuffer sb = new StringBuffer();
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i<10000000; i++){
            sb.append(":").append(i);
        }
        long end=new GregorianCalendar().getTimeInMillis();
        long endMemory=Runtime.getRuntime().freeMemory();
        System.out.println("Time Taken:"+(end-start));
        System.out.println("Memory used:"+(startMemory-endMemory));



    }


    static class Student implements Serializable
    {
        int id;
        String name;
        transient int age;
        Student(int id, String name,int age)  {
            this.id = id;
            this.name = name;
            this.age = age;
        }
    }

}


