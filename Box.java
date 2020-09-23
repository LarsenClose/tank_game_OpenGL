import java.nio.FloatBuffer;

public class Box<T> {
    public Box() {
        System.out.println("Super");
    }

    public String kind;
    public float speed;
    public Float bearing;
    public float width;
    public float height;

    // center
    public double x;
    public double y;
    public double z;

    public Triple color;

    private Triangle a, b;
    private String orientation;

    public Box(Triple centered, String orientation) {

        this.kind = "box";
        this.speed = 0;
        this.bearing = 0f;

        this.color = Colors.black;

        this.speed = 0;

        this.x = centered.x;
        this.y = centered.y;
        this.z = centered.z;

        this.orientation = orientation;

        if (this.orientation == "verticle") {
            this.width = Constants.basic_width;
            this.height = Constants.basic_height;
        } else if (this.orientation == "horizontal") {
            this.height = Constants.basic_width;
            this.width = Constants.basic_height;
        }

        this.a = new Triangle(new Vertex(new Triple((this.x + this.width), (this.y + this.height), this.z), this.color),
                new Vertex(new Triple((this.x - this.width), (this.y - this.height), this.z), this.color),
                new Vertex(new Triple((this.x + this.width), (this.y - this.height), this.z), this.color));

        this.b = new Triangle(new Vertex(new Triple((this.x + this.width), (this.y + this.height), this.z), this.color),
                new Vertex(new Triple((this.x - this.width), (this.y - this.height), this.z), this.color),
                new Vertex(new Triple((this.x - this.width), (this.y + this.height), this.z), this.color));
    }

    public void sendData(FloatBuffer pb, FloatBuffer cb) {
        a.a.sendData(pb, cb);
        a.b.sendData(pb, cb);
        a.c.sendData(pb, cb);
        b.a.sendData(pb, cb);
        b.b.sendData(pb, cb);
        b.c.sendData(pb, cb);
    }

}
