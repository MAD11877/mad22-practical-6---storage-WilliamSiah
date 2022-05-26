package sg.edu.np.mad.mad_exercise2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Random;

public class DbHandler extends SQLiteOpenHelper {

    public DbHandler (Context context, String fileName){
        super(context, fileName , null, 1);
    }


    // onCreate can only be called if they cannot find the db file
    // Changing version number will enable one to update the database. Changing it to 2, for instance, will call onUpgrade method
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_NEWUSER_TABLE = "CREATE TABLE " + "user" + " ("
                + "id" + " INTEGER PRIMARY KEY, " + "name" +
                " TEXT, " + "description" + " TEXT, " + "followed" + " INTEGER " + ")";
        db.execSQL(CREATE_NEWUSER_TABLE);

        // Init user list
        ArrayList<User> userList = new ArrayList<User>();
        Random rand = new Random();
        for(int i=1 ; i <= 20; ++i){
            // Getting random integer
            int randInt1 = rand.nextInt();
            int randInt2 = rand.nextInt();
            String name = String.format("Name %s",randInt1);
            String description = String.format("Description %s",randInt2);
            userList.add(new User(name,description, i, false));
        }

        // Insert users into database
        insertUsers(userList, db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + "user");
        onCreate(db);
    }

    public void insertUsers (ArrayList<User> userList,SQLiteDatabase db){
        ContentValues values = new ContentValues();

        // Get data for each user and put into database
        for (User user : userList){
            values.put("id",user.getID());
            values.put("name",user.getNameOfUser());
            values.put("description",user.getDescription());

            // Changing bool values to int. 0: False , 1:True
            int followed;
            if(user.isFollowed()){
                followed = 1;
            }
            else{
                followed = 0;
            }
            values.put("followed", followed);

            // Insert data
            db.insert("user",null, values);
        }
//        db.close();
    }

    // Return all user data from database
    public ArrayList<User> getUsers(){
        // Init user list
        ArrayList<User> userList = new ArrayList<User>();

        SQLiteDatabase db = this.getWritableDatabase();
        // Get whole table from data base
        Cursor cursor = db.rawQuery("select * from user",null);

        // Go through each row and get user data
        if (cursor.moveToFirst()) {
            // cursor.isAfterLast() returns true when cursor is at last row position.
            // Adding a ! (not) means perform till it is not at the end of cursor.
            //so while(!cursor.isAfterLast()){} means while loop will traverse till last record of cursor.
            while (!cursor.isAfterLast()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                String desc = cursor.getString(cursor.getColumnIndexOrThrow("description"));
                int intFollowed = cursor.getInt(cursor.getColumnIndexOrThrow("followed"));

                // Changing followed back to bool
                // 0: False , 1:True
                boolean followed;
                if (intFollowed == 0){
                    followed = false;
                }
                // if it is 1
                else{
                    followed = true;
                }

                // Add user object to list
                userList.add(new User(name,desc,id,followed));

                // Move to next row in database table
                cursor.moveToNext();
            }
            // Close cursor
            cursor.close();
        }
        // Close Db
        db.close();
        // Return userList
        return userList;
    }

    public void updateUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        // Put user data into ContentValues object
        values.put("id",user.getID());
        values.put("name",user.getNameOfUser());
        values.put("description",user.getDescription());

        // Changing bool values to int. 0: False , 1:True
        int followed;
        if(user.isFollowed()){
            followed = 1;
        }
        else{
            followed = 0;
        }
        values.put("followed", followed);

        // Update database
        db.update("user",values,"id = ?",new String[]{String.valueOf(user.getID())});
        db.close();
    }


}
