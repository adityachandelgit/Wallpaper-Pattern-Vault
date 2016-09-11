package com.retroid.secret.diary.pattern.locker.vault;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.startapp.android.publish.StartAppAd;
import com.startapp.android.publish.StartAppSDK;

public class MainActivity extends ActionBarActivity {

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.app_toolbar);
        setSupportActionBar(toolbar);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        Boolean masterPassSet = preferences.getBoolean("MasterPassSet", false);

        if (!masterPassSet) {
            getFragmentManager().beginTransaction().add(R.id.container, new MasterPassFragment()).commit();
        } else {
            getFragmentManager().beginTransaction().add(R.id.container, new SetPatternFragment()).commit();
        }
        /*if (savedInstanceState == null) {
			getFragmentManager().beginTransaction().add(R.id.container, new PlaceholderFragment()).commit();
		}*/


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        MainActivity.this.finish();
        System.exit(0);
    }
}