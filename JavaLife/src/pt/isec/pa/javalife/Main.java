package pt.isec.pa.javalife;

import javafx.application.Application;
import pt.isec.pa.javalife.model.Manager;
import pt.isec.pa.javalife.ui.MainJfx;

public class Main {
   public static Manager gameManager;
    static{
        gameManager = new Manager(10,10);
    }


    public static void main(String[] args) {
        Application.launch(MainJfx.class,args);
    }
}