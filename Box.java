import java.nio.FloatBuffer;


public class Box {
    public Box(){
        System.out.println("Super");
    }

    private String kind; 

    // center
    private float x;
    private float y;
    private float z;

    private float width;
    private float height;


    private Triangle a, b;

    public Box (Triple centered) {

        this.kind = "bullet";

        this.x = (float) centered.x;
        this.y = (float) centered.y;
        this.z = (float )centered.z;

        this.width = Constants.basic_width;
        this.height = Constants.basic_height;

        this.a = new Triangle( 
                    new Vertex(
                        new Triple( (this.x + this.width),(this.y + this.height), this.z),
                        Colors.black ),
                    new Vertex(
                        new Triple( (this.x - this.width),(this.y - this.height), this.z),
                        Colors.black ),
                    new Vertex(
                        new Triple( (this.x + this.width),(this.y - this.height),this.z),
                        Colors.black));

        this.b = new Triangle( 
                    new Vertex(
                        new Triple( (this.x + this.width),(this.y + this.height), this.z),
                        Colors.black ),
                    new Vertex(
                        new Triple( (this.x - this.width),(this.y - this.height), this.z),
                        Colors.black ),
                    new Vertex(
                        new Triple( (this.x - this.width),(this.y + this.height), this.z),
                        Colors.black));
    }


    public void sendData( FloatBuffer pb, FloatBuffer cb, FloatBuffer buff ) {
        a.sendData(pb, cb);
        b.sendData(pb, cb);
        buff.put( (float) this.x );
        buff.put( (float) this.y );
        buff.put( (float) this.z );
     }

}
    
