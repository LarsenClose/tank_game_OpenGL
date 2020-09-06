import java.nio.FloatBuffer;


public class Box {
    public Box(){
        System.out.println("Super");
    }

    private String kind; 

    // center
    private double x;
    private double y;
    private double z;

    private float width;
    private float height;

    private Triple color;


    private Triangle a, b;

    public Box (Triple centered) {

        this.kind = "basic";

        this.color = Colors.black;


        this.x = centered.x;
        this.y = centered.y;
        this.z = centered.z;

        this.width = Constants.basic_width;
        this.height = Constants.basic_height;

        this.a = new Triangle( 
                    new Vertex(
                        new Triple( (this.x + this.width),(this.y + this.height), this.z),
                        this.color ),
                    new Vertex(
                        new Triple( (this.x - this.width),(this.y - this.height), this.z),
                        this.color ),
                    new Vertex(
                        new Triple( (this.x + this.width),(this.y - this.height),this.z),
                        this.color ));

        this.b = new Triangle( 
                    new Vertex(
                        new Triple( (this.x + this.width),(this.y + this.height), this.z),
                        this.color ),
                    new Vertex(
                        new Triple( (this.x - this.width),(this.y - this.height), this.z),
                        this.color ),
                    new Vertex(
                        new Triple( (this.x - this.width),(this.y + this.height), this.z),
                        this.color));
    }


    public void sendData( FloatBuffer pb, FloatBuffer cb) {
        a.a.sendData(pb, cb);
        a.b.sendData(pb, cb);
        a.c.sendData(pb, cb);
        b.a.sendData(pb, cb);
        b.b.sendData(pb, cb);
        b.c.sendData(pb, cb);
     }

}
    
