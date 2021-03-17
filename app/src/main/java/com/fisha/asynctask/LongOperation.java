package com.fisha.asynctask;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class LongOperation extends AsyncTask<Void, Integer, String>
{
    private final Activity activity;
    private TextView textView;
    private ProgressBar progressBar;

    public LongOperation(Activity activity) {
        this.activity = activity;
    }

    @Override
    protected String doInBackground(Void... params)
    {
        for (int i = 1; i <= 5; i++) {
            try {
                Thread.sleep(1000);
                publishProgress(i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return "Executed";
    }

    @Override
    protected void onPostExecute(String result) {
        textView.setText(result);
        Toast.makeText(activity, "AsyncTask finished", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPreExecute() {
        textView = activity.findViewById(R.id.output);
        progressBar = activity.findViewById(R.id.progressBar);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        progressBar.setProgress(values[0]);
    }
}