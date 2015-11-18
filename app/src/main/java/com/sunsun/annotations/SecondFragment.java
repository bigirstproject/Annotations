package com.sunsun.annotations;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.sunsun.discovery.IDiscoveryClient;
import com.sunsun.discovery.IDiscoveryCore;
import com.sunsun.mylibrary.CoreEvent;
import com.sunsun.mylibrary.CoreFactory;
import com.sunsun.mylibrary.CoreManager;
import com.sunsun.util.MLog;


/**
 * A placeholder fragment containing a simple view.
 */
public class SecondFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = SecondFragment.class.getSimpleName();
    public static final String LIST_KEY = "list_key";
    private Button mbutton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CoreManager.addClient(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.second_frangment_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mbutton = (Button) view.findViewById(R.id.button);
        mbutton.setOnClickListener(this);
    }


    @CoreEvent(coreClientClass = IDiscoveryClient.class)
    public void discoveryResult(int result) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        MLog.verbose(TAG, "discoveryResult  result = " + result);
    }

    @Override
    public void onClick(View v) {
        CoreFactory.getCore(IDiscoveryCore.class).getDiscoveryInfo();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        CoreManager.removeClient(this);
    }
}
