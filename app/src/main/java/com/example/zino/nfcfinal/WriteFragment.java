package com.example.zino.nfcfinal;

import android.content.Intent;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Locale;

/**
 * Created by zino on 2016-06-23.
 */
public class WriteFragment extends Fragment implements View.OnClickListener{
    String TAG=this.getClass().getName();
    EditText edit_input;
    Button bt_write;
    TextView txt_msg;
    ProgressBar bar;
    MainActivity mainActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.write_layout, container, false);

        edit_input=(EditText)view.findViewById(R.id.edit_input);
        bt_write =(Button)view.findViewById(R.id.bt_write);
        txt_msg = (TextView) view.findViewById(R.id.txt_msg);
        bar = (ProgressBar)view.findViewById(R.id.bar);

        mainActivity=(MainActivity) getActivity();

        bt_write.setOnClickListener(this);
        return view;
    }

    public void startWrite(){
        mainActivity.mode="write";
        bar.setVisibility(View.VISIBLE);
    }

    public void writeData(){
        //Parcelable[] parcelables=mainActivity.intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
        Tag tag=mainActivity.intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);

        if(tag == null){
            return;
        }
        bar.setVisibility(View.GONE);


        NdefRecord ndefRecord = createTextRecord(edit_input.getText().toString() ,true);
        NdefMessage ndefMessage = new NdefMessage(new NdefRecord[]{ndefRecord});

        try {
            Ndef ndef = Ndef.get(tag);

            if (ndef == null) {
                formatTag(tag, ndefMessage);
                Toast.makeText(mainActivity, "태그를 포맷하겠습니다", Toast.LENGTH_SHORT).show();
            } else {
                ndef.connect();
                if (!ndef.isWritable()) {
                    ndef.close();
                    Toast.makeText(mainActivity, "쓰기를 할 수 없습니다", Toast.LENGTH_SHORT).show();
                    return;
                }
                ndef.writeNdefMessage(ndefMessage);
                ndef.close();
                Toast.makeText(mainActivity, "쓰기 완료", Toast.LENGTH_SHORT).show();
                edit_input.setText("");
            }
        } catch (Exception e) {

        }
    }

    public NdefRecord createTextRecord(String payload, boolean encodeInUtf8) {
        byte[] langBytes = Locale.getDefault().getLanguage().getBytes(Charset.forName("US-ASCII"));
        Charset utfEncoding = encodeInUtf8 ? Charset.forName("UTF-8") : Charset.forName("UTF-16");
        byte[] textBytes = payload.getBytes(utfEncoding);
        int utfBit = encodeInUtf8 ? 0 : (1 << 7);
        char status = (char) (utfBit + langBytes.length);
        byte[] data = new byte[1 + langBytes.length + textBytes.length];
        data[0] = (byte) status;
        System.arraycopy(langBytes, 0, data, 1, langBytes.length);
        System.arraycopy(textBytes, 0, data, 1 + langBytes.length, textBytes.length);
        NdefRecord record = new NdefRecord(NdefRecord.TNF_WELL_KNOWN, NdefRecord.RTD_TEXT, new byte[0], data);
        return record;
    }
    public void formatTag(Tag tag, NdefMessage ndefMessage) {
        NdefFormatable ndefFormatable = NdefFormatable.get(tag);

        try {
            if (ndefFormatable == null) {
                Toast.makeText(mainActivity, "형식이 알맞지 않습니다", Toast.LENGTH_SHORT).show();
            } else {

            }
            ndefFormatable.connect();
            ndefFormatable.format(ndefMessage);
            ndefFormatable.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (FormatException e) {
            e.printStackTrace();
        }

    }
    public void onClick(View view){
        startWrite();
    }

}
