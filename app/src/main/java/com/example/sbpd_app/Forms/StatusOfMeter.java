package com.example.sbpd_app.Forms;

import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Toast;

import com.example.sbpd_app.Database;
import com.example.sbpd_app.R;

public class StatusOfMeter extends AppCompatActivity {

    EditText et[]=new EditText[6];
    Button btn;
    ScrollView scrollView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_of_meter);
        scrollView=(ScrollView)findViewById(R.id.scrollMeter);
        et[0]=(EditText)findViewById(R.id.etId_meter33total);
        et[1]=(EditText)findViewById(R.id.etId_meter33working);
        et[2]=(EditText)findViewById(R.id.etId_meter33defect);

        et[3]=(EditText)findViewById(R.id.etId_meter11total);
        et[4]=(EditText)findViewById(R.id.etId_meter11working);
        et[5]=(EditText)findViewById(R.id.etId_meter11defect);

        et[2].setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus)
                {
                    if(et[2].getText().toString().length()>0 && et[0].getText().toString().length()>0 && et[1].getText().toString().length()>0)
                    {
                        int total,working,defect;
                        try{
                            total=Integer.parseInt(et[0].getText().toString());
                            working=Integer.parseInt(et[1].getText().toString());
                            defect=Integer.parseInt(et[2].getText().toString());
                            if(working+defect != total) {
                                Toast.makeText(StatusOfMeter.this, getString(R.string.meterWarning), Toast.LENGTH_LONG).show();
                                scrollView.fullScroll(View.FOCUS_UP);
                            }
                        }catch (NumberFormatException e)
                        {
                            Toast.makeText(StatusOfMeter.this,""+e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

        et[5].setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus)
                {
                    if(et[5].getText().toString().length()>0 && et[3].getText().toString().length()>0 && et[4].getText().toString().length()>0)
                    {
                        int total,working,defect;
                        try{
                            total=Integer.parseInt(et[3].getText().toString());
                            working=Integer.parseInt(et[4].getText().toString());
                            defect=Integer.parseInt(et[5].getText().toString());
                            if(working+defect != total) {
                                Toast.makeText(StatusOfMeter.this, getString(R.string.MeteringWarning), Toast.LENGTH_SHORT).show();
                                scrollView.scrollTo(0,et[2].getTop());
                            }
                        }catch (NumberFormatException e)
                        {
                            Toast.makeText(StatusOfMeter.this,""+e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }

    public void setSaveMeter(View v)
    {
        Integer value33[]=new Integer[3];
        Integer value11[]=new Integer[3];
        for(int i=0;i<3;i++)
        {
            try{
                value33[i]=Integer.parseInt(et[i].getText().toString().trim());
            }catch (SQLiteException e)
            {
                value33[i]=-1;
            }
        }

        for(int i=3;i<6;i++)
        {
            try{
                value11[i-3]=Integer.parseInt(et[i].getText().toString().trim());
            }catch (SQLiteException e)
            {
                value11[i-3]=-1;
            }
        }

        try {
            Database db=new Database(this);
            db.open();
            db.saveMeter(value33,"33KV");
            db.saveMeter(value11,"11KV");
            db.close();
            Toast.makeText(StatusOfMeter.this,"Saved successfully",Toast.LENGTH_SHORT).show();
        }catch (SQLiteException e)
        {
            Toast.makeText(StatusOfMeter.this,""+e.getMessage(),Toast.LENGTH_SHORT).show();
        }finally {
            for(int i=0;i<6;i++)
                et[i].setText("");
        }
        startActivity(new Intent(StatusOfMeter.this,PhotoAttactment.class));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(StatusOfMeter.this,com.example.sbpd_app.Home.class));
    }
}

