package com.mgzdev.spc.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.mgzdev.spc.SPC;

/**
 * Created by morf on 23.08.2015.
 */
public class RunBtn {

    private int x, y, w, h;

    private boolean clicked = false;

    private Texture atlas;
    private TextureRegion up;
    private TextureRegion down;
    private SPC s;
    private boolean justReleased = false;

    public RunBtn(SPC s, int x, int y, int w, int h){
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;

        this.s = s;

        //347, 194, 160, 80
        this.atlas = SPC.getAssets().getTexture("runbtn.png");
        this.up = new TextureRegion(atlas, 0, 0, 20, 10);
        this.down = new TextureRegion(atlas, 0, 10, 20, 10);
    }


    public void render(SpriteBatch b){
        justReleased = false;

        Vector3 mouse = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);

        mouse = s.cam.unproject(mouse);

        boolean inside = mouse.x >= this.x && mouse.y > this.y && mouse.x<(this.x + this.w) && mouse.y < (this.y + this.h);

        if(inside && Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
            clicked = true;
            b.draw(down,x,y,w,h);
        }else{
            if(!Gdx.input.isButtonPressed(Input.Buttons.LEFT) && clicked){
                justReleased = true;
            }
            b.draw(up,x,y,w,h);
            clicked = false;
        }

    }

    public boolean isJustReleased(){
        return justReleased;
    }
}
