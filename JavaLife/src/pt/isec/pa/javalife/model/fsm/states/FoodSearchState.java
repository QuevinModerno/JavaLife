package pt.isec.pa.javalife.model.fsm.states;

import pt.isec.pa.javalife.model.data.elements.IElement;
import pt.isec.pa.javalife.model.data.elements.Element;
import pt.isec.pa.javalife.model.fsm.ClientContext;
import pt.isec.pa.javalife.model.fsm.ClientStateAdapter;
import pt.isec.pa.javalife.model.fsm.ClientStates;
import pt.isec.pa.javalife.model.fsm.Ecosystem;
import pt.isec.pa.javalife.model.data.elements.Fauna;

import java.io.Serializable;

public class FoodSearchState extends ClientStateAdapter implements Serializable
{

    public FoodSearchState(ClientContext context, Ecosystem Ecosystem, Fauna Fauna) {
        super(context, Ecosystem, Fauna);
    }
    @Override
    public boolean selOpt(Element opt, String string) {
        return false;
    }

    @Override
    public ClientStates getState() {
        return ClientStates.FOODSEARCH;
    }

    @Override
    public void evolve() {
        //If Fauna in the same position as Flora, change state to EATING
        if(this.ecosystem.CheckElements(this.fauna.getArea(), Element.FLORA)){
            changeState(ClientStates.EATING);
            System.out.println("EATING");
        }
        //Else Chase Flora
        else {
            IElement referenceElement = this.ecosystem.getCloserElement(this.fauna, Element.FLORA);
            this.fauna.setReference(referenceElement); // Fauna will move to the position closer to the reference
            this.fauna.move();// or randomly
        }
    }
}