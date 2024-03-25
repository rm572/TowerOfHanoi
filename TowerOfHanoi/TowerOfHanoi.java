package TowerOfHanoi;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Stack;

public class TowerOfHanoi {
    public static void main(String[] args) throws IOException, EmptyStackException {
        solve(4);
    }

    public static void solve(int n) throws IOException, EmptyStackException {
        Path dbPath = Paths.get("TowerOfHanoi/Files/Output.txt");
        BufferedWriter bw = Files.newBufferedWriter(
            dbPath, 
            StandardCharsets.UTF_8);
            
        Stack<Integer> s0 = new Stack<Integer>();
        for (int i = 0; i < n; i++) {
            s0.push(n - i);
        }

        Stack<Integer> s1 = new Stack<Integer>();
        Stack<Integer> s2 = new Stack<Integer>();

        List<Stack<Integer>> list = new ArrayList<Stack<Integer>>();
        list.add(s0);
        list.add(s1);
        list.add(s2);

        int moveCount = 0;
        int prevFrom = 0;
        int prevTo = 0;

        int count = 0;

        for (int i = 0; i < Math.pow(2, n)-1; i++) {
            if (moveCount % 4 == 0) {
                int st = (count + 2) % 3;
                list.get(st).push(list.get(count).pop());
                prevFrom = count;
                prevTo = st;
            }

            else if (moveCount % 4 == 1) {
                int st = (count + 1) % 3;
                list.get(st).push(list.get(count).pop());
                prevFrom = count;
                prevTo = st;

                count++;
                count = count % 3;
                System.out.println("Count: " + count);
            }

            else if (moveCount % 12 == 2) {
                s1.push(s2.pop());
                prevFrom = 2;
                prevTo = 1;
            }

            else if (moveCount % 12 == 3) {
                int[] moves = makeMove(0, 2, list);
                prevFrom = moves[0];
                prevTo = moves[1];
                
            }

            else if (moveCount % 12 == 6 || moveCount % 12 == 10) {
                int cur = 3 - (prevFrom + prevTo);
                list.get(prevTo).push(list.get(cur).pop());
                prevFrom = cur;
            }

            else if (moveCount % 12 == 7 || moveCount % 12 == 11) {
                int oneStack = 3 - (prevTo + prevFrom);
                int twoStack = prevFrom;

                int[] moves = makeMove(oneStack, twoStack, list);
                prevFrom = moves[0];
                prevTo = moves[1];
            }

            System.out.println("Move count: " + moveCount);
            printStacks(list);
            writeTo(prevFrom, prevTo, bw);
            moveCount++;
        }
        bw.close();
    }

    /** Writes to the file
     * 
     * @param from
     * @param to
     * @param bw
     * @throws IOException
     */
    public static void writeTo(int from, int to, BufferedWriter bw) throws IOException{
        bw.write(from + ">" + to + "\n");
    }

    /** Prints the stacks
     * 
     * @param l
     */
    public static void printStacks(List<Stack<Integer>> l) {
        int x = 0;
        for (Stack<Integer> s: l) {
            Stack<Integer> stack = (Stack<Integer>) s.clone();
            Stack<Integer> temp = new Stack<Integer>();

            System.out.print(x + "| ");
            while (!stack.isEmpty()) {
                temp.push(stack.pop());
                
            }
            while (!temp.isEmpty()) {
                System.out.print(temp.pop() + " ");
            }
            System.out.println();
            x++;
        }
    }

    /** Returns the legal order of stacks (prevTo, prevFrom)
     * 
     * @param oneStack one of the stacks
     * @param twoStack another stack
     * @param list list of stacks
     * @return prevFrom, prevTo
     */
    public static int[] makeMove(int oneStack, int twoStack, List<Stack<Integer>> list) {
        boolean oneEmpty = list.get(oneStack).isEmpty();
        boolean twoEmpty = list.get(twoStack).isEmpty();
        int prevFrom = 0;
        int prevTo = 0;

        if (oneEmpty && !twoEmpty) {
            list.get(oneStack).push(list.get(twoStack).pop());
            prevFrom = twoStack;
            prevTo = oneStack;
        }
        else if (twoEmpty && !oneEmpty) {
            list.get(twoStack).push(list.get(oneStack).pop());
            prevFrom = oneStack;
            prevTo = twoStack;                    
        }

        else if (!twoEmpty && !oneEmpty) {
            int o = list.get(oneStack).peek();
            int t = list.get(twoStack).peek();

            if (o > t) {
                list.get(oneStack).push(list.get(twoStack).pop());
                prevTo = oneStack;
                prevFrom = twoStack;
            }
            else {
                list.get(twoStack).push(list.get(oneStack).pop());
                prevTo = twoStack;
                prevFrom = oneStack;                    
            }
        }  
        return new int[]{prevFrom, prevTo};      
    } 
}