package com.sunsun.annotations;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;

import com.sunsun.discovery.IDiscoveryClient;
import com.sunsun.mylibrary.CoreEvent;
import com.sunsun.mylibrary.CoreManager;
import com.sunsun.util.MLog;
import com.sunsun.util.TimeLog;

import java.util.ArrayList;


public class MainActivity extends FragmentActivity implements View.OnClickListener {
    private static final String TAG = MainActivity.class.getSimpleName();

    private Button mbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mbutton = (Button) findViewById(R.id.button);
        mbutton.setOnClickListener(this);
        CoreManager.addClient(this);
    }

    @CoreEvent(coreClientClass = IDiscoveryClient.class)
    public void discoveryResult(int result) {
        MLog.verbose(TAG, "discoveryResult  result = " + result);
    }

    @Override
    public void onClick(View v) {
        int count=0;
        ArrayList<String> list =new ArrayList<String>();
        TimeLog.startTime(TAG,"1000次循环",true);
        for (int i =0;i<1000;i++){
            list.add("couont"+ count++);
            MLog.verbose(TAG, "discoveryResult  count = "+count++ );
        }
        TimeLog.stopTime(TAG,list.size()+"  1000次循环",true);
       SecondActivity.startSecondActivity(this, "http://wwww.baidu.com");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CoreManager.removeClient(this);
    }
}
