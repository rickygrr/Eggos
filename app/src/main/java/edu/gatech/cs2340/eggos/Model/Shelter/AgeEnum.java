package edu.gatech.cs2340.eggos.Model.Shelter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chateau86 on 26-Mar-18.
 */

public enum AgeEnum {
    Newborn(1<<0, "Family with newborns"),
    Children(1<<1, "Children"),
    YoungAdult(1<<2, "Young adult"),
    All(1<<3,"All");

    public final int mask_val;
    public final String repr;
    public static final int ALL_MASK = 0b1111;
    AgeEnum(int num, String repr){
        this.mask_val = num;
        this.repr = repr;
    }
    public static int enum2Mask(List<AgeEnum> gEnum){
        int out = 0;
        for(AgeEnum g: gEnum){
            out = out | g.mask_val;
        }
        return out;
    }
    public static int enum2Mask(AgeEnum... gEnum){
        int out = 0;
        for(AgeEnum g: gEnum){
            out = out | g.mask_val;
        }
        return out;
    }
    public static List<AgeEnum> mask2Enums(int mask){
        List<AgeEnum> out = new ArrayList<AgeEnum>();
        for(AgeEnum g: AgeEnum.values()){
            if((g.mask_val & mask) > 0){
                out.add(g);
            }
        }
        return out;
    }

    public String toString(){
        return this.repr;
    }

    public static int addToMask(AgeEnum g, int m){
        if(g == null){
            return m;
        } else {
            return m | g.mask_val;
        }
    }

    public static int addToMask(String g, int m){
        return addToMask(AgeEnum.toEnum(g), m);
    }

    public static AgeEnum toEnum(String str){
        str = str.toUpperCase();
        for(AgeEnum g: AgeEnum.values()){
            if(str.charAt(0) == g.toString().charAt(0)){
                return g;
            }
        }
        return null;
    }
    public static List<AgeEnum> list2Enums(List<String> in){
        List<AgeEnum> out = new ArrayList<AgeEnum>();
        for(String s: in){
            AgeEnum g = AgeEnum.toEnum(s);
            out.add(g);
        }
        return out;
    }

    public static boolean maskContains(AgeEnum g, int m){
        return (g != null) && ((m & g.mask_val) > 0);
    }
    public static boolean maskContains(String g, int m) {
        return maskContains(AgeEnum.toEnum(g), m);
    }

    public static List<String> getAgeList(){
        ArrayList<String> out = new ArrayList<String>();
        for(AgeEnum g: AgeEnum.values()){
            out.add(g.toString());
        }
        return out;
    }
}
