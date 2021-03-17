package com.fisha.asynctask;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity
{
    private Button btn;
    private TextView txt;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBar);
        progressBar.setMax(5);
        txt =  findViewById(R.id.output);
        btn = findViewById(R.id.button1);
        btn.setOnClickListener(view -> {
            txt.setText("Result");
            progressBar.setProgress(0);
            new LongOperation(this).execute();
            //new LongOperationNestedClass().execute();
        });
    }

    // AsyncTask<Param, Progress, Result>
    private class LongOperationNestedClass extends AsyncTask<Void, Integer, String> {

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
            txt.setText(result);
            Toast.makeText(getApplicationContext(), "AsyncTask finished", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onPreExecute() {}

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressBar.setProgress(values[0]);
        }
    }
}
