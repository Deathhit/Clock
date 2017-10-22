package custom.clock.pages.settings;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import custom.clock.R;
import custom.clock.library.page.Page;
import custom.clock.settings.SettingsData;
import custom.clock.settings.SharedPreferencesContract;

import static custom.clock.settings.SharedPreferencesContract.AlarmTime.ONE_MINUTE;
import static custom.clock.settings.SharedPreferencesContract.AlarmTime.THIRTY_SECONDS;
import static custom.clock.settings.SharedPreferencesContract.AlarmTime.TWO_MINUTES;

public class AlarmTimeSettingSubPage extends Page implements AdapterView.OnItemSelectedListener{
    private static final String[] DURATIONS = {"30 seconds", "1 minute", "2 minutes"};

    private Spinner spinner;

    public AlarmTimeSettingSubPage(Context context) {
        super(context);
    }

    public AlarmTimeSettingSubPage(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AlarmTimeSettingSubPage(Context context, AttributeSet attrs, int defStyleAttr){
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void init() {
        View.inflate(getContext(), R.layout.item_alarm_time_setting, this);

        spinner = (Spinner)findViewById(R.id.alarmTimeSpinner);
        spinner.setAdapter(new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, DURATIONS));
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        SettingsData data = SharedPreferencesContract.getSettings(getContext());
        data.alarmTime = getValue();
        SharedPreferencesContract.saveSettings(getContext(),data);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void setValue(long alarmTime){
        spinner.setOnItemSelectedListener(null);

        if(alarmTime == THIRTY_SECONDS)
            spinner.setSelection(0);
        else if(alarmTime == ONE_MINUTE)
            spinner.setSelection(1);
        else
            spinner.setSelection(2);

        spinner.setOnItemSelectedListener(this);
    }

    private long getValue(){
        switch (spinner.getSelectedItemPosition()){
            case 0 :
                return THIRTY_SECONDS;
            case 1 :
                return ONE_MINUTE;
            default :
                return TWO_MINUTES;
        }
    }
}
