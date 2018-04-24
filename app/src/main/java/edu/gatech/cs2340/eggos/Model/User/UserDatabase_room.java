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
    UserDatabaseDAO dbDAO;

    private UserDatabase_room() {
    }

    /**
     * Get instance of user database interface
     * @return instance of user database interface
     */
    public static UserDatabaseInterface getInstance() {
        if(ourInstance.db == null){
            throw new IllegalStateException("Database not initialized");
        }
        return ourInstance;
    }

    /**
     * Get first instance of user database interface
     * @param cont context of user
     * @return instance of user database interface
     */
    @SuppressWarnings("ChainedMethodCall")
    //Builder pattern strikes again.
    public static UserDatabaseInterface getFirstInstance(Context cont) {
        if(ourInstance.db == null){
            //throw new IllegalStateException("Database not initialized");
            ourInstance.db = Room.databaseBuilder(cont,
                    UserDatabaseBackend_Room.class, "Users").allowMainThreadQueries().build();

            if(ourInstance.dbDAO.getRowCount() == 0){
                ourInstance._initTestDatabase();
            }
            ourInstance.dbDAO = ourInstance.db.userDBDAO();
        }
        return ourInstance;
    }

    /**
     * Add user to database
     * @param newUser user to add
     * @return whether or not the user was added
     */
    @Override
    public boolean addUser(User newUser) {
        if(userExists(newUser.getUsername())){
            return false;
        } else {
            this.dbDAO.insertAll(newUser);
            return true;
        }
    }

    /**
     * Update user
     * @param s user to update
     */
    @Override
    public void updateUser(User s) {
        int count = this.dbDAO.update(s);
        if(count > 1){
            throw new IllegalStateException(
                "User update wrote too many rows. Database probably clobbered. Rows: "+count
            );
        }
    }

    /**
     * Check if a user exists
     * @param username username of user
     * @return whether or not the user exists
     */
    @Override
    public boolean userExists(String username) {
        return this.dbDAO.userExistsCount(username) > 0;
    }

    /**
     * Get user from database
     * @param username username of user
     * @param password password of user
     * @return user
     */
    @Override
    public User getUser(String username, String password) {
        List<User> user = this.dbDAO.getUser(username, password);
        //Log.e("Room DB","User list is this long: "+user.size());
        if(user.isEmpty()) {
            return null;
        } else {
            return user.get(0);
        }
    }

    /**
     * Initialize test database of users
     */
    @Override
    public void _initTestDatabase() {

        this.addUser(new User("Monika","JustMonika",
                                UserTypeEnum.ADMIN.toString()));// Just Monika [ok]
        this.addUser(new User("a","b",
                                UserTypeEnum.ADMIN.toString()));
        this.addUser(new User("homelessdude","password",
                                UserTypeEnum.USER.toString()));
        this.addUser(new User("shelterguy","gimmeshelter",
                                UserTypeEnum.EMPLOYEE.toString()));
    }
}
