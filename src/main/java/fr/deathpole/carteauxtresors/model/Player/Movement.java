package fr.deathpole.carteauxtresors.model.Player;

import fr.deathpole.carteauxtresors.enums.EnumPlayerAction;

public class Movement {

    private EnumPlayerAction action;
    private Boolean executed;

    public Movement(EnumPlayerAction action, Boolean executed) {
        this.action = action;
        this.executed = executed;
    }

    public EnumPlayerAction getAction() {
        return action;
    }

    public Boolean isExecuted() {
        return executed;
    }

    public void setExecuted(Boolean alreadyExecuted) {
        executed = alreadyExecuted;
    }
}
