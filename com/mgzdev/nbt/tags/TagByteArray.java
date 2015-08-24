package com.mgzdev.nbt.tags;

import com.mgzdev.nbt.Constants;

/**
 * Created by morfeus on 2015-07-20.
 */
public class TagByteArray extends Tag{

    private final byte[] value;

    public TagByteArray(final String name, final byte[] value) {
        super(name);
        this.value = value;
    }

    @Override
    public byte[] getPayload() {
        return value;
    }

    @Override
    public Constants.Type getType() {
        return Constants.Type.BYTE_ARR;
    }


    @Override
    public String toString(){

        String ts = "TAG_BYTE_ARRAY(" + getName() + "){";

        for(int i = 0; i<value.length; i++){
            ts+= value[i] + (i==value.length-1?"}":",");
        }

        return ts;
    }
}
