package pt.isec.pa.javalife.model.data.elements;

import pt.isec.pa.javalife.model.fsm.Ecosystem;
import pt.isec.pa.javalife.model.data.Area;


public abstract sealed class Inanimado extends ElementStandard implements IElementStrength permits Rock {

    static int id = 0;
    public Inanimado (Area area, Ecosystem ecosystem)
    {
        super(id++, Element.INANIMADO, area, ecosystem);

        if (area == null  || !area.isValid()) { //area.height() == area.width()
            throw new IllegalArgumentException("Area is null");
        }
    }

    public Inanimado(Inanimado original) {
        super(original);
    }

    @Override
    public double getStrength() {
        return 0;
    }

    @Override
    public void setStrength(double forca) {

    }
    @Override
    public void evolve() {

    }
    @Override
    public boolean CheckIfDead()
    {
        return false;
    }
}
