package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.jokeviewer.JokeActivity;
import com.example.jokeviewer.util.Constants;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;
import com.udacity.gradle.builditbigger.backend.myApi.model.Joke;

import java.io.IOException;


public class MainActivity extends AppCompatActivity {

    private CountingIdlingResource mIdlingResource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void tellJoke(View view) {
        new JokesEndpointsAsyncTask().execute();
    }

    class JokesEndpointsAsyncTask extends AsyncTask<Void, Void, Joke> {
        private MyApi myApiService;

        @Override
        protected Joke doInBackground(Void... params) {
            if (mIdlingResource != null) mIdlingResource.increment();
            if (myApiService == null) buildApiService();

            try {
                return myApiService.getJoke().execute();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Joke joke) {
            super.onPostExecute(joke);
            if (mIdlingResource != null) mIdlingResource.decrement();
            if (joke == null) {
                Toast.makeText(MainActivity.this,
                        getString(R.string.msg_no_joke_found),
                        Toast.LENGTH_SHORT).show();
                return;
            }

            Intent intent = new Intent(getApplicationContext(), JokeActivity.class);
            intent.putExtra(Constants.EXTRA_JOKE_CATEGORY, joke.getCategory());
            intent.putExtra(Constants.EXTRA_JOKE_DESCRIPTION, joke.getDescription());
            startActivity(intent);
        }

        private void buildApiService() {
            MyApi.Builder builder = new MyApi.Builder(
                    AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null
            ).setRootUrl("http://10.0.2.2:8080/_ah/api")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> request) throws IOException {
                            request.setDisableGZipContent(true);
                        }
                    });
            myApiService = builder.build();
        }
    }

    @VisibleForTesting
    @NonNull
    public CountingIdlingResource registerIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new CountingIdlingResource("idling_resource");
        }
        return mIdlingResource;
    }
}
