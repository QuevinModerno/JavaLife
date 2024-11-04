package pt.isec.pa.javalife.model.fsm.states;

import pt.isec.pa.javalife.model.data.elements.Element;
import pt.isec.pa.javalife.model.fsm.ClientContext;
import pt.isec.pa.javalife.model.fsm.ClientStateAdapter;
import pt.isec.pa.javalife.model.fsm.ClientStates;
import pt.isec.pa.javalife.model.fsm.Ecosystem;
import pt.isec.pa.javalife.model.data.elements.Fauna;

import java.io.Serializable;


public class MovingState extends ClientStateAdapter implements Serializable
{
    public MovingState(ClientContext context, Ecosystem Ecosystem, Fauna Fauna) {
        super(context, Ecosystem, Fauna);
    }
    @Override
    public boolean selOpt(Element opt, String string) {
        changeState(ClientStates.ANIMALSEARCH);
        return true;
    }

    @Override
    public ClientStates getState() {
        return ClientStates.MOVING;
    }
    @Override
    public void evolve() {

        this.fauna.setReference(null);
        if(this.fauna.getStrength() < 35){
            System.out.println("I changed state");
            changeState(ClientStates.FOODSEARCH);
            return;
        }
        this.fauna.move();

    }
}
