package custom.clock.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import custom.clock.database.DatabaseContract.Entry;

import static custom.clock.alarm.AlarmContract.Type.WORKDAYS;
import static custom.clock.database.DatabaseContract.Entry.COLUMN_NAME_ENTRY_ALARM_TEXT;
import static custom.clock.database.DatabaseContract.Entry.COLUMN_NAME_ENTRY_DAY;
import static custom.clock.database.DatabaseContract.Entry.COLUMN_NAME_ENTRY_HOUR;
import static custom.clock.database.DatabaseContract.Entry.COLUMN_NAME_ENTRY_MINUTE;
import static custom.clock.database.DatabaseContract.Entry.COLUMN_NAME_ENTRY_STATUS;
import static custom.clock.database.DatabaseContract.Entry.TABLE_NAME;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String TEXT_TYPE = " TEXT",
        INTEGER_TYPE = " INTEGER",
        COMMA_SEP    = ",",

    SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    Entry._ID + " INTEGER PRIMARY KEY," +
                    Entry.COLUMN_NAME_ENTRY_DAY + INTEGER_TYPE + COMMA_SEP +
                    COLUMN_NAME_ENTRY_HOUR + INTEGER_TYPE + COMMA_SEP +
                    Entry.COLUMN_NAME_ENTRY_MINUTE + INTEGER_TYPE + COMMA_SEP +
                    Entry.COLUMN_NAME_ENTRY_STATUS + INTEGER_TYPE + COMMA_SEP +
                    Entry.COLUMN_NAME_ENTRY_ALARM_TEXT + TEXT_TYPE +
                    " )",

    SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME,

    DATABASE_NAME = "ClockDatabase.db";

    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);

        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME_ENTRY_DAY, WORKDAYS);
        cv.put(COLUMN_NAME_ENTRY_HOUR, 8);
        cv.put(COLUMN_NAME_ENTRY_MINUTE, 30);
        cv.put(COLUMN_NAME_ENTRY_STATUS, false);
        cv.put(COLUMN_NAME_ENTRY_ALARM_TEXT, "Default Alarm");

        db.insert(TABLE_NAME, null, cv);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);

        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
