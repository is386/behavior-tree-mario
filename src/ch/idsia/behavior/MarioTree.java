package ch.idsia.behavior;

import ch.idsia.behavior.actions.Jump;
import ch.idsia.behavior.actions.MoveLeft;
import ch.idsia.behavior.actions.MoveRight;
import ch.idsia.behavior.actions.Shoot;
import ch.idsia.behavior.conditions.IsCoinBehind;
import ch.idsia.behavior.conditions.IsEnemyBehind;
import ch.idsia.behavior.conditions.IsEnemyInFront;
import ch.idsia.behavior.conditions.IsEnemyInRange;
import ch.idsia.behavior.conditions.IsEnemyInRangeBehind;
import ch.idsia.behavior.conditions.IsObstacleInFront;

public class MarioTree extends BehaviorTree {

    public MarioTree() {
        super();

        Selector obstacleOrEnemy = new Selector();
        obstacleOrEnemy.insert(new IsObstacleInFront());
        obstacleOrEnemy.insert(new IsEnemyInFront());

        Sequence jumpRight = new Sequence();
        jumpRight.insert(obstacleOrEnemy);
        jumpRight.insert(new Jump());

        Sequence moveRight = new Sequence();
        moveRight.insert(new MoveRight());
        moveRight.insert(jumpRight);

        Sequence shootRight = new Sequence();
        shootRight.insert(new IsEnemyInRange());
        shootRight.insert(new Shoot());

        Sequence rightSide = new Sequence();
        rightSide.insert(moveRight);
        rightSide.insert(shootRight);

        Sequence shootLeft = new Sequence();
        shootLeft.insert(new IsEnemyInRangeBehind());
        shootLeft.insert(new MoveLeft());
        shootLeft.insert(new Shoot());

        Sequence jumpLeft = new Sequence();
        jumpLeft.insert(new IsEnemyBehind());
        jumpLeft.insert(new MoveLeft());
        jumpLeft.insert(new Jump());

        Sequence coinLeft = new Sequence();
        coinLeft.insert(new IsCoinBehind());
        coinLeft.insert(new MoveLeft());

        Limit coinLeftLimit = new Limit(coinLeft);

        Selector leftSide = new Selector();
        leftSide.insert(shootLeft);
        leftSide.insert(jumpLeft);
        leftSide.insert(coinLeftLimit);

        Selector root = new Selector();
        root.insert(leftSide);
        root.insert(rightSide);

        this.insert(root);
    }
}
