package com.example.tanxiao.opengldemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.tanxiao.opengldemo.decoration.SpaceColorItemDecoration;
import com.example.tanxiao.opengldemo.rcyadapter.base.BaseViewHolder;
import com.example.tanxiao.opengldemo.rcyadapter.quickadapter.QuickRcvAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    private RecyclerView recyclerView;
    private QuickRcvAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Example of a call to a native method
        TextView tv = (TextView) findViewById(R.id.sample_text);
        //        tv.setText(stringFromJNI());
        initRcv();
        initData();
    }

    private void initData() {
        List<String> list = new ArrayList<>();
        list.add("simple_Gl");
        list.add("Gl_camera_Preview");
        list.add("Gl_texture");
        list.add("Gl_texture_with_mallets");
        list.add("Gl_LUT_filters");
        adapter.replaceAll(list);
    }

    private void initRcv() {
        recyclerView = (RecyclerView) findViewById(R.id.rcv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,OrientationHelper.VERTICAL,false));
        adapter = new QuickRcvAdapter<String>(null, R.layout.item_simple) {
            @Override
            protected void convert(final BaseViewHolder<String> holder, final String item) {
                holder.setText(R.id.tv, item);
                holder.setOnClickListener(R.id.tv, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        toDemo(holder.getAdapterPosition(), item);
                    }
                });
            }
        };
        RecyclerView.ItemDecoration decoration = new SpaceColorItemDecoration(this, R.color.colorAccent,
                3, OrientationHelper.VERTICAL, SpaceColorItemDecoration.DRAW_OVER);
        recyclerView.addItemDecoration(decoration);
        recyclerView.setAdapter(adapter);
    }

    private void toDemo(int position, String item) {
        Intent intent = null;
        switch (position) {
            case 0: {
                intent = new Intent(this, GlActivity.class);
            }
            break;
            case 1: {
                intent = new Intent(this, GlPreviewActivity.class);
            }
            break;
            case 2: {
                intent = new Intent(this, GLTextureActivity.class);
            }
            break;
            case 3: {
                intent = new Intent(this, GLTextureWithMalletsActivity.class);
            }
            break;
            case 4: {
                intent = new Intent(this, GlLutFiltersActivity.class);
            }
            break;
            default:
                break;
        }
        startActivity(intent);
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}
