package com.mgzdev.scpu;

import com.mgzdev.scpu.tests.Test;

/**
 * Created by morf on 22.08.2015.
 */
public class Input implements HardwareDevice{

    private Test t;

    private int access=0;
    private int[] inputs;

    private int testOffset = 0;

    public Input(Test t){
        this.t = t;
        this.inputs = t.getInputs();
    }

    @Override
    public void request(SCPU cpu) {

        if(access+1<=inputs.length)cpu.SET(inputs[access], "A");
        access++;
    }

}
