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
