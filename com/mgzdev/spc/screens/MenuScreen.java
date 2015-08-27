package com.mgzdev.spc.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.mgzdev.nbt.NBTInputStream;
import com.mgzdev.nbt.NBTOutputStream;
import com.mgzdev.nbt.tags.TagCompound;
import com.mgzdev.nbt.tags.TagEnd;
import com.mgzdev.nbt.tags.TagInt;
import com.mgzdev.nbt.tags.TagIntArray;
import com.mgzdev.scpu.MemBanks;
import com.mgzdev.spc.SPC;
import com.mgzdev.spc.objects.MenuBtn;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Random;

/**
 * Created by morf on 23.08.2015.
 */
public class MenuScreen implements Screen{

    private SPC s;

    private Texture menu;

    private MenuBtn newGame;
    private MenuBtn passBtn;
    private MenuBtn exit;

    public MenuScreen(SPC s){
        this.s = s;

        menu = SPC.getAssets().getTexture("menu.png");

        newGame = new MenuBtn(this.s, "new game", 330,198,300,60);
        passBtn = new MenuBtn(this.s, "enter pass", 330,138,300,60);
        exit = new MenuBtn(this.s,    "exit game", 330,78,300,60);


        File passcodes = new File("data.d");
        if(!passcodes.exists()){
            generatePasscodes();
        }else{
            readPasscodes();
        }
    }


    //Hope you're not here to cheat ;)
    private void generatePasscodes(){
        for(int i = 0; i<9; i++){
            MemBanks.passcodes[i] = new Random().nextInt(90000) + 10000;
        }
        storePasscodes();
    }

    private void storePasscodes(){
        TagCompound t = new TagCompound("save");
        FileHandle f = Gdx.files.local("data.d");

        //TagIntArray tia = new TagIntArray("passcodes",MemBanks.passcodes);
        //t.addTag(tia);
        
        for (int i = 0; i < 9; i++) {
            t.addTag(new TagInt("p" + Integer.toString(i++), MemBanks.passcodes[i]));
        }

        // TagInt p1 = new TagInt("p1", MemBanks.passcodes[0]);
        // TagInt p2 = new TagInt("p2", MemBanks.passcodes[1]);
        // TagInt p3 = new TagInt("p3", MemBanks.passcodes[2]);
        // TagInt p4 = new TagInt("p4", MemBanks.passcodes[3]);
        // TagInt p5 = new TagInt("p5", MemBanks.passcodes[4]);
        // TagInt p6 = new TagInt("p6", MemBanks.passcodes[5]);
        // TagInt p7 = new TagInt("p7", MemBanks.passcodes[6]);
        // TagInt p8 = new TagInt("p8", MemBanks.passcodes[7]);
        // TagInt p9 = new TagInt("p9", MemBanks.passcodes[8]);

        // t.addTag(p1);
        // t.addTag(p2);
        // t.addTag(p3);
        // t.addTag(p4);
        // t.addTag(p5);
        // t.addTag(p6);
        // t.addTag(p7);
        // t.addTag(p8);
        // t.addTag(p9);

        System.out.println(t.toString());
        try{
            NBTOutputStream nos = new NBTOutputStream(f.write(false));
            nos.writeTag(t);
            nos.close();
        }catch(Exception e){
            System.out.println("Could not store passcodes!");
            System.out.println();
            e.printStackTrace();
            System.out.println();
            System.exit(-1);
        }

    }

    private void readPasscodes(){
        TagCompound t = null;
        FileHandle f = Gdx.files.local("data.d");
        try{
            NBTInputStream nis = new NBTInputStream(f.read());
            nis.print = true;
            t = (TagCompound)nis.readTag();

            nis.close();
        }catch(Exception e){
            System.out.println("Could not read passcodes!");
            System.out.println();
            e.printStackTrace();
            System.out.println();
            System.exit(-1);
        }

        MemBanks.passcodes[0] = ((TagInt)t.getTag("p1")).getPayload();
        MemBanks.passcodes[1] = ((TagInt)t.getTag("p2")).getPayload();
        MemBanks.passcodes[2] = ((TagInt)t.getTag("p3")).getPayload();
        MemBanks.passcodes[3] = ((TagInt)t.getTag("p4")).getPayload();
        MemBanks.passcodes[4] = ((TagInt)t.getTag("p5")).getPayload();
        MemBanks.passcodes[5] = ((TagInt)t.getTag("p6")).getPayload();
        MemBanks.passcodes[6] = ((TagInt)t.getTag("p7")).getPayload();
        MemBanks.passcodes[7] = ((TagInt)t.getTag("p8")).getPayload();
        MemBanks.passcodes[8] = ((TagInt)t.getTag("p9")).getPayload();
    }

    @Override
    public void render(float delta){

        s.batch.setColor(1,1,1,1);
        s.batch.draw(menu,0,0,SPC.VIRTUAL_WIDTH, SPC.VIRTUAL_HEIGHT);

        newGame.render(s.batch);
        passBtn.render(s.batch);
        exit.render(s.batch);


        if(newGame.isJustReleased()){
            s.setScreen(new StoryScreen(s, 0));
        }else if(passBtn.isJustReleased()){
            s.setScreen(new PasscodeScreen(s));
        }else if(exit.isJustReleased()){
            System.exit(0);
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
