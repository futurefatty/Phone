package com.yx.phonetest;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.yx.db.CustomerDao;
import com.yx.model.CustomerModel;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends Activity implements View.OnClickListener {
    private TextView tv_zy, tv_dr, tv_pl, tv_bh;
    private CustomerDao dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        tv_zy = findViewById(R.id.tv_zy);
        tv_dr = findViewById(R.id.tv_dr);
        tv_pl = findViewById(R.id.tv_pl);
        tv_bh = findViewById(R.id.tv_bh);

        tv_zy.setOnClickListener(this);
        tv_dr.setOnClickListener(this);
        tv_pl.setOnClickListener(this);
        tv_bh.setOnClickListener(this);
        dao = new CustomerDao(this);
        List<CustomerModel> m = new ArrayList<>();
        m.add(new CustomerModel(10086, 1, 0, "电信", System.currentTimeMillis() + "", "批号1"));
        m.add(new CustomerModel(1008611, 1, 0, "电信查话费", System.currentTimeMillis() + "", "批号1"));
        Gson gson = new Gson();
        String s = gson.toJson(m);
        Log.i("----------------","json  "+s);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_zy://验证
                break;
            case R.id.tv_dr://导入
                List<CustomerModel> copy = getCopy(HomeActivity.this);
                for (CustomerModel customerModel : copy) {
                    dao.add(customerModel);
                }
                Toast.makeText(this, "成功导入"+copy+"条数据", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_pl://批量发送
                Intent intent = new Intent(HomeActivity.this, CustomerListActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_bh://拨号
                Intent intent2 = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(intent2);
                break;
        }
    }

    public List<CustomerModel> getCopy(Context context) {
        // 获取系统剪贴板
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        // 返回数据
        ClipData clipData = clipboard.getPrimaryClip();
        if (clipData != null && clipData.getItemCount() > 0) {
            // 从数据集中获取（粘贴）第一条文本数据
            String s = clipData.getItemAt(0).getText().toString();
            Gson gson = new Gson();
            //Json的解析类对象
            JsonParser parser = new JsonParser();
            //将JSON的String 转成一个JsonArray对象
            JsonArray jsonArray = parser.parse(s).getAsJsonArray();
            return gson.fromJson(jsonArray, new TypeToken<List<CustomerModel>>() {
            }.getType());
        }
        return null;
    }


}
