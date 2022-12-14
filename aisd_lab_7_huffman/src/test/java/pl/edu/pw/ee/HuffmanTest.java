package pl.edu.pw.ee;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class HuffmanTest {

    @Test
    public void compressTest() {
        Huffman huffman = new Huffman();
        int minimalNumberOfBitesInNiemanie = 9339;
        int result = huffman.huffman(
                "D:\\Studia\\Semestr III\\AISD\\laboratoria\\2021Z_AISD_lab_git_aisd_2021_3_gr10\\aisd_lab_7_huffman\\",
                true);
    }

    @Test
    public void decompressTest() {
        Huffman huffman = new Huffman();
        int minimalNumberOfBitesInNiemanie = 9339;
        int result = huffman.huffman(
                "D:\\Studia\\Semestr III\\AISD\\laboratoria\\2021Z_AISD_lab_git_aisd_2021_3_gr10\\aisd_lab_7_huffman\\",
                false);
    }

    @Test
    public void niemanieCompressTest() {
        Huffman huffman = new Huffman();
        int minimalNumberOfBitesInNiemanie = 9339;
        int result = huffman.huffman(
                "D:\\Studia\\Semestr III\\AISD\\laboratoria\\2021Z_AISD_lab_git_aisd_2021_3_gr10\\aisd_lab_7_huffman\\",
                true);
        assertEquals(minimalNumberOfBitesInNiemanie, result);
    }

    @Test
    public void niemanieDecompressTest() {
        Huffman huffman = new Huffman();
        int numberOfCharactersInNiemanie = 1966;
        int result = huffman.huffman(
                "D:\\Studia\\Semestr III\\AISD\\laboratoria\\2021Z_AISD_lab_git_aisd_2021_3_gr10\\aisd_lab_7_huffman\\",
                false);
        assertEquals(numberOfCharactersInNiemanie, result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void nullDirectoryTest() {
        Huffman huffman = new Huffman();
        assertEquals(0, huffman.huffman(null, true));
        assertEquals(0, huffman.huffman(null, false));
        assert false;
    }

    @Test(expected = IllegalArgumentException.class)
    public void notExistingDirectoryTest() {
        Huffman huffman = new Huffman();
        assertEquals(0, huffman.huffman("D:\\notExistingDir\\", true));
        assertEquals(0, huffman.huffman("D:\\notExistingDir\\", false));
        assert false;
    }

    @Test(expected = IllegalArgumentException.class)
    public void fileInsteadOfDirectoryTest() {
        Huffman huffman = new Huffman();
        String dir = "D:\\Studia\\Semestr III\\AISD\\laboratoria\\2021Z_AISD_lab_git_aisd_2021_3_gr10\\aisd_lab_7_huffman\\emptyFile.txt\\";
        huffman.huffman(dir, true);
        huffman.huffman(dir, false);
        assert false;
    }
}
