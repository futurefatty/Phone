package com.yx.phonetest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yx.adapter.CustomerAdapter;
import com.yx.db.CustomerDao;
import com.yx.model.CustomerListModel;
import com.yx.model.CustomerModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CustomerListActivity extends Activity implements View.OnClickListener {
    private ImageView iv_goback;
    private TextView tv_title, tv_right;
    private RecyclerView rv_list;
    private CustomerAdapter customerAdapter;
    private CustomerDao customerDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);
        iv_goback = findViewById(R.id.iv_goback);
        tv_title = findViewById(R.id.tv_title);
        tv_right = findViewById(R.id.tv_right);
        rv_list = findViewById(R.id.rv_list);
        iv_goback.setOnClickListener(this);
        tv_right.setOnClickListener(this);
        tv_right.setText("确定");
        tv_title.setText("群发");

        customerDao = new CustomerDao(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rv_list.setLayoutManager(linearLayoutManager);
        List<CustomerModel> all = customerDao.findAll();
        if (all != null && all.size() > 0) {
            List<CustomerListModel> data = new ArrayList<>();
            for (int i = 0; i < all.size(); i++) {
                String title = all.get(i).customer_title;
                if (isrepeat(data, title)) continue;
                data.add(new CustomerListModel(title, 1, false, null));
                for (int j = 0; j < all.size(); j++) {
                    if (title.equals(all.get(j).customer_title)) {
                        data.add(new CustomerListModel(title, 2, false, all.get(j)));
                    }
                }
            }
            customerAdapter = new CustomerAdapter(data, this);
            rv_list.setAdapter(customerAdapter);
        }
    }

    private boolean isrepeat(List<CustomerListModel> data, String title) {
        for (int k = 0; k < data.size(); k++) {
            if (data.get(k).title.equals(title)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_goback:
                finish();
                break;
            case R.id.tv_right://发送
                List<CustomerModel> choose = customerAdapter.getChoose();
                Intent intent = new Intent(CustomerListActivity.this, SendSMSActivity.class);
                intent.putExtra("customer", (Serializable) choose);
                startActivity(intent);
                break;
        }
    }
}
