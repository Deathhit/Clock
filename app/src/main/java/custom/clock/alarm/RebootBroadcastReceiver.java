package custom.clock.alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import custom.clock.database.DatabaseContract;

public class RebootBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        DatabaseContract.init(context);

        AlarmData[] alarms = AlarmContract.getAlarms(DatabaseContract.selectAll());

        for(AlarmData alarm : alarms)
            AlarmContract.setAlarm(context, alarm);
    }
}
