package com.example.zino.nfcfinal;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    String TAG = this.getClass().getName();
    MyPagerAdapter pagerAdapter;
    ViewPager viewPager;

    NfcAdapter nfcAdapter;
    Intent intent;
    String mode="read";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "MainActivity is " + this);

        pagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        viewPager.setAdapter(pagerAdapter);

        init();
    }

    public void init() {
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (nfcAdapter == null) {
            Toast.makeText(this, "이 디바이스는 NFC를 지원하지 않습니다.", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    protected void onResume() {
        Intent intent = new Intent(this, this.getClass());
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        IntentFilter[] filters = new IntentFilter[]{};

        //현재 foreground에 있는 액티비티가 tag 정보를 받겟다!!
        nfcAdapter.enableForegroundDispatch(this, pendingIntent, filters, null);

        Log.d(TAG, "onResume()  호출");
        super.onResume();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        this.intent = intent;
        Log.d(TAG, "읽어들임 mode is "+mode);

        if(mode.equals("read")) {
            ReadFragment readFragment = (ReadFragment) pagerAdapter.fragments[0];
            readFragment.listen();
        }else if(mode.equals("write")){
            Log.d(TAG, "쓰기모드에서 읽어들임"+intent);
            WriteFragment writeFragment = (WriteFragment)pagerAdapter.fragments[1];
            writeFragment.writeData();
        }
        super.onNewIntent(intent);
    }

}
