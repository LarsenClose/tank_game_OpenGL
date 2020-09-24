import java.nio.FloatBuffer;

public class Mine extends Box {
  public double x;
  public double y;
  public double z;

  public Float width;
  public Float height;

  public Triple color;
  public Triple center;

  private Triangle a, b;

  public Mine(Triple center) {
    this.x = center.x;
    this.y = center.y;
    this.z = center.z;

    this.width = Constants.mine_dimensions;
    this.height = Constants.mine_dimensions;
    this.color = Colors.pink;

    buildTriangles(center, this.color);
  }

  public void sendData(FloatBuffer pb, FloatBuffer cb) {
    this.a.a.sendData(pb, cb);
    this.a.b.sendData(pb, cb);
    this.a.c.sendData(pb, cb);
    this.b.a.sendData(pb, cb);
    this.b.b.sendData(pb, cb);
    this.b.c.sendData(pb, cb);
  }

  public void buildTriangles(Triple location, Triple color) {
    this.x = location.x;
    this.y = location.y;
    this.color = color;

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
