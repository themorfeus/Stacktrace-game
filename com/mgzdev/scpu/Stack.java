package com.mgzdev.scpu;

import java.util.ArrayList;

/**
 * Created by morf on 22.08.2015.
 */
public class Stack {

    private static ArrayList<String> stack = new ArrayList<String>();

    public static void push(String a){
        stack.add(a);
    }

    public static String pop(){
        String s = stack.get(stack.size()-1);
        stack.remove(stack.size()-1);
        return s;
    }

}
