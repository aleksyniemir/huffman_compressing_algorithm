package pl.edu.pw.ee;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class Huffman {

    private int nUniqueChars = 0, i = 0, lenghtOfBinaryCode = 0;
    private ArrayList<Integer> listOfCharacters = new ArrayList<>();
    private ArrayList<Integer> listOfBinaryCode = new ArrayList<>();
    private BinaryNode[] dictionary;
    private int numberOfCharactersInCompressedFile = 0, numberOfCharactersInDecompressedFile = 0;

    public int huffman(String pathToRootDir, boolean compress) {
        if (pathToRootDir == null) {
            throw new IllegalArgumentException("Path to directory cannot be null!");
        }
        if (compress) {
            readFromDecompressedFile(pathToRootDir, compress);
            compressFile(pathToRootDir + "compressedFile.txt", pathToRootDir + "dictionary.txt", createADictionary(createHeap(createForest())));
            return this.numberOfCharactersInCompressedFile; 
        } else {
            readDictionary(pathToRootDir + "dictionary.txt");
            loadFileToDecompress(pathToRootDir + "compressedFile.txt");
            decompressFile(pathToRootDir + "decompressedFile.txt");
            return this.numberOfCharactersInDecompressedFile;
        }
    }

    private void readFromDecompressedFile(String pathToRootDir, boolean compress) {
        if (Files.notExists(Paths.get(pathToRootDir))) {
            throw new IllegalArgumentException("Path to the directory must be valid!");
        }
        try {
            String pathToFile = pathToRootDir + "decompressedFile.txt";
            File file = new File(pathToFile);
            if (!file.exists() || file.isDirectory()) {
                throw new IllegalArgumentException("The file must exists/path cannot lead to a directory!");
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), Charset.forName("UTF-8")));
            int c;
            while((c = reader.read()) != -1) {
                this.listOfCharacters.add(c);
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Problem with the decompressed file!");
        }
    }

    private ArrayList<HuffmanNode> createForest() {
        ArrayList<HuffmanNode> listOfUniqueNodes = new ArrayList<>(); 
        int didIfHappen = 0;
        for (int i = 0; i < this.listOfCharacters.size(); i++) {
            HuffmanNode node = new HuffmanNode(listOfCharacters.get(i), 1, null, null);
            for (int j = 0; j < listOfUniqueNodes.size(); j++) {
                if (listOfUniqueNodes.get(j).equals(node)) {
                    listOfUniqueNodes.get(listOfUniqueNodes.indexOf(node)).increaseFrequency();
                    didIfHappen = 1;
                }
            } 
            if (didIfHappen == 0) {
               listOfUniqueNodes.add(node);
               nUniqueChars++;
            }
            didIfHappen = 0;
        }
        return listOfUniqueNodes;
    }

    private HuffmanNode createHeap(ArrayList<HuffmanNode> nodes) {
        while(nodes.size() > 1) {
            HuffmanNode x = nodes.remove(0);
            HuffmanNode y = nodes.remove(0);
            HuffmanNode parent = new HuffmanNode(-1, x.getFrequency() + y.getFrequency(), x, y);
            nodes.add(parent);
            sort(nodes);
        }
        return nodes.remove(0);
    }

    private BinaryNode[] createADictionary(HuffmanNode root) {
        String s = "", zero = "0", one = "1";
        int binaryLengthLeft = 0, binaryLengthRight = 0;
        this.dictionary = new BinaryNode[nUniqueChars];
        createBinaryCode(root.getLeft(), s, zero, this.dictionary, binaryLengthLeft);
        createBinaryCode(root.getRight(), s, one, this.dictionary, binaryLengthRight); 
        return this.dictionary;
    }

    private void createBinaryCode(HuffmanNode node, String s, String sufiks, BinaryNode[] dictionary, int binaryLength) {
        if (node == null) {
            return;
        }
        s = s + sufiks;
        binaryLength++;
        int binaryLengthLeft = binaryLength, binaryLengthRight = binaryLength;
        if (node.isLeaf()) {
            dictionary[this.i] = new BinaryNode(s, node.getInteger(), binaryLength);
            this.i++;
            return;
        }
        String zero = "0", one = "1";
        createBinaryCode(node.getLeft(), s, zero, dictionary, binaryLengthLeft);
        createBinaryCode(node.getRight(), s, one, dictionary, binaryLengthRight);
    }

    private void compressFile(String pathToWrite, String pathToDictionary, BinaryNode[] dictionary) {
        try {
            File fileToWrite = new File(pathToWrite);
            File fileWithDictionary = new File(pathToDictionary);
            BufferedWriter writerDictionary = new BufferedWriter(new FileWriter(fileWithDictionary));
            while (this.i > 0) {
                writerDictionary.write(dictionary[this.i - 1].getInteger() + ":" + dictionary[this.i - 1].getLengthOfChar() + ":" + Integer.parseInt(dictionary[this.i - 1].getString(), 2) + "\n");
                this.i--;
            }
            writerDictionary.close();
            int m = 0;
            int j = 0; 
            BufferedWriter writerCompressedFile = new BufferedWriter(new FileWriter(fileToWrite));
            while (j < this.listOfCharacters.size()) {
                while (!this.dictionary[m].compareIntegers(listOfCharacters.get(j))) {
                    m++;
                    if (m >= this.dictionary.length) {
                        System.out.println("Reading from dictionary gone wrong!");
                    }
                }
                writerCompressedFile.write(this.dictionary[m].getString());
                numberOfCharactersInCompressedFile += this.dictionary[m].getLengthOfChar();
                j++;
                m = 0;
            }
            writerCompressedFile.close();
        } catch (IOException e) {
            System.out.print("Dictionary file or file to write does not exist!");
        }
    }

    private void readDictionary(String pathToDictionary) {
        try {
            int[] elemsForBinaryNode = new int[3];
            String[] stringElemsForBinaryNode = new String[3];
            File file = new File(pathToDictionary);
            Scanner input = new Scanner(file);
            ArrayList<BinaryNode> listOfNodes = new ArrayList<>();
            input.useDelimiter("\n");
            while (input.hasNext()) {
                stringElemsForBinaryNode = input.next().split(":", 3);
                for (i = 0; i < 3; i++) {
                    elemsForBinaryNode[i] = Integer.parseInt(stringElemsForBinaryNode[i]);
                }
                BinaryNode node = new BinaryNode(Integer.toBinaryString(elemsForBinaryNode[2]), elemsForBinaryNode[0], elemsForBinaryNode[1]);    
                listOfNodes.add(node);
            }
            input.close();
            this.dictionary = new BinaryNode[listOfNodes.size()];
            for (int j = 0; j <  listOfNodes.size(); j++) {
                this.dictionary[j] = listOfNodes.get(j);
            }
            
        } catch (IOException e) {
            System.out.println("Problem with reading from dictionary!");
        }
    }

    private void loadFileToDecompress(String pathToReadFile) {
        try {
            File file = new File(pathToReadFile);
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            int c;
            while((c = reader.read()) != -1) {
                this.lenghtOfBinaryCode++;
                this.listOfBinaryCode.add(c);
            }   
            reader.close();
        }   catch (IOException e) {
            System.out.println("Problem with compressed file!");
        }
    }

    private void decompressFile(String pathToFile) {
        try {
            String s = "", zeroOrOne;
            int indexOfBinaryCode = 0;
            File file = new File(pathToFile);
            OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8);
            while(indexOfBinaryCode < this.lenghtOfBinaryCode) {
                zeroOrOne = (this.listOfBinaryCode.get(indexOfBinaryCode) == 48 ? "0" : "1");
                s = s + zeroOrOne;
                for (int j = 0; j < dictionary.length; j++) {
                    if (dictionary[j].compareStrings(s)) {
                        writer.write((char) dictionary[j].getInteger());
                        this.numberOfCharactersInDecompressedFile++;
                        s = "";
                    }
                }
                indexOfBinaryCode++;
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Problem with dictionary file!");
        }
    }

    private ArrayList<HuffmanNode> sort(ArrayList<HuffmanNode> nodes) {
        int i = 1, j;
        HuffmanNode tmp;
        while (i < nodes.size()) {
            j = i;
            while (j > 0 && nodes.get(j - 1).getFrequency() > nodes.get(j).getFrequency()) {
                tmp = nodes.get(j);
                nodes.set(j, nodes.get(j - 1));
                nodes.set(j - 1, tmp);
                j--;
            }
            i++;
        }
        return nodes;
    }

    public int getNChar() {
        return this.nUniqueChars;
    }

    public int getNumberOfCharactersInCompressedFile() {
        return this.numberOfCharactersInCompressedFile;
    }
}
