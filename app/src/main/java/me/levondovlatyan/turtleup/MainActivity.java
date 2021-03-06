package me.levondovlatyan.turtleup;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button buttonPlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //getting the button
        buttonPlay = (Button) findViewById(R.id.buttonPlay);

        //adding a click listener
        buttonPlay.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        //starting game activity
        startActivity(new Intent(this, GameActivity.class));
    }
}
