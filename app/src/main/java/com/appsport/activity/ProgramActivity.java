package com.appsport.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.appsport.R;
import com.appsport.view.ProgramView;
import com.appsport.model.Tabata;
import com.orm.SugarRecord;

import java.util.List;

/**
 * @author LEBOC Philippe
 *
 * Utilisation de la librairie : https://github.com/Clans/FloatingActionButton
 */
public class ProgramActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 0x9345;
    private LinearLayout activeProgramLayout;

    private Tabata activeProgram;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program);

        // Active la toolbar
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_programs);
        setSupportActionBar(toolbar);

        showAllPrograms();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_programmes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_favorite:
                Toast.makeText(this, "action favorite", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_start:
                final Intent intent = new Intent(getApplicationContext(), TabataActivity.class);
                intent.putExtra("TABATA", activeProgram);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // Handle clicking

    public void onClickAddProgram(View view) {
        final Intent intention = new Intent(getApplicationContext(), TabataAddProgramActivity.class);
        startActivityForResult(intention, REQUEST_CODE);
    }

    /**
     * Edition du programme actif
     * @param view
     */
    public void onClickEditProgram(View view) {
        // TODO: implements me
    }

    /**
     * Supprime le programme actif
     * @param view
     */
    public void onClickDeleteProgram(View view) {
        new AlertDialog.Builder(this)
        .setIcon(android.R.drawable.ic_dialog_alert)
        .setTitle("Suppression")
        .setMessage("Voulez vous vraiment supprimer le programme ?")
        .setPositiveButton("Oui", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
            if(activeProgram != null)
            {
                Tabata.delete(activeProgram);
                showAllPrograms();
            }
            }

        })
        .setNegativeButton("Non", null)
        .show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            if(resultCode == Activity.RESULT_OK) {
                showAllPrograms();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(this, "Creation de programme annulée", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void showAllPrograms() {
        final LinearLayout mainLayout = (LinearLayout) findViewById(R.id.program_vlayout);
        final List<Tabata> programs = SugarRecord.listAll(Tabata.class);

        mainLayout.removeAllViewsInLayout(); // cleanup

        for (final Tabata program : programs)
        {
            final ProgramView view = new ProgramView(this, program);

            view.getVerticalTextLayout().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Set current Active program to inactive
                    // Petite bidouille car SugarORM a des soucis avec la gestion des booleans...
                    final List<Tabata> all = Tabata.find(Tabata.class, "");
                    for (Tabata tabata : all) {
                        tabata.setDefaultActive(false);
                    }
                    Tabata.saveInTx(all);

                    setActiveProgram((LinearLayout) v, true);

                    // Set the new selected program to active
                    view.getTabata().setDefaultActive(true);
                    view.getTabata().save();
                    activeProgram = view.getTabata();
                }
            });

            if(view.getTabata().isDefaultActive()) {
                setActiveProgram(view.getVerticalTextLayout(), false);
            }

            // render
            mainLayout.addView(view.getProgramSourceLayout());
        }
    }

    public LinearLayout getActiveProgram() {
        return activeProgramLayout;
    }

    public void setActiveProgram(LinearLayout view, boolean showToast) {
        if(getActiveProgram() != null){
            getActiveProgram().setBackgroundColor(getResources().getColor(R.color.colorGreenPrimary, getTheme()));
        }
        view.setBackgroundColor(Color.GRAY);
        this.activeProgramLayout = view;

        if(showToast) {
            final Toast toast = Toast.makeText(this, R.string.toast_changement_effectue, Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
