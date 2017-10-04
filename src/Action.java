/*
* missionaries and cannibals problem
* depict all arrangements of m and c on boat
*/
import java.util.ArrayList;

public class Action {
    public static ArrayList<Integer> mbs; // list of numbers of missionaries on boat
    public static ArrayList<Integer> cbs; // list of numbers of cannibals on boat

    private static Action action;

    Action(int N, int B) {
        mbs = new ArrayList<>();
        cbs = new ArrayList<>();
        for (int b = B; b > 0; b--) {
            for (int mb = 0; mb <= N; mb++) {
                if (mb <= b && b - mb >= 0 && b - mb <= N && (mb == 0 || 2 * mb >= b)) {
                    mbs.add(mb);
                    cbs.add(b - mb);
                }
            }
        }
    }

    public static synchronized Action getInstance(int N, int B) {
        if (action == null) {
            action = new Action(N, B);
        }
        return action;
    }
}
