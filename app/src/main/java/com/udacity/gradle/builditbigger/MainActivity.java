package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.jokeslibrary.Joke;
import com.example.jokeslibrary.JokeMaker;
import com.example.jokeviewer.JokeActivity;
import com.example.jokeviewer.util.Constants;


public class MainActivity extends AppCompatActivity {

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
        Joke joke = JokeMaker.getInstance().getRandomJoke();

        Intent intent = new Intent(getApplicationContext(), JokeActivity.class);
        intent.putExtra(Constants.EXTRA_JOKE_CATEGORY, joke.getCategory());
        intent.putExtra(Constants.EXTRA_JOKE_DESCRIPTION, joke.getDescription());
        startActivity(intent);
    }

}
