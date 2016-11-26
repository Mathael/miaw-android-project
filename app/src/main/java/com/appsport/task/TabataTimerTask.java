package com.appsport.task;

import android.media.AudioManager;
import android.media.ToneGenerator;
import android.widget.TextView;

import com.appsport.model.Tabata;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * IMPORTANT :
 * Les Runnable ne peuvent pas editer les Objets graphiques ce qui cause un arrêt du Runnable
 * sauf s'il est executé dans un Handler()
 *
 * @author LEBOC Philippe on 04/10/2016.
 *
 * Permet de modifier l'affichage du compteur.
 */
public final class TabataTimerTask implements Runnable {

    private static final ToneGenerator toneGenerator = new ToneGenerator(AudioManager.STREAM_ALARM, 100);
    private Tabata program;
    private TextView view;

    public TabataTimerTask(TextView view, Tabata program) {
        setView(view);
        setProgram(program);
    }

    @Override
    public synchronized void run() {
        final int timer = getProgram().getTimer();
        if(timer > 0)
        {
            getProgram().decreaseTimer();
            getView().setText(setToTimerFormat(timer * 1000));

            if(timer > 0 && timer <= 3)
            {
                handleBeep();
            }
        }
    }

    private void handleBeep() {
        toneGenerator.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 200);
    }

    private TextView getView() {
        return view;
    }

    private void setView(TextView view) {
        this.view = view;
    }

    private Tabata getProgram() {
        return program;
    }

    private void setProgram(Tabata program) {
        this.program = program;
    }

    private String setToTimerFormat(long millisUntilFinished) {
        return new SimpleDateFormat("mm:ss").format(new Date(millisUntilFinished));
    }
}