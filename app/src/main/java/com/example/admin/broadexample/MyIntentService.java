package com.example.admin.broadexample;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.ResultReceiver;
import android.support.annotation.Nullable;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static java.lang.Thread.sleep;


public class MyIntentService extends IntentService {
    private final int STATUS_RUNNING=0;
    private final int STATUS_FINISHED=1;
    private final int STATUS_ERROR=2;

    private static final String TAG = "BroadcastService";

    public static final int notify = 5000;  //interval between two services(Here Service run every 5 seconds)
    int count = 0;  //number of times service is display
    private Handler mHandler = new Handler(  );   //run on another Thread to avoid crash
    private Timer mTimer = null;
    Intent mintent;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     */
    public MyIntentService() {
        super( null );
    }
    public MyIntentService(String name) {
        super( name );
    }

    @Override
    public void onStart(@Nullable Intent intent, int startId) {
        super.onStart( intent, startId );

    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        final ResultReceiver receiver = intent.getParcelableExtra("receiver");
        if (mTimer != null) // Cancel if already existed
            mTimer.cancel();
        else
            mTimer = new Timer();   //recreate new
        mTimer.scheduleAtFixedRate(new TimeDisplay(receiver), 0, notify);
        return START_STICKY;

    }

    @Override
    public void onCreate() {
        super.onCreate();
 //Schedule task
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

    }
//    @Override
//    protected void onHandleIntent(Intent intent) {}
//
//
//        final ResultReceiver receiver = intent.getParcelableExtra("receiver");
//        String command = intent.getStringExtra("command");
//        final Bundle b = new Bundle();
//        b.putString( "Hello","World!" );
//
//        new TimerTask() {
//            @Override
//            public void run() {
//                try {
//                    // get some data or something
//
//                    receiver.send(STATUS_FINISHED, b);
//                } catch(Exception e) {
//                    b.putString(Intent.EXTRA_TEXT, e.toString());
//                    receiver.send(STATUS_ERROR, b);
//                }
//                mHandler.post(myrun);
//            }
//        };
//
//        Book book = new Book(1,"123 book");
//
//        b.putParcelable("book",book);
//
//        if(command.equals("query")) {
//            receiver.send(STATUS_RUNNING,b );
//            try {
//                // get some data or something
//
//                receiver.send(STATUS_FINISHED, b);
//            } catch(Exception e) {
//                b.putString(Intent.EXTRA_TEXT, e.toString());
//                receiver.send(STATUS_ERROR, b);
//            }
//        }
//    }

    Runnable myrun = new Runnable() {
        @Override
        public void run() {

            Log.i(TAG, " >>>>>>>>>> Runnable().run ");
            //Toast.makeText(MyIntentService.this,"Run start" , Toast.LENGTH_SHORT).show();
        }
    };



    class TimeDisplay extends TimerTask {

        private final ResultReceiver receiver;

        public TimeDisplay(ResultReceiver receiver) {
            this.receiver = receiver;
        }

        @Override
        public void run() {
            Log.i(TAG, " >>>>>>>>>> TimerTask.run ");

           // final ResultReceiver receiver = intent.getParcelableExtra("receiver");

            String body;
            GetBody gb = new GetBody();
            Gson gson = new Gson();
            try {
                body=gb.run("https://api.mlab.com/api/1/databases/evotor_base/collections/ReceiptDiscount?apiKey=lqnYbFBA_7ot9ePXX3B9Dvwx-B6daWNI");
                //String jsonString = "{\"_id\":\"N95\",\"Discount\":\"WP\"}";
                //List<Id<Oid>>  ml = new ArrayList<Id<Oid>>();
                Type myTytpe = new TypeToken<myList<Id>>(){}.getType();
                myList<Id> ml = gson.fromJson(body,myTytpe);

                Bundle b = new Bundle();
                b.putString( "Hello",ml.get(0)._id.$oid);
                Intent intent = new Intent();
                intent.putExtras( b );
//                getApplicationContext().sendBroadcast(intent);
                receiver.send(STATUS_RUNNING,b );
                //b.putParcelable("book",book);
                //Toast.makeText(MyIntentService.this,/*ml.get(0)._id.$oid*/"666" , Toast.LENGTH_SHORT).show();

            } catch (Exception e) {
                e.printStackTrace();
            }


            mHandler.post(myrun);




        }

    }

}

class GetBody {

    OkHttpClient client = new OkHttpClient();

    String run(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        // System.out.println(response.body());

        return response.body().string();
    }
}

class myList<Id>  extends ArrayList<Id> {


}
class Id{
    public int Discount;
    public Oid _id;
    public Id(Oid _id,int Discount){
        this._id=_id;
        this.Discount = Discount;
    }

    class Oid{
        public String $oid;
        public  Oid(String $oid) {
            this.$oid = $oid;
        }
    }
}





