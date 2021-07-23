package ch.idsia.agents.controllers;

import ch.idsia.behavior.BehaviorTree;
import ch.idsia.behavior.Selector;
import ch.idsia.behavior.Sequence;
import ch.idsia.behavior.actions.Fire;
import ch.idsia.behavior.actions.Jump;
import ch.idsia.behavior.actions.MoveRight;
import ch.idsia.behavior.conditions.IsEnemyInFront;
import ch.idsia.behavior.conditions.IsEnemyInRange;
import ch.idsia.behavior.conditions.IsObstacleInFront;
import ch.idsia.benchmark.mario.engine.sprites.Sprite;
import ch.idsia.benchmark.mario.environments.Environment;

public class BTAgent extends BasicMarioAIAgent {

    private BehaviorTree tree = new BehaviorTree();
    private final int fireRange = 4;
    private final int lookAhead = 4;

    public BTAgent() {
        super("BT Agent");
        reset();
        Sequence seq = new Sequence();
        Selector sel = new Selector();

        sel.insert(new IsObstacleInFront());
        sel.insert(new IsEnemyInFront());

        seq.insert(sel);
        seq.insert(new Jump());

        Sequence seq2 = new Sequence();
        seq2.insert(new MoveRight());
        seq2.insert(seq);

        Sequence seq3 = new Sequence();
        seq3.insert(new IsEnemyInRange());
        seq3.insert(new Fire());

        Sequence seq4 = new Sequence();
        seq4.insert(seq2);
        seq4.insert(seq3);

        tree.insert(seq4);
    }

    public boolean isObstacleInFront() {
        int x = marioEgoRow;
        int y = marioEgoCol;
        for (int i = 0; i < lookAhead; i++) {
            if (levelScene[x][y + i] != 0)
                return isMarioAbleToJump || !isMarioOnGround;
        }
        return false;
    }

    private boolean isCreature(int c) {
        switch (c) {
            case Sprite.KIND_GOOMBA:
            case Sprite.KIND_RED_KOOPA:
            case Sprite.KIND_RED_KOOPA_WINGED:
            case Sprite.KIND_GREEN_KOOPA_WINGED:
            case Sprite.KIND_GREEN_KOOPA:
                return true;
        }
        return false;
    }

    public boolean isEnemyInFront() {
        int x = marioEgoRow;
        int y = marioEgoCol;
        for (int i = 0; i < lookAhead; i++) {
            if (isCreature(enemies[x][y + i]) || isCreature(enemies[x + 1][y + i]) || isCreature(enemies[x + 2][y + i]))
                return isMarioAbleToJump || !isMarioOnGround;
        }
        return false;
    }

    public boolean isEnemyInRange() {
        int x = marioEgoRow;
        int y = marioEgoCol;
        for (int i = 0; i < fireRange; i++) {
            if (isCreature(enemies[x][y + i]) || isCreature(enemies[x + 1][y + i]) || isCreature(enemies[x + 2][y + i]))
                return isMarioAbleToShoot;
        }
        return false;
    }

    public void setAction(int act) {
        action[act] = true;
    }

    public boolean[] getAction() {
        reset();
        tree.run(this);
        return action;
    }

    public void reset() {
        action = new boolean[Environment.numberOfKeys];
    }

}
