package com.mgzdev.nbt;

import com.mgzdev.nbt.tags.*;

import java.io.Closeable;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;

/**
 * Created by morfeus on 2015-07-21.
 */
public class NBTOutputStream implements Closeable{

    private DataOutputStream os;

    public NBTOutputStream(final OutputStream os) throws IOException{
        this.os = new DataOutputStream(new GZIPOutputStream(os));
    }

    public NBTOutputStream(OutputStream os, final boolean gzipped) throws IOException {
        if (gzipped) {
            os = new GZIPOutputStream(os);
        }
        this.os = new DataOutputStream(os);
    }

    public void writeTag(final Tag tag) throws IOException{
        System.out.println(tag.getClass().getName());
//        String tagName = tag.getName();
//        final byte[] nameBytes = tagName.getBytes(Constants.CHARSET);

        if(tag.getType().equals(Constants.Type.END))throw new IOException("Named end tags are not allowed!");

        os.writeByte(tag.getType().getID());
        if(tag.getName()!=null && !tag.getName().equals("")){
            byte[] bytes = tag.getName().getBytes();
            os.writeShort((short) bytes.length);
            os.write(bytes);
        }

        writePayload(tag);

    }


    private void writePayload(Tag t) throws IOException{
        switch(t.getType()){

            case BYTE:
                bytePayload((TagByte)t);
                break;
            case SHORT:
                shortPayload((TagShort)t);
                break;
            case INT:
                intPayload((TagInt)t);
                break;
            case LONG:
                longPayload((TagLong)t);
                break;
            case FLOAT:
                floatPayload((TagFloat)t);
                break;
            case DOUBLE:
                doublePayload((TagDouble)t);
                break;
            case BYTE_ARR:
                byteArrayPayload((TagByteArray)t);
                break;
            case STRING:
                stringPayload((TagString)t);
                break;
            case LIST:
                listPayload((TagList)t);
                break;
            case COMPOUND:
                compoundPayload((TagCompound)t);
                break;
            case INT_ARR:
                intArrayPayload((TagIntArray)t);
                break;

            default:
                throw new IOException("Invalid tag type: " + t.getType() + "!");
        }
    }

    private void bytePayload(TagByte t) throws IOException{
        os.writeByte(t.getPayload());
    }

    private void shortPayload(TagShort t) throws IOException{
        os.writeShort(t.getPayload());
    }

    private void intPayload(TagInt t) throws IOException{
        os.writeInt(t.getPayload());
    }

    private void longPayload(TagLong t) throws IOException{
        os.writeLong(t.getPayload());
    }

    private void floatPayload(TagFloat t) throws IOException{
        os.writeFloat(t.getPayload());
    }

    private void doublePayload(TagDouble t) throws IOException{
        os.writeDouble(t.getPayload());
    }

    private void byteArrayPayload(TagByteArray t) throws IOException{
        os.writeInt(t.getPayload().length);
        os.write(t.getPayload());
    }

    private void stringPayload(TagString t) throws IOException{
        byte[] bytes = t.getPayload().getBytes();
        os.writeShort((short) bytes.length);
        os.write(bytes);
    }

    private void listPayload(TagList t) throws IOException{
        byte type = t.getListType().getID();
        int length = t.getPayload().size();

        os.writeByte(type);
        os.writeInt(length);

        for(Tag p : t.getPayload()){
            writePayload(p);
        }

    }

    private void compoundPayload(TagCompound t) throws IOException{
        for(Tag p:t.getPayload().values()){
            writeTag(p);
        }
        os.writeByte(0);
    }

    private void intArrayPayload(TagIntArray t) throws IOException{
        os.writeInt(t.getPayload().length);
        for(int i : t.getPayload()){
            os.writeInt(i);
        }
    }

    @Override
    public void close() throws IOException {
        os.close();
    }
}
