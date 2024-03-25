package TowerOfHanoi;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class TowerOfHanoiNew {
    public static void main(String[] args) throws IOException {
        solve(4);
    }
    
    public static void solve(int n) throws IOException {
        List<Stack<Integer>> l1 = new ArrayList<Stack<Integer>>();
        Stack<Integer> from = new Stack<Integer>();
        Stack<Integer> other = new Stack<Integer>();
        Stack<Integer> to = new Stack<Integer>();   

        for (int i = n; i > 0; i--) {
            from.push(i);
        }

        Path dbPath = Paths.get("TowerOfHanoi/Files/OutputNew.txt");
        BufferedWriter bw = Files.newBufferedWriter(
            dbPath, 
            StandardCharsets.UTF_8);     
        l1.add(from);
        l1.add(other);
        l1.add(to);
        
        //Move n-1 blocks to the "other" stack
        groupMove(n-1, 0, 1, 2, l1, bw);

        //Move the n block to the "to" stack
        if (to.isEmpty() && from.size() == 1) {
            singleMove(0, 2, l1, bw);
        }

        //Move the n-1 blocks from the "other" stack to the "to" stack
        groupMove(n-1, 1, 2, 0, l1, bw);
        bw.close();
    }

    /** Move a single block and write it in the file
     * 
     * @param from the stack where the block is moving from
     * @param to the stack where the block is moving to
     * @param l the list of stacks
     * @param bw BufferedWriter to write in file
     * @throws IOException
     */
    public static void singleMove(int from, int to, List<Stack<Integer>> l, BufferedWriter bw) throws IOException {
        l.get(to).push(l.get(from).pop());
        System.out.println(from + ">" + to);
        writeTo(from, to, bw);
        printStacks(l);
    }

    /** Write to the file
     * 
     * @param from
     * @param to
     * @param bw
     * @throws IOException
     */
    public static void writeTo(int from, int to, BufferedWriter bw) throws IOException{
        bw.write(from + ">" + to + "\n");
    }


    public static void groupMove(int n, int from, int to, int other, List<Stack<Integer>> l, BufferedWriter bw) throws IOException {
        //If there is only one block in the stack, simply move it using singleMove
        if (n == 1) {
            singleMove(from, to, l, bw);
        }

        //Shuffle the blocks with recursion by sending them from "from" to "other" and then "other" to "to"
        else if (n > 1) {
            groupMove(n-1, from, other, to, l, bw);
            singleMove(from, to, l, bw);
            groupMove(n-1, other, to, from, l, bw);
        }
    }

    /** Prints out the stacks in the console
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
}
