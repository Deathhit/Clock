package custom.clock.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.media.RingtoneManager;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

import static custom.clock.settings.SharedPreferencesContract.Ringtone.TITLE_ARRAY;
import static custom.clock.settings.SharedPreferencesContract.Ringtone.SRC_ARRAY;

public final class SharedPreferencesContract {
    public static abstract class AlarmTime{
        public static final long
                THIRTY_SECONDS = 30000,
                ONE_MINUTE = 60000,
                TWO_MINUTES = 120000;
    }

    public static abstract class Ringtone{
        public static final ArrayList<String>
                TITLE_ARRAY = new ArrayList<>(),
                SRC_ARRAY = new ArrayList<>();
    }

    private static final String SHARED_PREFERENCES_NAME = "clocksettings";

    private static final String
            ALARM_TIME = "alarmtime",
            RINGTONE = "ringtone",
            VIBRATION = "vibration";

    private static final long DEFAULT_ALARM_TIME = 30000;
    private static final int DEFAULT_RINGTONE_INDEX = 0;
    private static final boolean DEFAULT_VIBRATION = false;

    private SharedPreferencesContract(){}

    public static void init(Context context){
        RingtoneManager manager = new RingtoneManager(context);
        manager.setType(RingtoneManager.TYPE_RINGTONE);
        Cursor cursor = manager.getCursor();

        try {
            while (cursor.moveToNext()) {
                String title = cursor.getString(RingtoneManager.TITLE_COLUMN_INDEX);
                String uri = cursor.getString(RingtoneManager.URI_COLUMN_INDEX) + "/" + cursor.getString(RingtoneManager.ID_COLUMN_INDEX);

                TITLE_ARRAY.add(title);
                SRC_ARRAY.add(uri);
            }
        }finally {
            cursor.close();
        }
    }

    public static SettingsData getSettings(Context context){
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCES_NAME, MODE_PRIVATE);

        SettingsData settingsData = new SettingsData();

        settingsData.alarmTime = preferences.getLong(ALARM_TIME, DEFAULT_ALARM_TIME);

        settingsData.ringtoneIndex = preferences.getInt(RINGTONE, DEFAULT_RINGTONE_INDEX);

        settingsData.vibration = preferences.getBoolean(VIBRATION, DEFAULT_VIBRATION);

        return settingsData;
    }

    public static void saveSettings(Context context, SettingsData data){
        SharedPreferences.Editor editor = context.getSharedPreferences(SHARED_PREFERENCES_NAME, MODE_PRIVATE).edit();

        editor.putLong(ALARM_TIME, data.alarmTime);
        editor.putInt(RINGTONE, data.ringtoneIndex);
        editor.putBoolean(VIBRATION, data.vibration);

        editor.commit();
    }
}
