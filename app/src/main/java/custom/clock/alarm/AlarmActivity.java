package custom.clock.alarm;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Vibrator;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import custom.clock.R;
import custom.clock.settings.SettingsData;
import custom.clock.settings.SharedPreferencesContract;

import static custom.clock.alarm.AlarmContract.Activity.ALARM_TEXT;
import static custom.clock.settings.SharedPreferencesContract.Ringtone.SRC_ARRAY;

public class AlarmActivity extends Activity {
    private static final long VIBRATION_TIME = 15000;

    private final Runnable stopTask = new Runnable() {
        public void run() {
            if(vibration)
                vibrator.cancel();
            stopAlarm();
        }
    };

    private Handler handler;
    private Vibrator vibrator;

    private TextView timeTextView;

    private MediaPlayer mediaPlayer;

    private long alarmTime;
    private Uri ringtoneUri;
    private boolean vibration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        timeTextView = (TextView)findViewById(R.id.timeTextView);

        setTimeText();
        ((TextView)findViewById(R.id.contentTextView)).setText(getIntent().getStringExtra(ALARM_TEXT));

        getSettings();

        initMediaPlayer(ringtoneUri);

        mediaPlayer.start();

        handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(stopTask, alarmTime);

        if(vibration) {
            vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(VIBRATION_TIME);
        }
    }

    @Override
    public void onBackPressed(){
        confirm(null);
    }


    public void confirm(View v){
        handler.removeCallbacks(stopTask);
        handler.post(stopTask);
    }

    private void getSettings(){
        SharedPreferencesContract.init(this);

        SettingsData data = SharedPreferencesContract.getSettings(this);

        alarmTime = data.alarmTime;
        ringtoneUri = Uri.parse(SRC_ARRAY.get(data.ringtoneIndex));
        vibration = data.vibration;
    }

    private void initMediaPlayer(Uri ringtoneSrc) {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
        mediaPlayer.setLooping(true);
        try {
            mediaPlayer.setDataSource(this,ringtoneSrc);
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void stopAlarm(){
        try {
            mediaPlayer.stop();
            mediaPlayer.release();

            finish();
        }catch (Exception ignored){}
    }

    private void setTimeText(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("HH:mm ");
        String strDate = format.format(calendar.getTime());

        timeTextView.setText(strDate);
    }
}
