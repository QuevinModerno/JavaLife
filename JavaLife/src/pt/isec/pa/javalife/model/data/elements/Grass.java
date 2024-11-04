package pt.isec.pa.javalife.model.data.elements;

import pt.isec.pa.javalife.model.data.*;
import pt.isec.pa.javalife.model.fsm.Ecosystem;

import java.io.Serializable;

public final class Grass extends Flora {

    public Grass(Area area, Ecosystem ecosystem) {
        super(area, ecosystem);
    }

    public Grass(Area area, double strength, Ecosystem ecosystem) {
        super(area, strength, ecosystem);
    }

    public Grass(Grass original) {
        super(original);
    }

    @Override
    public void reproduce() {
        if(getStrength() == 90 && getIMated() <= 2) {
            setStrength( 60);
            incrementeIMated();
            Area a  = getArea().CheckSourroundingArea(this.ecosystem);
            if( a != null){
                this.ecosystem.addToWaitingList( new Grass(a, this.ecosystem) );
            }
        }
    }
}
