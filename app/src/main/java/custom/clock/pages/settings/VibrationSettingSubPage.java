package custom.clock.pages.settings;


import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import custom.clock.R;
import custom.clock.library.page.Page;
import custom.clock.settings.SettingsData;
import custom.clock.settings.SharedPreferencesContract;

public class VibrationSettingSubPage extends Page implements CompoundButton.OnCheckedChangeListener{
    private Switch vibrationSwitch;

    public VibrationSettingSubPage(Context context) {
        super(context);
    }

    public VibrationSettingSubPage(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public VibrationSettingSubPage(Context context, AttributeSet attrs, int defStyleAttr){
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void init() {
        View.inflate(getContext(), R.layout.item_vibration_setting, this);

        vibrationSwitch = (Switch)findViewById(R.id.vibrationSwitch);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        SettingsData data = SharedPreferencesContract.getSettings(getContext());
        data.vibration = getValue();
        SharedPreferencesContract.saveSettings(getContext(),data);
    }

    private boolean getValue(){
        return vibrationSwitch.isChecked();
    }

    public void setValue(boolean status){
        //Disable listener before setting UI.
        vibrationSwitch.setOnCheckedChangeListener(null);

        vibrationSwitch.setChecked(status);

        vibrationSwitch.setOnCheckedChangeListener(this);
    }


}
