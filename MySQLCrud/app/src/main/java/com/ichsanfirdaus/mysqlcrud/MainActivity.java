package com.ichsanfirdaus.mysqlcrud;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    Button btnLihatData;
    Button btnBuatData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLihatData = (Button) findViewById(R.id.btnLihatData);
        btnBuatData = (Button) findViewById(R.id.btnTambahData);

        btnLihatData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), SemuaBukuTamu.class);
                startActivity(i);
            }
        });

        btnBuatData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), TambahBukuTamu.class);
                startActivity(i);
            }
        });
    }
}
