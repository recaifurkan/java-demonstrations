public class Livelock1 {
  public static void main(String[] args) throws InterruptedException {
    Object left = new Object();
    Object right = new Object();
    Pedestrian one = new Pedestrian(left, right, 0); // one's left is one's left
    Pedestrian two = new Pedestrian(right, left, 1); // one's left is two's right, so have to swap order
    one.setOther(two);
    two.setOther(one);
    one.start();
    two.start();
  }
}

class Pedestrian extends Thread {
  private Object l;
  private Object r;
  private Pedestrian other;
  private Object current;

  Pedestrian(Object left, Object right, int firstDirection) {
    l = left;
    r = right;
    if (firstDirection == 0) {
      current = l;
    } else {
      current = r;
    }
  }

  void setOther(Pedestrian otherP) {
    other = otherP;
  }

  Object getDirection() {
    return current;
  }

  Object getOppositeDirection() {
    if (current.equals(l)) {
      return r;
    } else {
      return l;
    }
  }

  void switchDirection() throws InterruptedException {
    Thread.sleep(100);
    current = getOppositeDirection();
    System.out.println(Thread.currentThread().getName() + " is stepping aside.");
  }

  public void run() {
    while (getDirection().equals(other.getDirection())) {
      try {
        switchDirection();
        Thread.sleep(100);
      } catch (InterruptedException e) {
      }
    }
  }
}