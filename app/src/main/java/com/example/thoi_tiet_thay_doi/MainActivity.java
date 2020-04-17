package com.example.thoi_tiet_thay_doi;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.database.sqlite.SQLiteDatabase;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<thoitiet> temp = new ArrayList<>();
    private ArrayAdapter<thoitiet> adt;
    private ListView lv;
    private EditText ed1;
    private  EditText ed2;
    private SQLiteDatabase db;
    // save kieu thoi tiet
    String kieuthoieit = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        anhXa();

        // tao csdl
        initData();
        //laydang kieu

    }
    private void anhXa()
    {
        ed1 = findViewById(R.id.ed1);
        ed2 = findViewById(R.id.ed2);
        lv = findViewById(R.id.lv1);
        adt = new ArrayAdapter<thoitiet>(this , 0 , temp){
            @Override
            public View getView(int position,  View convertView,  ViewGroup parent) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.item_temp , null );
                TextView thanhpho = convertView.findViewById(R.id.txt1);
                TextView nhietdo = convertView.findViewById(R.id.txt2);
                TextView trangthai = convertView.findViewById(R.id.txt3);
                ImageView hinhanh = convertView.findViewById(R.id.iamge1);
                thoitiet t = temp.get(position);
                thanhpho.setText(t.getNuoc());
                nhietdo.setText(String.valueOf(t.getTemp()));

                // tim gia tri nho nhat



                int idtemp = R.drawable.ic_may;
                if(t.getTemp() < 20)
                {
                    kieuthoieit = "mua to";
                    idtemp = R.drawable.ic_mua;
                    trangthai.setText("Mưa to");

                }
                else if(t.getTemp() >= 20 && t.getTemp() < 30)
                {
                    kieuthoieit = "may mu";
                    idtemp = R.drawable.ic_may;
                    trangthai.setText("mây mù");
                }
                else if(t.getTemp() >= 30)
                {
                    kieuthoieit = "nang to";
                    idtemp = R.drawable.ic_nang;
                    trangthai.setText("nang to ");
                }
                hinhanh.setImageResource(idtemp);
                return convertView;

            }
        };
        lv.setAdapter(adt);

        findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ThemMoi();
            }
        });

        findViewById(R.id.btn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timraThapNhat();
            }
        });
    }
    private void ThemMoi()
    {
      try {
          thoitiet t = new thoitiet();
          String nuoc = ed1.getText().toString().trim();
          String nhieudo = ed2.getText().toString().trim();

          if(TextUtils.isEmpty(nuoc))
          {
              Toast.makeText(this, "nhap thieu", Toast.LENGTH_SHORT).show();
              return;
          }
          if(TextUtils.isEmpty(nhieudo))
          {
              Toast.makeText(this, "Nhop thieu", Toast.LENGTH_SHORT).show();
              return;
          }
          t.setNuoc(nuoc);
          t.setTemp(Integer.parseInt(nhieudo));
          temp.add(t);
          SaveThoiTiet();
          adt.notifyDataSetChanged();
      }
      catch (Exception e)
      {
          Toast.makeText(this, "" + e, Toast.LENGTH_SHORT).show();
      }
    }
    private void initData()
    {
        db = openOrCreateDatabase("thoitiet.db",MODE_PRIVATE , null);
        String sql = "CREATE table IF NOT exists tbthoitiet (id integer primary key autoincrement, " + "name text," +  "kieu text," + "nhiet text)";
        db.execSQL(sql);
    }
    private void SaveThoiTiet() {
        String name = ed1.getText().toString();
        String nhiet = ed2.getText().toString();
        String sql = "INSERT INTO tbthoitiet (name, kieu, nhiet) VALUES(" + "'" + name + "'," + "'" + kieuthoieit + "'," + "'" + nhiet + "')";
        db.execSQL(sql);
    }
    private void timraThapNhat()
    {

        int min = temp.get(0).getTemp();
       String quocgia = "";
        for(int i = 0 ; i< temp.size() ; i++)
        {
            if(min > temp.get(i).getTemp())
            {
                min = temp.get(i).getTemp();
               quocgia = temp.get(i).getNuoc();
            }
        }
        Toast.makeText(this,""+ min, Toast.LENGTH_SHORT).show();

        Intent i = new Intent(this , InfoActivity.class);
        i.putExtra(InfoActivity.KEY_NUOC , quocgia);
        i.putExtra(InfoActivity.KEY_TEMP , min);
        startActivity(i);
    }
}
