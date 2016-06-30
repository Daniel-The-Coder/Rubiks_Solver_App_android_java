package com.hfad.rubikssolver;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;

public class Solver extends AppCompatActivity {

    public HashMap<String, char[][]> cube = new HashMap<>();
    private CubeConfig cubeConfig;
    private CubeConfig solution;
    private ArrayList<String> moves;
    private Backtracker backtracker;
    public boolean missingSelections = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solver);

        Intent intent = getIntent();
        this.cube = (HashMap<String, char[][]>)intent.getSerializableExtra("cube");

        this.backtracker = new Backtracker();
    }

    public void solve(View view) {
        TextView txt = (TextView) findViewById(R.id.textView);
        txt.setText("SOLVING...");

        CharSequence text1 = "Solving... This may take a while.";
        Toast toast1=Toast.makeText(this, text1, Toast.LENGTH_LONG);
        toast1.show();
        double start = System.currentTimeMillis(); // start the clock
        String time;
        try {
            this.cubeConfig = new CubeConfig(this.cube);
            //System.out.println(this.cubeConfig);
            this.solution = (CubeConfig) this.backtracker.solve(cubeConfig);
            this.moves = solution.getMoves();
            if (moves.size()==0){
                CharSequence text = "Cube is already solved.";
                Toast toast=Toast.makeText(this, text, Toast.LENGTH_LONG);
                toast.show();
            }
            else{
                CharSequence text = "Solved.";
                Toast toast=Toast.makeText(this, text, Toast.LENGTH_LONG);
                toast.show();
            }
            time=("Elapsed time: " +
                    (System.currentTimeMillis() - start)/1000.0 + " seconds");
            String st = time+"\n\nSteps to solution: \n\n";
            for (String s : moves) {
                st += s + "\n";
            }
            if (moves.size()!=0) {
                txt.setText(st);
            }
            else{
                txt.setText("Cube is already solved.");
            }
        } catch (NoSuchElementException e) {
            time=("Elapsed time: " +
                    (System.currentTimeMillis() - start)/1000.0 + " seconds");
            txt.setText(time+"\n\nOperation terminated, it's taking too long..");
            CharSequence text2 = "Ran out of time, can't solve this cube.";
            Toast toast2=Toast.makeText(this, text2, Toast.LENGTH_LONG);
            toast2.show();
        }
    }

    public void home(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}

