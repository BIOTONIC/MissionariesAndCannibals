/*
* missionaries and cannibals problem
* iterative deepening depth-first search
*/

import java.util.ArrayList;
import java.util.Stack;

public class IDS {
    int N; // numbers of missionaries and cannibals are both N
    int B; // number of seats on boat

    Action action;
    ArrayList<State> explored;
    Stack<String> records; // records of every optimal step

    static final int SUCCESS = 0;
    static final int FAILURE = -1;
    static final int CUTOFF = -2;

    public IDS(int N, int B) {
        System.out.println("\nIDS N=" + N + " B=" + B);

        this.N = N;
        this.B = B;

        action = new Action(N, B);
        int limit = 0;
        int result;
        State state;
        while (true) {
            // iterate limit
            limit++;
            System.out.println("try depth limit " + limit);
            // init state and explored list before every iteration
            state = new State(N, B);
            explored = new ArrayList<>();
            records = new Stack<>();
            result = search(state, limit);
            if (result == SUCCESS || result == FAILURE) {
                break;
            }
        }

        // print result
        if (result == SUCCESS) {
            int i = 1;
            while (!records.empty()) {
                System.out.println("step " + i + ": " + records.pop());
                i++;
            }
        } else {
            System.out.println("no solution");
        }
    }

    int search(State state, int limit) {
        explored.add(state);
        if (state.isGoal()) {
            explored.remove(state);
            System.out.println("optimal solution found");
            return SUCCESS;
        }
        if (limit == 0) {
            explored.remove(state);
            //System.out.println("cut off");
            return CUTOFF;
        }

        boolean isCutoff = false;
        // traversal all actions
        for (int i = 0; i < action.mbs.size(); i++) {
            State child = state.doAction(action.mbs.get(i), action.cbs.get(i));
            // graph search
            // only expand valid and non-repetitive states
            if (child != null && !explored.contains(child)) {
                //System.out.println(child);
                // recursion, decreasing limit
                int result = search(child, limit - 1);
                if (result == CUTOFF) {
                    isCutoff = true;
                } else if (result ==SUCCESS) {
                    // find a step
                    records.push(state.toPath(action.mbs.get(i), action.cbs.get(i)));
                    explored.remove(state);
                    return result;
                }
            }
        }
        explored.remove(state);
        if (isCutoff) {
            return CUTOFF;
        } else {
            return FAILURE;
        }
    }
}
