package pt.isec.pa.javalife.model.fsm.states;

import pt.isec.pa.javalife.model.data.elements.Element;
import pt.isec.pa.javalife.model.data.elements.Fauna;
import pt.isec.pa.javalife.model.fsm.ClientContext;
import pt.isec.pa.javalife.model.fsm.ClientStateAdapter;
import pt.isec.pa.javalife.model.fsm.ClientStates;
import pt.isec.pa.javalife.model.fsm.Ecosystem;
import pt.isec.pa.javalife.model.data.elements.Animal;

import java.io.Serializable;


public class AttackingState extends ClientStateAdapter implements Serializable {
    public AttackingState(ClientContext context, Ecosystem manageEcosystem, Fauna animal) {
        super(context, manageEcosystem, animal);
    }

    @Override
    public boolean selOpt(Element opt, String string) {
        return false;
    }

    @Override
    public ClientStates getState() {
        return ClientStates.ATTACKING;
    }

    @Override
    public void evolve()
    {
        if(this.fauna.getReference() == null)
        {
            System.out.println("No animal found");
            fauna.setReference(this.ecosystem.getWeakerFauna(this.fauna));
        }
        else {
            double distance = this.ecosystem.getDistanceBetweenElements(this.fauna, this.fauna.getReference());

            if (distance <= 1) {
                Animal AnimalReference = (Animal) this.fauna.getReference();

                if (AnimalReference.getState() == ClientStates.ATTACKING) {
                    ChooseKiller(AnimalReference);
                } else if (this.fauna.getStrength() <= 10) {
                    AnimalReference.setStrength(AnimalReference.getStrength()
                            + this.fauna.getStrength()
                    );
                    this.fauna.setStrength(0);
                } else {
                    this.fauna.setStrength(this.fauna.getStrength()
                            + AnimalReference.getStrength()
                    );
                }
                System.out.println("Reference Animal: " + AnimalReference.getStrength());
            }
            System.out.println("Atacking Animal: " + this.fauna.getStrength());

        }

        this.fauna.move();
    }

    private void ChooseKiller(Animal animal)
    {
        if(this.fauna.getStrength() > animal.getStrength())
        {
            animal.setStrength(0);
        }
        else
        {
            this.fauna.setStrength(0);
            changeState(ClientStates.MOVING);
        }
    }
}
