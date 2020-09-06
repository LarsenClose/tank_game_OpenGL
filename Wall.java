import java.nio.FloatBuffer;



public class Wall extends Box {
    public Wall(){
        System.out.println("Super");
    }

    private String kind;
    private Triple color;

    private Vertex upLeft;
    private Vertex upRight;
    private Vertex dnRight;
    private Vertex dnLeft;

    public Wall (Triple uL, Triple uR, Triple dR, Triple dL) {

        this.kind = "wall";
        this.color = Colors.cyan;
        



        this.upLeft = new Vertex(uL, this.color);
        this.upRight = new Vertex(uR, this.color);
        this.dnRight = new Vertex(dR, this.color);
        this.dnLeft = new Vertex(dL, this.color);

    }

        // put box data in a buffer
        public void sendData( FloatBuffer pb, FloatBuffer cb) {
            upLeft.sendData(pb, cb);
            upRight.sendData(pb, cb);
            dnRight.sendData(pb, cb);
            dnLeft.sendData(pb, cb);
        }
    

    
}
