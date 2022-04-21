import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class HuffmanEnconding {
    public static void main(String[] args) {
        Map<Character, Integer> symbols = new HashMap<Character, Integer>();
        String text = "Hello this is a test text to test the huffman encoding on for testing purposes";
        text.toLowerCase();

        //Iterate over the text to evaluate every single symbol
        for(Character c : text.toCharArray()) {
            symbols.put(c, symbols.containsKey(c) ? symbols.get(c) + 1 : 1);
        }
        List<Map.Entry<Character, Integer>> characters = symbols.entrySet().stream().sorted(Map.Entry.comparingByValue()).collect(Collectors.toList());
        Integer depth = sqrt(characters.size());
        //Write out the contents of symbols map
        for(Map.Entry<Character, Integer> entry : characters) {
            System.out.println(entry.getKey() + "/" + entry.getValue());
        }
    }
}