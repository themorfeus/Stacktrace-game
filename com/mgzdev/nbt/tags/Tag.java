package com.mgzdev.nbt.tags;

import com.mgzdev.nbt.Constants;

/**
 * Created by morfeus on 2015-05-22.
 */
public abstract class Tag {

    private final String name;

    public Tag(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public abstract Object getPayload();

    public abstract Constants.Type getType();

    @Override
    public String toString(){
        return "GENERIC_TAG";
    }
}
