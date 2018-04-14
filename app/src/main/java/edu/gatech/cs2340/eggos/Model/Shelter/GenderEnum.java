package edu.gatech.cs2340.eggos.Model.Shelter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by chateau86 on 26-Mar-18.
 */

public enum GenderEnum {
    Men(1, "Men"),
    Women(1<<1, "Women");

    public final int mask_val;
    public final String repr;
    public static final int ALL_MASK = 0b11;
    GenderEnum(int num, String repr){
        this.mask_val = num;
        this.repr = repr;
    }
    public static int enum2Mask(Iterable<GenderEnum> gEnum){
        int out = 0;
        for(GenderEnum g: gEnum){
            out = out | g.mask_val;
        }
        return out;
    }
    public static int enum2Mask(GenderEnum... gEnum){
        int out = 0;
        for(GenderEnum g: gEnum){
            out = out | g.mask_val;
        }
        return out;
    }

    public static int addToMask(GenderEnum g, int m){
        if(g == null){
            return m;
        } else {
            return m | g.mask_val;
        }
    }
    public static int addToMask(String g, int m){
        return addToMask(GenderEnum.toEnum(g), m);
    }

    public static boolean maskContains(GenderEnum g, int m){
        return (g != null) && ((m & g.mask_val) > 0);
    }
    public static boolean maskContains(String g, int m) {
        return maskContains(GenderEnum.toEnum(g), m);
    }

    public static List<GenderEnum> mask2Enums(int mask){
        List<GenderEnum> out = new ArrayList<>();
        for(GenderEnum g: GenderEnum.values()){
            if((g.mask_val & mask) > 0){
                out.add(g);
            }
        }
        return out;
    }

    public static Iterable<GenderEnum> list2Enums(Iterable<String> in){
        Collection<GenderEnum> out = new ArrayList<>();
        for(String s: in){
            GenderEnum g = GenderEnum.toEnum(s);
            out.add(g);
        }
        return out;
    }

    public String toString(){
        return this.repr;
    }
    public static GenderEnum toEnum(String str){
        String _str = str.toUpperCase();
        for(GenderEnum g: GenderEnum.values()){
            if(_str.charAt(0) == g.repr.charAt(0)){
                return g;
            }
        }
        return null;
    }

    public static Iterable<String> getGenderList(){
        Collection<String> out = new ArrayList<>();
        for(GenderEnum g: GenderEnum.values()){
            out.add(g.toString());
        }
        return out;
    }
}
