package com.appsport.enums;

import com.appsport.R;

/**
 * @author LEBOC Philippe on 05/10/2016.
 *
 * Define all possible state for a Tabata !
 */
public enum TabataState {

    NONE("None", R.color.colorWhite),
    PREPARE("Preparation !", R.color.PREPARE),
    REST("Repos", R.color.REST),
    WORK("Exercices !", R.color.WORK),
    FINISH("Terminé !", R.color.colorWhite);

    private String name;
    private int color;

    TabataState(String name, int color) {
        setName(name);
        setColor(color);
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
