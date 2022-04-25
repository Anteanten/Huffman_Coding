import java.util.HashMap;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

class HuffmanEnconding {
    static class Node {
        public Node child0, child1;
        public Character symbol;
        public Integer value;
        Node(Node child0, Node child1, Character symbol, Integer value) {
            this.child0 = child0;
            this.child1 = child1;
            this.symbol = symbol;
            this.value = value;
        }
        Node(Node child0, Node child1) {
            this.child0 = child0;
            this.child1 = child1;
            this.value = child0.value + child1.value;
        }
    }

    static String treeWrite(Node node, String code) {
        String write = "";
        if(node.child0 != null) {
            write += code + "[label=" + (node.child0.value + node.child1.value) + "];\n";
            write += code + "->" + code + "0;\n";
            write += treeWrite(node.child0, code + "0");
            write += code + "->" + code + "1;\n";
            write += treeWrite(node.child1, code + "1");
        } else {
            write += code + "->" + node.symbol + ";\n";
        }
        return write;
    }

    public static void main(String[] args) {
        Map<Character, Integer> symbols = new HashMap<Character, Integer>();
        String text = "testingtestingthisisatestforthistestingtestyousee";
        text.toLowerCase();

        //Iterate over the text to evaluate every single symbol
        for(Character c : text.toCharArray()) {
            symbols.put(c, symbols.containsKey(c) ? symbols.get(c) + 1 : 1);
        }
        ArrayList<Node> tree = new ArrayList<Node>();
        for(Map.Entry<Character, Integer> c : symbols.entrySet()) {
            tree.add(new Node(null, null, c.getKey(), c.getValue()));
        }

        for(Node n : tree) {
            System.out.println(n.symbol + "/" + n.value);
        }
        
        while(tree.size() > 1) {
            //Index denoting the smallest node
            Node smallest = tree.get(0);
            Node secondSmallest = tree.get(1);
            //If index 1 is smaller than index 0 switch places
            if(smallest.value < secondSmallest.value) {
                smallest = tree.get(1); 
                secondSmallest = tree.get(0);
            }
            for(int i = 2; i < tree.size(); i++) {
                if(tree.get(i).value <= smallest.value) {
                    secondSmallest = smallest;
                    smallest = tree.get(i);
                }
            }
            tree.add(new Node(smallest, secondSmallest));
            tree.remove(smallest);
            tree.remove(secondSmallest);
        }
        

        //Write to a dot file
        String write = "digraph G {\n";
        write += "Start -> 0;\n" + treeWrite(tree.get(0).child0, "0");
        write += "\n";
        write += "Start -> 1;\n" + treeWrite(tree.get(0).child1, "1");
        write += "}";
        try{
        FileWriter file = new FileWriter("graph.dot");
        file.write(write);
        file.close();
        }
        catch (IOException e){
            System.out.println(e.toString());
        }
    }
}