package com.example.userasus.asynctask.Services;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Hora extends Service {
    public Hora() {
    }

    HoraService obj=null;

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(this,"Servicio creado",Toast.LENGTH_LONG).show();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        obj = new HoraService();
        obj.execute();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this,"Servicio destruido",Toast.LENGTH_LONG).show();
        obj.cancel(true);

    }
    public class HoraService extends AsyncTask<Void,String,Void>{
        private SimpleDateFormat dateFormat;
        private String date;
        private boolean cent;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dateFormat = new SimpleDateFormat("HH:mm:ss");
            cent = true;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            while(cent){
                if(obj.isCancelled()){
                    break;
                }
                try {
                    Thread.sleep(5000);
                    date = dateFormat.format(new Date());
                    publishProgress(date);///llamando al metodo onProgressUpdate
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            Toast.makeText(getApplicationContext(),"La hora es:"+values[0],Toast.LENGTH_LONG).show();
        }
        @Override
        protected void onCancelled() {
            super.onCancelled();
            cent=false;
            obj=null;
        }
    }
}
