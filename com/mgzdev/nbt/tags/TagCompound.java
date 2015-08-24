package com.mgzdev.nbt.tags;

import com.mgzdev.nbt.Constants;

import java.util.HashMap;

/**
 * Created by morfeus on 2015-05-22.
 */
public class TagCompound extends Tag{

    private final HashMap<String, Tag> payload;

    public TagCompound(final String name, final HashMap<String, Tag> payload){
        super(name);
        this.payload = payload;
    }

    public TagCompound(final String name){
        this(name, new HashMap<String, Tag>());
    }

    public Tag getTag(String name){
        return payload.get(name);
    }

    public void addTag(Tag t){
        payload.put(t.getName(), t);
    }

    @Override
    public HashMap<String, Tag> getPayload() {
        return payload;
    }

    @Override
    public Constants.Type getType() {
        return Constants.Type.COMPOUND;
    }

    @Override
    public String toString(){
        String ts = "\tTAG_COMPOUND(" + getName() + "){\n";

        for(Tag t : payload.values()){
            ts+="\t"+t.toString() +"\n";
        }

        ts+="\t}";

        return ts;
    }
}
