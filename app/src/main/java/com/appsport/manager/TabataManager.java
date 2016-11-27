package com.appsport.manager;

import android.content.Context;
import android.os.Handler;
import android.widget.TextView;

import com.appsport.R;
import com.appsport.common.Scheduler;
import com.appsport.enums.TabataState;
import com.appsport.model.Tabata;
import com.appsport.task.TabataNextStateTask;
import com.appsport.task.TabataTimerTask;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledFuture;

/**
 * @author LEBOC Philippe on 05/10/2016.
 *
 * This class is the Manager of a Tabata program.
 * It will launch all timers, label changes, ...
 */
public final class TabataManager {

    private Context context;
    private Tabata program;
    private TextView timerView;
    private TextView labelView;
    private TextView tabataLeftLabelView;
    private TextView cycleLeftLabelView;
    private Handler handler = new Handler();

    private List<ScheduledFuture<?>> scheduledTasks;

    private TabataManager() {

    }

    /**
     * Initialize variables used by TabataManager
     * WARNING: DONT LET THESE VARIABLES NULL TO PREVENT NPE
     * @param program the program to be executed
     * @param timerValue the view that will be edited every seconds (its our timer)
     */
    public void initAndStart(Context context, Tabata program, TextView timerValue, TextView label, TextView tabataLeftView, TextView cycleLeftView) {
        this.context = context;
        setProgram(program);

        setTimerView(timerValue);
        setLabelView(label);
        setTabataLeftLabelView(tabataLeftView);
        setCycleLeftLabelView(cycleLeftView);

        setScheduledTasks(new ArrayList<ScheduledFuture<?>>());
        handle(TabataState.PREPARE);
    }

    public void resume(Tabata tabata, TextView timerValue, TextView label) {
        setProgram(tabata);
        setTimerView(timerValue);
        setLabelView(label);

        switch(tabata.getCurrentState()) {
            case PREPARE: schedule(tabata.getTimer(), TabataState.PREPARE, TabataState.WORK); break;
            case WORK: schedule(tabata.getTimer(), TabataState.WORK, TabataState.REST); break;
            case REST: schedule(tabata.getTimer(), TabataState.REST, TabataState.WORK); break;
            case FINISH: handle(TabataState.FINISH); break;
            default: break;
        }
    }

    /**
     * @param state of the current Tabata task
     */
    public void handle(TabataState state)
    {
        System.out.println("Tabata = "+getProgram().getCurrentTabata());
        switch(state)
        {
            case PREPARE:
            {
                if(getProgram().getCurrentTabata() > 0)
                {
                    schedule(getProgram().getPrepareTime(), TabataState.PREPARE, TabataState.WORK);
                } else {
                    getProgram().setCurrentState(TabataState.FINISH);
                    handle(TabataState.FINISH);
                }
                break;
            }
            case REST:
            {
                getProgram().decreaseCycle();

                if(getProgram().getCurrentCycle() > 0)
                {
                    schedule(getProgram().getMaxRestTime(), TabataState.REST, TabataState.WORK);
                } else {
                    getProgram().decreaseTabata();
                    getProgram().setCurrentState(TabataState.PREPARE);
                    handle(TabataState.PREPARE);
                }
                break;
            }
            case WORK:
            {
                schedule(getProgram().getMaxWorkTime(), TabataState.WORK, TabataState.REST);
                break;
            }
            case FINISH:
            {
                getProgram().setCurrentState(TabataState.FINISH);
                getScheduledTasks().clear();
                updateLabel(TabataState.FINISH);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        getTimerView().setText(R.string.label_timer_value);
                        getLabelView().setText(R.string.label_start_pour_commencer);
                    }
                });
                break;
            }
            default:
                cancelAllTimers();
                System.out.println("Erreur inattendue");
                break;
        }
    }

    /**
     * @param timer is the countdown time (in seconds)
     * @param currentState is the current state
     * @param nextState is the next logic state
     */
    private void schedule(int timer, final TabataState currentState, TabataState nextState) {
        getProgram().setCurrentState(currentState);
        getProgram().setTimer(timer);
        final ScheduledFuture taskAtFixedRate = Scheduler.getInstance().scheduleAtFixedRateGraphic(new TabataTimerTask(getTimerView(), getProgram()), 0, 1000, (timer-1) * 1000);
        final ScheduledFuture nextEvent = Scheduler.getInstance().schedule(new TabataNextStateTask(nextState), (timer) * 1000 - 300);

        getScheduledTasks().add(taskAtFixedRate);
        getScheduledTasks().add(nextEvent);

        updateLabel(currentState);
    }

    /**
     * Pour modifier une vue lorsqu'on est pas dans l'activity, il faut absolument passer par un Handler()
     * @param state
     */
    public void updateLabel(final TabataState state) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                getLabelView().setText(state.getName());
                getTabataLeftLabelView().setText(context.getResources().getString(R.string.label_tabata_restants_$count, Math.max(0, getProgram().getCurrentTabata())));
                getCycleLeftLabelView().setText(context.getResources().getString(R.string.label_cycle_restants_$count, Math.max(0, getProgram().getCurrentCycle())));
            }
        });
    }

    /**
     * Cancel all timer launched by the Manager and clear the list
     */
    public void cancelAllTimers() {
        if(getScheduledTasks() != null){
            for (ScheduledFuture<?> scheduledFuture : getScheduledTasks())
                if(scheduledFuture != null)
                    scheduledFuture.cancel(true);
            getScheduledTasks().clear();
        }
    }

    public Tabata getProgram() {
        return program;
    }

    public void setProgram(Tabata program) {
        this.program = program;
    }

    public TextView getTimerView() {
        return timerView;
    }

    public void setTimerView(TextView timerView) {
        this.timerView = timerView;
    }

    public TextView getLabelView() {
        return labelView;
    }

    public void setLabelView(TextView labelView) {
        this.labelView = labelView;
    }

    public List<ScheduledFuture<?>> getScheduledTasks() {
        return scheduledTasks;
    }

    public void setScheduledTasks(List<ScheduledFuture<?>> scheduledTasks) {
        this.scheduledTasks = scheduledTasks;
    }

    public TextView getTabataLeftLabelView() {
        return tabataLeftLabelView;
    }

    public void setTabataLeftLabelView(TextView tabataLeftLabelView) {
        this.tabataLeftLabelView = tabataLeftLabelView;
    }

    public TextView getCycleLeftLabelView() {
        return cycleLeftLabelView;
    }

    public void setCycleLeftLabelView(TextView cycleLeftLabelView) {
        this.cycleLeftLabelView = cycleLeftLabelView;
    }

    public static TabataManager getInstance()
    {
        return TabataManager.SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder
    {
        protected static final TabataManager INSTANCE = new TabataManager();
    }

}
