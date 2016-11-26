package com.appsport.enums;

/**
 * @author LEBOC Philippe on 05/10/2016.
 *
 * Define all possible state for a Tabata !
 */
public enum TabataState {

    NONE("None"),
    PREPARE("Preparation !"),
    REST("Repos"),
    WORK("Exercices !"),
    FINISH("Terminé !");

    private String name;

    TabataState(String name) {
        setName(name);
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }
}
