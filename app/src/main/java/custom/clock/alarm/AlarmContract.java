package custom.clock.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.Calendar;

import custom.clock.database.DatabaseContract;

import static custom.clock.alarm.AlarmContract.Activity.ALARM_TEXT;
import static custom.clock.alarm.AlarmContract.BroadcastReceiver.ID;
import static custom.clock.alarm.AlarmContract.BroadcastReceiver.TIME;
import static custom.clock.alarm.AlarmContract.BroadcastReceiver.TYPE;
import static custom.clock.alarm.AlarmContract.Type.FRIDAY;
import static custom.clock.alarm.AlarmContract.Type.MONDAY;
import static custom.clock.alarm.AlarmContract.Type.SATURDAY;
import static custom.clock.alarm.AlarmContract.Type.SUNDAY;
import static custom.clock.alarm.AlarmContract.Type.THURSDAY;
import static custom.clock.alarm.AlarmContract.Type.TUESDAY;
import static custom.clock.alarm.AlarmContract.Type.WEDNESDAY;
import static custom.clock.alarm.AlarmContract.Type.WEEKENDS;
import static custom.clock.alarm.AlarmContract.Type.WORKDAYS;

public final class AlarmContract {
    public static abstract class Type{
        public static final int
                WORKDAYS = Calendar.SUNDAY-1,
                SUNDAY = Calendar.SUNDAY,
                MONDAY = Calendar.MONDAY,
                TUESDAY = Calendar.TUESDAY,
                WEDNESDAY = Calendar.WEDNESDAY,
                THURSDAY = Calendar.THURSDAY,
                FRIDAY = Calendar.FRIDAY,
                SATURDAY = Calendar.SATURDAY,
                WEEKENDS = Calendar.SATURDAY+1;
    }

    static abstract class BroadcastReceiver{
        static final String TYPE = "type";
        static final String ID = "id";
        static final String TIME = "time";
    }

    static abstract class Activity{
        static final String ALARM_TEXT = "alarmtext";
    }

    public static String typeToString(int type){
        switch (type){
            case WORKDAYS :
                return "Workdays";
            case SUNDAY :
                return "Sunday";
            case MONDAY :
                return "Monday";
            case TUESDAY :
                return "Tuesday";
            case WEDNESDAY :
                return "Wednesday";
            case THURSDAY :
                return "Thursday";
            case FRIDAY :
                return "Friday";
            case SATURDAY :
                return "Saturday";
            case WEEKENDS :
                return "Weekends";
        }

        return null;
    }

    public static String[] getTypes(){
        String[] types = new String[9];

        types[0] = typeToString(0);
        types[1] = typeToString(1);
        types[2] = typeToString(2);
        types[3] = typeToString(3);
        types[4] = typeToString(4);
        types[5] = typeToString(5);
        types[6] = typeToString(6);
        types[7] = typeToString(7);
        types[8] = typeToString(8);

        return types;
    }

    public static AlarmData[] getAlarms(Cursor cursor){
        ArrayList<AlarmData> alarms = new ArrayList<>();

        try{
            while (cursor.moveToNext()) {
                AlarmData alarm = new AlarmData();

                alarm.id = cursor.getInt(cursor.getColumnIndex(DatabaseContract.Entry._ID));
                alarm.day = cursor.getInt(cursor.getColumnIndex(DatabaseContract.Entry.COLUMN_NAME_ENTRY_DAY));
                alarm.hour = cursor.getInt(cursor.getColumnIndex(DatabaseContract.Entry.COLUMN_NAME_ENTRY_HOUR));
                alarm.minute = cursor.getInt(cursor.getColumnIndex(DatabaseContract.Entry.COLUMN_NAME_ENTRY_MINUTE));
                alarm.status = cursor.getInt(cursor.getColumnIndex(DatabaseContract.Entry.COLUMN_NAME_ENTRY_STATUS)) == 1;
                alarm.alarmText = cursor.getString(cursor.getColumnIndex(DatabaseContract.Entry.COLUMN_NAME_ENTRY_ALARM_TEXT));

                alarms.add(alarm);
            }
        }finally {
            cursor.close();
        }

        return alarms.toArray(new AlarmData[alarms.size()]);
    }

    public static void setAlarm(Context context, AlarmData data){
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(android.app.Activity.ALARM_SERVICE);
        Intent intent = new Intent(context,AlarmBroadcastReceiver.class);

        intent.putExtra(TYPE, data.day);
        intent.putExtra(ID, data.id);
        intent.putExtra(ALARM_TEXT, data.alarmText);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, data.hour);
        calendar.set(Calendar.MINUTE, data.minute);
        calendar.set(Calendar.SECOND, 0);

        if(System.currentTimeMillis() > calendar.getTimeInMillis())
            calendar.add(Calendar.DAY_OF_WEEK,1);

        intent.putExtra(TIME, calendar.getTimeInMillis());

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                data.id,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        if(data.status){
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }else
            alarmManager.cancel(pendingIntent);
    }

    public static ContentValues alarmToContentValues(AlarmData alarm){
        ContentValues cv = new ContentValues();

        cv.put(DatabaseContract.Entry.COLUMN_NAME_ENTRY_DAY, alarm.day);
        cv.put(DatabaseContract.Entry.COLUMN_NAME_ENTRY_HOUR, alarm.hour);
        cv.put(DatabaseContract.Entry.COLUMN_NAME_ENTRY_MINUTE, alarm.minute);
        cv.put(DatabaseContract.Entry.COLUMN_NAME_ENTRY_STATUS, alarm.status ? 1 : 0);
        cv.put(DatabaseContract.Entry.COLUMN_NAME_ENTRY_ALARM_TEXT, alarm.alarmText);

        return cv;
    }

    private AlarmContract(){}
}
