package com.mgzdev.scpu.tests;

/**
 * Created by morf on 22.08.2015.
 */
public class M1Test extends Test{


    public M1Test() {
        super(1, 1, 1);

        setInputs(new int[]{5, 1, 3, 7, 1, 13, 9, 4, 8, 6});
        setOutputs(new int[]{10, 2, 6, 14, 2, 26, 18, 8, 16, 12});


    }


}
