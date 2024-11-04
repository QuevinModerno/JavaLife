package pt.isec.pa.javalife.model.data.elements;

import pt.isec.pa.javalife.model.data.Area;
import pt.isec.pa.javalife.model.fsm.Ecosystem;


public final class Rock extends Inanimado{

    public Rock(Area area, Ecosystem ecosystem) {
        super(area, ecosystem);
    }

    public Rock(Rock original) {
        super(original);
    }

    @Override
    public void applyHerbicide() {

    }

    @Override
    public void applySun() {

    }

    @Override
    public void injectStrength() {

    }
}
