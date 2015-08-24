package com.mgzdev.spc.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.mgzdev.spc.SPC;

/**
 * Created by morf on 23.08.2015.
 */
public class MenuBtn {

    private int x, y, w, h;

    private boolean clicked = false;

    private Texture fill;
    private SPC s;
    private boolean justReleased = false;

    private String text;

    private Sound pop;

    public MenuBtn(SPC s, String text, int x, int y, int w, int h){
        this.text = text;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;

        this.s = s;
        this.fill = SPC.getAssets().getTexture("fill.png");
        this.pop = s.getAssets().getSound("pop.wav");
    }


    public void render(SpriteBatch b){
        justReleased = false;

        Vector3 mouse = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);

        mouse = s.cam.unproject(mouse);

        boolean inside = mouse.x >= this.x && mouse.y > this.y && mouse.x<(this.x + this.w) && mouse.y < (this.y + this.h);

        if(inside){
            float alpha = Gdx.input.isButtonPressed(Input.Buttons.LEFT)?1:.5f;
            s.batch.setColor(1, 1, 1, alpha);
            s.batch.draw(fill, x, y, w, h);
        }

        if(inside && Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
            if(!clicked){
                pop.play(.5f);
            }

            clicked = true;
        }else{
            if(!Gdx.input.isButtonPressed(Input.Buttons.LEFT) && clicked){
                justReleased = true;
                pop.play(.5f);
            }
            clicked = false;
        }

        BitmapFont.TextBounds tb = SPC.getFont(50).getBounds(text);

        float tx = this.w/2 - tb.width/2 + this.x;
        float ty = this.h/2 + tb.height/2 + this.y;

        if(inside)SPC.getFont(50).setColor(0, 0, 0, 1);
        SPC.getFont(50).draw(s.batch, text, tx, ty);

        SPC.getFont(50).setColor(1, 1, 1, 1);

    }

    public boolean isJustReleased(){
        return justReleased;
    }
}
