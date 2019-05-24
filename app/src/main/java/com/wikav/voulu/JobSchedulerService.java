package com.wikav.voulu;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.util.Log;
import android.widget.Toast;


public class JobSchedulerService extends JobService {
    String TAG="myJob";
   private Mexecuter mexecuter;
    @Override
    public boolean onStartJob(final JobParameters params) {

        mexecuter= new Mexecuter(getApplicationContext()){
            @Override
            protected void onPostExecute(String s) {
               super.onPostExecute(s);
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
                Log.i(TAG, s);
                jobFinished(params,false);
            }
        };
        mexecuter.execute();
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.i(TAG, "onStopJob:");
        mexecuter.cancel(true);
        return false;
    }
}
