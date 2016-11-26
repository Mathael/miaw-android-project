package com.appsport.task;

import java.util.concurrent.ScheduledFuture;

/**
 * @author LEBOC Philippe on 04/10/2016.
 *
 * Tâche permettant d'arrêter une autre tâche.
 * Utilisée principalement pour arrêter un ScheduledAtFixedRate qui ne s'arrête pas de lui même.
 */
public final class CancelTimerTask implements Runnable {

    private ScheduledFuture scheduledFuture;

    public CancelTimerTask(ScheduledFuture scheduledFuture) {
        setScheduledFuture(scheduledFuture);
    }

    @Override
    public synchronized void run() {
        scheduledFuture.cancel(true);
    }

    public ScheduledFuture getScheduledFuture() {
        return scheduledFuture;
    }

    public void setScheduledFuture(ScheduledFuture scheduledFuture) {
        this.scheduledFuture = scheduledFuture;
    }
}
