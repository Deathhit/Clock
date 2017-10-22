package custom.clock.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.provider.BaseColumns;

import static custom.clock.database.DatabaseContract.Entry.TABLE_NAME;

public final class DatabaseContract {
    /***Inner class that defines the table contents.***/
    public static abstract class Entry implements BaseColumns {
        public static final String
                TABLE_NAME = "Alarms",
                COLUMN_NAME_ENTRY_DAY = "day",
                COLUMN_NAME_ENTRY_HOUR = "hour",
                COLUMN_NAME_ENTRY_MINUTE = "minute",
                COLUMN_NAME_ENTRY_STATUS = "status",
                COLUMN_NAME_ENTRY_ALARM_TEXT = "alarmtext";
    }

    private static DatabaseHelper helper;

    private DatabaseContract(){}

    /***Give context of the app.***/
    public synchronized static void init(Context appContext){
        if(helper == null)
            helper = new DatabaseHelper(appContext);
    }

    public static Cursor selectAll(){
        String sql = "SELECT * FROM " + TABLE_NAME;

        return helper.getReadableDatabase().rawQuery(sql,null);
    }

    public static void insert(ContentValues values){
        helper.getWritableDatabase().insert(TABLE_NAME, null, values);
    }

    public static void update(int id, ContentValues values){
        helper.getWritableDatabase().update(TABLE_NAME, values, Entry._ID + " = " + id, null);
    }

    public static void remove(int id){
        helper.getWritableDatabase().delete(TABLE_NAME, Entry._ID + " = " + id, null);
    }
}
