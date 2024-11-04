package pt.isec.pa.javalife.model.fsm;

import pt.isec.pa.javalife.model.data.elements.Fauna;
import pt.isec.pa.javalife.model.data.elements.Element;

import java.io.Serializable;

public class ClientStateAdapter implements IClientState, Serializable {

    protected ClientContext context;
    protected Ecosystem ecosystem;
    protected Fauna fauna;

    protected ClientStateAdapter(ClientContext context, Ecosystem ecosystem, Fauna fauna) {
        this.context = context;
        this.ecosystem = ecosystem;
        this.fauna = fauna;
    }

    protected void changeState(ClientStates newState) {

        context.changeState(newState.createState(context, this.ecosystem, this.fauna));
    }

    @Override
    public boolean selOpt(Element opt, String string) {
        return false;
    }

    @Override
    public boolean onMessageReceived(Object message) {
        return false;
    }

    @Override
    public ClientStates getState() {
        return null;
    }

    @Override
    public void evolve() {}


}

