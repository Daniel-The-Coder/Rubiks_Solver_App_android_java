package com.hfad.rubikssolver;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class FinalView extends AppCompatActivity {

    //HashMap<String, char[][]> cube = new HashMap<>();
    HashMap<String, ArrayList<Button>> buttonsMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_view);

        //POPULATE HASHMAP WITH ALL THE BUTTONS

        ArrayList<Button> top = new ArrayList<>();
        top.add((Button) findViewById(R.id.top11));top.add((Button) findViewById(R.id.top12));top.add((Button) findViewById(R.id.top13));
        top.add((Button) findViewById(R.id.top21));top.add((Button) findViewById(R.id.top22));top.add((Button) findViewById(R.id.top23));
        top.add((Button) findViewById(R.id.top31));top.add((Button) findViewById(R.id.top32));top.add((Button) findViewById(R.id.top33));
        buttonsMap.put("top", top);

        ArrayList<Button> front = new ArrayList<>();
        front.add((Button) findViewById(R.id.front11));front.add((Button) findViewById(R.id.front12));front.add((Button) findViewById(R.id.front13));
        front.add((Button) findViewById(R.id.front21));front.add((Button) findViewById(R.id.front22));front.add((Button) findViewById(R.id.front23));
        front.add((Button) findViewById(R.id.front31));front.add((Button) findViewById(R.id.front32));front.add((Button) findViewById(R.id.front33));
        buttonsMap.put("front", front);

        ArrayList<Button> left = new ArrayList<>();
        left.add((Button) findViewById(R.id.left11));left.add((Button) findViewById(R.id.left12));left.add((Button) findViewById(R.id.left13));
        left.add((Button) findViewById(R.id.left21));left.add((Button) findViewById(R.id.left22));left.add((Button) findViewById(R.id.left23));
        left.add((Button) findViewById(R.id.left31));left.add((Button) findViewById(R.id.left32));left.add((Button) findViewById(R.id.left33));
        buttonsMap.put("left", left);

        ArrayList<Button> right = new ArrayList<>();
        right.add((Button) findViewById(R.id.right11));right.add((Button) findViewById(R.id.right12));right.add((Button) findViewById(R.id.right13));
        right.add((Button) findViewById(R.id.right21));right.add((Button) findViewById(R.id.right22));right.add((Button) findViewById(R.id.right23));
        right.add((Button) findViewById(R.id.right31));right.add((Button) findViewById(R.id.right32));right.add((Button) findViewById(R.id.right33));
        buttonsMap.put("right", right);

        ArrayList<Button> bottom = new ArrayList<>();
        bottom.add((Button) findViewById(R.id.bottom11));bottom.add((Button) findViewById(R.id.bottom12));bottom.add((Button) findViewById(R.id.bottom13));
        bottom.add((Button) findViewById(R.id.bottom21));bottom.add((Button) findViewById(R.id.bottom22));bottom.add((Button) findViewById(R.id.bottom23));
        bottom.add((Button) findViewById(R.id.bottom31));bottom.add((Button) findViewById(R.id.bottom32));bottom.add((Button) findViewById(R.id.bottom33));
        buttonsMap.put("bottom", bottom);

        ArrayList<Button> back = new ArrayList<>();
        back.add((Button) findViewById(R.id.back11));back.add((Button) findViewById(R.id.back12));back.add((Button) findViewById(R.id.back13));
        back.add((Button) findViewById(R.id.back21));back.add((Button) findViewById(R.id.back22));back.add((Button) findViewById(R.id.back23));
        back.add((Button) findViewById(R.id.back31));back.add((Button) findViewById(R.id.back32));back.add((Button) findViewById(R.id.back33));
        buttonsMap.put("back", back);

        Intent intent = getIntent();
        //this.cube = (HashMap<String, char[][]>)intent.getSerializableExtra("cube");

        //read hashmap and color grid
        for (String key:Storage.cube.keySet()){
            char[][] thisFace = Storage.cube.get(key);
            for (int i=0;i<3;i++){
                for (int j=0;j<3;j++){
                    int buttonIndex = i*3 + j;
                    Button btn = buttonsMap.get(key).get(buttonIndex);

                    if (thisFace[i][j] == 'W'){
                        btn.setBackgroundColor(Color.WHITE);
                    }
                    else if (thisFace[i][j] == 'Y'){
                        btn.setBackgroundColor(Color.YELLOW);
                    }
                    else if (thisFace[i][j] == 'O'){
                        btn.setBackgroundColor(Color.MAGENTA);
                    }
                    else if (thisFace[i][j] == 'R'){
                        btn.setBackgroundColor(Color.RED);
                    }
                    else if (thisFace[i][j] == 'B'){
                        btn.setBackgroundColor(Color.BLUE);
                    }
                    else if (thisFace[i][j] == 'G'){
                        btn.setBackgroundColor(Color.GREEN);
                    }
                    else{
                        btn.setBackgroundColor(Color.LTGRAY);
                    }
                }
            }
        }
    }

    public void finalGoTop(View view){
        Intent intent = new Intent(this, TopActivity.class);
        //intent.putExtra("cube", this.cube);
        startActivity(intent);
    }

    public void finalGoFront(View view){
        Intent intent = new Intent(this, FrontActivity.class);
        //intent.putExtra("cube",this.cube);
        startActivity(intent);
    }

    public void finalGoLeft(View view){
        Intent intent = new Intent(this, LeftActivity.class);
        //intent.putExtra("cube",this.cube);
        startActivity(intent);
    }

    public void finalGoRight(View view){
        Intent intent = new Intent(this, RightActivity.class);
        //intent.putExtra("cube",this.cube);
        startActivity(intent);
    }

    public void finalGoBottom(View view){
        Intent intent = new Intent(this, BottomActivity.class);
        //intent.putExtra("cube",this.cube);
        startActivity(intent);
    }

    public void finalGoBack(View view){
        Intent intent = new Intent(this, BackActivity.class);
        //intent.putExtra("cube", this.cube);
        startActivity(intent);
    }

    public void solve(View view){
        boolean flag = true;
        for (String key:Storage.cube.keySet()){
            char[][] face = Storage.cube.get(key);
            for (int i=0;i<3;i++){
                for (int j=0;j<3;j++){
                    if (face[i][j]=='W' || face[i][j]=='Y' || face[i][j]=='O' || face[i][j]=='R' || face[i][j]=='B' || face[i][j]=='G' ){
                        //flag stays true
                    }
                    else{
                        flag=false;
                        break;
                    }
                }
                if (!flag){
                    break;
                }
            }
            if (!flag){
                break;
            }
        }

        if (flag) { //all cells of cube filled
            Intent intent = new Intent(this, Solver.class);
            //intent.putExtra("cube",this.cube);
            startActivity(intent);
        }
        else{
            CharSequence text = "Some selections missing. Click on the face you want to go to and complete selections.";
            Toast toast=Toast.makeText(this, text, Toast.LENGTH_LONG);
            toast.show();

        }
    }
}
