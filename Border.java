import java.nio.FloatBuffer;

public class Border extends Box {
    public Border() {
        System.out.println("Super");
    }

    private String kind;
    private Triple color;
    private Float width;

    private Vertex upLeft;
    private Vertex upRight;
    private Vertex dnRight;
    private Vertex dnLeft;

    private Vertex northEast;
    private Vertex northWest;

    private Vertex eastSouth;
    private Vertex eastNorth;

    private Vertex southEast;
    private Vertex southWest;

    private Vertex westSouth;
    private Vertex westNorth;

    private Triangle n1, n2, e1, e2, s1, s2, w1, w2;

    public Border(Triple uL, Triple uR, Triple dR, Triple dL) {

        this.kind = "wall";
        this.color = Colors.cyan;
        this.width = Constants.wall_width;

        this.upLeft = new Vertex(uL, this.color);
        this.upRight = new Vertex(uR, this.color);
        this.dnRight = new Vertex(dR, this.color);
        this.dnLeft = new Vertex(dL, this.color);

        // Vertices for north wall minus the wall_width constant
        this.northEast = new Vertex(new Triple(this.upRight.position.x, this.upRight.position.y - this.width, 0),
                this.color);
        this.northWest = new Vertex(new Triple(this.upLeft.position.x, this.upLeft.position.y - this.width, 0),
                this.color);

        // Vertices for east wall minus the wall_width constant
        this.eastSouth = new Vertex(new Triple(this.dnRight.position.x - this.width, this.dnRight.position.y, 0),
                this.color);
        this.eastNorth = new Vertex(new Triple(this.upRight.position.x - this.width, this.dnRight.position.y, 0),
                this.color);

        // Vertices for south wall plus the wall_width constant
        this.southEast = new Vertex(new Triple(this.dnRight.position.x, this.dnRight.position.y + this.width, 0),
                this.color);
        this.southWest = new Vertex(new Triple(this.dnLeft.position.x, this.dnLeft.position.y + this.width, 0),
                this.color);

        // Vertices for east wall plus the wall_width constant
        this.westNorth = new Vertex(new Triple(this.upLeft.position.x + this.width, this.dnRight.position.y, 0),
                this.color);
        this.westSouth = new Vertex(new Triple(this.dnLeft.position.x + this.width, this.dnRight.position.y, 0),
                this.color);

        // north wall
        this.n1 = new Triangle(this.upLeft, this.upRight, this.northEast);
        this.n2 = new Triangle(this.upLeft, this.northWest, this.northEast);

        // east wall
        this.e1 = new Triangle(this.upRight, this.dnRight, this.eastSouth);
        this.e2 = new Triangle(this.upRight, this.eastNorth, this.eastSouth);

        // south wall
        this.s1 = new Triangle(this.dnRight, this.dnLeft, this.southEast);
        this.s2 = new Triangle(this.dnLeft, this.southWest, this.southEast);

        // west wall
        this.w1 = new Triangle(this.dnLeft, this.upLeft, this.westNorth);
        this.w2 = new Triangle(this.dnLeft, this.westSouth, this.westNorth);
    }

    // put box data in a buffer
    public void sendData(FloatBuffer pb, FloatBuffer cb) {
        // north wall
        n1.a.sendData(pb, cb);
        n1.b.sendData(pb, cb);
        n1.c.sendData(pb, cb);

        n2.a.sendData(pb, cb);
        n2.b.sendData(pb, cb);
        n2.c.sendData(pb, cb);

        // east wall
        e1.a.sendData(pb, cb);
        e1.b.sendData(pb, cb);
        e1.c.sendData(pb, cb);

        e2.a.sendData(pb, cb);
        e2.b.sendData(pb, cb);
        e2.c.sendData(pb, cb);

        // south wall
        s1.a.sendData(pb, cb);
        s1.b.sendData(pb, cb);
        s1.c.sendData(pb, cb);

        s2.a.sendData(pb, cb);
        s2.b.sendData(pb, cb);
        s2.c.sendData(pb, cb);

        // west wall
        w1.a.sendData(pb, cb);
        w1.b.sendData(pb, cb);
        w1.c.sendData(pb, cb);

        w2.a.sendData(pb, cb);
        w2.b.sendData(pb, cb);
        w2.c.sendData(pb, cb);

    }

}
