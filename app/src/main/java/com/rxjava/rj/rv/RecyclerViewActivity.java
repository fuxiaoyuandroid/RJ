package com.rxjava.rj.rv;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.rxjava.rj.R;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewActivity extends AppCompatActivity {
    private HeaderOrFooterRecyclerView rv;
    private List<String> list;

    private FooterAdapter adapter;
    /*private WrapRecyclerViewAdapter mAdapter;*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add("这纯属是测试数据,闹着玩的"+i);
        }
        adapter = new FooterAdapter(list,this);
       /* mAdapter = new WrapRecyclerViewAdapter(adapter);*/
        rv = (HeaderOrFooterRecyclerView) findViewById(R.id.rv);
        LinearLayoutManager manager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rv.setLayoutManager(manager);
        rv.setAdapter(adapter);
        View headView = LayoutInflater.from(this).inflate(R.layout.header_layout,rv,false);
        rv.addHeaderView(headView);
        headView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RecyclerViewActivity.this,"这已经是最新数据了",Toast.LENGTH_LONG).show();
            }
        });

        adapter.setOnItemClickLister(new FooterAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(RecyclerViewActivity.this,list.get(position),Toast.LENGTH_LONG).show();
                list.remove(position);
                adapter.notifyDataSetChanged();
            }
        });
        rv.addFooterView(LayoutInflater.from(this).inflate(R.layout.footer_layout,rv,false));
    }
}
