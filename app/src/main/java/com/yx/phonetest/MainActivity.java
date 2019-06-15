package com.yx.phonetest;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.provider.CallLog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.yx.util.TelephonyTool;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText et_phone,et_time;
    private Button bt_bh, bt_bh2;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 10:
                    TelephonyTool.endCall(MainActivity.this);
                    break;

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_phone = findViewById(R.id.et_phone);
        bt_bh = findViewById(R.id.bt_bh);
        bt_bh2 = findViewById(R.id.bt_bh2);
        et_time = findViewById(R.id.et_time);
        bt_bh.setOnClickListener(this);
        bt_bh2.setOnClickListener(this);
        TelephonyManager telManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        telManager.listen(new MyPhoneStateListener(), PhoneStateListener.LISTEN_CALL_STATE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_bh:
                String s = et_phone.getText().toString();
                String s1 = et_time.getText().toString();
                if (!TextUtils.isEmpty(s)) {
                    callPhone(s);
                    handler.sendEmptyMessageDelayed(10, Integer.parseInt(s1)*1000);
                }
                break;
            case R.id.bt_bh2:
//                TelephonyTool.answerRingingCall(this);
                TelephonyTool.endCall(this);
                Log.i("--------------", "是否接听" + getCallLogState());
                break;
        }
    }

    /**
     * 拨打电话（直接拨打电话）
     *
     * @param phoneNum 电话号码
     */
    public void callPhone(String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        startActivity(intent);
    }


    public class MyPhoneStateListener extends PhoneStateListener {
        private boolean isHangUp = false;//用来判断挂断电话

        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            Log.i("---------------", "状态a " + state);
            switch (state) {
                case TelephonyManager.CALL_STATE_IDLE: /* 无任何状态时 */
                    if (isHangUp) {//若isHangUp为true说明是由OFFHOOK状态触发得IDLE状态，所以为挂断
                        isHangUp = false;
                    }
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK: /* 接起电话时 */
                    isHangUp = true;
                    break;
                case TelephonyManager.CALL_STATE_RINGING: /* 电话进来时 */
                    Log.i("---------------", "来电号码 " + incomingNumber);
                    break;
            }
            super.onCallStateChanged(state, incomingNumber);
        }
    }

    private boolean getCallLogState() {
        ContentResolver cr = getContentResolver();
        final Cursor cursor = cr.query(CallLog.Calls.CONTENT_URI,
                new String[]{CallLog.Calls.NUMBER, CallLog.Calls.TYPE, CallLog.Calls.DURATION},
                CallLog.Calls.NUMBER + "=? and " + CallLog.Calls.TYPE + "= ?",
                new String[]{"10086", "CallLog.Calls.TYPE"}, null);
        while (cursor.moveToNext()) {
            int durationIndex = cursor.getColumnIndex(CallLog.Calls.DURATION);
            long durationTime = cursor.getLong(durationIndex);
            if (durationTime > 0) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }


}
