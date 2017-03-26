package com.example.userasus.asynctask;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

//https://developer.android.com/reference/android/os/AsyncTask.html
public class MainActivity extends AppCompatActivity {

    LinearLayout linear;
    TextView valor;
    Pintar obj = new Pintar();
    Boolean on=false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        linear = (LinearLayout) findViewById(R.id.cuadro);
        valor = (TextView) findViewById(R.id.tvEscribe);
    }
    public void colorear(View v) throws InterruptedException {
        for(int h=0;h<=199;){
            pintar(h);
            Thread.sleep(50);
            h++;
        }
    }
    public void colorear2(View v){
        if(on){
            obj.cancel(true);
            obj.execute();
        }else{
            obj.execute();
            on= true;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(on){
            obj.cancel(true);
        }
    }

    public void pintar(int x){
        int red= this.generarAleatorio2();
        int green = this.generarAleatorio2();
        int blue = this.generarAleatorio2();
        linear.setBackgroundColor(Color.rgb(red,green,blue));
        //String label=valor.getText().toString();
        valor.setText("X: "+x);
    }
    public int generarAleatorio2(){
        return  new Random().nextInt(256);
    }

    public class Pintar extends AsyncTask<Void,Integer,Void>{
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            int red=generarAleatorio();
            int blue=generarAleatorio();
            int green=generarAleatorio();
            linear.setBackgroundColor(Color.rgb(red,green,blue));
            //String label=valor.getText().toString();
            valor.setText("X: "+String.valueOf(values[0]));
        }
        @Override
        protected Void doInBackground(Void... voids) {
            int x=0;
            while (x<=199){
                publishProgress(x);//Llama a >>>>> onProgressUpdate
                try {
                    Thread.sleep(50);
                    x++;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Context con = getApplicationContext();
            Toast.makeText(con,"Terminado",Toast.LENGTH_LONG).show();
        }
        @Override
        protected void onCancelled() {
            super.onCancelled();
                on=false;
                obj=null;
        }
        public int generarAleatorio(){
            return  new Random().nextInt(256);
        }
    }
}
