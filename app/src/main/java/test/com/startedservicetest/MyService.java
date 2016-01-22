package test.com.startedservicetest;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

public class MyService extends Service {

    /**
     * Debugging tag used by the Android logger.
     */
    private String TAG = getClass().getSimpleName();

    private boolean continueWorking;

    /**
     * Intent used to start the Service.
     */
    private static String ACTION_START_TEST_SERVICE = "action.start.test.service";


    public MyService() {
        continueWorking = false;
    }

    /**
     * This factory method returns an intent used to play and stop
     * playing a song, which is designated by the @a songURL.
     */
    public static Intent makeIntent(final Context context) {
        // Create and return an intent that points to the
        // MusicService.
        return new Intent(context, MyService.class);
    }


    /**
     * Hook method called when a new instance of Service is created.
     * One time initialization code goes here.
     */
    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate() entered");

        // Always call super class for necessary
        // initialization/implementation.
        super.onCreate();

        Log.d(TAG, "onCreate() exit");
    }

    /**
     * Hook method called every time startService() is called with an
     * Intent associated with this MusicService.
     */
    @Override
    public int onStartCommand (Intent intent,
                               int flags,
                               int startid) {
        Log.d(TAG, "onStartCommand() entered");

        startThread();


        // Don't restart Service if it shuts down.
        return START_NOT_STICKY;
    }

    private void startThread() {

        final Runnable runnableSleep = new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "Inside Thread start");

                continueWorking = true;
                int count = 0;

                while (continueWorking == true && count < 10) {
                    try {
                        TimeUnit.SECONDS.sleep(1);
                        count++;
                    } catch (InterruptedException e) {
                        Log.d(TAG, "onclickButton5: Error when sleeping");
                        e.printStackTrace();
                    }
                }

                Log.d(TAG, "Inside Thread end: count = " + count);

                stopSelf();
            }
        };
        new Thread(runnableSleep).start();

    }

    /**
     * Hook method called when the MusicService is stopped.
     */
    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy() entered");

        continueWorking = false;

        // Call up to the super class.
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
