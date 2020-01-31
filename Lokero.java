public class Lokero {

   public Lokero (int i, Node n) {
         indeksi = i;
         node = n;
         seuraava = edellinen = null;
      }
      
      /* setterit */
      public void indeksi (int i) {
         indeksi = i;
      }
      
      public void node (Node n) {
         node = n;
      }
      
      public void seuraava (Lokero s){
         seuraava = s;
      }
      
      public void edellinen (Lokero e) {
         edellinen = e;
      }
      
      /* getterit */
      public int indeksi () {
         return indeksi;
      }

      public Node node () {
         return node;
      }
      
      public Lokero seuraava () {
         return seuraava;
      }

      public Lokero edellinen () {
         return edellinen;
      }
      
      private int indeksi;
      private Node node;
      private Lokero seuraava;
      private Lokero edellinen;
}