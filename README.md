## CP468 Assignment1 Missionaries and Cannibals
### By Group 4 ZZ-TW-ML-YC
<script type="text/x-mathjax-config">
MathJax.Hub.Config({
  tex2jax: {inlineMath: [['$','$'], ['$','$']]}
});
</script>
<script type="text/javascript" src="http://cdn.mathjax.org/mathjax/latest/MathJax.js?config=default"></script>

#### Abstraction

For convenience, in the remainder, each abbreviation has its own meaning: 

|Abbreviation|Meanning|
|-|-|
|$\mathbf{M}$|missionary|
|$\mathbf{C}$|cannibal|
|$N$|number of $\mathbf{M}$ or $\mathbf{C}$ (the same)|
|$B$|number of seats in boat|
|$ml$|number of $\mathbf{M}$ on left side|
|$mb$|number of $\mathbf{M}$ in boat|
|$mr$|number of $\mathbf{M}$ on right side|
|$cl$|number of $\mathbf{C}$ on left side|
|$cb$|number of $\mathbf{C}$ in boat|
|$cr$|number of $\mathbf{C}$ on right side|
|$pos$|position of boat, left side: 1, right side: 0|
|$(ml,cl,pos)$|one state when the boat is empty and on side pos, $ml \mathbf{M}$ and $cl \mathbf{C}$ are on the left side|
|$L(mb,cb)$|carry $mb \mathbf{M}$ and $cb \mathbf{C}$ to left side|
|$R(mb,cb)$|carry $mb \mathbf{M}$ and $cb \mathbf{C}$ to right side| 

####Reality constraints
Each number must be non-negative and less than the total number: $$0<=ml,mb,mr,cl,cb,cr<=N$$ $$0<=mb,cb<=B$$ $$ml+mb+mr==cl+cb+cr==N$$
The boat can not across the river without someone in it: $$0<mb+cb<=B$$

####Security constraints
Whether on left, right or in boat, the number of $\mathbf{M}$ should not less than that of $\mathbf{C}$: $$ml==0||ml>=cl$$ $$mb==0||mb>=cb$$ $$mr==0||mr>=cr$$

#### Initial State
$N \mathbf{M}$ and $N \mathbf{C}$ are on one side, let's say left side, with a boat at the same side, described as $(N,N,1)$.

#### Goal State
$N \mathbf{M}$, $N \mathbf{C}$ and the boat are on the right side, described as $(0,0,0)$.  

#### State Space
Take $N==3$, $B==2$ as an example, below is the complete state space graph:
![](./statespace.png)

There are 10 states and $16*2=32$ transitions in total. For short we just show half of transitions:

|Transitions|
|-|
$Result\bigl((3,3,1),R(1,1)\bigr)=(2,2,0)$  
$Result\bigl((3,3,1),R(0,1)\bigr)=(3,2,0)$  
$Result\bigl((3,3,1),R(0,2)\bigr)=(3,1,0)$  
$Result\bigl((2,2,0),L(1,0)\bigr)=(3,2,1)$  
$Result\bigl((3,1,0),L(0,1)\bigr)=(3,2,1)$  
$Result\bigl((3,2,1),R(0,2)\bigr)=(3,0,0)$  
$Result\bigl((3,0,0),L(0,1)\bigr)=(3,1,1)$  
$Result\bigl((3,1,1),R(2,0)\bigr)=(1,1,0)$  
$Result\bigl((1,1,0),L(1,1)\bigr)=(2,2,1)$  
$Result\bigl((2,2,1),R(2,0)\bigr)=(0,2,0)$  
$Result\bigl((0,2,0),L(0,1)\bigr)=(0,3,1)$  
$Result\bigl((0,3,1),R(0,2)\bigr)=(0,1,0)$  
$Result\bigl((0,1,0),L(0,1)\bigr)=(0,2,1)$  
$Result\bigl((0,1,0),L(1,0)\bigr)=(1,1,1)$  
$Result\bigl((0,2,1),R(0,2)\bigr)=(0,0,0)$  
$Result\bigl((1,1,1),R(1,1)\bigr)=(0,0,0)$  

#### Path Cost
Path cost increased by one each time when the boat cross the river.

#### Algorithm Analysis
We considered two algorithms for this problem:  
iterative deenpening depth-first search(IDS) and iterative deepening a\* search(IDA\*).  

||IDS|IDA\*|
|-|-|-|
|complete|Y|Y|
|optimal|Y|Y|
|time|$O(b^d)$|$O(b^d)$|
|space|$O(bd)$|$O(bd)$|

IDS and IDA\* are both complete in that they will continue searching until find a solution or exhaust all states. They are optimal in that the cost is increasing during iteration, in other words, the first solution found must cost least.  
IDS and IDA\* are also the same in structure: outside is a iteration of limit and inside is a recursion of search function.  
But IDS uses $g(n)$ (cost from start state to current state $n$) as limit, IDA\* uses $f(n)=g(n)+h(n)$ (global estimated cost) instead.  
Moreover, IDS does DFS within the limit from one child state; while IDA\* will expand at the child state with the minimum value of $f(n)$.  

