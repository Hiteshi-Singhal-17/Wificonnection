package com.example.wificonnection;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {
    private Switch wifiSwitch ;
    private WifiManager wifiManager ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wifiSwitch=findViewById(R.id.wifi_switch) ;
        //To instantiate the wifi Manager class
        wifiManager=(WifiManager)getApplicationContext().getSystemService(Context.WIFI_SERVICE) ;

        wifiSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    boolean wifiStatus = wifiManager.setWifiEnabled(true);
                    if(wifiStatus) {
                        //its 100% guaranteed that wifi is enabled
                    }else {
                        //Andrid system was not able to on WIFI due to XYZ internal reason.

                    }
                    wifiSwitch.setText("WiFi is ON");
                }
                else{
                    wifiManager.setWifiEnabled(false) ;
                    wifiSwitch.setText("WiFi is OFF");
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION) ;
        registerReceiver(wifiStateReceiver,intentFilter) ;
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(wifiStateReceiver);
    }

    private BroadcastReceiver wifiStateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int wifiStateExtra= intent.getIntExtra(wifiManager.EXTRA_WIFI_STATE,
                    wifiManager.WIFI_STATE_UNKNOWN) ;

            switch (wifiStateExtra){
                case WifiManager.WIFI_STATE_ENABLED:
                    wifiSwitch.setChecked(true);
                    wifiSwitch.setText("WiFi is ON");
                    break ;
                case WifiManager.WIFI_STATE_DISABLED:
                    wifiSwitch.setChecked(false);
                    wifiSwitch.setText("WiFi is OFF");
                    break ;
            }
        }
    };

}