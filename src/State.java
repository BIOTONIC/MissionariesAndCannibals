/*
* missionaries and cannibals problem
* depict status structure for IDS & IDA*
*/
class State {
    int n; // numbers of missionaries or cannibals
    int ml; // numbers of missionaries on the left
    int cl; // numbers of cannibals on the left
    int pos; // current position of the boat, 0: on the right, 1: on the left

    // start state: ml=N, cl=N, pos=1
    // goal state: ml=0, cl=0, pos=0

    State(int n) {
        this.n = n;
        this.ml = n;
        this.cl = n;
        this.pos = 1;
    }

    State(int n, int ml, int cl, int pos) {
        this.n = n;
        this.ml = ml;
        this.cl = cl;
        this.pos = pos;
    }

    State doAction(int mb, int cb) {
        if (pos == 0) {
            // current at right, to left
            //
            // 0<=ml+mb<=N
            // && 0<=cl+cb<=N
            // && (ml+mb==0||ml+mb==N||ml+mb==cl+cb)
            if (ml + mb >= 0 && ml + mb <= n
                    && cl + cb >= 0 && cl + cb <= n
                    && (ml + mb == 0 || ml + mb == n || ml + mb == cl + cb)) {
                return new State(n, ml + mb, cl + cb, 1);
            } else {
                return null;
            }
        } else {
            // current at left, to right
            //
            // 0<=ml-mb<=N
            // && 0<=cl-cb<=N
            // && (ml==mb||ml+mb==N||ml-mb==cl-cb)
            if (ml - mb >= 0 && ml - mb <= n
                    && cl - cb >= 0 && cl - cb <= n
                    && (ml == mb || ml + mb == n || ml - mb == cl - cb)) {
                return new State(n, ml - mb, cl - cb, 0);
            } else {
                return null;
            }
        }
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null) {
            return false;
        }
        if (getClass() != other.getClass()) {
            return false;
        }
        State otherState = (State) other;
        if (otherState.n == this.n && otherState.ml == this.ml
                && otherState.cl == this.cl && otherState.pos == this.pos) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "(" + this.ml + "," + this.cl + "," + this.pos + ")";
    }

    public String toPath(int mb, int cb) {
        return toString() + " " + (pos == 1 ? "=> " : "<= ") + "(" + mb + "," + cb + ")";
    }

    public String toPath(State child) {
        return toString() + " " + (pos == 1 ? "=> " : "<= ") + "("
                + Math.abs(child.ml - ml) + "," + Math.abs(child.cl - cl) + ")";
    }

    boolean isGoal() {
        if (ml == 0 && cl == 0 && pos == 0) {
            return true;
        } else {
            return false;
        }
    }
}