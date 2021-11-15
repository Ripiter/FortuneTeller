package com.peter.fortuneteller;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements  JokeWatcher {

    TextView jokeText;
    ApiCaller caller;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        jokeText = findViewById(R.id.joke_text);

        caller = new ApiCaller();

        caller.addListener(this);

        Button btn = findViewById(R.id.getjoke_btn);
        btn.setOnClickListener(this::getJokePressed);



        caller.getRequest(MainActivity.this);
    }

    public void getJokePressed(View v){
        caller.getRequest(MainActivity.this);
    }

    @Override
    public void update(String joke) {
        jokeText.setText(joke);
    }

    @Override
    public void errorUpdate(String errorMessage) {
        jokeText.setText(errorMessage);
    }
}