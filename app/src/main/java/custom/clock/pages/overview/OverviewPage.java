package custom.clock.pages.overview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import custom.clock.MainActivity;
import custom.clock.R;
import custom.clock.library.page.Page;
import custom.clock.pages.add.AddAlarmPage;

public class OverviewPage extends Page implements View.OnClickListener{
    private TextView dateText;
    private ListView listView;

    public OverviewPage(Context context) {
        super(context);
    }

    public OverviewPage(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OverviewPage(Context context, AttributeSet attrs, int defStyleAttr){
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void init(){
        View.inflate(getContext(), R.layout.page_overview, this);

        dateText = (TextView)findViewById(R.id.dateText);
        listView = (ListView)findViewById(R.id.clockList);

        findViewById(R.id.addImage).setOnClickListener(this);
    }

    @Override
    public void onShow(){
        listView.setAdapter(MainActivity.getListAdapter());
        setDate();
    }

    @Override
    public void onClick(View v) {
        MainActivity.goToNext(new AddAlarmPage(getContext()));
    }

    private void setDate(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy / MM / dd ");
        String strDate = format.format(calendar.getTime());

        dateText.setText(strDate);
    }



}
