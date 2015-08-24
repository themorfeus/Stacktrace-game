package com.mgzdev.scpu;

import com.mgzdev.scpu.tests.M5Test;
import com.mgzdev.scpu.tests.Test;
import com.mgzdev.spc.screens.GameScreen;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by morf on 22.08.2015.
 */
public class SCPU {

    private int A, B, C, X, Y, Z, M, F;

    private int mem[][] = new int[8][16000];

    private HardwareDevice[] hwd = new HardwareDevice[16];

    private HashMap<String, Integer> labels = new HashMap<String, Integer>();

    private String ops[];

    private GameScreen g;

    public SCPU(GameScreen g, Test t){

        this.g = g;

        hwd[0] = new Input(t);
        hwd[1] = new Output(t);
    }

    public void load(String code){

        g.updateRegisters(new int[]{0,0,0,0,0,0,0,0});
        g.updateProgramCounter(0);
        for(int i = 0; i<GameScreen.TESTS; i++){
            g.updateTestResults(i, null, false);
        }

        String[] lines = code.split("\n");

        int memPointer = 0;
        M = 0;
        //load the program into the memory

        ArrayList<String> opList = new ArrayList<String>();

        for(String s: lines){
            if(s.startsWith(";"))continue;

            String[] ops = s.split(" ");


            if(ops[0].trim().startsWith(":") && ops.length<2){
                ops[0] = ops[0].trim();
                labels.put(ops[0], memPointer);
                continue;
            }
            this.ops = ops;
            for(int i = 0; i<ops.length; i++){

                String o = ops[i];

                opList.add(o.trim());

                String p = o.trim().toUpperCase();

                    if(p.equals("ADD"))
                        writeMem(memPointer, 0x1);

                    else if(p.equals("MUL"))
                        writeMem(memPointer, 0x2);

                    else if(p.equals("SUB"))
                        writeMem(memPointer, 0x3);

                else if(p.equals("DIV"))
                        writeMem(memPointer, 0x4);

                else if(p.equals("SET"))
                        writeMem(memPointer, 0x5);

                else if(p.equals("CPY"))
                        writeMem(memPointer, 0x6);

                else if(p.equals("CLR"))
                        writeMem(memPointer, 0x7);

                else if(p.equals("IFC"))
                        writeMem(memPointer, 0x8);

                else if(p.equals("IFI"))
                        writeMem(memPointer, 0x9);

                else if(p.equals("JMP"))
                        writeMem(memPointer, 0xA);

                else if(p.equals("WRM"))
                        writeMem(memPointer, 0xB);

                else if(p.equals("RDM"))
                        writeMem(memPointer, 0xC);

                else if(p.equals("HWR"))
                        writeMem(memPointer, 0xD);

                else if(p.equals("HLT"))
                        writeMem(memPointer, 0xFF);

                else
                        writeMem(memPointer, 0x0);

                memPointer++;
            }

        }

        for(String s:labels.keySet()){
            if(opList.contains(s) && opList.get(opList.indexOf(s)+1).trim().equals("JMP")){

                for(int i = 0; i<opList.size(); i++){
                    if(opList.get(i).equals(s))opList.set(i, labels.get(s)+"");
                }
            }
        }

        this.ops = new String[opList.size()];
        ops = opList.toArray(ops);
    }

    private int PC = 0;
    private boolean skip = false;

    private long lastTime = 0;

    private boolean execute = true;

    public void execute(){
        PC = 0;
        while(execute){
            int[] registers = new int[]{A, B, C, X, Y, Z, M, F};
            g.updateRegisters(registers);

            g.updateProgramCounter(PC);

            if(System.currentTimeMillis() - lastTime < 100)continue;

            int op = readProgMem(PC);
            //System.out.println(ops[PC]);
            if(op!=0 && skip){
                PC++;
                skip = false;
                continue;
            }

            switch(op){

                case 0x0:
                    if(!skip)Stack.push(ops[PC]);
                    break;

                case 0x1:
                    String ra = Stack.pop();
                    String rb = Stack.pop();

                    int res = (int)Math.floor(regVal(ra) + regVal(rb));

                    SET(res, "A");
                    break;

                case 0x2:
                    ra = Stack.pop();
                    rb = Stack.pop();

                    res = (int)Math.floor(regVal(ra) * regVal(rb));

                    SET(res, "A");
                    break;

                case 0x3:
                    ra = Stack.pop();
                    rb = Stack.pop();

                    res = (int)Math.floor(regVal(ra) - regVal(rb));

                    SET(res, "A");
                    break;

                case 0x4:
                    ra = Stack.pop();
                    rb = Stack.pop();

                    res = (int)Math.floor(regVal(ra) / regVal(rb));
                    SET(res, "A");
                    break;

                case 0x5:
                    int a = Integer.parseInt(Stack.pop());
                    String reg = Stack.pop();

                    SET(a, reg);
                    break;

                case 0x6:
                    ra = Stack.pop();
                    rb = Stack.pop();

                    SET(regVal(ra), rb);
                    break;

                case 0x7:
                    int memAddr = Integer.parseInt(Stack.pop());
                    writeMem(memAddr, 0);
                    break;

                case 0x8:
                    ra = Stack.pop();
                    rb = Stack.pop();

                    skip = regVal(ra) != regVal(rb);
                    break;

                case 0x9:
                    ra = Stack.pop();
                    rb = Stack.pop();

                    skip = regVal(ra) == regVal(rb);
                    break;

                case 0xA:
                    int nPC = Integer.parseInt(Stack.pop());
                    PC = nPC;
                    continue;

                case 0xB:
                    memAddr = Integer.parseInt(Stack.pop());
                    writeMem(memAddr, regVal("A"));
                    break;

                case 0xC:
                    memAddr = Integer.parseInt(Stack.pop());
                    SET(readMem(memAddr), "A");
                    break;

                case 0xD:
                    memAddr = Integer.parseInt(Stack.pop());
                    hwd[memAddr].request(this);
                    break;

                case 0xFF:
                    execute = false;
                    g.cpuRunning = false;
                    break;

            }

            PC++;

            lastTime = System.currentTimeMillis();
        }
    }

    public void updateTest(int test, String result, boolean passed){
        g.updateTestResults(test, result, passed);
    }

    public void SET(int a, String reg){

        if(a<0){
            a = 65535+a;
        }
        a%=65536;

        if(reg.toUpperCase().equals("A"))
            A = a;
        if(reg.toUpperCase().equals("B"))
            B = a;
        if(reg.toUpperCase().equals("C"))
            C = a;
        if(reg.toUpperCase().equals("X"))
            X = a;
        if(reg.toUpperCase().equals("Y"))
            Y = a;
        if(reg.toUpperCase().equals("Z"))
            Z = a;
        if(reg.toUpperCase().equals("M"))
            M = a;
    }

    public void setFReg(int a){
        F = a;
    }

    public synchronized int regVal(String reg){
            if(reg.equals("A"))
                return A;
            if(reg.equals("B"))
                return B;
            if(reg.equals("C"))
                return C;
            if(reg.equals("X"))
                return X;
            if(reg.equals("Y"))
                return Y;
            if(reg.equals("Z"))
                return Z;
            if(reg.equals("F"))
                return F;


        return 0;

    }

    public void endTesting(){
        g.endTesting();
    }

    private void writeMem(int address, int val){
        mem[M][address] = val;
    }

    private int readMem(int address){
        return mem[M][address];
    }

    private int readProgMem(int address){
        return mem[0][address];
    }


}
