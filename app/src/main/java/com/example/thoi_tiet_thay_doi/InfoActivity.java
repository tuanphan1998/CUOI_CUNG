package com.example.thoi_tiet_thay_doi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class InfoActivity extends AppCompatActivity {

    public static final String KEY_NUOC = "day";
    public static final String KEY_TEMP = "temp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        String day = getIntent().getStringExtra(KEY_NUOC);
        int temp = getIntent().getIntExtra(KEY_TEMP , 0);
        TextView nuoc = findViewById(R.id.txt11);
        nuoc.setText(""+day);

        TextView temp = findViewById(R.id.txt12);
        temp.setText("" + temp);
    }
}
