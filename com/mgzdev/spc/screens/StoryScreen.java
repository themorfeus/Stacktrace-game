package com.mgzdev.spc.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.mgzdev.scpu.MemBanks;
import com.mgzdev.scpu.SCPU;
import com.mgzdev.scpu.tests.*;
import com.mgzdev.spc.SPC;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by morf on 23.08.2015.
 */
public class StoryScreen implements Screen {

    private SPC s;

    private int step;
    private final int maxSteps;

    private int id = 0;

    private String[] text;

    private long timeStarted;

    private Texture[] screens;

    private Texture fill;
    private Texture cont;

    private Sound e1, e2, e3, noise, next;

    public StoryScreen(SPC s, int id){
        this.s = s;
        this.id = id;
        String[] bank = new String[]{};
        switch(this.id){
            case(0):
                bank = MemBanks.start;
                break;
            case(1):
                 bank = MemBanks.first;
                break;
            case(2):
                 bank = MemBanks.second;
                break;
            case(3):
                 bank = MemBanks.third;
                break;
            case(4):
                 bank = MemBanks.fourth;
                break;
            case(5):
                 bank = MemBanks.fifth;
                break;
            case(6):
                 bank = MemBanks.sixth;
                break;
            case(7):
                 bank = MemBanks.seventh;
                break;
            case(8):
                 bank = MemBanks.eighth;
                break;
            case(9):
                 bank = MemBanks.nineth;
                break;
        }

        maxSteps = bank.length + ((id==0)?0:1);
        text = new String[maxSteps];
        screens = new Texture[maxSteps];

        int lastText = 0;
        for(int i = 0; i<bank.length; i++){
            text[i] = bank[i].substring(2);
            int tid = Integer.parseInt(bank[i].substring(0, 1));
            if(tid==lastText){
                screens[i] = screens[i-1];
                continue;
            }

            screens[i] = SPC.getAssets().getTexture(id + "/" + tid + ".png");
        }


        if(id!=0) {
            screens[maxSteps - 1] = screens[maxSteps - 2];
            text[maxSteps - 1] = "Passcode for test " + (id+1) + ":" + MemBanks.passcodes[id-1];
        }
        fill = SPC.getAssets().getTexture("fill.png");
        cont = SPC.getAssets().getTexture("cont.png");

        e1 = SPC.getAssets().getSound("e1.wav");
        e2 = SPC.getAssets().getSound("e2.wav");
        e3 = SPC.getAssets().getSound("e3.wav");
        noise = SPC.getAssets().getSound("noise.wav");
        next = SPC.getAssets().getSound("next.wav");
    }

    private float nAlpha = -.5f;
    private boolean launched = false;
    private int snd = 0;

    @Override
    public void render(float delta){
        if(timeStarted == 0)timeStarted = System.currentTimeMillis();

        s.batch.draw(screens[step], 0, 0, SPC.VIRTUAL_WIDTH, SPC.VIRTUAL_HEIGHT);

        s.batch.setColor(0, 0, 0, .5f);
        s.batch.draw(fill, 0, 0, SPC.VIRTUAL_WIDTH, 150);
        s.batch.setColor(1, 1, 1, 1);

        SPC.getFont(30).setColor(1, 1, 1, 1);
        SPC.getFont(30).drawWrapped(s.batch, text[step], 0, 100, SPC.VIRTUAL_WIDTH, BitmapFont.HAlignment.CENTER);


        if(System.currentTimeMillis()-timeStarted > 500 && step<maxSteps && !launched){
            SPC.getFont(25).draw(s.batch, "enter>", SPC.VIRTUAL_WIDTH - SPC.getFont(25).getBounds("enter>").width-10, 15);
            if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)){
                next.play(.3f);
                timeStarted = 0;
                step++;
            }
        }

        if(step>=maxSteps && this.id==9 && !launched){
            timeStarted = 0;
            step--;
            launched = true;
        }

        if(step>=maxSteps && this.id!=9){
            s.setScreen(new GameScreen(s, this.id));
        }

        if(launched){
            nAlpha+=0.005f;

            if(new Random().nextInt(10) == 0){
                int i = snd%3;
                snd++;
                switch(i){
                    case(0):
                        e1.play(.5f, 1, new Random().nextFloat() * (new Random().nextInt(2)==0?1:-1));
                        break;
                    case(1):
                        e2.play(.5f, 1, new Random().nextFloat() * (new Random().nextInt(2)==0?1:-1));
                        break;
                    case(2):
                        e3.play(.5f, 1, new Random().nextFloat() * (new Random().nextInt(2)==0?1:-1));
                        break;
                }
            }

            float drawAlpha = nAlpha;
            if(drawAlpha<0)drawAlpha = 0;
            if(drawAlpha>1)drawAlpha = 1;

            s.batch.setColor(1, 1, 1, drawAlpha);
            s.batch.draw(fill, 0, 0, SPC.VIRTUAL_WIDTH, SPC.VIRTUAL_HEIGHT);
            s.batch.setColor(1,1,1,1);

            if(Math.abs(.7-nAlpha)<0.01f)noise.play(.5f);

            if(nAlpha>1.5)s.setScreen(new MenuScreen(s));
        }

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
