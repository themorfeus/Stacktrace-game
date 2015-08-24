package com.mgzdev.scpu.tests;

/**
 * Created by morf on 22.08.2015.
 */
public abstract class Test{

    private int inputs;
    private int outputs;

    private int testCount;

    private int[] inputTable;
    private int[] outputTable;

    private int test = 0;

    protected Test(int inputs, int outputs, int number){
        this.inputs = inputs;
        this.outputs = outputs;
        this.test = number;
    }

    protected void setInputs(int[] inputs){
        inputTable = inputs;
    }

    protected void setOutputs(int[] outputs){
        outputTable = outputs;
    }

    public int getOutputCount(){
        return outputs;
    }

    public int[] getInputs(){
        return inputTable;
    }

    public int[] getOutputs(){
       return outputTable;
    }

    public int getTestNum(){
        return test;
    }
}
