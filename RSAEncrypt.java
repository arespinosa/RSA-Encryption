import java.util.*;
import java.io.FileWriter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.math.BigInteger;
import java.util.HashMap;

public class RSAEncrypt {

    // Empty Constructor to be used for main 
    public RSAEncrypt(){

    }

    public void RSAEncrypt(String inputFile, String keyFile) {
        // First we are creating a HashMap, that will store all the words and associated number 
        HashMap<String, String> words = new HashMap<String, String>();
        words.put("a", "00");
        words.put("b", "01");
        words.put("c", "02");
        words.put("d", "03");
        words.put("e", "04");
        words.put("f", "05");
        words.put("g", "06");
        words.put("h", "07");
        words.put("i", "08");
        words.put("j", "09");
        words.put("k", "10");
        words.put("l", "11");
        words.put("m", "12");
        words.put("n", "13");
        words.put("o", "14");
        words.put("p", "15");
        words.put("q", "16");
        words.put("r", "17");
        words.put("s", "18");
        words.put("t", "19");
        words.put("u", "20");
        words.put("v", "21");
        words.put("w", "22");
        words.put("x", "23");
        words.put("y", "24");
        words.put("z", "25");
        words.put(" ", "26");
        words.put(".", "27");
        words.put(",", "28");
        words.put("\n", "29");
        //First step is to extract the values from the KeyFile 
        BigInteger e = null;
        BigInteger n = null;

        try (BufferedReader br = new BufferedReader(new FileReader(keyFile))) {
            String line = br.readLine();
            while (line != null) {
                line = line.trim();
                if (line.startsWith("e")) {
                    String value = line.split("=")[1].trim();
                    e = new BigInteger(value);
                } else if (line.startsWith("n")) {
                    String value = line.split("=")[1].trim();
                    n = new BigInteger(value);
                }

                line = br.readLine();
            }
        }
        catch(IOException err){
            err.printStackTrace();
        }

        //Step 2. Begin encrypting every three blocks 
        String outputFile = "test.enc";
        int blockSize = 3;
        
        // Encrypting each block of size 3 
        try (BufferedReader br = new BufferedReader(new FileReader(inputFile));
        FileWriter fw = new FileWriter(outputFile)) {
   
        int character = br.read();
        String encryptNum = "";
        int count = 0;
    
        while(character != -1) {
            char temp = Character.toLowerCase((char) character);
            String mapped = words.get(String.valueOf(temp));
    
            if(mapped != null){
                encryptNum += mapped;
                count++;
            }
    
            if(count == 3){
                BigInteger P = new BigInteger(encryptNum);
                // C = P^e % n
                BigInteger C = P.modPow(e, n);
                fw.write(C.toString() + " ");
                encryptNum = "";
                count = 0;
            }

            character = br.read();
        }
    
        // In case our last block is not of size 3, we do this 
        if(!encryptNum.isEmpty()){
            BigInteger P = new BigInteger(encryptNum);
            BigInteger C = P.modPow(e, n);
            fw.write(C.toString() + " ");
        }
    
    } catch(IOException err){
        err.printStackTrace();
    }
}
    
    public static void main(String[] args) {
        RSAEncrypt rsa = new RSAEncrypt();
        rsa.RSAEncrypt(args[0], args[1]);
        System.out.println("test.enc file created!");
    }

}