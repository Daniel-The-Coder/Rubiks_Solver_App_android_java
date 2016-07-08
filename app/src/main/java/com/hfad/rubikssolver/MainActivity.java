package com.hfad.rubikssolver;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    //HashMap<String,char[][]> cube;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Storage.cube=new HashMap<>();
        Storage.cube.put("top", new char[3][3]);
        Storage.cube.put("front", new char[3][3]);
        Storage.cube.put("left", new char[3][3]);
        Storage.cube.put("right", new char[3][3]);
        Storage.cube.put("bottom", new char[3][3]);
        Storage.cube.put("back", new char[3][3]);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CharSequence text = "Cube cleared.";
        Toast toast=Toast.makeText(this, text, Toast.LENGTH_SHORT);
        toast.show();
    }

    public void onStart(View view){
        Intent intent = new Intent(this, TopActivity.class);
        //intent.putExtra("cube", Storage.cube);
        startActivity(intent);
    }
}
