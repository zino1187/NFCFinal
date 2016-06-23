package com.example.zino.nfcfinal;

import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.tech.Ndef;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by zino on 2016-06-23.
 */
public class EraseFragment extends Fragment implements View.OnClickListener{
    Button bt_erase;
    TextView txt_msg;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.erase_layout, container, false);

        bt_erase =(Button)view.findViewById(R.id.bt_erase);
        txt_msg = (TextView) view.findViewById(R.id.txt_msg);

        return view;
    }

    public void erase(){
        //Ndef ndefTag = Ndef.get(tag);
        //ndefTag.writeNdefMessage(new NdefMessage(new NdefRecord(NdefRecord.TNF_EMPTY, null, null, null)));
    }

    @Override
    public void onClick(View view) {
        erase();
    }
}
