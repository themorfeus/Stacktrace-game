package com.mgzdev.scpu.tests;

/**
 * Created by morf on 22.08.2015.
 */
public class M2Test extends Test{


    public M2Test() {
        super(2, 1, 2);

        setInputs(new int[]{5, 1, 3, 4, 5, 7, 3, 3, 8, 1, 6, 9, 5, 6, 3, 2, 12, 3, 4, 6});
        setOutputs(new int[]{9, 2, 3, 3, 15, 3, 4, 4, 21, 2});


    }


}
