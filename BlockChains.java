/* Online Java Compiler and Editor */
import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

class Blocks {
    String data;
    String hash;
    String prev_hash;
    Blocks(String data,String hash,String prev_hash) {
        this.data=data;
        this.hash=hash;
        this.prev_hash=prev_hash;
    }
}
class BlockChain {
    ArrayList<Blocks> arr=new ArrayList<>();
    public String generate(String data) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(data.getBytes());

            // Convert the hash to a hexadecimal representation
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            String hashValue = hexString.toString();
            return hashValue;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "error";
    }
    BlockChain() {
        String hashStart=generate("gen_start");
        String hashLast=generate("gen_last");

        Blocks b=new Blocks("gen-data",hashStart,hashLast);
        arr.add(b);
    }

    public void add(String data) {
        String prev=arr.get(arr.size()-1).hash;
        String hash=generate(data+prev);
        Blocks block=new Blocks(data,hash,prev);
        arr.add(block);

    }
}
public class  BlockChains{

    public static void main(String []args) {
        BlockChain bl=new BlockChain();
        bl.add("1");
        bl.add("2");
        bl.add("3");
        //String data = "This is the data to be hashed";


        for(Blocks b:bl.arr) {

            System.out.print(b.data +" "+b.hash+" "+b.prev_hash);


            System.out.println();
        }

    }
}
