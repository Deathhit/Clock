package custom.clock.pages.add;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;

import custom.clock.MainActivity;
import custom.clock.R;
import custom.clock.alarm.AlarmContract;
import custom.clock.alarm.AlarmData;
import custom.clock.database.DatabaseContract;
import custom.clock.library.page.Page;

public class AddNotePage extends Page implements View.OnClickListener{
    private AlarmData alarmData;

    public AddNotePage(Context context) {
        super(context);
    }

    public AddNotePage(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AddNotePage(Context context, AttributeSet attrs, int defStyleAttr){
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void init() {
        View.inflate(getContext(), R.layout.page_add_note, this);

        findViewById(R.id.addButton).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        alarmData.alarmText = ((EditText)findViewById(R.id.noteEditText)).getText().toString();

        DatabaseContract.insert(AlarmContract.alarmToContentValues(alarmData));

        MainActivity.loadListAdapter();

        MainActivity.message("Item Added");

        MainActivity.goBackToRoot();
    }

    public void setAlarmData(AlarmData alarmData){
        this.alarmData = alarmData;
    }
}
