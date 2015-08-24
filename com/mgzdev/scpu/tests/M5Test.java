package com.mgzdev.scpu.tests;

/**
 * Created by morf on 22.08.2015.
 */
public class M5Test extends Test{


    public M5Test() {
        super(1, 3, 5);

        setInputs(new int[]{5,42,16,89});
        setOutputs(new int[]{1,0,0,8,6,3,3,2,1,17,12,8});


    }


}
