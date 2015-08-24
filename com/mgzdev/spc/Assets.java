package com.mgzdev.spc;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.mgzdev.nbt.NBTInputStream;
import com.mgzdev.nbt.tags.TagByteArray;
import com.mgzdev.nbt.tags.TagCompound;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

/**
 * Created by morfeus on 2015-07-28.
 */
public class Assets {

    private TagCompound assets;

    private TagCompound sounds;
    private TagCompound images;
    private TagCompound icons;

    public Assets(){
        FileHandle f;
        if(Gdx.files!=null)f = Gdx.files.internal("assets.pak");
        else f = new FileHandle("assets.pak");

        System.out.println("[Assets] Loading assets from " + f.file().getAbsolutePath() + "...");

        NBTInputStream nis;
        TagCompound t = null;
        try {
            nis = new NBTInputStream(f.read());

            if(nis==null){
                System.err.println("[Assets] Input stream is null!");
                return;
            }

            t = (TagCompound)nis.readTag();

            nis.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        if(t==null){
            System.err.println("[Assets] Failed to read the file!");
            return;
        }

        this.assets = t;

        this.sounds = (TagCompound)assets.getTag("snd");
        this.images = (TagCompound)assets.getTag("img");
        this.icons = (TagCompound)assets.getTag("icons");

        System.out.println("[Assets] Done loading.");
    }

    int order = 0;

    public TagCompound getTag(String parent, String name){
        if(name==null)return (TagCompound) assets.getTag(parent);
        return (TagCompound) ((TagCompound)assets.getTag(parent)).getTag(name);
    }

    public Sound getSound(String name){

        String[] path = name.split("/");
        if(path.length<2)return getSound(name, sounds);

        TagCompound parent = (TagCompound)sounds.getTag(path[0]);
        return getSound(path[1], parent);
    }

    public Sound getSound(String name, TagCompound parent){
        order++;

        name = name.toLowerCase();

        String prefix = (999+new Random().nextInt(9000)) + "" + order;
        String suffix = new Random().nextInt(999) + ".wav";

        try {
            File tempFile = File.createTempFile(prefix, suffix, null);
            FileOutputStream fos = new FileOutputStream(tempFile);

            byte[] sound = ((TagByteArray)parent.getTag(name)).getPayload();

            fos.write(sound);
            fos.close();

            Sound s =  Gdx.audio.newSound(Gdx.files.absolute(tempFile.getAbsolutePath()));

            tempFile.delete();

            return s;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Texture getTexture(String name){

        String[] path = name.split("/");
        if(path.length<2)return getTexture(name, images);

        TagCompound parent = (TagCompound)images.getTag(path[0]);
        return getTexture(path[1], parent);
    }

    private Texture getTexture(String name, TagCompound parent){
        order++;

        name = name.toLowerCase();
        try {
            byte[] texture = ((TagByteArray)parent.getTag(name)).getPayload();
            return new Texture(new Pixmap(texture, 0, texture.length));
        } catch (Exception e) {
            System.err.println("[Assets.getTexture] Texture not found! " + name);
            e.printStackTrace();
            return null;
        }
    }


    public String getIcon(String name){
        order++;

        name = name.toLowerCase();

        String prefix = (999+new Random().nextInt(9000)) + "" + order;
        String suffix = new Random().nextInt(999) + ".png";

        try {
            File tempFile = File.createTempFile(prefix, suffix, null);
            FileOutputStream fos = new FileOutputStream(tempFile);

            byte[] icon = ((TagByteArray)icons.getTag(name)).getPayload();

            fos.write(icon);
            fos.close();

            tempFile.deleteOnExit();
            return tempFile.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getFont(String name){
        order++;

        name = name.toLowerCase();

        String prefix = (999+new Random().nextInt(9000)) + "" + order;
        String suffix = new Random().nextInt(999) + ".ttf";

        try {
            File tempFile = File.createTempFile(prefix, suffix, null);
            FileOutputStream fos = new FileOutputStream(tempFile);

            byte[] font = ((TagByteArray)assets.getTag(name)).getPayload();

            fos.write(font);
            fos.close();

            tempFile.deleteOnExit();
            return tempFile.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
