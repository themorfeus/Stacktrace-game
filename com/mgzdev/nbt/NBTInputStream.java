package com.mgzdev.nbt;

import com.mgzdev.nbt.tags.*;

import java.io.Closeable;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.zip.GZIPInputStream;

/**
 * Created by morfeus on 2015-07-21.
 */
public class NBTInputStream implements Closeable {

    private final DataInputStream is;

    public boolean print = false;

    public NBTInputStream(InputStream is, final boolean gzipped) throws IOException {
        if (gzipped) {
            is = new GZIPInputStream(is);
        }
        this.is = new DataInputStream(is);
    }

    public NBTInputStream(final InputStream is) throws IOException {
        this.is = new DataInputStream(new GZIPInputStream(is));
    }

    public Tag readTag() throws IOException{
        return readTag(0, -1);
    }

    private Tag readTag(int depth, int t) throws IOException{
        return readTag(depth, t, true);
    }

    private Tag readTag(int depth, int t, boolean readName) throws IOException{

        if(t==-1) t = is.readByte() & 0xFF;

        final int type = t;

        //System.out.println(Constants.getTypeByID(type));

        String name;

        if(type==0 || !readName){
            name="";
        }else{
            short length = is.readShort();
            //System.out.println("L:"+length);
            byte[] bytes = new byte[length];

            is.readFully(bytes);

            name = new String(bytes);
            //System.out.println(name);
        }

        switch (type){

            case 0:
                if(depth==0)throw new IOException("End tag without preceeding Compound!");
                return new TagEnd();
            case 1:
                return bytePayload(name, depth);
            case 2:
                return shortPayload(name, depth);
            case 3:
                return intPayload(name, depth);
            case 4:
                return longPayload(name, depth);
            case 5:
                return floatPayload(name, depth);
            case 6:
                return doublePayload(name, depth);
            case 7:
                return byteArrayPayload(name, depth);
            case 8:
                return stringPayload(name, depth);
            case 9:
                return listPayload(name, depth);
            case 10:
                return compoundPayload(name, depth);
            case 11:
                return intArrayPayload(name, depth);
            default:
                return new TagEnd();

        }

    }

    private TagByte bytePayload(String name, int depth) throws IOException{
        byte b = is.readByte();
        return new TagByte(name, b);
    }

    private TagShort shortPayload(String name, int depth) throws IOException{
        short s = is.readShort();
        return new TagShort(name, s);
    }

    private TagInt intPayload(String name, int depth) throws IOException{
        int i = is.readInt();
        return new TagInt(name, i);
    }

    private TagLong longPayload(String name, int depth) throws IOException{
        long l = is.readLong();
        return new TagLong(name, l);
    }

    private TagFloat floatPayload(String name, int depth) throws IOException{
        float f = is.readFloat();
        return new TagFloat(name, f);
    }

    private TagDouble doublePayload(String name, int depth) throws IOException{
        double d = is.readDouble();
        return new TagDouble(name, d);
    }

    private TagByteArray byteArrayPayload(String name, int depth) throws IOException{
        int length = is.readInt();
        byte[] bytes = new byte[length];

        is.readFully(bytes);
        return new TagByteArray(name, bytes);
    }

    private TagString stringPayload(String name, int depth) throws IOException{
        short length = is.readShort();
        byte[] bytes = new byte[length];

        is.readFully(bytes);
        return new TagString(name, new String(bytes));
    }

    private TagList listPayload(String name, int depth) throws IOException{
        byte tagID = is.readByte();
        int length = is.readInt();

        //System.out.println("LC: " + Constants.getTypeByID(tagID) + " LL: " + length);

        List<Tag> tags = new ArrayList<Tag>();

        for(int i = 0; i<length; i++){
            tags.add(readTag(depth + 1, tagID, false));
        }

        return new TagList(name, tagID, tags);
    }

    private TagCompound compoundPayload(String name, int depth) throws IOException{

        HashMap<String, Tag> payload = new HashMap<String, Tag>();

        while(true){
            final Tag tag = readTag(depth+1, -1);
            if(tag instanceof TagEnd){
                break;
            }else{
                payload.put(tag.getName(), tag);
            }
        }

        return new TagCompound(name, payload);
    }

    private TagIntArray intArrayPayload(String name, int depth) throws IOException{
        int length = ((TagInt)readTag()).getPayload();
        int[] ints = new int[length];

        for(int i = 0; i<length; i++){
            ints[i] = is.readInt();
        }

        return new TagIntArray(name, ints);
    }


    @Override
    public void close() throws IOException {
        is.close();
    }
}
