import java.util.ArrayList;
import java.util.Arrays;

public class HuffmanEncodingWithHeap {
    private static final int ASCII8 = 256;
    private static final char LEFT = '0';
    private static final char RIGHT = '1';
    private static final String EMPTY = "";
    private static final int BITS_PER_BYTE = 8;


    /**
     * Parses a string and produce a frequency count for each of its symbols. Counts
     * are stored in an array with a place for each possible ASCII character -- all
     * 26 of them. Many elements in the array will have a frequency of 0 because
     * they correspond to not printable characters.
     *
     * @param message String to parse, one character at a time.
     * @return array with frequency count for each possible ASCII value.
     */
    static public int[] countFrequency(String message) {
        int[] frequencies = new int[ASCII8];
        // Nothing to parse if message is null
        if (message != null) {
            for (int i = 0; i < message.length(); i++) {
                frequencies[(int) message.charAt(i)]++;
            }
        }
        return frequencies;
    } // method countFrequency

    /**
     * Build a Minimum Heap of huffman nodes where each Huffman Node contains
     * an ascii code and its frequency in the String that is to be compressed.
     *
     * @param freq int[] with the frequency counts for each ASCII symbol
     * @return a Min Heap object of Huffman Nodes.
     */
    public static MinHeap<HuffmanNode> buildForest(int[] freq) {
        MinHeap<HuffmanNode> forest = new MinHeap<>();
        for (int asciiCode = 0; asciiCode < freq.length; asciiCode++) {
            if (freq[asciiCode] > 0) {
                HuffmanNode newNode = new HuffmanNode((char) asciiCode, freq[asciiCode]);
                forest.insert(newNode);
            }
        }
        return forest;
    } // method buildForest

    /**
     * Apply Huffman's algorithm to organize a forest of Huffman Nodes into a Huffman tree contained
     * within a Minimum Heap. To achieve this, we remove the two nodes of least frequency from the
     * forest, make them left and right children of a new node with no symbol and a combined
     * frequency of its children and place it in the forest. After each iteration, the forest size
     * reduced by one node. When there is 1 node left in the forest, it is the root of the Huffman
     * tree.
     *
     * @param forest Minimum Heap of Huffman Nodes with symbol and frequency data.
     * @return a HuffmanNode that represents the root of the HuffMan Tree
     */
    public static HuffmanNode buildTree(MinHeap<HuffmanNode> forest) {
        while (forest.size() > 1) {
            HuffmanNode t1 = forest.removeMin();
            HuffmanNode t2 = forest.removeMin();
            HuffmanNode combined = new HuffmanNode(t1.getFrequency() + t2.getFrequency());
            combined.setRight(t1);
            combined.setLeft(t2);
            forest.insert(combined);
        }
        return forest.getMin();
    }
    /**
     * Recursive traversal of a Huffman tree to produce an encoding table.
     *
     * @param node  Huffman tree node to process
     * @param code  encoding value up to the current point in traversal
     * @param codes String[] with Huffman codes produced so far.
     */
    public static void createEncodingTable(HuffmanNode node, String code, String[] codes) {
        // Only process non-null nodes
        if (node != null) {
            // Base case when node has a symbol -- we've reach a leaf node in the tree
            if ((int) node.getSymbol() != 0) {
                codes[(int) node.getSymbol()] = code;
            } else {
                // Recursive case: intermediate nodes; explore their children updating the
                // corresponding code with left and right information
                createEncodingTable(node.getLeft(), code + LEFT, codes);
                createEncodingTable(node.getRight(), code + RIGHT, codes);
            }
        }
    } // method createEncodingTable

    /**
     * Helper method to launch the recursive traversal of the Huffman tree
     *
     * @param node The root of the Huffman tree
     * @return String[] indexed by ASCII values (0-255) containing the Huffman code
     *         for the corresponding character
     */
    public static String[] createEncodingTable(HuffmanNode node) {
        String[] codes = new String[ASCII8];
        createEncodingTable(node, EMPTY, codes);
        return codes;
    } // method createEncodingTable

    /**
     * Prints out the Huffman codes for ASCII symbols in a given message
     *
     * @param codes String[] the Huffman codes indexed by ASCII value
     */
    public static void displayCodes(String[] codes) {
        for (int i = 0; i < codes.length; i++) {
            if (codes[i] != null) {
                System.out.printf("\n '%s' --> %-10s", (char) i, codes[i]);
            }
        }
    } // method displayCodes

    /**
     * Computes the number of bits required for the compressed message
     *
     * @param message String to compress
     * @param codes   Huffman codes for compression
     * @return int with length of compressed message
     */
    public static int computeCompressionLength(String message, String[] codes) {
        int compressionLength = 0;
        for (int i = 0; i < message.length(); i++) {
            compressionLength += codes[message.charAt(i)].length();
        }
        return compressionLength;
    } // method computeCompressionLength

    /**
     * Prints a brief report about the length of the compressed message v. the
     * length of the uncompressed message.
     *
     * @param message String to compress
     * @param codes   Huffman codes for compression
     */
    public static void reportEfficiency(String message, String[] codes) {
        System.out.printf("\nCompressed message requires %d bits versus %d bits for ASCII encoding.\n",
                computeCompressionLength(message, codes), message.length() * BITS_PER_BYTE);
    } // method reportEfficiency

    /**
     * Orchestrate the Huffman encoding of a string
     *
     * @param message String to encode/compress
     */
    static void encode(String message) {
        int[] frequencies = countFrequency(message);
        MinHeap<HuffmanNode> forest = buildForest(frequencies);
        HuffmanNode huffmanTreeRoot = buildTree(forest);
        String[] codes = createEncodingTable(huffmanTreeRoot);
        displayCodes(codes);
        reportEfficiency(message, codes);
    } // method encode

    /** Driver code */
    public static void main(String[] args) {
        String message = "now is the winter of our discontent made glorious by this son of york and all the clouds that lourd over our house in the deep bossom of the ocean lay";
        encode(message);
    } // method main

} // Class HuffmanEncodingWithHeap
