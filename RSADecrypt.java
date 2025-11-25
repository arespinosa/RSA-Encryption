import java.util.*;
import java.io.FileWriter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.math.BigInteger;
import java.util.HashMap;

public class RSADecrypt{
    // Empty constructor to use for main
    public RSADecrypt(){
    }

    public void RSADecrypt(String ciphertext, String privkeyFile){
        HashMap<String, String> revwords = new HashMap<String, String>();
        revwords.put("00", "a");
        revwords.put("01", "b");
        revwords.put("02", "c");
        revwords.put("03", "d");
        revwords.put("04", "e");
        revwords.put("05", "f");
        revwords.put("06", "g");
        revwords.put("07", "h");
        revwords.put("08", "i");
        revwords.put("09", "j");
        revwords.put("10", "k");
        revwords.put("11", "l");
        revwords.put("12", "m");
        revwords.put("13", "n");
        revwords.put("14", "o");
        revwords.put("15", "p");
        revwords.put("16", "q");
        revwords.put("17", "r");
        revwords.put("18", "s");
        revwords.put("19", "t");
        revwords.put("20", "u");
        revwords.put("21", "v");
        revwords.put("22", "w");
        revwords.put("23", "x");
        revwords.put("24", "y");
        revwords.put("25", "z");
        revwords.put("26", " ");
        revwords.put("27", ".");
        revwords.put("28", ",");
        revwords.put("29", "\n");

        //First step is to extract the values from the privateKeytext
        BigInteger d = null;
        BigInteger n = null;

        try (BufferedReader br = new BufferedReader(new FileReader(privkeyFile))) {
            String line = br.readLine();
            while (line != null) {
                line = line.trim();
                if (line.startsWith("d")) {
                    String value = line.split("=")[1].trim();
                    d = new BigInteger(value);
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

        // Second step is to begin the decryption phase 
        String outputFile = "test.dec";
        try (BufferedReader br = new BufferedReader(new FileReader(ciphertext))) {
            FileWriter fw = new FileWriter(outputFile);
            String line = br.readLine();
            String[] cipherStrings = line.split("\\s+"); // split on any whitespace

            for(String encryptBlock: cipherStrings){
                BigInteger temp = new BigInteger(encryptBlock);
                BigInteger P = temp.modPow(d, n);
            
                // calculate expected length in digits
                int expectedLength = 6; // 3 chars * 2 digits per char
                String result = P.toString();
            
                // pad leading zeros if needed
                while(result.length() < expectedLength){
                    result = "0" + result;
                }
            
                StringBuilder plaintext = new StringBuilder();
                for(int i = 0; i < result.length(); i += 2){
                    String lookup = result.substring(i, i+2);
                    plaintext.append(revwords.get(lookup));
                }
            
                fw.write(plaintext.toString());
            }

            fw.close();
        }
        catch(IOException err){
            err.printStackTrace();
        }
    }   
    public static void main(String[] args){
        RSADecrypt rsa = new RSADecrypt();
        rsa.RSADecrypt(args[0], args[1]);
        System.out.println("test.dec file created!");
    }
}