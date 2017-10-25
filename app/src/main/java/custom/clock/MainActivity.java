package custom.clock;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import custom.clock.alarm.AlarmContract;
import custom.clock.alarm.AlarmData;
import custom.clock.database.DatabaseContract;
import custom.clock.library.adapter.ViewPagerAdapter;
import custom.clock.library.page.Page;
import custom.clock.library.page.PageContainer;

import custom.clock.library.page.PageContainerActivity;
import custom.clock.pages.about.AboutPage;
import custom.clock.pages.overview.OverviewPage;
import custom.clock.pages.ClockItem.ClockItemAdapter;
import custom.clock.pages.settings.SettingsPage;

public class MainActivity extends PageContainerActivity implements View.OnClickListener, ViewPager.OnPageChangeListener{
    private static ClockItemAdapter clockListAdapter;

    private static Page aboutPage;

    private static Toast toast;

    private View[] icons = new View[2];

    private ViewPager pagePager;

    public static ClockItemAdapter getListAdapter(){
        return clockListAdapter;
    }

    public static void loadListAdapter(){
        clockListAdapter.clear();

        AlarmData[] alarms = AlarmContract.getAlarms(DatabaseContract.selectAll());

        for(AlarmData alarm : alarms)
            clockListAdapter.add(alarm);

        clockListAdapter.notifyDataSetChanged();
    }

    public static void message(String message){
        toast.setText(message);
        toast.show();
    }

    @SuppressLint("ShowToast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DatabaseContract.init(getApplicationContext());

        clockListAdapter = new ClockItemAdapter(this);
        loadListAdapter();

        toast = Toast.makeText(this,"",Toast.LENGTH_SHORT);

        setContentView(R.layout.activity_main);
        setContainer((PageContainer)findViewById(R.id.rootContainer));

        aboutPage = new AboutPage(this);

        icons[0] = findViewById(R.id.overviewImage);
        icons[1] = findViewById(R.id.settingsImage);

        pagePager = (ViewPager)findViewById(R.id.pagePager);

        icons[0].setOnClickListener(this);
        icons[1].setOnClickListener(this);

        icons[0].setSelected(true);

        ViewPagerAdapter pageAdapter = new ViewPagerAdapter();

        pageAdapter.add(0, new OverviewPage(this));
        pageAdapter.add(1, new SettingsPage(this));

        pagePager.setAdapter(pageAdapter);

        pagePager.setCurrentItem(0);

        pagePager.addOnPageChangeListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.overviewImage :
                select(0);
                pagePager.setCurrentItem(0);
                break;
            case R.id.settingsImage :
                select(1);
                pagePager.setCurrentItem(1);
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about:
                if(!current().equals(aboutPage))
                    goToNext(aboutPage);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

    @Override
    public void onPageSelected(int position) {
        select(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {}

    private void select(int index){
        for(View v : icons)
            v.setSelected(false);

        icons[index].setSelected(true);
    }
}