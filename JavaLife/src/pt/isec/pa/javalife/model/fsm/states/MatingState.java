package pt.isec.pa.javalife.model.fsm.states;


import pt.isec.pa.javalife.model.data.elements.Element;
import pt.isec.pa.javalife.model.fsm.ClientContext;
import pt.isec.pa.javalife.model.fsm.ClientStateAdapter;
import pt.isec.pa.javalife.model.fsm.ClientStates;
import pt.isec.pa.javalife.model.fsm.Ecosystem;
import pt.isec.pa.javalife.model.data.elements.Fauna;

import java.io.Serializable;


public class MatingState extends ClientStateAdapter implements Serializable {
    private static int count = 0;
    public MatingState(ClientContext context, Ecosystem manageEcosystem, Fauna Fauna) {
        super(context, manageEcosystem, Fauna);
    }

    @Override
    public boolean selOpt(Element opt, String string) {
        return false;
    }

    @Override
    public ClientStates getState() {
        return ClientStates.MATING;
    }

    @Override
    public void evolve()
    {
        if(fauna.getStrength() < 35)
        {
            changeState(ClientStates.FOODSEARCH);
            return;
        }
        this.fauna.setReference(this.ecosystem.getStrongerFauna(this.fauna));
        Fauna FaunaReference = (Fauna) this.fauna.getReference();
        if(FaunaReference == null){
            return;
        }
        double distance = this.ecosystem.getDistanceBetweenElements(this.fauna, FaunaReference);


        if(distance <= 10)
        {
            System.out.println("Mating count" + count);
            count++;
            if(count == 10){
                System.out.println("I am moving.");
                this.fauna.setStrength(this.fauna.getStrength() - 25);
                this.fauna.reproduce();
                count = 0;
                changeState(ClientStates.MOVING);
            }
        }
        else{
            count = 0;
        }

        this.fauna.move();
    }
}
