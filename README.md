### How to Run
1. Compile the code by running: javac *.java
2. Then Generate the Keys w/ either key size or p,q,e
3. 

### **To Generate Keys** (Note, you will get a pub_key.txt and pri_key.txt generated) 
1. Method One: java RSAGenKey 12
- This is when you give one argument k and the program wiill randomly pick p and q in k bits and generate the public and private key pairs.

2. Method Two: java RSAGenKey 6551 4733 8311
- This is when you give the values for (p,q,e)
- The program will should generate the corresponding key pairs

