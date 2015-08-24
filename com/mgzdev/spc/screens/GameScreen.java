package com.mgzdev.spc.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.utils.ScissorStack;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mgzdev.scpu.SCPU;
import com.mgzdev.scpu.tests.*;
import com.mgzdev.spc.SPC;
import com.mgzdev.spc.objects.Editor;
import com.mgzdev.spc.objects.RunBtn;

/**
 * Created by morf on 22.08.2015.
 */
public class GameScreen implements Screen {

    public static final int TESTS = 100;

    private SPC s;

    private Texture background;
    private Texture fill;

    private Editor e;

    private int[] registers = new int[8];
    private int pc = 0;

    private String[] testResults = new String[TESTS];
    private boolean[] passed = new boolean[TESTS];

    private final int test;

    private RunBtn runBtn;

    private boolean switching;

    private final int testID;
    private Test t;

    public GameScreen(SPC s, int tid){
        this.s = s;
        this.background = SPC.getAssets().getTexture("codescreen.png");
        this.fill = SPC.getAssets().getTexture("fill.png");
        this.e = new Editor(this.s, 66, 74, 248, 392);
        this.test = tid+1;
        this.runBtn = new RunBtn(this.s, 347, 114, 160, 80);


        testID = tid;

        switch(testID){
            case(1):
                t = new M2Test();
                break;
            case(2):
                t = new M3Test();
                break;
            case(3):
                t = new M4Test();
                break;
            case(4):
                t = new M5Test();
                break;
            case(5):
                t = new M6Test();
                break;
            case(6):
                t = new M7Test();
                break;
            case(7):
                t = new M8Test();
                break;
            case(8):
                t = new M9Test();
                break;
            default:
                t = new M1Test();
                break;
        }
    }

    public synchronized void updateRegisters(int[] regs){
        registers = regs;
    }

    public synchronized void updateTestResults(int test, String result, boolean passedTest){
        testResults[test] = result;
        passed[test] = passedTest;
    }
    public synchronized void updateProgramCounter(int pc){
        this.pc = pc;
    }

    private Thread cpuThread;
    @Override
    public void render(float delta) {

        drawCodeEditor();

        int lineY = 170;

        for(int i = 0; i<testResults.length; i++){
            if(testResults[i]!=null){
                lineY+=30;
            }
        }

        for(int i = testResults.length-1; i>=0; i--){
            if(testResults[i]!=null)s.getFont(30).draw(s.batch, "ANS" + i + ": " + testResults[i], 687, lineY - 30 * i);
        }

        s.batch.draw(background, 0, 0, SPC.VIRTUAL_WIDTH, SPC.VIRTUAL_HEIGHT);

//      351 466 152 194


        //341 66
        s.getFont(30).draw(s.batch, "REG    A    " + registers[0], 361, 460);
        s.getFont(30).draw(s.batch, "REG    B    " + registers[1], 361, 430);
        s.getFont(30).draw(s.batch, "REG    C    " + registers[2], 361, 400);

        s.getFont(30).draw(s.batch, "REG    X    " + registers[3], 361, 370);
        s.getFont(30).draw(s.batch, "REG    Y    " + registers[4], 361, 340);
        s.getFont(30).draw(s.batch, "REG    Z    " + registers[5], 361, 310);

        s.getFont(30).draw(s.batch, "REG    M    " + registers[6], 361, 280);
        s.getFont(30).draw(s.batch, "REG    F    " + registers[7], 361, 250);

        s.getFont(30).draw(s.batch, "PC:      " + pc, 361, 87);


// 300 48

        s.getFont(30).drawWrapped(s.batch, "Test " + test, 300, 45, 172, BitmapFont.HAlignment.CENTER);

        runBtn.render(s.batch);

        //714 445

        if(!cpuRunning){

            int cnt = 0;
            int correct = 0;

            for(String s:testResults){
                if(s==null)continue;
                cnt++;
            }

            for(int i = 0; i<cnt; i++){
                if(passed[i])correct++;
            }

            if(cnt!=0){
                s.getFont(30).drawWrapped(s.batch, correct + "/" + cnt, 714, 470, 172, BitmapFont.HAlignment.CENTER);
            }
        }

        if(runBtn.isJustReleased() && !cpuRunning && !switching){

            final GameScreen g = this;

            cpuThread = new Thread(new Runnable(){

                @Override
                public void run() {
                    cpuRunning = true;
                    try {
                        SCPU cpu = new SCPU(g, t);

                        String code = e.getCode();

                        cpu.load(code);
                        cpu.execute();
                    }catch(Exception e){
                        cpuRunning = false;

                        System.out.println("Exception!" + e.getMessage());
                        g.updateProgramCounter(-1);
                    }
                }
            });

            if(!cpuRunning)cpuThread.start();

        }

        float drawAlpha = oAlpha;
        if(drawAlpha<0)drawAlpha = 0;
        if(drawAlpha>1)drawAlpha = 1;

        if(switching){

            oAlpha+=0.05f;

            if(oAlpha>1.5f){
                s.setScreen(new StoryScreen(s, this.testID+1));
            }
        }
        if(entering){

            oAlpha-=0.05f;

            s.batch.setColor(0, 0, 0, drawAlpha);
            s.batch.draw(fill, 0, 0, SPC.VIRTUAL_WIDTH, SPC.VIRTUAL_HEIGHT);
            s.batch.setColor(1,1,1,1);

            if(oAlpha<-1f){
                entering = false;
            }
        }


        s.batch.setColor(0, 0, 0, drawAlpha);
        s.batch.draw(fill, 0, 0, SPC.VIRTUAL_WIDTH, SPC.VIRTUAL_HEIGHT);
        s.batch.setColor(1,1,1,1);
    }

    private boolean entering = true;
    private float oAlpha = 1.5f;
    public boolean cpuRunning = false;

    public void endTesting(){
        boolean sw = true;
        for(int i = 0; i<TESTS; i++){
            if(testResults[i]==null)continue;
            if(!passed[i]){
                sw = false;
                break;
            }
        }
        switching = sw;
    }

    private void drawCodeEditor(){
        e.render(s.batch);
    }

    @Override
    public void show() {}
    @Override
    public void resize(int width, int height) {}
    @Override
    public void pause() {}
    @Override
    public void resume() {}
    @Override
    public void hide() {}
    @Override
    public void dispose() {}
}
