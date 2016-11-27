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

    private final EditText nameView = (EditText) findViewById(R.id.input_tabata_name);
    private final EditText tabataCntView = (EditText) findViewById(R.id.input_tabata_count);
    private final EditText cycleCntView = (EditText) findViewById(R.id.input_tabata_cycles_count);
    private final EditText prepareTimeView = (EditText) findViewById(R.id.input_tabata_prepare_time);
    private final EditText restTimeView = (EditText) findViewById(R.id.input_tabata_rest_time);
    private final EditText workTimeView = (EditText) findViewById(R.id.input_tabata_work_time);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabata_edit);

        // Check s'il s'agit d'une edition d'un programme existant.
        final Tabata programToEdit = getIntent().getExtras().getParcelable(Constants.PARCELABLE_TABATA_TAG);
        if(programToEdit == null) {
            finish();
            return;
        }

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
        // TODO
    }
}
