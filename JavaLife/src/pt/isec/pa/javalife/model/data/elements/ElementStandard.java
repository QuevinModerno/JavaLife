package pt.isec.pa.javalife.model.data.elements;

import pt.isec.pa.javalife.model.data.Area;
import pt.isec.pa.javalife.model.fsm.Ecosystem;

public abstract sealed class ElementStandard implements IElement permits Inanimado, Flora, Fauna {
    private int Id;
    private Element Type;
    private Area Area;
    protected Ecosystem ecosystem;

    public ElementStandard(int id, Element type, Area area, Ecosystem ecosystem) {
        Id = id;
        Type = type;
        Area = area;
        this.ecosystem = ecosystem;
    }

    public int getId() {
        return Id;
    }
    @Override
    public Element getType() {
        return this.Type;
    }

    @Override
    public Area getArea() {
        return this.Area;
    }
    @Override
    public void setArea(Area area) {
        this.Area = area;
    }

    public ElementStandard(ElementStandard original) {
        Id = original.getId();
        Type = original.getType();
        Area = original.getArea();
        ecosystem = original.ecosystem;
    }
}