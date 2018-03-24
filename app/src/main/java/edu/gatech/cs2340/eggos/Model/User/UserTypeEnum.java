package edu.gatech.cs2340.eggos.Model.User;

import java.util.List;

/**
 * User permission enum
 * Created by chateau86 on 14-Feb-18.
 */

public enum UserTypeEnum {
    ADMIN, EMPLOYEE, USER, GUEST;
    public static UserTypeEnum[] getRegisterableEnum() {
        return new UserTypeEnum[]{ADMIN, EMPLOYEE, USER};
    }

    public UserTypeEnum fromString(String enum_str){
        char chr = enum_str.toLowerCase().charAt(0);
        if(chr == 'a'){
            return ADMIN;
        } else if (chr == 'e'){
            return EMPLOYEE;
        } else {
            return USER;
        }
    }
}
