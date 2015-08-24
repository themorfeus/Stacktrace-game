package com.mgzdev.scpu.tests;

/**
 * Created by morf on 22.08.2015.
 */
public class M6Test extends Test{


    public M6Test() {
        super(3, 3, 6);

        setInputs(new int[]{5,3,2,8,3,4,9,4,5,12,3,6,5,8,4});
        setOutputs(new int[]{2,1,2,1,0,4,2,1,4,4,2,6,0,1,2});


    }


}
