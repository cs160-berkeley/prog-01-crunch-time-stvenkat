package com.example.sreeshav.prog01;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    GridView myGrid;
    Spinner firstSpinner;
    Spinner secondSpinner;
    String userValue;
    public static String repsOrMinutes = "";
    String exerciseType;
    String buttonText;
    Integer exerciseAmount;
    public static int squatCal = 0, situpCal = 0, pullupCal = 0, pushupCal = 0, cyclingCal = 0, walkingCal = 0, joggingCal = 0;
    public static int legliftCal = 0, plankingCal = 0, swimCal = 0, jumpjackCal = 0, stairCal = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        EditText userInput = (EditText) findViewById(R.id.edit_number);
        userInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                TextView input = (TextView) findViewById(R.id.edit_number);
                userValue = input.getText().toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        Spinner leftSpinner= (Spinner) findViewById(R.id.spinner);
        Spinner rightSpinner= (Spinner) findViewById(R.id.spinner2);

        myGrid= (GridView) findViewById(R.id.gridview);
        myGrid.setAdapter(new Adapter(this));

        Button b = (Button) findViewById(R.id.button);
        buttonText = b.getText().toString();


        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.repcounts_array, android.R.layout.simple_spinner_item);
        ArrayAdapter adapter2 = ArrayAdapter.createFromResource(this, R.array.exercises_array, android.R.layout.simple_spinner_item);
        leftSpinner.setAdapter(adapter);
        leftSpinner.setOnItemSelectedListener(this);
        rightSpinner.setAdapter(adapter2);
        rightSpinner.setOnItemSelectedListener(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void doSomething(View v) {
        exerciseAmount = Integer.parseInt(userValue);
        if (repsOrMinutes.equals("Reps")) {
            squatCal = ((100*exerciseAmount)/225);
            situpCal = ((100*exerciseAmount)/200);
            pullupCal = exerciseAmount;
            pushupCal = ((100*exerciseAmount)/350);
            cyclingCal = 0;
            walkingCal = 0;
            joggingCal = 0;
            legliftCal = 0;
            plankingCal = 0;
            swimCal = 0;
            jumpjackCal = 0;
            stairCal = 0;
        } else if (repsOrMinutes.equals("Minutes")) {
            cyclingCal = (100/12) * exerciseAmount;
            walkingCal = 5 * exerciseAmount;
            joggingCal = (100/12) * exerciseAmount;
            legliftCal = 4 * exerciseAmount;
            plankingCal = 4 * exerciseAmount;
            swimCal = (100/13) * exerciseAmount;
            jumpjackCal = 10 * exerciseAmount;
            stairCal = (100/15) * exerciseAmount;
            squatCal = 0;
            situpCal = 0;
            pullupCal = 0;
            pushupCal = 0;
        } else {
            cyclingCal = ((12*exerciseAmount)/100);
            walkingCal = exerciseAmount/5;
            joggingCal = ((12*exerciseAmount)/100);
            legliftCal = exerciseAmount/4;
            plankingCal = exerciseAmount/4;
            swimCal = ((13*exerciseAmount)/100);
            jumpjackCal = exerciseAmount/10;
            stairCal = ((15*exerciseAmount)/100);
            squatCal = ((225*exerciseAmount)/100);
            situpCal = ((200*exerciseAmount)/100);
            pullupCal = exerciseAmount;
            pushupCal = ((350*exerciseAmount)/100);
        }
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.spinner:
                TextView selection = (TextView) view;
                repsOrMinutes = selection.getText().toString();
                break;
            case R.id.spinner2:
                TextView selection2 = (TextView) view;
                exerciseType = selection2.getText().toString();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public class Exercises {
        String exerciseType;
        Exercises(String exerciseType) {
            this.exerciseType=exerciseType;
        }
    }

    public class Adapter extends BaseAdapter {
        ArrayList<Exercises> list;
        int[] caloriesBurned;
        Context context;
        Adapter(Context context) {
            this.context=context;
            list = new ArrayList<Exercises>();
            Resources res=context.getResources();
            String[] tempExerciseNames = res.getStringArray(R.array.exercises_array);
            for(int i=0; i<12; i++) {
                Exercises tempExercises = new Exercises(tempExerciseNames[i]);
                list.add(tempExercises);
            }
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        class ViewHolder {
            TextView myExercise;
            ViewHolder(View v) {
                myExercise= (TextView) v.findViewById(R.id.textView);
            }
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row= convertView;
            ViewHolder holder= null;
            if (row==null) {
                LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                row= inflater.inflate(R.layout.single_item, parent, false);
                holder= new ViewHolder(row);
                row.setTag(holder);
            } else {
                holder= (ViewHolder) row.getTag();
            }
            Exercises temp= list.get(position);
            caloriesBurned = new int[12];
            caloriesBurned[0] = squatCal;
            caloriesBurned[1] = situpCal;
            caloriesBurned[2] = pullupCal;
            caloriesBurned[3] = pushupCal;
            caloriesBurned[4] = walkingCal;
            caloriesBurned[5] = joggingCal;
            caloriesBurned[6] = cyclingCal;
            caloriesBurned[7] = legliftCal;
            caloriesBurned[8] = plankingCal;
            caloriesBurned[9] = swimCal;
            caloriesBurned[10] = jumpjackCal;
            caloriesBurned[11] = stairCal;
            if (repsOrMinutes.equals("Calories")) {
                if (position < 4) {
                    holder.myExercise.setText(temp.exerciseType + ": " + caloriesBurned[position] + " reps");
                } else {
                    holder.myExercise.setText(temp.exerciseType + ": " + caloriesBurned[position] + " min");
                }
            } else if (repsOrMinutes.equals("Reps")) {
                if (position < 4) {
                    holder.myExercise.setText(temp.exerciseType + ": " + caloriesBurned[position] + " calories");
                } else {
                    holder.myExercise.setText(temp.exerciseType + ": N/A");
                }
            } else {
                if (position < 4) {
                    holder.myExercise.setText(temp.exerciseType + ": N/A");
                } else {
                    holder.myExercise.setText(temp.exerciseType + ": " + caloriesBurned[position] + " calories");
                }
            }
            holder.myExercise.setTextSize(14);
            holder.myExercise.setTextColor(Color.WHITE);
            holder.myExercise.setBackgroundResource(R.color.colorBackground);
            holder.myExercise.setPadding(100, 140, 100, 140);
            holder.myExercise.setGravity(1);
            return row;
        }
    }
}

