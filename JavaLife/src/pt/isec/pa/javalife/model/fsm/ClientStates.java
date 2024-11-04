package pt.isec.pa.javalife.model.fsm;

import pt.isec.pa.javalife.model.data.elements.Animal;
import pt.isec.pa.javalife.model.data.elements.Fauna;
import pt.isec.pa.javalife.model.fsm.states.*;

import java.io.Serializable;

public enum ClientStates implements Serializable {
    MOVING, FOODSEARCH, EATING, ATTACKING, ANIMALSEARCH, MATING;


    IClientState createState(ClientContext context, Ecosystem manageEcosystem, Fauna animal) {
        return switch (this) {
            case MOVING -> new MovingState(context, manageEcosystem,animal);
            case FOODSEARCH -> new FoodSearchState(context, manageEcosystem,animal);
            case EATING -> new EatingState(context, manageEcosystem,animal);
            case ATTACKING -> new AttackingState(context, manageEcosystem, animal);
            case ANIMALSEARCH -> new FaunaSearchState(context, manageEcosystem,animal);
            case MATING -> new MatingState(context, manageEcosystem,animal);
        };
    }
}
