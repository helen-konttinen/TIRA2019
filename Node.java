public class Node {

   public Node (int d, int s, int r) {
     data = d;
     source = s;
     rivinumero = r;
     amount = 1;
  }
  
  /* setterit */
  public void data (int d) {
     data = d;
  }
  
  public void amount (int a) {
     amount = a;
  }

  /* getterit */
  public int data () {
     return data;
  }

  public int source () {
     return source;
  }

  public int rivi () {
     return rivinumero;
  }

  public int amount () {
     return amount;
  }
  
  private int data;    // solmun tietosisältö
  private int source; // xor operaatio  n apuarvo
  private int rivinumero; //and opraation apuarvo
  private int amount; //or operaation apuarvo
}