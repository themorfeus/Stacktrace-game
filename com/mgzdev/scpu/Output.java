package com.mgzdev.scpu;

import com.mgzdev.scpu.tests.Test;
/**
 * Created by morf on 22.08.2015.
 */
public class Output implements HardwareDevice{

    private Test t;

    private int access=0;
    private int[] outputs;

    private int givenAnswers = 0;
    private int neededAnswers;

    private int[] answers;

    private int testOffset = 0;

    public Output(Test t){
        this.t = t;
        this.outputs = t.getOutputs();
        this.neededAnswers = t.getOutputCount();
        answers = new int[neededAnswers];
    }



    int test = 0;

    @Override
    public void request(SCPU cpu) {

        answers[givenAnswers] = cpu.regVal("A");
        givenAnswers++;


        boolean passed = true;
        if(givenAnswers==neededAnswers){
            for(int i = 0; i<neededAnswers; i++){
                //System.out.println("A" +i + " is " + ((answers[i]==outputs[i+testOffset])?"OK":("NOK, expecting " + outputs[i+testOffset] + " got " + answers[i])));

                if(outputs[i+testOffset] != answers[i])passed = false;
                //System.out.println(outputs[i+testOffset]  + " " + answers[i]);

            }

            testOffset+=neededAnswers;

            System.out.println();
            cpu.updateTest(test, passed?"OK":"NOK", passed);


            givenAnswers = 0;
            if(testOffset<=outputs.length - neededAnswers){
                test++;
                cpu.execute();
            }else{
                cpu.endTesting();
            }
        }
    }

}
