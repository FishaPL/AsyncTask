package com.fisha.asynctask;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.ref.WeakReference;

public class LongOperation extends AsyncTask<Void, Integer, String>
{
    private final WeakReference<Activity> activity;
    private final WeakReference<TextView> textView;
    private final WeakReference<ProgressBar> progressBar;

    public LongOperation(Activity activity) {
        this.activity = new WeakReference<>(activity);
        this.textView = new WeakReference<>(activity.findViewById(R.id.output));
        this.progressBar = new WeakReference<>(activity.findViewById(R.id.progressBar));
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
        textView.get().setText(result);
        Toast.makeText(activity.get(), "AsyncTask finished", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPreExecute() {
        textView.get().setText("Result");
        progressBar.get().setProgress(0);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        progressBar.get().setProgress(values[0]);
    }
}