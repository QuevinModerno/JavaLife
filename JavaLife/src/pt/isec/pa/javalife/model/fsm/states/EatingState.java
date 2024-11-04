package pt.isec.pa.javalife.model.fsm.states;

import pt.isec.pa.javalife.model.data.elements.*;
import pt.isec.pa.javalife.model.fsm.ClientContext;
import pt.isec.pa.javalife.model.fsm.ClientStateAdapter;
import pt.isec.pa.javalife.model.fsm.ClientStates;
import pt.isec.pa.javalife.model.fsm.Ecosystem;

import java.io.Serializable;
import java.util.Set;


public class EatingState extends ClientStateAdapter implements Serializable {
    public EatingState(ClientContext context, Ecosystem manageEcosystem, Fauna animal) {
        super(context, manageEcosystem, animal);
    }

    @Override
    public boolean selOpt(Element opt, String string) {
        return false;
    }

    @Override
    public ClientStates getState() {
        return ClientStates.EATING;
    }

    @Override
    public void evolve() {
        if (fauna.getStrength() == 100) {
            changeState(ClientStates.MOVING);
        } else {
            Grass grass = getGrass();

            if (grass == null) //grass is over
            {
                if (fauna.getStrength() < 80) {
                    if (this.ecosystem.IsThereAnyFlora()) {
                        System.out.println("Maintain foodSearch " + this.fauna.getStrength());
                        changeState(ClientStates.FOODSEARCH);
                    } else {
                        fauna.setReference(this.ecosystem.getWeakerFauna(this.fauna));
                        System.out.println(this.fauna.getStrength() + "I am going to atack: " + this.ecosystem.getWeakerFauna(this.fauna).getType());
                        changeState(ClientStates.ATTACKING);
                    }
                } else {
                    System.out.println("I am going to mate.");
                    changeState(ClientStates.MATING);
                }
            } else {
                fauna.setStrength(fauna.getStrength() + grass.getLoseStrength());
            }
        }
    }

    private Grass getGrass() {
        Set<IElement> iElement = this.ecosystem.getElementsInArea(fauna.getArea());
        for (IElement i : iElement) {
            if (i instanceof Flora && ((Flora) i).getStrength() > 0) {
                return (Grass) i;
            }
        }
        return null;
    }
}
