import java.nio.FloatBuffer;



public class Bullet extends Box {

    public String kind;
    public float speed;

    // center
    public double x;
    public double y;
    public double z;

    public Float width;
    public Float height;
    public Float bearing;
    public Triple color;

    private Triangle a, b;

    public Bullet(Triple centered, Float bearing) {

        this.kind = "bullet";
        this.bearing = bearing;

        this.x = centered.x;
        this.y = centered.y;
        this.z = centered.z;

        // defined in Constants class
        this.width = Constants.bullet_width;
        this.height = Constants.bullet_height;
        this.speed = Constants.bullet_speed;

        this.color = Colors.silver;

        buildTriangles(centered);
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

        public void updateLocation(Double x, Double y) {
            buildTriangles(new Triple(x, y, 0));
        }
    
        public void buildTriangles(Triple location){
            this.x = location.x;
            this.y = location.y;
    
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
    
    
    


}

