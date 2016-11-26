package com.appsport.task;

import com.appsport.manager.TabataManager;
import com.appsport.enums.TabataState;

/**
 * @author LEBOC Philippe on 05/10/2016.
 *
 * Permet de démarrer la prochaine étape du Tabata
 */
public final class TabataNextStateTask implements Runnable {

    private TabataState state;

    public TabataNextStateTask(TabataState state) {
        setState(state);
    }

    @Override
    public synchronized void run() {
        TabataManager.getInstance().handle(getState());
    }

    public TabataState getState() {
        return state;
    }

    public void setState(TabataState state) {
        this.state = state;
    }
}
