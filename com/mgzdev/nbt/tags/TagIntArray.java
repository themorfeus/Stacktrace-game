package com.mgzdev.nbt.tags;

import com.mgzdev.nbt.Constants;

/**
 * Created by morfeus on 2015-07-20.
 */
public class TagIntArray extends Tag{

    private final int[] value;

    public TagIntArray(final String name, final int
            [] value) {
        super(name);
        this.value = value;
    }

    @Override
    public int[] getPayload() {
        return value;
    }

    @Override
    public Constants.Type getType() {
        return Constants.Type.INT_ARR;
    }

    @Override
    public String toString(){

        String ts = "TAG_INT_ARRAY(" + getName() + "){";

        for(int i = 0; i<value.length; i++){
            ts+= value[i] + (i==value.length-1?"}":",");
        }

        return ts;
    }
}
