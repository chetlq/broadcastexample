package com.example.admin.broadexample;

import android.content.Intent;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MyResultReceiver.Receiver{

    public MyResultReceiver mReceiver;
    private Button mCheatButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        mReceiver = new MyResultReceiver(new Handler());
        mReceiver.setReceiver(this);

        final Intent intent = new Intent(Intent.ACTION_SYNC, null, this, MyIntentService.class);



        mCheatButton = (Button)findViewById(R.id.test_button);
        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                intent.putExtra("receiver", mReceiver);
                intent.putExtra("command", "query");
                startService(intent);

            }
        });
    }




    @Override
    protected void onPause() {
        super.onPause();
        mReceiver.setReceiver(null);
    }

    @Override
    public void onReceiveResult(int resultCode, Bundle resultData) {
        switch (resultCode) {
            case 0:
                String results2 = resultData.getString( "Hello" );
                Toast toast = Toast.makeText(getApplicationContext(),
                        results2, Toast.LENGTH_SHORT);
                toast.show();
                break;
            case 1:

//                Book book1 = resultData.getParcelable("book");
//                String results3 = resultData.getString( "Hello" );
//                Toast toast2 = Toast.makeText(getApplicationContext(),
//                        (CharSequence) book1.title, Toast.LENGTH_SHORT);
//                toast2.show();
                // do something interesting
                // hide progress
                break;
            case 2:
                // handle the error;
                break;
        }
    }


    public interface ResultReceiverCallBack<T>{
        public void onSuccess(T data);
        public void onError(Exception exception);
    }


}


