package com.mgzdev.nbt;

import java.nio.charset.Charset;

/**
 * Created by morfeus on 2015-05-22.
 */
public class Constants {

    public enum Type{
        END(0),BYTE(1),SHORT(2),INT(3),LONG(4),FLOAT(5), DOUBLE(6),
        BYTE_ARR(7),STRING(8),LIST(9),COMPOUND(10), INT_ARR(11);

        private byte id;

        Type(int id){
            this.id = (byte)id;
        }

        public byte getID(){
            return id;
        }

    }
    
    
    public static Type getTypeByID(int id){
        switch (id){

            case 1:
                return Type.BYTE;
                
            case 2:
                return Type.SHORT;
                
            case 3:
                return Type.INT;
                
            case 4:
                return Type.LONG;
                
            case 5:
                return Type.FLOAT;
                
            case 6:
                return Type.DOUBLE;
                
            case 7:
                return Type.BYTE_ARR;
                
            case 8:
                return Type.STRING;
                
            case 9:
                return Type.LIST;
                
            case 10:
                return Type.COMPOUND;
                
            case 11:
                return Type.INT_ARR;
                

            default:
                return Type.END;
                

        }
    }
}
