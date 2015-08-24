package com.mgzdev.nbt.tags;

import com.mgzdev.nbt.Constants;

/**
 * Created by morfeus on 2015-07-20.
 */
public class TagEnd extends Tag{


    public TagEnd() {
        super("");
    }

    @Override
    public Object getPayload() {
        return null;
    }

    @Override
    public Constants.Type getType() {
        return Constants.Type.END;
    }

    @Override
    public String toString(){
        return "TAG_END";
    }
}
