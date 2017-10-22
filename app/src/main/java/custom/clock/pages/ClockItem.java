package custom.clock.pages;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import custom.clock.MainActivity;
import custom.clock.R;
import custom.clock.alarm.AlarmContract;
import custom.clock.alarm.AlarmData;
import custom.clock.database.DatabaseContract;
import custom.clock.library.page.Page;

public class ClockItem extends Page implements CompoundButton.OnCheckedChangeListener, View.OnClickListener{
    private AlarmData alarmData;

    public ClockItem(Context context) {
        super(context);
    }

    public ClockItem(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ClockItem(Context context, AttributeSet attrs, int defStyleAttr){
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void init(){
        View.inflate(getContext(), R.layout.item_clock, this);

        findViewById(R.id.deleteImage).setOnClickListener(this);
        ((Switch)findViewById(R.id.clockSwitch)).setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(isChecked) {
            alarmData.status = true;
            AlarmContract.setAlarm(getContext(), alarmData);
            MainActivity.message("Alarm Scheduled");
        } else {
            alarmData.status = false;
            AlarmContract.setAlarm(getContext(), alarmData);
            MainActivity.message("Alarm Canceled");
        }

        DatabaseContract.update(alarmData.id, AlarmContract.alarmToContentValues(alarmData));
    }

    @Override
    public void onClick(View view) {
        //Cancel pending broadcast.
        onCheckedChanged(null, false);
        //Remove from database.
        DatabaseContract.remove(alarmData.id);

        //Refresh current page.
        MainActivity.loadListAdapter();

        MainActivity.message("Clock Deleted");
    }

    public void setData(AlarmData alarmData){
        this.alarmData = alarmData;

        Switch clockSwitch = (Switch)findViewById(R.id.clockSwitch);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, alarmData.hour);
        calendar.set(Calendar.MINUTE, alarmData.minute);

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        ((TextView)findViewById(R.id.timeTextView)).setText(AlarmContract.typeToString(alarmData.day) + "\n" +
                sdf.format(calendar.getTime()));

        //Disable listener before configuring UI.
        clockSwitch.setOnCheckedChangeListener(null);
        if(alarmData.status)
            clockSwitch.setChecked(true);
        else
            clockSwitch.setChecked(false);
        clockSwitch.setOnCheckedChangeListener(this);
    }

    public AlarmData getAlarm(){
        return alarmData;
    }

    public static class ClockItemAdapter extends ArrayAdapter<AlarmData> {
        public ClockItemAdapter(Context context){super(context, 0);}

        public ClockItemAdapter(Context context, AlarmData[] data) {
            super(context, 0, data);
        }

        public ClockItemAdapter(Context context, List<AlarmData> data) {
            super(context, 0, data);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if(convertView == null)
                convertView = new ClockItem(getContext());

            ((ClockItem)convertView).setData(getItem(position));

            return convertView;
        }
    }
}
