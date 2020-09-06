import java.nio.FloatBuffer;



public class Bullet extends Box {

    private String kind;
    private float speed;

    // center
    private double x;
    private double y;
    private double z;

    private Float width;
    private Float height;
    private Float bearing;

    private Triangle a, b;

    public Bullet(Triple centered, Float direction) {

        this.kind = "bullet";

        this.x = centered.x;
        this.y = centered.y;
        this.z = centered.z;

        this.width = Constants.bullet_width;
        this.height = Constants.bullet_height;
        this.bearing = direction;

        this.speed = .4f;

        this.a = new Triangle( 
                    new Vertex(
                        new Triple( (this.x + this.width),(this.y + this.height), this.z),
                        Colors.silver ),
                    new Vertex(
                        new Triple( (this.x - this.width),(this.y - this.height), this.z),
                        Colors.silver ),
                    new Vertex(
                        new Triple( (this.x + this.width),(this.y - this.height),this.z),
                        Colors.silver));

        this.b = new Triangle( 
                    new Vertex(
                        new Triple( (this.x + .1f),(this.y + .1f), this.z),
                        Colors.silver ),
                    new Vertex(
                        new Triple( (this.x - .1f),(this.y - .1f), this.z),
                        Colors.silver ),
                    new Vertex(
                        new Triple( (this.x - .1f),(this.y + .1f), this.z),
                        Colors.silver));
                        
        }
        // put all the data for this triangle to
        // pb and cb
        public void sendData( FloatBuffer pb, FloatBuffer cb ) {
            this.a.a.sendData( pb, cb );
            this.a.b.sendData( pb, cb );
            this.a.c.sendData( pb, cb );
        }


}

