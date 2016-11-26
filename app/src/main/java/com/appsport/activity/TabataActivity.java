package com.appsport.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.appsport.R;
import com.appsport.manager.TabataManager;
import com.appsport.model.Tabata;

import java.util.List;

/**
 * @author LEBOC Philippe
 */
public class TabataActivity extends AppCompatActivity {

    private Button startButton;
    private TextView textValue;
    private TextView timerValue;
    private TextView tabataLeftCount;
    private TextView cycleLeftCount;

    private Tabata tabata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabata);

        final Tabata tabata = getIntent().getExtras().getParcelable("TABATA");
        if(tabata == null) finish();

        setTabata(tabata);

        startButton = (Button) findViewById(R.id.tabata_startstop_button);

        textValue = (TextView) findViewById(R.id.tabata_state_text);
        timerValue = (TextView) findViewById(R.id.tabata_counter);
        tabataLeftCount = (TextView) findViewById(R.id.tabata_left_count);
        cycleLeftCount = (TextView) findViewById(R.id.cycle_left_count);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        System.out.println("onSaveInstanceState : "+getTabata());
        TabataManager.getInstance().cancelAllTimers();
        outState.putParcelable("TABATA", getTabata());
    }

    @Override
    protected void onRestoreInstanceState(Bundle state) {
        super.onRestoreInstanceState(state);
        setTabata((Tabata)state.getParcelable("TABATA"));
        if(getTabata() != null) {
            startButton.setEnabled(false);
            TabataManager.getInstance().resume(getTabata(), timerValue, textValue);
        }
    }

    public void buttonTabataStart(View view) {
        // Launch default program
        getTabata().setCurrentCycle(getTabata().getMaxCycleCount());
        getTabata().setCurrentTabata(getTabata().getMaxTabata());
        setTabata(getTabata());

        startButton.setEnabled(false);

        TabataManager.getInstance().initAndStart(getTabata(), timerValue, textValue, tabataLeftCount, cycleLeftCount);
    }

    public void buttonTabataPause(View view) {
        TabataManager.getInstance().cancelAllTimers();
        Toast toast = Toast.makeText(this, R.string.toast_programme_entrainement_arrete, Toast.LENGTH_LONG);
        toast.show();

        startButton.setEnabled(true);
        textValue.setText(R.string.label_start_pour_commencer);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.out.println("Destroy : " + this.getClass().getSimpleName());
        TabataManager.getInstance().cancelAllTimers();
    }

    public Tabata getTabata() {
        return tabata;
    }

    public void setTabata(Tabata tabata) {
        this.tabata = tabata;
    }
}
