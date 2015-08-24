package com.mgzdev.spc.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.mgzdev.scpu.MemBanks;
import com.mgzdev.spc.SPC;

/**
 * Created by morf on 24.08.2015.
 */
public class PasscodeScreen implements Screen, InputProcessor{

    private SPC s;

    public PasscodeScreen(SPC s){
        this.s = s;
        Gdx.input.setInputProcessor(this);
    }

    private String enterP = "Enter Passcode:";
    private String field = "_ _ _ _ _";

    private String displayCode = "";
    private String code = "";


    @Override
    public void render(float delta) {

        SPC.getFont(100).drawWrapped(s.batch, enterP, 0, 300, SPC.VIRTUAL_WIDTH, BitmapFont.HAlignment.CENTER);


        SPC.getFont(100).draw(s.batch, displayCode, SPC.VIRTUAL_WIDTH/2 - SPC.getFont(100).getBounds(field).width/2, 220);
        SPC.getFont(100).drawWrapped(s.batch, field, 0, 200, SPC.VIRTUAL_WIDTH, BitmapFont.HAlignment.CENTER);
        SPC.getFont(20).drawWrapped(s.batch, "type 00000 to exit to main menu", 0, 20, SPC.VIRTUAL_WIDTH, BitmapFont.HAlignment.CENTER);

    }

    @Override
    public boolean keyTyped(char character) {

        if(!isInteger(character+""))return false;

        code+=character;
        displayCode+=character + " ";

        if(code.length()>=5){
            int c = Integer.parseInt(code);

            if(c==0){
                s.setScreen(new MenuScreen(s));
                return false;
            }

            for(int i = 0; i<9; i++){
                if(c==MemBanks.passcodes[i]){
                    s.setScreen(new StoryScreen(s, i+1));
                    return false;
                }
            }
            displayCode = "";
            code = "";
        }

        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    @Override
    public void show() {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    public boolean isInteger(String s){
        try{
            int i = Integer.parseInt(s);
        }catch(Exception e){
            return false;
        }
        return true;
    }
}
