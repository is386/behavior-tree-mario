package ch.idsia.behavior;

import ch.idsia.agents.controllers.BTAgent;

public class Limit extends Decorator {

    private int runSoFar = 0;
    private final int runLimit = 120;

    public Limit(Task t) {
        super(t);
    }

    @Override
    public boolean run(BTAgent mario) {
        if (runSoFar < 0) {
            runSoFar++;
            return false;
        } else if (runSoFar >= runLimit) {
            runSoFar = -5;
            return false;
        }
        runSoFar++;
        return task.run(mario);
    }
}
