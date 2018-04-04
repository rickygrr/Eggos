package edu.gatech.cs2340.eggos.Model.User;

import android.arch.persistence.room.Room;
import android.content.Context;

import java.util.List;

/**
 * Created by chateau86 on 26-Mar-18.
 */

public final class UserDatabase_room implements UserDatabaseInterface {
    private static final UserDatabase_room ourInstance = new UserDatabase_room();
    private UserDatabaseBackend_Room db;

    private UserDatabase_room() {
    }

    public static UserDatabase_room getInstance() {
        if(ourInstance.db == null){
            throw new IllegalStateException("Database not initialized");
        }
        return ourInstance;
    }

    public static UserDatabase_room getFirstInstance(Context cont) {
        if(ourInstance.db == null){
            //throw new IllegalStateException("Database not initialized");
            ourInstance.db = Room.databaseBuilder(cont,
                    UserDatabaseBackend_Room.class, "Users").allowMainThreadQueries().build();

            if(ourInstance.db.userDBDAO().getRowCount() == 0){
                ourInstance._initTestDatabase();
            }
        }
        return ourInstance;
    }

    @Override
    public boolean addUser(User newUser) {
        if(userExists(newUser.getUsername())){
            return false;
        } else {
            this.db.userDBDAO().insertAll(newUser);
            return true;
        }
    }

    @Override
    public boolean updateUser(User s) {
        int count = this.db.userDBDAO().update(s);
        if(count == 0){
            return false;
        } else {
            if(count > 1){
                throw new IllegalStateException("User update wrote too many rows. Database probably clobbered. Rows: "+count);
            }
            return true;
        }
    }

    @Override
    public boolean userExists(String username) {
        return !this.db.userDBDAO().userExists(username).isEmpty();
    }

    @Override
    public User getUser(String username, String password) {
        List<User> user = this.db.userDBDAO().getUser(username, password);
        //Log.e("Room DB","User list is this long: "+user.size());
        if(user.isEmpty()) {
            return null;
        } else {
            return user.get(0);
        }
    }

    @Override
    public void _initTestDatabase() {

        this.addUser(new User("Monika","JustMonika",UserTypeEnum.ADMIN.toString()));// Just Monika [ok]
        this.addUser(new User("a","b",UserTypeEnum.ADMIN.toString()));
        this.addUser(new User("homelessdude","password",UserTypeEnum.USER.toString()));
        this.addUser(new User("shelterguy","gimmeshelter",UserTypeEnum.EMPLOYEE.toString())); //Denzel Washington rolling over an MD-80 not included.
    }
}
