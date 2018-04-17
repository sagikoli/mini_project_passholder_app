package com.example.sagik.mini_project_firebase;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.Date;
import java.util.Random;

public class Code_Gen extends AppCompatActivity {
    int conv(int x,int k)
    {
        int y=0;
        int temp=x*k;
        int units=temp%10;
        temp/=10;
        if(temp==0)
            return units;
        else
        {
            y=units+temp;
            units=y%10;
            y=y/10;
            if(y==0)
                return units;
            else
                return y;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code__gen);
        ImageView img=findViewById(R.id.img);
        TextView txt=findViewById(R.id.textView7);
        Random r=new Random();
        Date dt=new Date();
        long temp= dt.getTime();
        String code="";
        String time=Long.toString(temp);
        char time_arr[]=time.toCharArray();
        int keys[]={1,2,4,5};
        int k=keys[r.nextInt(4)];
        int id=Integer.parseInt(MainActivity.unm);
        int id_digits[]=new  int[6];
        char id_chars[]=Integer.toString(id).toCharArray();
        for(int i = 0; i<id_chars.length; i++)
        {
            id_digits[i]=conv(Character.digit(id_chars[i],10),k);
        }

        int ctr=id_chars.length;
        ctr--;
        code+=k;
        for(int i=0;i<time_arr.length;i++)
        {
            if(ctr>=0) {
                code += time_arr[i];
                code += id_digits[ctr];
                ctr--;
            }
            else
            {
                code += time_arr[i];
                code += r.nextInt(10);
            }
        }
        Log.i("qwerty",time);
        Log.i("qwerty 2",code);
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {

            txt.setText(dt.toString());

            BitMatrix bitMatrix = multiFormatWriter.encode(code, BarcodeFormat.QR_CODE  , 1000, 1000);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            img.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();

        }
    }
}
