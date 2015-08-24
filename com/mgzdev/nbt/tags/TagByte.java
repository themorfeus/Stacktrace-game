package com.mgzdev.nbt.tags;

import com.mgzdev.nbt.Constants;

/**
 * Created by morfeus on 2015-07-20.
 */
public class TagByte extends Tag{

    private final byte value;

    public TagByte(final String name, final byte value) {
        super(name);
        this.value = value;
    }

    @Override
    public Byte getPayload() {
        return value;
    }

    @Override
    public Constants.Type getType() {
        return Constants.Type.BYTE;
    }

    @Override
    public String toString(){
        return "TAG_BYTE(" + getName() + "): " + value;
    }
}
