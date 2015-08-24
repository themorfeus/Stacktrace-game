package com.mgzdev.spc;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mgzdev.scpu.MemBanks;
import com.mgzdev.scpu.tests.M1Test;
import com.mgzdev.spc.screens.GameScreen;
import com.mgzdev.spc.screens.MenuScreen;
import com.mgzdev.spc.screens.StoryScreen;
import org.w3c.dom.Text;

import java.util.HashMap;

public class SPC extends Game {

	public static final int VIRTUAL_WIDTH = 960, VIRTUAL_HEIGHT=540;
    public static final String FONT_FILE = "Pixeltype.ttf";

    private static HashMap<Integer, BitmapFont> fonts;


	public SpriteBatch batch;
	public OrthographicCamera cam;

	public Viewport v;

    private static Assets assets;

    public SPC(){
    }

	public SPC(Assets a){
        this.assets = a;
    }

	@Override
	public void create () {

        if(assets==null)assets = new Assets();

		batch = new SpriteBatch();
		cam = new OrthographicCamera();

        cam.position.set(VIRTUAL_WIDTH/2,VIRTUAL_HEIGHT/2,0);

		v = new ExtendViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, cam);

        //this.setScreen(new GameScreen(this, new M1Test()));
        this.setScreen(new MenuScreen(this));
	}

	@Override
	public void render () {

		v.apply();
		cam.update();

		batch.setProjectionMatrix(cam.combined);

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		super.render();
		batch.end();
	}

	@Override
	public void resize(int w, int h){
		v.update(w, h);
	}

    public static Assets getAssets(){
        return assets;
    }

    public static BitmapFont getFont(int size){
        if(fonts==null) fonts = new HashMap<Integer, BitmapFont>();
        if(!fonts.containsKey(size)){
            FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.absolute(getAssets().getFont(SPC.FONT_FILE)));
            FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
            parameter.size = size;
            parameter.magFilter = Texture.TextureFilter.Nearest;
            parameter.minFilter = Texture.TextureFilter.Nearest;
            fonts.put(size, generator.generateFont(parameter));
            generator.dispose();
        }

        return fonts.get(size);
    }
}
