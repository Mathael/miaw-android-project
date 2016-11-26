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
import android.widget.TextView;
import android.widget.Toast;

import com.appsport.R;
import com.appsport.common.Constants;
import com.appsport.view.ProgramView;
import com.appsport.model.Tabata;
import com.orm.SugarRecord;

import java.util.List;

/**
 * @author LEBOC Philippe
 *
 * Utilisation de la librairie : FloatingActionButton pour l'affichage des boutons d'actions [ajout, edition, suppression]
 * @link https://github.com/Clans/FloatingActionButton
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
    public boolean onPrepareOptionsMenu(Menu menu)
    {
        MenuItem favorite = menu.findItem(R.id.action_favorite);
        favorite.setVisible(activeProgram != null);

        if(activeProgram != null && activeProgram.isFavorite()) favorite.setIcon(android.R.drawable.btn_star_big_on);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId())
        {
            case R.id.action_favorite:
            {
                if(activeProgram != null)
                {
                    if(!activeProgram.isFavorite())
                    {
                        activeProgram.setFavorite(true);
                        activeProgram.save();
                        showAllPrograms();
                    }
                    else
                    {
                        activeProgram.setFavorite(false);
                        activeProgram.save();
                        showAllPrograms();
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(), R.string.vous_devez_selectionner_un_programme, Toast.LENGTH_SHORT).show();
                }
                return true;
            }
            case R.id.action_start:
            {
                final Intent intent = new Intent(getApplicationContext(), TabataActivity.class);
                intent.putExtra(Constants.PARCELABLE_TABATA_TAG, activeProgram);
                startActivity(intent);
                return true;
            }
            default: return super.onOptionsItemSelected(item);
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
        if(activeProgram == null) return;

        new AlertDialog.Builder(this)
        .setIcon(android.R.drawable.ic_dialog_alert)
        .setTitle(R.string.title_suppression)
        .setMessage(getResources().getString(R.string.voulez_vous_vraiment_supprimer_$text, activeProgram.getName()))
        .setPositiveButton(R.string.oui, new DialogInterface.OnClickListener()
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
        .setNegativeButton(R.string.non, null)
        .show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            if(resultCode == Activity.RESULT_OK) {
                showAllPrograms();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(this, R.string.creation_de_programme_annulee, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void showAllPrograms() {
        final LinearLayout favoriteLayout = (LinearLayout) findViewById(R.id.program_favorites);
        final LinearLayout mainLayout = (LinearLayout) findViewById(R.id.program_vlayout);
        final List<Tabata> programs = SugarRecord.listAll(Tabata.class);

        final TextView programLabel = new TextView(getApplicationContext());
        programLabel.setText(R.string.programmes);
        programLabel.setTextColor(getResources().getColor(R.color.colorWhite));

        favoriteLayout.removeAllViewsInLayout(); // cleanup
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

                    // Set the new selected program to active
                    view.getTabata().setDefaultActive(true);
                    view.getTabata().save();
                    activeProgram = view.getTabata();

                    // Manage background colors & menu options
                    setActiveProgramView((LinearLayout) v);
                }
            });

            if(view.getTabata().isDefaultActive()) {
                setActiveProgramView(view.getVerticalTextLayout());
            }

            // render
            if(program.isFavorite()) favoriteLayout.addView(view.getProgramSourceLayout());
            else mainLayout.addView(view.getProgramSourceLayout());
        }

        if(favoriteLayout.getChildCount() > 0)
        {
            final TextView favoriteTitle = new TextView(getApplicationContext());
            favoriteTitle.setText(R.string.favoris);
            favoriteTitle.setTextColor(getResources().getColor(R.color.colorWhite));
            favoriteLayout.addView(favoriteTitle, 0);
        }

        if(mainLayout.getChildCount() > 0)
        {
            mainLayout.addView(programLabel, 0);
        }
    }

    public LinearLayout getActiveProgramView() {
        return activeProgramLayout;
    }

    /**
     * Fonction permettant un changement visuel du programme actif.
     * Elle va remettre le background-color de chaque programme par défaut et prendre le
     * container (view) du programme actif courant pour lui donner une couleur différente afin
     * qu'il puisse facilement être différencier visuellement.
     * @param view
     */
    public void setActiveProgramView(LinearLayout view) {
        if(getActiveProgramView() != null){
            getActiveProgramView().setBackgroundColor(getResources().getColor(R.color.colorGreenPrimary, getTheme()));
        }
        view.setBackgroundColor(Color.GRAY);
        this.activeProgramLayout = view;

        invalidateOptionsMenu();
    }
}
