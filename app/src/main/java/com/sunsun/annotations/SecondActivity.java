package com.sunsun.annotations;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import com.sunsun.util.TimeLog;


public class SecondActivity extends FragmentActivity {

    public static final String TAG = SecondActivity.class
            .getSimpleName();

    public static void startSecondActivity(Context context, String url) {
        TimeLog.startTime(TAG, "startSecondActivity", true);
        Intent intent = new Intent(context, SecondActivity.class);
        intent.putExtra(SecondFragment.LIST_KEY, url);
        context.startActivity(intent);
    }

    public static final String TAG_NAME_FRAGMENT = "fragment";
    private Fragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TimeLog.stopTime(TAG,"onCreate", true);
        setContentView(R.layout.second_activity_layout);
        mFragment = getSupportFragmentManager().findFragmentByTag(
                TAG_NAME_FRAGMENT);
    }

    @Override
    public void onResume() {
        super.onResume();
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();
        if (mFragment == null) {
            mFragment = new SecondFragment();
            Intent intent = getIntent();
            if (intent != null) {
                Bundle params = intent.getExtras();
                if (mFragment != null) {
                    mFragment.setArguments(params);
                }
            }
            transaction.add(R.id.container, mFragment, TAG_NAME_FRAGMENT);
        }
        transaction.show(mFragment);
        transaction.commitAllowingStateLoss();
    }

}
