package pt.isec.pa.javalife.model.data.elements;

import pt.isec.pa.javalife.model.data.*;
import pt.isec.pa.javalife.model.fsm.ClientContext;
import pt.isec.pa.javalife.model.fsm.ClientStates;
import pt.isec.pa.javalife.model.fsm.Ecosystem;

import java.io.Serializable;
import java.util.*;

import static java.lang.Math.min;

public final class Animal extends Fauna{

    public Animal(Area area, Ecosystem ecosystem) {
        super(area, ecosystem);
    }

    public Animal(Area area, double strength, Ecosystem ecosystem) {
        super(area, strength, ecosystem);
    }

    public Animal(Animal original) {
        super(original);
    }
}
