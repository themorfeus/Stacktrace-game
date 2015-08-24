package com.mgzdev.spc.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.mgzdev.spc.SPC;

import java.util.ArrayList;

/**
 * Created by morf on 22.08.2015.
 */
public class Editor implements InputProcessor{

    private int x, y;
    private int width, height;

    private int offsY = 0;

    private Sound pop;

    private ArrayList<String> lines = new ArrayList<String>();

    private int currentLine = 0;
    private int caretPos = 0;
    private SPC s;


    public Editor(SPC s, int x, int y, int width, int height){

        this.s = s;

        Gdx.input.setInputProcessor(this);
        this.x = x;
        this.y = y;

        this.width = width;
        this.height = height;

        lines.add("");
        this.pop = s.getAssets().getSound("pop.wav");

    }


    private static final String CARET = "|";

    private int tick = 0;
    private boolean showCaret = true;

    private boolean drawOverlay = false;
    private boolean active = false;

    private Texture fill;

    public String getCode(){
        String temp = "";

        for(String s:lines){
            temp+=s+"\n";
        }

        return temp;
    }

    public void render(SpriteBatch b){

        if(fill==null)fill = SPC.getAssets().getTexture("fill.png");
        tick++;

        if(tick%30==0)showCaret = !showCaret;

        int lineY = y + height;

        for(int i = 0; i<lines.size(); i++){

            //System.out.println(lineY);
            String s = lines.get(i);

            SPC.getFont(24).setColor(1, 1, 1, 1);
            BitmapFont.TextBounds bnd = SPC.getFont(24).drawWrapped(b, s, x, lineY, width);

            if(i == currentLine){
                if(showCaret)SPC.getFont(24).draw(b, CARET, x + caretPos -2, lineY);
            }

            float oy = Math.max((bnd.height)+5, (SPC.getFont(24).getLineHeight())+5);
            lineY-=oy;


            b.setColor(1,1,1,.2f);
            b.draw(fill, x, lineY, width, 1);
            b.setColor(1,1,1,1);

        }

        if(active){

            b.setColor(1,1,1,.5f * (active?1.5f:1));
            b.draw(fill, x, y, 5, 5);
            b.setColor(1,1,1,1);
        }
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    static private final char BACKSPACE = '\b';
    static private final char ENTER = '\r';

    @Override
    public boolean keyTyped(char character) {

        if(!active)return false;

        String total = lines.get(currentLine);
        char[] arr = total.toCharArray();

        pop.play(.5f);

        switch(character){
            case(BACKSPACE):
                if(caretPos>0)caretPos-= SPC.getFont(24).getBounds(arr[total.length()-1] +"").width;
                if(total.length()>=1)
                total = total.substring(0, total.length()-1);
                else{
                    if(currentLine==0)break;
                    currentLine--;
                    total = lines.get(currentLine);
                    lines.remove(currentLine+1);
                    caretPos = 0;

                    arr = total.toCharArray();

                    for(char c: arr){
                        caretPos+= SPC.getFont(24).getBounds(c+"").width;
                    }
                }
                break;

            case(ENTER):
                currentLine++;
                if(currentLine>18){
                    currentLine = 18;
                    break;
                }
                caretPos = 0;
                lines.add(currentLine, "");
                total = lines.get(currentLine);
                break;
//            case(Input.Keys.SPACE):
//                total+=" ";
//                break;

            default:
                if(total.length()>22)break;
                if(!SPC.getFont(24).containsCharacter(character))break;
                String charc = (character + "").toUpperCase();
                total+=charc;
                caretPos+= SPC.getFont(24).getBounds(charc+"").width;
                break;
        }






        lines.set(currentLine, total);
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        Vector3 mouse = new Vector3(screenX, screenY, 0);

        s.cam.unproject(mouse);

        if(mouse.x>x && mouse.x<x+width && mouse.y>y && mouse.y<y+height)active = true;
        else active = false;

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

        Vector3 mouse = new Vector3(screenX, screenY, 0);

        s.cam.unproject(mouse);

        if(mouse.x>x && mouse.x<x+width && mouse.y>y && mouse.y<y+height){
            drawOverlay = true;
        }else{
            drawOverlay = false;
        }

        return false;
    }


}
