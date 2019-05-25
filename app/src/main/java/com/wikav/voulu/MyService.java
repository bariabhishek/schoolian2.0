package com.wikav.voulu;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

import androidx.annotation.NonNull;

public class MyService extends JobService {

    BackgroundTask backgroundTask;
    @Override
    public boolean onStartJob(final JobParameters job) {
        Toast.makeText(this, "startt", Toast.LENGTH_SHORT).show();
        backgroundTask= new BackgroundTask()
        {
            @Override
            protected void onPostExecute(String s) {
                Log.i("myJob", s);
                Toast.makeText(getApplicationContext(), "start: "+s, Toast.LENGTH_SHORT).show();
                jobFinished(job,false);
            }
        };
        backgroundTask.execute();

        return false;
    }

    @Override
    public boolean onStopJob(@NonNull JobParameters job) {
        return true;
    }

    public class BackgroundTask extends AsyncTask<Void,Void,String>
    {
        @Override
        protected String doInBackground(Void... voids) {
            return "mera Kam Ho rha he";
        }
    }
}
