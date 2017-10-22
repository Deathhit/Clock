package custom.clock.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

import static custom.clock.alarm.AlarmContract.Activity.ALARM_TEXT;
import static custom.clock.alarm.AlarmContract.BroadcastReceiver.ID;
import static custom.clock.alarm.AlarmContract.BroadcastReceiver.TIME;
import static custom.clock.alarm.AlarmContract.BroadcastReceiver.TYPE;
import static custom.clock.alarm.AlarmContract.Type.SATURDAY;
import static custom.clock.alarm.AlarmContract.Type.SUNDAY;
import static custom.clock.alarm.AlarmContract.Type.WEEKENDS;
import static custom.clock.alarm.AlarmContract.Type.WORKDAYS;

/***Custom BroadcastReceiver for Alarm.***/
public class AlarmBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent alarmIntent = new Intent(context, AlarmActivity.class);
        alarmIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        alarmIntent.putExtra(ALARM_TEXT, intent.getStringExtra(ALARM_TEXT));

        int type = intent.getIntExtra(TYPE,-1);

        if((type == WORKDAYS && isCurrentDayWorkday())
                || (type == WEEKENDS && !isCurrentDayWorkday())
                    || (type == getCurrentDayOfWeek()))
            context.startActivity(alarmIntent);

        scheduleNextAlarm(context, intent);
    }

    private static int getCurrentDayOfWeek(){
        Calendar calendar = Calendar.getInstance();

        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    private static boolean isCurrentDayWorkday(){
        int day = getCurrentDayOfWeek();

        return !(day == SUNDAY || day == SATURDAY);
    }

    private static void scheduleNextAlarm(Context context, Intent previousIntent){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(previousIntent.getLongExtra(TIME, -1));
        calendar.add(Calendar.DAY_OF_WEEK, 1);

        previousIntent.putExtra(TIME, calendar.getTimeInMillis());

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, previousIntent.getIntExtra(ID, -1),
                previousIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }
}
