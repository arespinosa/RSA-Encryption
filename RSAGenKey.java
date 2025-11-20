import java.util.*;
import java.math.BigInteger;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random; 

/***
 * This file will perform Step 1. of the RSA Algorithm. 
 * That is to generate the public and private key pairs 
 */
public class RSAGenKey {
    // Creating a constructor 
    public RSAGenKey() { 

    }

    /**
     * If only one param is given, the program should
     * randomly picks p and q in k bits and generates a key pair
     * @param k: Key Size
     * @return: File 1: pub_key.txt containing p & q
     * @return: File 2: pri_key.txt containing d & n
     */
    public void RSAGenKey(String K) {
        // Initializing random generator and Constant Value One
        Random random = new Random(); 
        BigInteger One = new BigInteger("1");
        int k = Integer.parseInt(K);

        // Creating p and q to be random primes of k bit length
        BigInteger p = BigInteger.probablePrime(k, random);
        BigInteger q = BigInteger.probablePrime(k, random);
        

        // Edge Case: If p and q are somehow the same number, going to re-randomize q
        boolean Equal = p.equals(q);
        while(Equal){
            q = BigInteger.probablePrime(k, random);
            Equal = p.equals(q);
        }

        // Generated n by (p * q)
        BigInteger n = p.multiply(q);

        // Now to generate Euler(n) which can be (p-1) * (q-1) due Euler's 2nd property
        BigInteger p_1 = p.subtract(One);
        BigInteger q_1 = q.subtract(One);
        BigInteger euler_n = p_1.multiply(q_1);

        // Now, we need to select an e value which should only be used if 
        // gcd(e, euler_n) = 1
        BigInteger e = new BigInteger("3");
        BigInteger gcd_val = e.gcd(euler_n);

        boolean gcd_val_not_one = true;

        // If the gcd is not one, we need to keep adding e by 2 which will contain mostly odds
        // but eventually find a prime that will work 
        if( !(gcd_val.equals(One)) ) {
            while(gcd_val_not_one){
                e = e.add(new BigInteger("2"));
                gcd_val = e.gcd(euler_n);
                if(gcd_val.equals(One)){
                        gcd_val_not_one = false;
                    }
            }
            
        }

        BigInteger d = e.modInverse(euler_n);

        // Now we generate the two files pub_key and priv_key
        try (FileWriter pubKeyWriter = new FileWriter("pub_key.txt")) {
            pubKeyWriter.write("e = " + e.toString() + "\n");
            pubKeyWriter.write("n = " + n.toString() + "\n");
            System.out.println("pub_key.txt generated successfully.");
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        try (FileWriter privKeyWriter = new FileWriter("pri_key.txt")) {
            privKeyWriter.write("d = " + d.toString() + "\n");
            privKeyWriter.write("n = " + n.toString() + "\n");
            System.out.println("pri_key.txt generated successfully.");
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return;

    }

    /**
     * The second method will now take 
     * p, q, and e as the input and should generate the key pairs
     * @param p, q, & e
     * @return: File 1: pub_key.txt containing p & q
     * @return: File 2: pri_key.txt containing d & n
     */

    public void RSAGenKey(String P, String Q, String E) {
        BigInteger One = new BigInteger("1");

        BigInteger p  = new BigInteger(P);
        BigInteger q  = new BigInteger(Q);
        BigInteger e  = new BigInteger(E);


        // Calculating n 
        BigInteger n = p.multiply(q);

        BigInteger p_1 = p.subtract(One);
        BigInteger q_1 = q.subtract(One);
        BigInteger euler_n = p_1.multiply(q_1);

        BigInteger d = e.modInverse(euler_n);

        // Now we generate the two files pub_key and priv_key
        try (FileWriter pubKeyWriter = new FileWriter("pub_key.txt")) {
            pubKeyWriter.write("e = " + e.toString() + "\n");
            pubKeyWriter.write("n = " + n.toString() + "\n");
            System.out.println("pub_key.txt generated successfully.");
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        try (FileWriter privKeyWriter = new FileWriter("pri_key.txt")) {
            privKeyWriter.write("d = " + d.toString() + "\n");
            privKeyWriter.write("n = " + n.toString() + "\n");
            System.out.println("pri_key.txt generated successfully.");
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return;
    }

    public static void main(String[] args) {
        RSAGenKey rsaKey = new RSAGenKey();
        if (args.length == 1) {
            System.out.println("Only given keySize");
            rsaKey.RSAGenKey(args[0]);
           
        }
        else {
            System.out.println("Given p, q, and e values");
            rsaKey.RSAGenKey(args[0], args[1], args[2]);
        }
    }
}