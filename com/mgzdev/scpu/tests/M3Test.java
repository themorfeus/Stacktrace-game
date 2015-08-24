package com.mgzdev.scpu.tests;

/**
 * Created by morf on 22.08.2015.
 */
public class M3Test extends Test{


    public M3Test() {
        super(3, 1, 3);

        setInputs(new int[]{5,3,2,2,4,5,6,5,12,1,3,3,7,8,9,6,3,1,2,9,4});
        setOutputs(new int[]{0,7,35,5,10,65533,11});


    }


}
