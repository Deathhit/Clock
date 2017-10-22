package custom.clock.pages.settings;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import custom.clock.R;
import custom.clock.library.page.Page;
import custom.clock.settings.SettingsData;
import custom.clock.settings.SharedPreferencesContract;

public class SettingsPage extends Page{
    private AlarmTimeSettingSubPage alarmTimeSettingSubPage;
    private RingtoneSettingSubPage ringtoneSettingSubPage;
    private VibrationSettingSubPage vibrationSettingSubPage;

    public SettingsPage(Context context) {
        super(context);
    }

    public SettingsPage(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SettingsPage(Context context, AttributeSet attrs, int defStyleAttr){
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void init() {
        View.inflate(getContext(), R.layout.page_settings, this);

        alarmTimeSettingSubPage = (AlarmTimeSettingSubPage)findViewById(R.id.alarmTimeSettingSubPage);
        ringtoneSettingSubPage = (RingtoneSettingSubPage)findViewById(R.id.ringtoneSettingSubPage);
        vibrationSettingSubPage = (VibrationSettingSubPage)findViewById(R.id.vibrationSettingSubPage);
    }

    @Override
    public void onShow(){
        SharedPreferencesContract.init(getContext());

        SettingsData data = SharedPreferencesContract.getSettings(getContext());

        alarmTimeSettingSubPage.setValue(data.alarmTime);
        ringtoneSettingSubPage.setValue(data.ringtoneIndex);
        vibrationSettingSubPage.setValue(data.vibration);
    }
}
