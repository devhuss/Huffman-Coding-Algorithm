// Hussein Mohamoud
// CS 463-01: Algorithms
// Homework 7: Huffman Coding Algorithm
// May 6th, 2021

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.HashMap;
import java.util.Map;


class Tree {
    public final int data;
    public final Tree leftChild;
    public final Tree rightChild;
    public final Character ch;

    //constructor
    private Tree(int d, Character c,  Tree left, Tree right) {
        data = d;
        leftChild = left;
        rightChild = right;
        ch = c;
    }

    static Tree leaf(char c, int f) {
        return new Tree(f, c, null, null);
    }

    static Tree combine(Tree a, Tree b) {
        return new Tree(a.data + b.data, null, a, b);
    }

    public boolean isLeaf() {return leftChild == null && rightChild == null;}




}




public class Huff {
    private static void printString(Map<Character, String> data, Tree root, String string) {
        if (root.isLeaf()) {
            data.put(root.ch, string);
            return;
        }
        printString(data, root.leftChild, string + "0");
        printString(data, root.rightChild, string + "1");
    }

    public Map<Character, String> compute_coding(Map<Character, Integer> character_counts) {

        PriorityQueue<Tree> queue = new PriorityQueue<Tree>(character_counts.size(), Comparator.comparingInt(l -> l.data));

        for (Character ch : character_counts.keySet()) {
            Integer freq = character_counts.get(ch);
            queue.add(Tree.leaf(ch, freq));
        }

        Tree root = null;

        while (queue.size() > 1) {
            Tree x = queue.peek();
            x = queue.poll();

            Tree y = queue.peek();
            y = queue.poll();

            Tree tree = Tree.combine(x, y);
            root = tree;
            queue.add(tree);
        }

        HashMap<Character, String> code_word = new HashMap<Character, String>();
        printString(code_word, root, ": ");

        return code_word;
    }


    public static void main(String[] args) {
        Map<Character, Integer> freqs = new HashMap<Character, Integer>(){{
            put('a', 15);
            put('e', 7);
            put('i', 30);
            put('o', 120);
            put('u', 11);
        }};
        Map<Character, String> codes = new Huff().compute_coding(freqs);
        for (Character ch : freqs.keySet()) {
            String code_word = codes.get(ch);
            System.out.println(ch + "" + code_word);
        }
    }
}