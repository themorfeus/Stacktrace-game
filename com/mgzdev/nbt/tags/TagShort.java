package com.mgzdev.nbt.tags;

import com.mgzdev.nbt.Constants;

/**
 * Created by morfeus on 2015-07-20.
 */
public class TagShort extends Tag{

    private final short value;

    public TagShort(final String name, final short value) {
        super(name);
        this.value = value;
    }

    @Override
    public Short getPayload() {
        return value;
    }
    @Override
    public Constants.Type getType() {
        return Constants.Type.SHORT;
    }
    @Override
    public String toString(){
        return "TAG_SHORT(" + getName() + "): " + value;
    }
}