Another thing worth talking is the selection of hueristic function of IDA\*.  

//TODO 启发函数的选择

#### Implementation in Java
Source code can be find here: https://github.com/BIOTONIC/MissionariesAndCannibals

|Test.java|State.java|Action.java|IDS.java|IDAStar.java|
|-|-|-|-|-|
|call two algorithms|store state's info & provide functions related to state|use two lists to store all valid arrangements of $\mathbf{M}$ and $\mathbf{C}$ on boat|IDS algorithm|IDA* algorithm|

Below is the output of two algorithms when $N==3$ and $B==2$. Solutions of two algorithms are the same because they traverse all actions of a given state by the same sequence provided by Action.java. One difference is that IDA\*'s limit starts directly from 4.
```
iterative deepening depth-first search
try depth limit 1
try depth limit 2
try depth limit 3
try depth limit 4
try depth limit 5
try depth limit 6
try depth limit 7
try depth limit 8
try depth limit 9
try depth limit 10
try depth limit 11
optimal solution found
step 1: (3,3,1) => (0,2)
step 2: (3,1,0) <= (0,1)
step 3: (3,2,1) => (0,2)
step 4: (3,0,0) <= (0,1)
step 5: (3,1,1) => (2,0)
step 6: (1,1,0) <= (1,1)
step 7: (2,2,1) => (2,0)
step 8: (0,2,0) <= (0,1)
step 9: (0,3,1) => (0,2)
step 10: (0,1,0) <= (0,1)
step 11: (0,2,1) => (0,2)

iterative deepening a star search
try depth limit 4
try depth limit 5
try depth limit 6
try depth limit 7
try depth limit 8
try depth limit 9
try depth limit 10
try depth limit 11
optimal solution found
step 1: (3,3,1) => (0,2)
step 2: (3,1,0) <= (0,1)
step 3: (3,2,1) => (0,2)
step 4: (3,0,0) <= (0,1)
step 5: (3,1,1) => (2,0)
step 6: (1,1,0) <= (1,1)
step 7: (2,2,1) => (2,0)
step 8: (0,2,0) <= (0,1)
step 9: (0,3,1) => (0,2)
step 10: (0,1,0) <= (0,1)
step 11: (0,2,1) => (0,2)
```

Both algorithms are able to solve $N$ missionaries, $N$ cannibals and $B$ seats problems. Below is the ouput when $N==7$ and $B==4$:
```
step 1: (7,7,1) => (0,4)
step 2: (7,3,0) <= (0,2)
step 3: (7,5,1) => (0,4)
step 4: (7,1,0) <= (0,2)
step 5: (7,3,1) => (4,0)
step 6: (3,3,0) <= (1,1)
step 7: (4,4,1) => (2,2)
step 8: (2,2,0) <= (1,1)
step 9: (3,3,1) => (2,2)
step 10: (1,1,0) <= (1,1)
step 11: (2,2,1) => (2,2)
```

//TODO 何时有解无解


The remainder is the source code. Download it from [github](https://github.com/BIOTONIC/MissionariesAndCannibals) if you want to run and test it.
```
// Test.java
// call two algorithms

public class Test {
    public static void main(String[] args) {
        new IDS(7, 4, 1000); // N=7 B=4 upperLimit=1000
        new IDAStar(3, 2, 1000); // N=3 B=2 upperLimit=1000
    }
}
```


```
// State.java 
// store state's info & provide functions related to state

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
```

```
// Action.java
// use two lists to store all valid arrangements of m and c on boat

import java.util.ArrayList;

public class Action {
    public static ArrayList<Integer> mbs; // list of numbers of missionaries on boat
    public static ArrayList<Integer> cbs; // list of numbers of cannibals on boat

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
}
```

```
// IDS.java
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

    public IDS(int N, int B, int upperLimit) {
        System.out.println("iterative deepening depth-first search");

        this.N = N;
        this.B = B;

        action = new Action(N,B);
        int limit = 0;
        int result;
        State state;
        do {
            // iterate limit
            limit++;
            System.out.println("try depth limit " + limit);
            // init state and explored list before every iteration
            state = new State(N);
            explored = new ArrayList<>();
            records = new Stack<>();
            result = search(state, limit);
            if (result == SUCCESS) {
                break;
            } else if (result >= upperLimit) {
                result = FAILURE;
                break;
            }
        } while (search(state, limit) != SUCCESS);

        // print result
        if (result == SUCCESS) {
            int i = 1;
            while (!records.empty()) {
                System.out.println("step " + i + ": " + records.pop());
                i++;
            }
        } else {
            System.out.println("no solution when upper limit is " + limit);
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
                    // once result from child is cutoff (child state limit == 0)
                    // current state is cutoff too
                    // need to increase depth limit
                    // so just jump out of the for loop immediately
                    isCutoff = true;
                    break;
                } else if (result != FAILURE) {
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
            System.out.println("no solution");
            return FAILURE;
        }
    }
}
```

```
// IDAStar.java

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
```
