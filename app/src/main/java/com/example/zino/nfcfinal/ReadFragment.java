package com.example.zino.nfcfinal;

import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by zino on 2016-06-23.
 */
public class ReadFragment extends Fragment implements View.OnClickListener {
    String TAG = this.getClass().getName();
    Button bt_read;
    TextView txt_msg;
    MainActivity mainActivity;
    ProgressBar bar;



    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.read_layout, container, false);

        bt_read = (Button) view.findViewById(R.id.bt_read);
        txt_msg = (TextView) view.findViewById(R.id.txt_msg);
        bar = (ProgressBar) view.findViewById(R.id.bar);
        bar.setVisibility(View.GONE);

        bt_read.setOnClickListener(this);

        Log.d(TAG, "getActivity() is " + getActivity());

        mainActivity = (MainActivity) getActivity();


        return view;
    }


    @Override
    public void onClick(View view) {
        readStart();
    }

    public void readStart() {
        mainActivity.mode="read";
        bar.setVisibility(View.VISIBLE);

    }

    public void listen(){
        Log.d(TAG, "intent of MainActivity is " + mainActivity.intent);
        Intent intent = mainActivity.intent;

        Parcelable[] parcelables=intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);

        if(parcelables == null){
            return;
        }
        bar.setVisibility(View.GONE);

        for(int a=0;a<parcelables.length;a++){
            NdefMessage ndefMessage=(NdefMessage)parcelables[a];

            NdefRecord[] records=ndefMessage.getRecords();

            for(int i=0;i<records.length;i++){
                String msg=decode(records[i].getPayload());
                txt_msg.setText(msg);
            }

        }
    }

    public String decode(byte[] buf) {
        String strText = "";
        String textEncoding = ((buf[0] & 0200) == 0) ? "UTF-8" : "UTF-16";
        int langCodeLen = buf[0] & 0077;

        try {
            strText = new String(buf, langCodeLen + 1, buf.length - langCodeLen - 1, textEncoding);
        } catch (Exception e) {
            Log.d("tag1", e.toString());
        }
        return strText;
    }
}
