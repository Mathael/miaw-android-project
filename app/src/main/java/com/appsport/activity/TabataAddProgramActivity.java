package com.appsport.activity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.appsport.R;
import com.appsport.model.Tabata;

/**
 * @author LEBOC Philippe
 */
public class TabataAddProgramActivity extends AppCompatActivity {

    private EditText tabataCntView;
    private EditText nameView;
    private EditText cycleCntView;
    private EditText prepareTimeView;
    private EditText restTimeView;
    private EditText workTimeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_add_program);

        tabataCntView = (EditText) findViewById(R.id.input_tabata_count);
        nameView = (EditText) findViewById(R.id.input_tabata_name);
        cycleCntView = (EditText) findViewById(R.id.input_tabata_cycles_count);
        prepareTimeView = (EditText) findViewById(R.id.input_tabata_prepare_time);
        restTimeView = (EditText) findViewById(R.id.input_tabata_rest_time);
        workTimeView = (EditText) findViewById(R.id.input_tabata_work_time);
    }

    public void onClickAddProgram(View view) {
        final String name = nameView.getText().toString();
        final int tabataCnt = Integer.parseInt(tabataCntView.getText().toString());
        final int cycleCnt = Integer.parseInt(cycleCntView.getText().toString());
        final int prepareTime = Integer.parseInt(prepareTimeView.getText().toString());
        final int restTime = Integer.parseInt(restTimeView.getText().toString());
        final int workTime = Integer.parseInt(workTimeView.getText().toString());

        final Tabata tabata = new Tabata(name, tabataCnt, prepareTime, workTime, restTime, cycleCnt);
        //System.out.println("Nouveau programme -> \r\n" + tabata.toString());
        Tabata.save(tabata);

        //final Intent intent = new Intent(Intent.FLAG_ACTIVITY_CLEAR_TOP); // exemple d'utilisation des FLAG
        setResult(Activity.RESULT_OK);
        finish();
    }

    @Override
    public void onBackPressed() {
        // When the user hits the back button set the resultCode
        // to Activity.RESULT_CANCELED to indicate a failure
        setResult(Activity.RESULT_CANCELED);
        super.onBackPressed();
    }
}
