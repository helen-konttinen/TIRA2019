package Harjoitustyo.Tietoluokat;

public class Node {

    public Node (Object d, int source, int r) {
      data = d;
      syotetiedosto = source;
      rivinumero = r;
   }
   
   /* setters */
   public void data (Object d) {
      data = d;
   }
   
   /* getters */
   public Object data () {
      return data;
   }
   
   private Object data;    // solmun tietosis�lt�
   private int syotetiedosto; // xor operaatio  n apuarvo
   private int rivinumero;
}