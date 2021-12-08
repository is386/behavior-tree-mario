package ch.idsia.behavior.conditions;

import ch.idsia.agents.controllers.BTAgent;
import ch.idsia.behavior.Task;

public class IsEnemyInRangeBehind implements Task {

    @Override
    public boolean run(BTAgent mario) {
        return mario.isEnemyInRange(-1);
    }

}