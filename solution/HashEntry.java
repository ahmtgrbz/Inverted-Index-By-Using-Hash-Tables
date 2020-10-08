package datastr;

import java.util.*;

public class HashEntry implements Map<String, HashEntry> {
    String dosya;
    int sayisi;

    public HashEntry(String dosyaadi, int sayi) {

        dosyaadi = dosya;
        sayi = sayisi;
    }

    public String toString() {
       return "(" + dosya + "," + sayisi + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HashEntry)) return false;
        HashEntry hashEntry = (HashEntry) o;
        return sayisi == hashEntry.sayisi &&
                dosya.equals(hashEntry.dosya);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dosya, sayisi);
    }

    public String getKey() {
        return dosya;
    }
    public int getValue() {
        return sayisi;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean containsKey(Object key) {
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        return false;
    }

    @Override
    public HashEntry get(Object key) {
        return null;
    }

    @Override
    public HashEntry put(String key, HashEntry value) {
        return null;
    }

    @Override
    public HashEntry remove(Object key) {
        return null;
    }

    @Override
    public void putAll(Map<? extends String, ? extends HashEntry> m) {

    }

    @Override
    public void clear() {

    }

    @Override
    public Set<String> keySet() {
        return null;
    }

    @Override
    public Collection<HashEntry> values() {
        return null;
    }

    @Override
    public Set<Entry<String, HashEntry>> entrySet() {
        return null;
    }
}

