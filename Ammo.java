import java.nio.FloatBuffer;

public class Ammo extends Box{

    public double x;
    public double y;
    public double z;
    
    public Float width;
    public Float height;

    public Triple color;
    public Triple center;

    private Triangle a, b;

    public Ammo(Triple center) {

        this.x = center.x;
        this.y = center.y;
        this.z = center.z;

        this.width = Constants.ammo_dimensions;
        this.height = Constants.ammo_dimensions;
        this.color = Colors.new_blue;

        buildTriangles(center, this.color);

    }

           
        // put all the data for this triangle to
        // pb and cb
        public void sendData( FloatBuffer pb, FloatBuffer cb ) {
                this.a.a.sendData( pb, cb );
                this.a.b.sendData( pb, cb );
                this.a.c.sendData( pb, cb );
                this.b.a.sendData( pb, cb );
                this.b.b.sendData( pb, cb );
                this.b.c.sendData( pb, cb );
        }


    
        public void buildTriangles(Triple location, Triple color){
            this.x = location.x;
            this.y = location.y;
            this.color = color;
    
            this.a = new Triangle( 
                        new Vertex(
                            new Triple( (this.x + this.width),(this.y + this.height), this.z),
                            this.color),
                        new Vertex(
                            new Triple( (this.x - this.width),(this.y - this.height), this.z),
                            this.color ),
                        new Vertex(
                            new Triple( (this.x + this.width),(this.y - this.height),this.z),
                            this.color));
            
            this.b = new Triangle( 
                        new Vertex(
                            new Triple( (this.x + this.width),(this.y + this.height), this.z),
                            this.color),
                        new Vertex(
                            new Triple( (this.x - this.width),(this.y - this.height), this.z),
                            this.color),
                        new Vertex(
                            new Triple( (this.x - this.width),(this.y + this.height), this.z),
                            this.color));
        }

        public void updateColor(){
            this.color = Colors.white;
            buildTriangles(new Triple(this.x, this.y, this.z), this.color);
        }

}
