package com.appsport;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.appsport.activity.ProgramActivity;
import com.appsport.activity.StatsActivity;
import com.appsport.common.Constants;
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

        // Support de la toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Effectue une vérification permettant de savoir s'il s'agit du premier lancement ou non
        // En cas de premier lancement de l'application, on installe quelques programmes par défaut.
        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if(prefs.getBoolean(Constants.FIRST_RUN_TAG, true))
        {
            prefs.edit().putBoolean(Constants.FIRST_RUN_TAG, false).apply();
            initializeApplication();
        }

        // Affiche les détails du programme actif
        showCurrentProgram();
    }

    @Override
    public void onResume() {
        super.onResume();
        showCurrentProgram();
    }

    /**
     * Affichage des détails du programme actif
     */
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

    /**
     * Lors du click sur le bouton programme, l'utilisateur est renvoyer sur l'activité correspondante
     * @param view le button "Programmes"
     */
    public void onClickProgramButton(View view) {
        final Intent activity = new Intent(getApplicationContext(), ProgramActivity.class);
        this.startActivity(activity);
    }

    /**
     * Lors du click sur le bouton statisques, l'utilisateur est renvoyer sur l'activité correspondante
     * @param view le bouton statistiques
     */
    public void onClickStatsButton(View view){
        final Intent activity = new Intent(getApplicationContext(), StatsActivity.class);
        this.startActivity(activity);
    }

    /**
     * Ensemble d'executions à faire lors du premier lancement de l'application (après installation)
     */
    private void initializeApplication() {
        // Création d'un ensemble de programmes par défaut
        final Tabata program_one = new Tabata("Programme 1", 2, 15, 10, 10, 2);
        final Tabata program_two = new Tabata("Programme 2", 2, 15, 15, 10, 2);
        final Tabata program_three = new Tabata("Programme 3", 2, 25, 20, 10, 3);
        final Tabata program_four = new Tabata("Programme 4", 3, 25, 20, 10, 3);
        final Tabata program_five = new Tabata("Programme 5", 4, 60, 25, 10, 5);

        Tabata.saveInTx(program_one, program_two, program_three, program_four, program_five);
    }
}
