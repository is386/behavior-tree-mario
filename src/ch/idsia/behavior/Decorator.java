package ch.idsia.behavior;

import ch.idsia.agents.controllers.BTAgent;

public class Decorator implements Task {
    protected Task task;

    public Decorator(Task t) {
        task = t;
    }

    @Override
    public boolean run(BTAgent mario) {
        return false;
    }
}
