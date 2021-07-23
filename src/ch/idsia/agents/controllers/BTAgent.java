package ch.idsia.agents.controllers;

import ch.idsia.behavior.BehaviorTree;
import ch.idsia.behavior.Selector;
import ch.idsia.behavior.Sequence;
import ch.idsia.behavior.actions.Jump;
import ch.idsia.behavior.actions.MoveLeft;
import ch.idsia.behavior.actions.MoveRight;
import ch.idsia.behavior.actions.Shoot;
import ch.idsia.behavior.conditions.IsEnemyInFront;
import ch.idsia.behavior.conditions.IsEnemyInFrontLeft;
import ch.idsia.behavior.conditions.IsEnemyInRange;
import ch.idsia.behavior.conditions.IsEnemyInRangeLeft;
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

        Selector sel1 = new Selector();

        sel1.insert(new IsObstacleInFront());
        sel1.insert(new IsEnemyInFront());

        Sequence seq = new Sequence();
        seq.insert(sel1);
        seq.insert(new Jump());

        Sequence seq2 = new Sequence();
        seq2.insert(new MoveRight());
        seq2.insert(seq);

        Sequence seq3 = new Sequence();
        seq3.insert(new IsEnemyInRange());
        seq3.insert(new Shoot());

        Sequence seq4 = new Sequence();
        seq4.insert(seq2);
        seq4.insert(seq3);

        Sequence seq5 = new Sequence();
        seq5.insert(new IsEnemyInRangeLeft());
        seq5.insert(new MoveLeft());
        seq5.insert(new Shoot());

        Sequence seq6 = new Sequence();
        seq6.insert(new IsEnemyInFrontLeft());
        seq6.insert(new MoveLeft());
        seq6.insert(new Jump());

        Selector sel3 = new Selector();
        sel3.insert(seq5);
        sel3.insert(seq6);

        Selector sel2 = new Selector();
        sel2.insert(sel3);
        sel2.insert(seq4);

        tree.insert(sel2);
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

    public boolean isEnemyInFront(int dir) {
        int x = marioEgoRow;
        int y = marioEgoCol;
        for (int i = 0; i < lookAhead; i++) {
            int j = i * dir;
            if (isCreature(enemies[x][y + j]) || isCreature(enemies[x + 1][y + j]) || isCreature(enemies[x + 2][y + j]))
                return isMarioAbleToJump || !isMarioOnGround;
        }
        return false;
    }

    public boolean isEnemyInRange(int dir) {
        int x = marioEgoRow;
        int y = marioEgoCol;
        for (int i = 0; i < fireRange; i++) {
            int j = i * dir;
            if ((isCreature(enemies[x][y + j]) && levelScene[x][y + j] == 0)
                    || (isCreature(enemies[x + 1][y + j]) && levelScene[x + 1][y + j] == 0)
                    || (isCreature(enemies[x + 2][y + j]) && levelScene[x + 2][y + j] == 0))
                return marioMode == 2;
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
