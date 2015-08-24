package com.mgzdev.nbt.tags;

import com.mgzdev.nbt.Constants;

/**
 * Created by morfeus on 2015-07-20.
 */
public class TagFloat extends Tag{

    private final float value;

    public TagFloat(final String name, final float value) {
        super(name);
        this.value = value;
    }

    @Override
    public Float getPayload() {
        return value;
    }

    @Override
    public Constants.Type getType() {
        return Constants.Type.FLOAT;
    }

    @Override
    public String toString(){
        return "TAG_FLOAT(" + getName() + "): " + value;
    }
}
