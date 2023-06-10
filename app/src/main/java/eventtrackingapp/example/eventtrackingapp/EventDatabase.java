package eventtrackingapp.example.eventtrackingapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;

public class EventDatabase extends SQLiteOpenHelper {

    private static EventDatabase databaseManager;

    private static final String EVENTS_DB ="eventDB.2";
    private static final int EVENTS_VERSION=1;
    private static final String TABLE_NAME ="Events";
    private static final String COUNTER="Counter";
    private static final String ID_FIELD="id";
    private static final String COLUMN_NAME="eName";
    private static final String COLUMN_DATE="eDate";
    private static final String COLUMN_TIME="eTime";
    private static final String COLUMN_DETAILS="Details";

    private static final String TABLE_UNAME ="User";
    private static final String COLUMN_UNAME="uName";
    private static final String COLUMN_PASSWORD="uPassword";
    private static final String COLUMN_PERMISSION="uPermission";

    public EventDatabase(Context context) {
        super(context,EVENTS_DB, null, EVENTS_VERSION);
    }

    // create database in none exists
    public static EventDatabase instanceOfDatabase(Context context){
        if(databaseManager == null)
            databaseManager = new EventDatabase(context);

        return databaseManager;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // create schema for Event table
        StringBuilder sql;
        sql = new StringBuilder()
                .append("CREATE TABLE ")
                .append(TABLE_NAME)
                .append("(")
                .append(COUNTER)
                .append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append(ID_FIELD)
                .append(" INT, ")
                .append(COLUMN_NAME)
                .append(" TEXT, ")
                .append(COLUMN_DATE)
                .append(" TEXT, ")
                .append(COLUMN_TIME)
                .append(" TEXT, ")
                .append(COLUMN_DETAILS)
                .append(" TEXT)");

        sqLiteDatabase.execSQL(sql.toString());

        // create schema for user table
        StringBuilder user;
        user = new StringBuilder()
                .append("CREATE TABLE ")
                .append(TABLE_UNAME)
                .append("(")

                .append(COLUMN_UNAME)
                .append(" TEXT, ")
                .append(COLUMN_PASSWORD)
                .append(" TEXT, ")
                .append(COLUMN_PERMISSION)
                .append(" TEXT)");

        sqLiteDatabase.execSQL(user.toString());
    }

    // add event to database
    public void addDatabaseEvent(Event event)    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(ID_FIELD, event.getEventID());
        contentValues.put(COLUMN_NAME, event.getEventName());
        contentValues.put(COLUMN_DATE, event.getEventDate());
        contentValues.put(COLUMN_TIME, event.getEventTime());
        contentValues.put(COLUMN_DETAILS, event.getEventDetails());

        sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
    }

    // add user to database
    public void addUser(User user)    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_UNAME, user.getUsername());
        contentValues.put(COLUMN_PASSWORD, user.getPassword());
       // contentValues.put(COLUMN_PERMISSION, user.getPermissionsID());

        sqLiteDatabase.insert(TABLE_UNAME, null,contentValues);
    }

    // find a username and password match in th database
    public boolean findUserInDB(String name, String pass) {
        SQLiteDatabase userDatabase = this.getReadableDatabase();

        String u,p;

        try    {
            Cursor cursor = userDatabase.query(TABLE_UNAME,new String[] {COLUMN_UNAME, COLUMN_PASSWORD}, COLUMN_UNAME + "= ? AND " + COLUMN_PASSWORD + "= ?", new String[] {name,pass}, null, null, null);
            if (cursor.moveToFirst())   {
                do {
                    u = cursor.getString(0);
                    if (u.equals(name)) {
                        p = cursor.getString(1);
                        if(p.equals(pass))
                            return true;
                    }
                }
                while(cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    // update event in database
    public void updateEventInDB(Event event) {
        SQLiteDatabase eventDatabase = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ID_FIELD, event.getEventID());
        values.put(COLUMN_NAME, event.getEventName());
        values.put(COLUMN_DATE,event.getEventDate());
        values.put(COLUMN_TIME, event.getEventTime());
        values.put(COLUMN_DETAILS, event.getEventDetails());

        eventDatabase.update(TABLE_NAME, values, ID_FIELD + " =? ", new String[]{String.valueOf(event.getEventID())});
    }

    // delete event from database
    public void deleteEventInDB(int eventID)    {
        SQLiteDatabase eventDatabase = this.getWritableDatabase();

        eventDatabase.execSQL(" DELETE FROM " + TABLE_NAME + " WHERE " + ID_FIELD + "=" + eventID + ";");
    }

    // populate database values into eventArrayList
    public void populateEventArrayList() {
        SQLiteDatabase eventDatabase = this.getReadableDatabase();

        try(Cursor result = eventDatabase.rawQuery("SELECT * FROM " + TABLE_NAME, null)) {
            if (result.getCount() != 0)
            {
                while (result.moveToNext())
                {
                    int id = result.getInt(1);
                    String name = result.getString(2);
                    String date = result.getString(3);
                    String time = result.getString(4);
                    String details = result.getString(5);
                    Event event = new Event(id, name, date, time, details);
                    Event.eventArrayList.add(event);
                }
            }
        }
    }

    // method included to appease Android, but not necessary for app functionality
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // intentionally blank
    }
}
