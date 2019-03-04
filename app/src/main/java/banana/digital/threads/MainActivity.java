package banana.digital.threads;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.runnable) TextView runnableTextView;
    @BindView(R.id.thread) TextView threadTextView;
    int i = 0;
    boolean locked = false;
    Thread textThread;
    Thread textThread1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                //textThread("abcdefghijklmnopqrstuvwxyz", 100);
            }
        };

        //textRunnable("abcdefghijklmnopqrstuvwxyz", 100);


        textThread = new Thread(runnable);
        textThread1 = new Thread(runnable);

        textThread.start();
        //textThread1.start();

        textRunnable("abcdefghijklmnopqrstuvwxyz", 100);


    }



    public void textRunnable(String s, int period) {
        final char[] chars = s.toCharArray();
        final StringBuffer sb = new StringBuffer();

        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if(i < chars.length) {
                    sb.append(String.valueOf(chars[i]));
                    runnableTextView.setText(sb.toString());
                    Log.e("fafa", "Runnable " + sb.toString());

                    i = i+ 1;
                }
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 0, period);
    }



/*
    public synchronized void textThread(String s, int period) {
        final char[] chars = s.toCharArray();
        final StringBuffer sb = new StringBuffer();
        for(int a = 0; a < chars.length; a++) {
            sb.append(String.valueOf(chars[a]));
            threadTextView.setText(sb.toString());
            try {
                Thread.sleep(period);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    */
}
