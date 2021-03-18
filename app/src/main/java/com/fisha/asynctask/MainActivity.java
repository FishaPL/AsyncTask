package com.fisha.asynctask;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity
{
    private Button btn;
    private TextView txt;
    private ProgressBar progressBar;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setMax(5);
        txt =  findViewById(R.id.output);
        btn = findViewById(R.id.button1);
        btn.setOnClickListener(view -> {
            DownloadImage();
            new LongOperation(this).execute();
        });
    }

    private void DownloadImage () {
        ImageDownloaderNestedClass asyncTask = new ImageDownloaderNestedClass();
        try {
            URL url = new URL("https://developer.android.com/codelabs/android-training-create-asynctask/img/3833ce18e9dc9ccc.png");
            asyncTask.execute(url);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    // AsyncTask<Param, Progress, Result>
    private class ImageDownloaderNestedClass extends AsyncTask<URL, Integer, Bitmap> {

        @Override
        protected Bitmap doInBackground(URL... params)
        {
            try {
                HttpURLConnection connection = (HttpURLConnection) params[0].openConnection();
                connection.connect();
                InputStream inputStream = connection.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                return bitmap;

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if (bitmap != null)
                imageView.setImageBitmap(bitmap);
        }

        @Override
        protected void onPreExecute() {}

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressBar.setProgress(values[0]);
        }
    }
}
