package ch.idsia.behavior.actions;

import ch.idsia.agents.controllers.BTAgent;
import ch.idsia.behavior.Task;
import ch.idsia.benchmark.mario.engine.sprites.Mario;

public class Jump implements Task {
    @Override
    public boolean run(BTAgent mario) {
        mario.setAction(Mario.KEY_JUMP);
        return true;
    }
}
