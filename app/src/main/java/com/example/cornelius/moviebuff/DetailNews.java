package com.example.cornelius.moviebuff;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

/**
 * Created by Cornelius on 24/02/2018.
 */

public class DetailNews extends AppCompatActivity{
    String judulDetail,isiDetail;
    TextView txtJudulDetail,txtIsiDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_news);

        getSupportActionBar().setTitle("Cinema News");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtJudulDetail=(TextView)findViewById(R.id.txt_judul_detail);
        txtIsiDetail=(TextView)findViewById(R.id.txt_isi_detail);

        Intent intent = getIntent();

        judulDetail=intent.getStringExtra("judulDetail");
        isiDetail=intent.getStringExtra("isiDetail");

        txtJudulDetail.setText(judulDetail);
        txtIsiDetail.setText(isiDetail);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if (item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
