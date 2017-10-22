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

import static custom.clock.settings.SharedPreferencesContract.Ringtone.TITLE_ARRAY;

public class RingtoneSettingSubPage extends Page implements AdapterView.OnItemSelectedListener{
    private Spinner spinner;

    public RingtoneSettingSubPage(Context context) {
        super(context);
    }

    public RingtoneSettingSubPage(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RingtoneSettingSubPage(Context context, AttributeSet attrs, int defStyleAttr){
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void init() {
        View.inflate(getContext(), R.layout.item_ringtone_setting, this);

        spinner = (Spinner)findViewById(R.id.ringtoneSpinner);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        SettingsData data = SharedPreferencesContract.getSettings(getContext());
        data.ringtoneIndex = getValue();
        SharedPreferencesContract.saveSettings(getContext(),data);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private int getValue(){
        return spinner.getSelectedItemPosition();
    }

    public void setValue(int ringtoneIndex){
        //Disable listener before changing UI.
        spinner.setOnItemSelectedListener(null);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, TITLE_ARRAY);

        spinner.setAdapter(adapter);

        spinner.setSelection(ringtoneIndex);

        spinner.setOnItemSelectedListener(this);
    }
}
