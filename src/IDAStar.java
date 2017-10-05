/*
* missionaries and cannibals problem
* iterative deepening a star
*/

import java.util.Stack;

public class IDAStar {
    int N; // numbers of missionaries and cannibals are both N
    int B; // number of seats on boat

    Action action;
    Stack<State> path;
    Stack<String> records; // records of every optimal step

    static final int SUCCESS = 0;
    static final int FAILURE = -1;

    public IDAStar(int N, int B, int upperLimit) {
        System.out.println("iterative deepening a star search");

        this.N = N;
        this.B = B;

        action = new Action(N,B);
        records = new Stack<>();
        State state = new State(N);
        path = new Stack<>();
        path.push(state);
        int limit = h(state);

        int result;
        do {
            System.out.println("try depth limit " + limit);

            result = search(0, limit);
            if (result == SUCCESS) {
                break;
            } else if (result >= upperLimit) {
                result = FAILURE;
                break;
            }
            limit = result;
        } while (result != SUCCESS);

        // print result
        if (result == SUCCESS) {
            State child = path.pop();
            while (!path.empty()) {
                records.push(path.peek().toPath(child));
                child = path.pop();
            }
            int i = 1;
            while (!records.empty()) {
                System.out.println("step " + i + ": " + records.pop());
                i++;
            }
        } else {
            System.out.println("no solution when upper limit is " + limit);
        }
    }

    int h(State state) {
        return state.ml + state.cl - 2 * state.pos;
    }

    int search(int g, int limit) {
        State state = path.peek();
        int f = g + h(state);
        if (f > limit) {
            return f;
        } else if (state.isGoal()) {
            System.out.println("optimal solution found");
            return SUCCESS;
        }
        int min = Integer.MAX_VALUE;
        // traversal all actions
        for (int i = 0; i < action.mbs.size(); i++) {
            State child = state.doAction(action.mbs.get(i), action.cbs.get(i));
            // graph search
            // only expand valid and non-repetitive states
            if (child != null && !path.contains(child)) {
                //System.out.println(child);
                path.push(child);
                int result = search(g + 1, limit);
                if (result == SUCCESS) {
                    return SUCCESS;
                }
                if (result < min) {
                    min = result;
                }
                path.pop();
            }
        }
        return min;
    }
}
