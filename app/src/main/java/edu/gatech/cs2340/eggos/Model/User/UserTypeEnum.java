package edu.gatech.cs2340.eggos.Model.User;

/**
 * User permission enum
 * Created by chateau86 on 14-Feb-18.
 */

public enum UserTypeEnum {
    ADMIN, EMPLOYEE, USER; // --Commented out by Inspection (08-Apr-18 15:57):GUEST;
    public static UserTypeEnum[] getRegisterableEnum() {
        return new UserTypeEnum[]{ADMIN, EMPLOYEE, USER};
    }

}
