package ch.idsia.agents.controllers;

import ch.idsia.behavior.BehaviorTree;
import ch.idsia.behavior.MarioTree;
import ch.idsia.benchmark.mario.engine.sprites.Sprite;
import ch.idsia.benchmark.mario.environments.Environment;

public class BTAgent extends BasicMarioAIAgent {

    private BehaviorTree tree = new MarioTree();
    private final int fireRange = 4;
    private final int lookAhead = 4;

    public BTAgent() {
        super("BT Agent");
        reset();
    }

    public boolean isObstacleInFront() {
        int x = marioEgoRow;
        int y = marioEgoCol;
        for (int i = 0; i < lookAhead; i++) {
            if (levelScene[x][y + i] != 0)
                return (isMarioAbleToJump || !isMarioOnGround);
        }
        return false;
    }

    private boolean isCreature(int c) {
        switch (c) {
            case Sprite.KIND_GOOMBA:
            case Sprite.KIND_GOOMBA_WINGED:
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

    public boolean isCoinBehind() {
        int x = marioEgoRow;
        int y = marioEgoCol;
        for (int i = 0; i < lookAhead; i++) {
            if (levelScene[x][y - i] == 2 || levelScene[x + 1][y - i] == 2)
                return true;
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
