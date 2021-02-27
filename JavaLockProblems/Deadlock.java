public class Deadlock {
  static class Friend {
    private final String name;

    public Friend(String name) {
      this.name = name;
    }

    public String getName() {
      return this.name;
    }

    public synchronized void selamVer(Friend dost) {
      System.out.format("%s: %s" + "  bana selam verdi!%n", this.name, dost.getName());
      dost.selamAl(this);
    }

    public synchronized void selamAl(Friend dost) {
      System.out.format("%s: %s" + " selamımı aldı!%n", this.name, dost.getName());
    }
  }

  public static void main(String[] args) {
    final Friend ali = new Friend("Ali");
    final Friend veli = new Friend("Veli");
    new Thread(new Runnable() {
      public void run() {
        ali.selamVer(veli);
      }
    }).start();
    new Thread(new Runnable() {
      public void run() {
        veli.selamVer(ali);
      }
    }).start();
  }
}