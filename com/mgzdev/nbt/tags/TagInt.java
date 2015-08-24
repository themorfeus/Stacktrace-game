package com.mgzdev.nbt.tags;

import com.mgzdev.nbt.Constants;

/**
 * Created by morfeus on 2015-07-20.
 */
public class TagInt extends Tag{

    private final int value;

    public TagInt(final String name, final int value) {
        super(name);
        this.value = value;
    }

    @Override
    public Integer getPayload() {
        return value;
    }

    @Override
    public Constants.Type getType() {
        return Constants.Type.INT;
    }

    @Override
    public String toString(){
        return "TAG_INT(" + getName() + "): " + value;
    }
}
