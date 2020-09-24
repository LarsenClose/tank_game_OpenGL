import java.awt.Point;
import java.lang.Math;
import java.nio.FloatBuffer;

public class Tank extends Box {
  public String kind;
  public float speed;
  public Float bearing;
  public Float width;
  public Float height;

  public float ammunition;

  public Point tank_point;
  public Point origin;

  // center
  public double x;
  public double y;
  public double z;

  public boolean is_red;

  public Triple color;

  private Triangle a, b;

  public Tank(Triple centered, Triple colors, boolean is_red) {
    this.kind = "tank";
    this.speed = 0;

    this.x = centered.x;
    this.y = centered.y;
    this.z = centered.z;

    this.color = colors;

    this.is_red = is_red;

    this.ammunition = Constants.starting_ammunition;
    this.width = Constants.tank_width;
    this.height = Constants.tank_height;

    buildTriangles(centered);

    this.tank_point = new Point((int) (this.x * 10), (int) (this.y * 10));
    this.origin = new Point(0, 0);

    float starting_bearing = (float) getAngle(this.x, this.y, 0d, 0d);

    this.bearing = starting_bearing;
  }

  public double getAngle(double x1, double y1, double x2, double y2) {
    double angle = Math.toDegrees(Math.atan2(x2 - x1, y2 - y1));
    // Keep angle between 0 and 360
    angle = angle + Math.ceil(-angle / 360) * 360;

    return angle;
  }

  public void sendData(FloatBuffer pb, FloatBuffer cb) {
    a.a.sendData(pb, cb);
    a.b.sendData(pb, cb);
    a.c.sendData(pb, cb);
    b.a.sendData(pb, cb);
    b.b.sendData(pb, cb);
    b.c.sendData(pb, cb);
  }

  public void updateLocation(Double x, Double y) {
    buildTriangles(new Triple(x, y, 0));
  }

  public void buildTriangles(Triple location) {
    this.x = location.x;
    this.y = location.y;

    this.a =
      new Triangle(
        new Vertex(
          new Triple((this.x + this.width), (this.y + this.height), this.z),
          this.color
        ),
        new Vertex(
          new Triple((this.x - this.width), (this.y - this.height), this.z),
          this.color
        ),
        new Vertex(
          new Triple((this.x + this.width), (this.y - this.height), this.z),
          this.color
        )
      );

    this.b =
      new Triangle(
        new Vertex(
          new Triple((this.x + this.width), (this.y + this.height), this.z),
          this.color
        ),
        new Vertex(
          new Triple((this.x - this.width), (this.y - this.height), this.z),
          this.color
        ),
        new Vertex(
          new Triple((this.x - this.width), (this.y + this.height), this.z),
          this.color
        )
      );
  }
}
