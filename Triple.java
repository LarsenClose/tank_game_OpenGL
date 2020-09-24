import java.nio.FloatBuffer;

public class Triple {
  public double x, y, z;

  public Triple(double a, double b, double c) {
    x = a;
    y = b;
    z = c;
  }

  public void sendData(FloatBuffer buff) {
    buff.put((float) x);
    buff.put((float) y);
    buff.put((float) z);
  }
}
