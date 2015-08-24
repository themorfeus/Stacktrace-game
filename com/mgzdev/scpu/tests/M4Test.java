package com.mgzdev.scpu.tests;

/**
 * Created by morf on 22.08.2015.
 */
public class M4Test extends Test{


    public M4Test() {
        super(3, 1, 4);

        setInputs(new int[]{3,6,5,2,4,7,4,7,9,9,6,5,1,4,6,3,7,2});
        setOutputs(new int[]{18,23,30,12,27,10});


    }


}
