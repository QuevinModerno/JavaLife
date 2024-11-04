package pt.isec.pa.javalife.model.fsm.states;

import pt.isec.pa.javalife.model.data.elements.Element;
import pt.isec.pa.javalife.model.data.elements.Fauna;
import pt.isec.pa.javalife.model.fsm.ClientContext;
import pt.isec.pa.javalife.model.fsm.ClientStateAdapter;
import pt.isec.pa.javalife.model.fsm.ClientStates;
import pt.isec.pa.javalife.model.fsm.Ecosystem;
import pt.isec.pa.javalife.model.data.elements.Animal;

import java.io.Serializable;


public class FaunaSearchState extends ClientStateAdapter implements Serializable {
    public FaunaSearchState(ClientContext context, Ecosystem manageEcosystem, Fauna animal) {
        super(context, manageEcosystem, animal);
    }

    @Override
    public boolean selOpt(Element opt, String string) {
        return false;
    }

    @Override
    public ClientStates getState() {
        return ClientStates.ANIMALSEARCH;
    }
}