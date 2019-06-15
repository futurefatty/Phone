package com.yx.phonetest;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Telephony;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yx.model.CustomerModel;

import java.util.ArrayList;
import java.util.List;

public class SendSMSActivity extends Activity implements View.OnClickListener {
    private ImageView iv_goback;
    private TextView tv_title, tv_right;
    private EditText et_input;
    private List<CustomerModel> choose;
    private int i = 0;

    // 自定义ACTION常数作为Intent Filter识别常数
    private String SMS_SEND_ACTION = "SMS_SEND_ACTION";
    // receiverlistner作为registerReceiver方法的receiver参数
    ReceiverListner receiverlistner = new ReceiverListner();
    // intentfilter作为registerReceiver方法的filter参数
    IntentFilter intentfilter = new IntentFilter();

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 30:
                    i++;
                    if (i < choose.size()) {
                        SendMsgIfSuc(choose.get(i).customer_number + "", et_input.getText().toString());
                    } else {
                        Toast.makeText(SendSMSActivity.this, "发送完成", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sendsms);
        iv_goback = findViewById(R.id.iv_goback);
        tv_title = findViewById(R.id.tv_title);
        tv_right = findViewById(R.id.tv_right);
        et_input = findViewById(R.id.et_input);
        iv_goback.setOnClickListener(this);
        tv_right.setOnClickListener(this);
        tv_right.setText("发送");
        tv_title.setText("短信");
        choose = (ArrayList<CustomerModel>) getIntent().getSerializableExtra("customer");
        String defaultSmsApp = null;
        String currentPn = getPackageName();//获取当前程序包名
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            defaultSmsApp = Telephony.Sms.getDefaultSmsPackage(this);//获取手机当前设置的默认短信应用的包名
        }
        if (!defaultSmsApp.equals(currentPn)) {
            Intent intent = new Intent(Telephony.Sms.Intents.ACTION_CHANGE_DEFAULT);
            intent.putExtra(Telephony.Sms.Intents.EXTRA_PACKAGE_NAME, currentPn);
            startActivity(intent);
        }

//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT)
        //申请成为默认短信
//        Intent intent = new Intent(Telephony.Sms.Intents.ACTION_CHANGE_DEFAULT);
//        intent.putExtra(Telephony.Sms.Intents.EXTRA_PACKAGE_NAME, getPackageName());
//        startActivity(intent);
        // 广播注册
        intentfilter.addAction(SMS_SEND_ACTION);
        registerReceiver(receiverlistner, intentfilter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_goback:
                finish();
                break;
            case R.id.tv_right:
                if (choose != null && choose.size() != 0) {
                    SendMsgIfSuc(choose.get(i).customer_number + "", et_input.getText().toString());
                }
                break;
        }
    }


    /**
     * 发送短信并监听是否发送成功
     *
     * @param num 电话号码
     * @param msg 信息内容
     */
    private void SendMsgIfSuc(String num, String msg) {
        SmsManager sms = SmsManager.getDefault();
        try {
            // 创建ACTION常数的Intent，作为PendingIntent的参数
            Intent SendIt = new Intent(SMS_SEND_ACTION);
            // 接收消息传送后的广播信息SendPendIt，作为信息发送sendTextMessage方法的sentIntent参数
            PendingIntent SendPendIt = PendingIntent.getBroadcast(getApplicationContext(), 0, SendIt, PendingIntent.FLAG_CANCEL_CURRENT);
            // 发送短信
            sms.sendTextMessage(num, null, msg, SendPendIt, null);
        } catch (Exception e) {
            // 异常提醒
            Log.i("-------------", "发送错误  " + e.getMessage());
        }
    }

    /**
     * 监听短信状态
     */
    public class ReceiverListner extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                // 获取短信状态
                switch (getResultCode()) {
                    // 短信发送成功
                    case Activity.RESULT_OK:
                        Log.i("-------------", "发送成功  ");
                        handler.sendEmptyMessage(30);
                        break;
                    // 短信发送不成功
                    default:
                        Log.i("-------------", "发送失败  ");
                        break;
                }
            } catch (Exception e) {
                Log.i("-------------", "监听短信状态错误  " + e.getMessage());
            }
        }
    }

}
