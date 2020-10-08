package datastr;
import java.io.*;
import java.util.*;

public class HashTable {

    static public int TABLE_SIZE = 128;
    static Map<Integer, Map<String, HashEntry>> keywordsIndex;
    ;
    ArrayList<String> stopWords;
    ArrayList<String> Allwords;
    static int collisioncounter = 0;
    List<HashEntry> linkedList = new LinkedList<>();

    public HashTable() {
        keywordsIndex = new HashMap<Integer, Map<String, HashEntry>>(128);
        stopWords = new ArrayList<String>(700);
        Allwords = new ArrayList<String>(15000);

    }

    public void makeIndex(String docsFile, String noiseWrds) throws FileNotFoundException {
        Scanner sc = new Scanner(new File(noiseWrds));
        while (sc.hasNext()) {
            String word = sc.next();
            stopWords.add(word);
        }
        String[] splitted;
        String ag;
        try (BufferedReader okuyucu = new BufferedReader(new FileReader("/bbc" + docsFile))) {
            String oankisatir = okuyucu.readLine();
            while (oankisatir != null) {

                String DELIMITERS = "[-+=" +
                        " " +        //space
                        "\r\n " +    //carriage return line fit
                        "1234567890" + //numbers
                        "’'\"" +       // apostrophe
                        "(){}<>\\[\\]" + // brackets
                        ":" +        // colon
                        "," +        // comma
                        "‒–—―" +     // dashes
                        "…" +        // ellipsis
                        "!" +        // exclamation mark
                        "." +        // full stop/period
                        "«»" +       // guillemets
                        "-‐" +       // hyphen
                        "?" +        // question mark
                        "‘’“”" +     // quotation marks
                        ";" +        // semicolon
                        "/" +        // slash/stroke
                        "⁄" +        // solidus
                        "␠" +        // space?
                        "·" +        // interpunct
                        "&" +        // ampersand
                        "@" +        // at sign
                        "*" +        // asterisk
                        "\\" +       // backslash
                        "•" +        // bullet
                        "^" +        // caret
                        "¤¢$€£¥₩₪" + // currency
                        "†‡" +       // dagger
                        "°" +        // degree
                        "¡" +        // inverted exclamation point
                        "¿" +        // inverted question mark
                        "¬" +        // negation
                        "#" +        // number sign (hashtag)
                        "№" +        // numero sign ()
                        "%‰‱" +      // percent and related signs
                        "¶" +        // pilcrow
                        "′" +        // prime
                        "§" +        // section sign
                        "~" +        // tilde/swung dash
                        "¨" +        // umlaut/diaeresis
                        "_" +        // underscore/understrike
                        "|¦" +       // vertical/pipe/broken bar
                        "⁂" +        // asterism
                        "☞" +        // index/fist
                        "∴" +        // therefore sign
                        "‽" +        // interrobang
                        "※" +          // reference mark
                        "]";

                splitted = oankisatir.toLowerCase().split(DELIMITERS);

                for (int i = 0; i < splitted.length; i++) {
                    System.out.println(splitted[i]);
                    Allwords.add(splitted[i]);
                }


                oankisatir = okuyucu.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Allwords.removeAll(stopWords);

        HashMap<String, HashEntry> indexlenmisolan = loadingKeyWords(Allwords, docsFile);//anahtarı key wordler
        birlestirici(indexlenmisolan);


    }

    private HashMap<String, HashEntry> loadingKeyWords(ArrayList<String> Allwords, String docsFile) throws FileNotFoundException {
        HashMap<String, HashEntry> loader = new HashMap<String, HashEntry>();
        Iterator<String> itr = Allwords.iterator();
        while (itr.hasNext()) {
            String keyWord = (itr.next());
            if (loader.containsKey(keyWord) && loader.get(keyWord).dosya.equals(docsFile)) {
                loader.get(keyWord).sayisi++;
            } else {
                HashEntry hshentry = new HashEntry(docsFile, 1);
                loader.put(keyWord, hshentry);
            }
        }
        return loader;
    }

    public Map<Integer, Map<String, HashEntry>> birlestirici(HashMap<String, HashEntry> indexlenmisolan) {
        for (Map.Entry<String, HashEntry> entry : indexlenmisolan.entrySet()) {
            Integer gecici = hashFunction(entry.getKey());
            String emt = entry.getKey();
        }
        return keywordsIndex;
    }


    public int hashFunction(String Key) {
        //Horner's rule
        int hash = 7;
        String key2 = Key.toLowerCase();
        for (int i = 0; i < Key.length(); i++) {
            hash = ((hash * 31 + key2.charAt(i)) % TABLE_SIZE);// sayıların çok büyümemesi için her harfte modulus işlemi yapmayı amaçladım.
        }
        return hash;

        //Simple Summation Function
                /* int hash =0;
                String key2 =key.toLowerCase();
                for (int i = 0; i < key.length(); i++) {
                hash = ((hash + key2.charAt(i))/keywordsIndex.size());
                return hash;}*/

    }


    public Map<String, HashEntry> get(String Key) {
        int hash1 = hashFunction(Key);
        if (keywordsIndex.get(hash1) != null) {
            System.out.println("Search: " + Key);
            return keywordsIndex.get(hash1);
        } else {
            System.out.println("Search: " + Key);
            System.out.println("This position is clear(null). NOT FOUND !");
            return keywordsIndex.get(null);
        }
    }

    public void put(String key, String docsFile) {
        String a = key.toLowerCase();
        int hash = hashFunction(a);
        resize(TABLE_SIZE);
        if (keywordsIndex.containsKey(hash) && (keywordsIndex.get(hash).equals(a))) {
            keywordsIndex.get(key);
            System.out.println("There is a collision!");
            collisioncounter++;
            int newhash=LP(key,hash);
            while (!(keywordsIndex.containsKey(newhash) && (keywordsIndex.get(hash).equals(a)))){
                    LP(key,newhash);
            }HashEntry hshentry1 = new HashEntry(docsFile,1);
            keywordsIndex.put(newhash,hshentry1);
        } else {
            HashEntry hshentry = new HashEntry(docsFile, 1);
            keywordsIndex.put(hash, hshentry);

        }
    }

    public void remove(String key) {
        String a = key.toLowerCase();
        int hash = hashFunction(a);
        if (keywordsIndex.containsKey(hash) && (keywordsIndex.get(hash).equals(a))) {
            keywordsIndex.remove(key);

        }
    }

    public static boolean isPrime(int num){
        for(int i =2; i*i <= num;i++){
            if(num % i == 0){
                return false;
            }
        }
        return true;
    }

    public static int getNextPrime(int minNumber){
        for(int i = minNumber; true; i++ ){
            if(isPrime(i)){
                return i;
            }
        }
    }
    public static int resize(int e) {
        if(isPrime(e)) {
            return TABLE_SIZE = e;
        }else{
            int primeCounter= getNextPrime(e);
            return TABLE_SIZE;
        }
    }


    private int LP(String key, int hashnum) {
        int hash = 7;
        String key2 = key.toLowerCase();
        for (int i = 0; i < key.length(); i++) {
            hash = ((hash * 31 + key2.charAt(i)) / TABLE_SIZE);
        }
        return hash;
    }

    public int doublehasing1func(String key) {
        int hashvalue = hashFunction(key);
        hashvalue = hashvalue % TABLE_SIZE;
        if (hashvalue < 0) {
            hashvalue += TABLE_SIZE;
        }
        return hashvalue;
    }

    public int doublehasing2func(String key) {
        int hashvalue = hashFunction(key);
        hashvalue = hashvalue % TABLE_SIZE;
        if (hashvalue < 0) {
            hashvalue += TABLE_SIZE;
        }
        return 3 - hashvalue % 3;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HashTable)) return false;
        HashTable hashTable = (HashTable) o;
        return TABLE_SIZE == hashTable.TABLE_SIZE &&
                Objects.equals(keywordsIndex, hashTable.keywordsIndex) &&
                Objects.equals(stopWords, hashTable.stopWords) &&
                Objects.equals(Allwords, hashTable.Allwords);
    }

    @Override
    public int hashCode() {
        return Objects.hash(TABLE_SIZE, keywordsIndex, stopWords, Allwords);
    }
}