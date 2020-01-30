package Harjoitustyo.Tietoluokat;

public class Lokero {

   public Lokero (int i, Node d) {
         indeksi = i;
         data = d;
         seuraava = edellinen = null;
         mod = 0;
      }
      
      /* setters */
      public void indeksi (int i) {
         indeksi = i;
      }
      
      public void data (Node d) {
         data = d;
      }
      
      public void seuraava (Lokero s){
         seuraava = s;
      }
      
      public void edellinen (Lokero e) {
         edellinen = e;
      }
      
      public void mod (int m) {
         mod = m;
      }
      
      /* getters */
      public int indeksi () {
         return indeksi;
      }
      
      public Node data () {
         return data;
      }
      
      public Lokero seuraava () {
         return seuraava;
      }

      public Lokero edellinen () {
         return edellinen;
      }
      
      public int mod () {
         return mod;
      }
      
      
      private int indeksi;
      private Node data;
      private Lokero seuraava;
      private Lokero edellinen;
      private int mod;  // k�ytet��n indeksin apuna alkion uudelleensijoitukseen
}