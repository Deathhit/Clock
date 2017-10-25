package custom.clock.pages.add;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TimePicker;

import custom.clock.R;
import custom.clock.alarm.AlarmContract;
import custom.clock.alarm.AlarmData;
import custom.clock.library.page.Page;
import custom.clock.library.page.PageContainerActivity;

public class AddAlarmPage extends Page implements OnClickListener{
    private TimePicker timePicker;
    private Spinner spinner;

    public AddAlarmPage(Context context) {
        super(context);
    }

    public AddAlarmPage(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AddAlarmPage(Context context, AttributeSet attrs, int defStyleAttr){
        super(context, attrs, defStyleAttr);
    }

    @Override public void init(){
        View.inflate(getContext(), R.layout.page_add_alarm, this);

        timePicker = (TimePicker)findViewById(R.id.timePicker);
        spinner = (Spinner)findViewById(R.id.spinner);

        timePicker.setIs24HourView(true);
        spinner.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, AlarmContract.getTypes()));

        findViewById(R.id.nextButton).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        AlarmData alarmData = new AlarmData();
        alarmData.day = spinner.getSelectedItemPosition();

        if (Build.VERSION.SDK_INT >= 23 ) {
            alarmData.hour = timePicker.getHour();
            alarmData.minute = timePicker.getMinute();
        }else {
            //noinspection deprecation
            alarmData.hour = timePicker.getCurrentHour();
            //noinspection deprecation
            alarmData.minute = timePicker.getCurrentMinute();
        }

        alarmData.status = false;

        AddNotePage page = new AddNotePage(getContext());
        page.setAlarmData(alarmData);

        PageContainerActivity.goToNext(page);
    }
}
