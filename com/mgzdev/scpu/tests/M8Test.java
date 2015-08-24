package com.mgzdev.scpu.tests;

/**
 * Created by morf on 22.08.2015.
 */
public class M8Test extends Test{


    public M8Test() {
        super(3, 1, 8);

        setInputs(new int[]{6,4,2,5,1,8,3,8,6});
        setOutputs(new int[]{0, 4, 11});


    }


}
