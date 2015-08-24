package com.mgzdev.nbt.tags;

import com.mgzdev.nbt.Constants;

/**
 * Created by morfeus on 2015-07-20.
 */
public class TagDouble extends Tag{

    private final double value;

    public TagDouble(final String name, final double value) {
        super(name);
        this.value = value;
    }

    @Override
    public Double getPayload() {
        return value;
    }

    @Override
    public Constants.Type getType() {
        return Constants.Type.DOUBLE;
    }

    @Override
    public String toString(){
        return "TAG_DOUBLE(" + getName() + "): " + value;
    }
}
