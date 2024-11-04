package pt.isec.pa.javalife.model.data.elements;

import pt.isec.pa.javalife.model.data.*;
import pt.isec.pa.javalife.model.fsm.Ecosystem;

public abstract sealed class Flora extends ElementStandard implements IElementImage, IElementStrength,
        IElementReproduce permits Grass {
    private double Strength;
    private int IMated = 0;
    static int id = 0;
    private double LoseStrength;
    public boolean sunEffectActive;
    private int sunEffectDuration = 10;

    public Flora(Area area, Ecosystem ecosystem) {
        super(id++, Element.FLORA, area, ecosystem);
        this.Strength = 50;
        this.LoseStrength = 1;
        if (area == null || !area.isValid()) { //area.height() == area.width()
            throw new IllegalArgumentException("Area is null");
        }
    }

    public Flora(Area area, double strength, Ecosystem ecosystem) {
        super(id++, Element.FLORA, area, ecosystem);
        this.Strength = strength;
        this.LoseStrength = 1;
        if (area == null || !area.isValid()) { //area.height() == area.width()
            throw new IllegalArgumentException("Area is null");
        }
    }

    public Flora(Flora original) {
        super(original);
        this.Strength = original.getStrength();
        this.IMated = original.getIMated();
        this.LoseStrength = original.getLoseStrength();
    }

//    public Flora(int id, Element type,Area area, Ecosystem ecosystem) {
//        super(id, type, area, ecosystem);
//    }

    @Override
    public String getImagem() {
        return null;
    }

    @Override
    public void setImagem(String imagem) {

    }

    public double getLoseStrength() {
        return LoseStrength;
    }

    @Override
    public double getStrength() {
        return this.Strength;
    }

    @Override
    public void setStrength(double Strength) {
        this.Strength = Strength;
    }

    @Override
    public void evolve() {

        if (sunEffectActive && sunEffectDuration > 0) {
            this.Strength += 1;
            sunEffectDuration--;
            if (sunEffectDuration == 0) {
                sunEffectActive = false;
            }
        } else {
            this.Strength += 0.5;
        }
        reproduce();
        CheckIfSteped();
//        CheckIfDead();
    }

    public int getIMated() {
        return IMated;
    }

    public void incrementeIMated() {
        this.IMated++;
    }


    private Area CheckSourroundingArea() {
        //Check if any surrounding area is free
        double right = this.getArea().direita();
        double left = this.getArea().esquerda();
        double top = this.getArea().cima();
        double bottom = this.getArea().baixo();
        //RIGHT
        for (int i = 0; i < 4; i++) {
            if (i == Directions.RIGHT.ordinal()) {
                right += this.getArea().width();
                left += this.getArea().width();
            } else if (i == Directions.LEFT.ordinal()) {
                right -= this.getArea().width();
                left -= this.getArea().width();
            } else if (i == Directions.DOWN.ordinal()) {
                top -= this.getArea().height();
                bottom -= this.getArea().height();
            } else {
                top += this.getArea().height();
                bottom += this.getArea().height();
            }


            if (this.ecosystem.getElementsInArea(new Area(top, left, bottom, right)).isEmpty()) {
                return new Area(top, left, bottom, right);
            }
        }

        return null;
    }

    @Override
    public boolean CheckIfDead() {
        return Strength <= 0;
    }

    private void CheckIfSteped() {
        if (this.ecosystem.getElementsInArea(this.getArea()).stream().filter(x -> x instanceof Fauna).count() > 1) {//filter -> prevents stepping on itself
            this.setStrength(this.getStrength() - getLoseStrength());
        }
    }

    @Override
    public void applyHerbicide() {
        this.Strength = 0;
    }

    @Override
    public void injectStrength() {

    }

    @Override
    public void applySun() {
        sunEffectActive = true;
        sunEffectDuration = 10;
    }
}
