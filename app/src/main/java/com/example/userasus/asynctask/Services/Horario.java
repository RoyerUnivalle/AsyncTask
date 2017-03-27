package com.example.userasus.asynctask.Services;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Horario extends Service {

    MostrarHora myTask=null;

    public Horario() {
    }

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
        MostrarHora hora = new MostrarHora();
        myTask=hora;
        myTask.execute();
        String estado= myTask.getStatus().toString();
        Toast.makeText(this, "onStartCommand! : "+estado, Toast.LENGTH_SHORT).show();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        myTask.cancel(true);
        Toast.makeText(this,"Servicio destruido",Toast.LENGTH_LONG).show();
    }

    public  class MostrarHora extends AsyncTask<Void,String,Void>{

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
            int i=0;
            while (cent){
                if(myTask.isCancelled()){
                    break;
                }
                date = dateFormat.format(new Date());
                try {
                    publishProgress(date);
                    // Stop 5s
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                i++;
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            Toast.makeText(getApplicationContext(), "Hora actual: " + values[0], Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            cent=false;
        }
    }
}
