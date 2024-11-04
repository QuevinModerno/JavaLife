package pt.isec.pa.javalife.model;
import pt.isec.pa.javalife.model.data.Area;
import pt.isec.pa.javalife.model.data.elements.Element;

import java.io.Serializable;

public class UiElement implements Serializable {
    private int Id;
    private double Strength;
    private Area Area;
    private Element type;

    public UiElement(int id, double strength, Area area, Element type) {
        this.Id = id;
        this.Strength = strength;
        this.Area = area;
        this.type = type;
    }

    public double getStrength() {
        return Strength;
    }

    public Element getType() {
        return type;
    }

    public Area getArea() {
        return Area;
    }
    public int getId() {
        return  Id;
    }

    @Override
    public String toString() {
        return
                "Id=" + Id +
                ", Strength=" + Strength +
                ", Area=" + Area +
                ", type=" + type ;
    }

    public void applySun() {
    }

    public void applyHerbicide() {
    }

    public void injectStrength() {
    }
}
