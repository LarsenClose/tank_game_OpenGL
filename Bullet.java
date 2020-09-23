import java.nio.FloatBuffer;



public class Bullet extends Box {

    public String kind;
    public float speed;

    // center
    public double x;
    public double y;
    public double z;

    public boolean alive;

    public Triple center;
    public Float width;
    public Float height;
    public Float bearing;
    public Triple color;

    private Triangle a, b;

    public Bullet(Triple center, Float bearing, float identifier) {

        this.kind = "bullet";
        this.bearing = bearing;

        this.x = center.x;
        this.y = center.y;
        this.z = center.z;
        this.alive = true;


        // defined in Constants class
        this.width = Constants.bullet_width;
        this.height = Constants.bullet_height;
        this.speed = Constants.bullet_speed;

        this.color = Colors.silver;

        buildTriangles(center, this.color);
        
        

    }

                        
        
        // put all the data for this triangle to
        // pb and cb
        public void sendData( FloatBuffer pb, FloatBuffer cb ) {
            if (alive ){
                this.a.a.sendData( pb, cb );
                this.a.b.sendData( pb, cb );
                this.a.c.sendData( pb, cb );
                this.b.a.sendData( pb, cb );
                this.b.b.sendData( pb, cb );
                this.b.c.sendData( pb, cb );
            }
                
        }

        public void updateLocation(Double x, Double y) {
            buildTriangles(new Triple(x, y, 0), this.color);
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

