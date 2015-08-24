package com.mgzdev.nbt.tags;

import com.mgzdev.nbt.Constants;

import java.util.Collections;
import java.util.List;

/**
 * Created by morfeus on 2015-05-22.
 */
public class TagList extends Tag{

    private final Constants.Type type;

    private final List<Tag> value;

    public TagList(final String name, final int type, final List<Tag> value) {
        super(name);
        this.value = Collections.unmodifiableList(value);
        this.type = Constants.getTypeByID(type);
    }


    public TagList(final String name, final Constants.Type type, final List<Tag> value) {
        super(name);
        this.type = type;
        this.value = Collections.unmodifiableList(value);
    }

    public Constants.Type getListType() {
        return type;
    }

    @Override
    public Constants.Type getType() {
        return Constants.Type.LIST;
    }

    @Override
    public List<Tag> getPayload() {
        return value;
    }

    @Override
    public String toString() {
        String ts = "TAG_LIST(" + getName() + "){\n";

        for(Tag t : value){
            ts+="\t"+t.toString() +"\n";
        }

        return ts;
    }
}
