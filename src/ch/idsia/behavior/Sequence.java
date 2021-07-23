package ch.idsia.behavior;

import java.util.ArrayList;

import ch.idsia.agents.controllers.BTAgent;

public class Sequence implements Task {

    private ArrayList<Task> tasks;

    public Sequence() {
        tasks = new ArrayList<Task>();
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public void insert(Task task) {
        tasks.add(task);
    }

    @Override
    public boolean run(BTAgent mario) {
        Task t;
        for (int i = 0; i < tasks.size(); i++) {
            t = tasks.get(i);
            if (!t.run(mario))
                return false;
        }
        return true;
    }

}
