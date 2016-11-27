package com.appsport.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.appsport.enums.TabataState;
import com.orm.SugarRecord;
import com.orm.dsl.Ignore;

/**
 * @author LEBOC Philippe on 04/10/2016.
 */
public class Tabata extends SugarRecord implements Parcelable {

    private String name;

    private boolean isDefaultActive;

    private int maxTabata;
    private int maxRestTime;
    private int maxWorkTime;

    private boolean isFavorite;

    // Dynamic vars (used here to re-launch the tabata timer when orientation change)
    @Ignore
    private int currentTabata;
    @Ignore
    private int currentCycle;
    @Ignore
    private int timer;
    @Ignore
    private TabataState currentState;

    /**
     * Time between 2 Tabatas
     */
    private int prepareTime;

    /**
     * Count of (Work <-> Rest) states
     */
    private int maxCycleCount;

    public Tabata() {
        setMaxCycleCount(getMaxCycleCount());
        setCurrentState(TabataState.NONE);
        setCurrentTabata(getMaxTabata());
    }

    public Tabata(String name, int tabata, int prepareTime, int workTime, int restTime, int cycle) {
        setName(name);
        setMaxTabata(tabata);
        setPrepareTime(prepareTime);
        setMaxWorkTime(workTime);
        setMaxRestTime(restTime);
        setMaxCycleCount(cycle);
        setDefaultActive(false);
        setCurrentState(TabataState.NONE);
        setFavorite(false);
    }

    protected Tabata(Parcel in) {
        setId(in.readLong());
        name = in.readString();
        isDefaultActive = in.readByte() != 0;
        maxTabata = in.readInt();
        maxRestTime = in.readInt();
        maxWorkTime = in.readInt();
        currentTabata = in.readInt();
        currentCycle = in.readInt();
        timer = in.readInt();
        prepareTime = in.readInt();
        maxCycleCount = in.readInt();
        currentState = TabataState.values()[in.readInt()];
        isFavorite = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(getId());
        dest.writeString(name);
        dest.writeByte((byte) (isDefaultActive ? 1 : 0));
        dest.writeInt(maxTabata);
        dest.writeInt(maxRestTime);
        dest.writeInt(maxWorkTime);
        dest.writeInt(currentTabata);
        dest.writeInt(currentCycle);
        dest.writeInt(timer);
        dest.writeInt(prepareTime);
        dest.writeInt(maxCycleCount);
        dest.writeInt(currentState.ordinal());
        dest.writeByte((byte)(isFavorite ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Tabata> CREATOR = new Creator<Tabata>() {
        @Override
        public Tabata createFromParcel(Parcel in) {
            return new Tabata(in);
        }

        @Override
        public Tabata[] newArray(int size) {
            return new Tabata[size];
        }
    };

    public void decreaseCycle() {
        setCurrentCycle(getCurrentCycle() - 1);
    }

    public void decreaseTabata() {
        setCurrentTabata(getCurrentTabata() - 1);
    }

    public void decreaseTimer() {
        setTimer(getTimer() - 1);
    }

    public int getMaxTabata() {
        return maxTabata;
    }

    public void setMaxTabata(int maxTabata) {
        this.maxTabata = maxTabata;
    }

    public int getMaxRestTime() {
        return maxRestTime;
    }

    public void setMaxRestTime(int maxRestTime) {
        this.maxRestTime = maxRestTime;
    }

    public int getMaxWorkTime() {
        return maxWorkTime;
    }

    public void setMaxWorkTime(int maxWorkTime) {
        this.maxWorkTime = maxWorkTime;
    }

    public int getPrepareTime() {
        return prepareTime;
    }

    public void setPrepareTime(int prepareTime) {
        this.prepareTime = prepareTime;
    }

    public int getMaxCycleCount() {
        return maxCycleCount;
    }

    public void setMaxCycleCount(int maxCycleCount) {
        this.maxCycleCount = maxCycleCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCurrentTabata() {
        return currentTabata;
    }

    public void setCurrentTabata(int currentTabata) {
        this.currentTabata = currentTabata;
    }

    public int getCurrentCycle() {
        return currentCycle;
    }

    public void setCurrentCycle(int currentCycle) {
        this.currentCycle = currentCycle;
    }

    public int getTimer() {
        return timer;
    }

    public void setTimer(int timer) {
        this.timer = timer;
    }

    public boolean isDefaultActive() {
        return isDefaultActive;
    }

    public void setDefaultActive(boolean defaultActive) {
        this.isDefaultActive = defaultActive;
    }

    public TabataState getCurrentState() {
        return currentState;
    }

    public void setCurrentState(TabataState currentState) {
        this.currentState = currentState;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    @Override
    public String toString() {
        return "[ " + getId() + " ]" + getName() + " :\r\n\t"
                + getMaxTabata() + " Tabatas\r\n\t"
                + getMaxCycleCount() + " Cycles\r\n\t"
                + getPrepareTime() + " Prepare time\r\n\t"
                + getMaxWorkTime() + " Work time\r\n\t"
                + getMaxRestTime() + " Rest time";
    }
}
