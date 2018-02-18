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
}
