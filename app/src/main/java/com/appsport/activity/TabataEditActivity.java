package com.appsport.activity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.appsport.R;
import com.appsport.common.Constants;
import com.appsport.model.Tabata;

/**
 * @author LEBOC Philippe
 * Activité destinée à l'édition d'un programme
 */
public class TabataEditActivity extends AppCompatActivity {

    private Tabata program;

    private EditText nameView;
    private EditText tabataCntView;
    private EditText cycleCntView;
    private EditText prepareTimeView;
    private EditText restTimeView;
    private EditText workTimeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabata_edit);

        // Récupération des champs
        nameView = (EditText) findViewById(R.id.input_tabata_name);
        tabataCntView = (EditText) findViewById(R.id.input_tabata_count);
        cycleCntView = (EditText) findViewById(R.id.input_tabata_cycles_count);
        prepareTimeView = (EditText) findViewById(R.id.input_tabata_prepare_time);
        restTimeView = (EditText) findViewById(R.id.input_tabata_rest_time);
        workTimeView = (EditText) findViewById(R.id.input_tabata_work_time);

        // Check s'il s'agit d'une edition d'un programme existant.
        final Tabata programToEdit = getIntent().getExtras().getParcelable(Constants.PARCELABLE_TABATA_TAG);
        if(programToEdit == null) {
            finish();
            return;
        }

        setProgram(programToEdit);

        nameView.setText(this.getResources().getString(R.string.placeholder_string_$1, programToEdit.getName()));
        tabataCntView.setText(this.getResources().getString(R.string.placeholder_number_$1, programToEdit.getMaxTabata()));
        cycleCntView.setText(this.getResources().getString(R.string.placeholder_number_$1, programToEdit.getMaxCycleCount()));
        prepareTimeView.setText(this.getResources().getString(R.string.placeholder_number_$1, programToEdit.getPrepareTime()));
        restTimeView.setText(this.getResources().getString(R.string.placeholder_number_$1, programToEdit.getMaxRestTime()));
        workTimeView.setText(this.getResources().getString(R.string.placeholder_number_$1, programToEdit.getMaxWorkTime()));
    }

    @Override
    public void onBackPressed() {
        // When the user hits the back button set the resultCode
        // to Activity.RESULT_CANCELED to indicate a failure
        setResult(Activity.RESULT_CANCELED);
        super.onBackPressed();
    }

    public void onClickValidateEdition(View view) {
        if(getProgram() != null)
        {
            final String name = nameView.getText().toString();
            final int tabataCnt = Integer.parseInt(tabataCntView.getText().toString());
            final int cycleCnt = Integer.parseInt(cycleCntView.getText().toString());
            final int prepareTime = Integer.parseInt(prepareTimeView.getText().toString());
            final int restTime = Integer.parseInt(restTimeView.getText().toString());
            final int workTime = Integer.parseInt(workTimeView.getText().toString());

            getProgram().setName(name);
            getProgram().setMaxTabata(tabataCnt);
            getProgram().setMaxCycleCount(cycleCnt);
            getProgram().setPrepareTime(prepareTime);
            getProgram().setMaxRestTime(restTime);
            getProgram().setMaxWorkTime(workTime);

            getProgram().save();
        }
        setResult(Activity.RESULT_OK);
        finish();
    }

    public Tabata getProgram() {
        return program;
    }

    public void setProgram(Tabata program) {
        this.program = program;
    }
}
