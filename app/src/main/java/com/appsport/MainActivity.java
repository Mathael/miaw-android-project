package com.appsport;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.appsport.activity.ProgramActivity;
import com.appsport.activity.StatsActivity;
import com.appsport.activity.TabataActivity;
import com.appsport.model.Tabata;

import java.util.List;

/**
 * @author LEBOC Philippe
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        showCurrentProgram();
    }

    private void showCurrentProgram() {
        final List<Tabata> tabatas = Tabata.find(Tabata.class, "is_Default_Active = ?", "1");
        if(!tabatas.isEmpty()) {
            final Tabata tabata = tabatas.get(0);

            final TextView labelCurrentTabataActiveProgram = (TextView) findViewById(R.id.label_current_program);
            final TextView homeTabataCount = (TextView) findViewById(R.id.home_tabata_count);
            final TextView homeCycleCount = (TextView) findViewById(R.id.home_cycle_count);
            final TextView homePrepareTime = (TextView) findViewById(R.id.home_prepare_time);
            final TextView homeWorkTime = (TextView) findViewById(R.id.home_work_time);
            final TextView homeRestTime = (TextView) findViewById(R.id.home_rest_time);

            labelCurrentTabataActiveProgram.setText(tabata.getName());
            homeTabataCount.setText(""+tabata.getMaxTabata());
            homeCycleCount.setText(""+tabata.getMaxCycleCount());
            homePrepareTime.setText(""+tabata.getPrepareTime());
            homeWorkTime.setText(""+tabata.getMaxWorkTime());
            homeRestTime.setText(""+tabata.getMaxRestTime());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        showCurrentProgram();
    }

    /**
     * OnClick Listeners
     */
    public void onClickTabataButton(View view) {
        final Intent activity = new Intent(getApplicationContext(), TabataActivity.class);
        activity.putExtra("program", 1);
        activity.putExtra("restTime", 5);
        activity.putExtra("workTime", 10);
        activity.putExtra("prepareTime", 8);
        activity.putExtra("cycles", 2);
        this.startActivity(activity);
    }

    public void onClickProgramButton(View view) {
        final Intent activity = new Intent(getApplicationContext(), ProgramActivity.class);
        this.startActivity(activity);
    }

    public void onClickStatsButton(View view){
        final Intent activity = new Intent(getApplicationContext(), StatsActivity.class);
        this.startActivity(activity);
    }
}
