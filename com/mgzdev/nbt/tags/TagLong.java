package com.mgzdev.nbt.tags;

import com.mgzdev.nbt.Constants;

/**
 * Created by morfeus on 2015-07-20.
 */
public class TagLong extends Tag{

    private final long value;

    public TagLong(final String name, final long value) {
        super(name);
        this.value = value;
    }

    @Override
    public Long getPayload() {
        return value;
    }

    @Override
    public Constants.Type getType() {
        return Constants.Type.LONG;
    }

    @Override
    public String toString(){
        return "TAG_LONG(" + getName() + "): " + value;
    }
}
