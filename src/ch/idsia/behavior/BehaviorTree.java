package ch.idsia.behavior;

import java.util.ArrayList;

import ch.idsia.agents.controllers.BTAgent;

public class BehaviorTree {
    private ArrayList<Task> tasks;

    public BehaviorTree() {
        tasks = new ArrayList<Task>();
    }

    public void insert(Task t) {
        tasks.add(t);
    }

    public void run(BTAgent mario) {
        Task t;
        for (int i = 0; i < tasks.size(); i++) {
            t = tasks.get(i);
            if (t.run(mario)) {
                break;
            }
        }
    }

}
