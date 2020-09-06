import java.nio.FloatBuffer;
import java.lang.Math;
import java.awt.Point;



public class Tank extends Box {
    public Tank(){
        System.out.println("Super");
    }

    // // statics set from settings class
    private float speed;
    private Bullet bullet;
    private float ammunition;
    private String kind; 

    // center
    private double x;
    private double y;
    private double z;

    // center as point
    public Point center;


    private Triple color;

    private Float width;
    private Float height;
    private Float bearing;

    private Triangle a, b;

    public Tank(Triple centered, String which_kind, Triple colors) {

        this.speed = 0;
        this.ammunition = 30;
        this.kind = which_kind;

        this.x = centered.x;
        this.y = centered.y;
        this.z = centered.z;

        this.color = colors;


        this.width = Constants.tank_width;
        this.height = Constants.tank_height;


        this.b = new Triangle( 
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
                        new Triple( (this.x + .1f),(this.y + .1f), this.z),
                        this.color),
                    new Vertex(
                        new Triple( (this.x - .1f),(this.y - .1f), this.z),
                        this.color),
                    new Vertex(
                        new Triple( (this.x - .1f),(this.y + .1f), this.z),
                        this.color));
   
        this.center = new Point( (int) this.x, (int) this.y);
        this.bearing = getAngle(this.center);

    }

    public double middle_of_vector_pairs(
	    double x1, 
	    double y1, 
	    double x2, 
	    double y2) {
	        
	    double ac = Math.abs(y2 - y1);
	    double cb = Math.abs(x2 - x1);
	        
	    return Math.hypot(ac, cb) / 2;
    }

    public float getAngle(Point target) {
        float angle = (float) Math.toDegrees(Math.atan2(target.y - y, target.x - x));
    
        if(angle < 0){
            angle += 360;
        }
    
        return angle;
    }

    public void speed(int up_or_down){
        if (up_or_down == 0 ) {
            this.speed = 0;
        }
        else if (up_or_down == 1) {
            this.speed +=1;
        }

    }

    public void turn(String direction){
        if (direction == "left" ) {
            this.bearing += 15;
        }
        else if (direction == "right") {
            this.bearing -=15;
        }
    }

    // public void fire(int round){
    //     if (ammunition >0){
    //         this.bullet = new Bullet( new Triple(this.x, this.y, this.z), this.bearing);
    //         Ex3.boxes.add(this.bullet);
    //     }
//  }


    // put box data in a buffer
    public void sendData( FloatBuffer pb, FloatBuffer cb, FloatBuffer buff) {
       a.sendData(pb, cb);
       b.sendData(pb, cb);
       buff.put( ( float ) this.x );
       buff.put( ( float ) this.y );
       buff.put( ( float ) this.z );

    }


}
 