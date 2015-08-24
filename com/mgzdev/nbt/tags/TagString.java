package com.mgzdev.nbt.tags;

import com.mgzdev.nbt.Constants;

/**
 * Created by morfeus on 2015-07-20.
 */
public class TagString extends Tag{

    private final String value;

    public TagString(String name, String value) {
        super(name);
        this.value = value;
    }

    @Override
    public String getPayload() {
        return value;
    }
    @Override
    public Constants.Type getType() {
        return Constants.Type.STRING;
    }
    @Override
    public String toString(){
        return "TAG_STRING(" + getName() + "): '" + value +"'";
    }
}
