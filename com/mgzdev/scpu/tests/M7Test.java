package com.mgzdev.scpu.tests;

/**
 * Created by morf on 22.08.2015.
 */
public class M7Test extends Test{


    public M7Test() {
        super(4, 1, 7);

        setInputs(new int[]{1,2,3,4,4,3,5,2,6,14,7,4,9,8,2,5});
        setOutputs(new int[]{14,43,168,98});


    }


}
