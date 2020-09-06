import java.nio.FloatBuffer;
import java.lang.Math;
import java.awt.Point;



public class Tank extends Box {

    // // statics set from settings class
    private float speed;
    private Bullet bullet;
    private float ammunition;
    private String kind; 

    // center
    private float x;
    private float y;
    private float z;

    // center as point
    public Point center;

    // not sure how to address instasuperce supernstance variable
    public String color;



    private Float width;
    private Float height;
    private Float bearing;

    private Triangle a, b;

    public Tank(Triangle over, Triangle under, String which_kind, String which_color) {

        this.speed = 0;
        this.ammunition = 30;
        this.kind = which_kind;



        this.a = over;
        this.b = under;

        Double v1x = this.a.a.position.x;
        Double v1y = this.a.a.position.y;

        Double v2x = this.a.b.position.x;
        Double v2y = this.a.b.position.y;

        Double v3x = this.a.c.position.x;
        Double v3y = this.a.c.position.y;
        this.x = (float) middle_of_vector_pairs(v1x, v1y, v2x, v2y);
        this.y = (float) middle_of_vector_pairs(v2x, v3y, v3x, v3y);
        this.z = 0;

        this.center = new Point( (int) this.x, (int) this.y);

        this.width = 2 * x;
        this.height = 2 * y;
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
        else if (up_or_down ==1) {
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

    public void fire(int round){
        if (ammunition >0){
            this.bullet = new Bullet( new Triple(this.x, this.y, this.z), this.bearing);

        }

        



    }


    // put box data in a buffer
    public void sendData( FloatBuffer pb, FloatBuffer cb, FloatBuffer buff) {
       a.sendData(pb, cb);
       b.sendData(pb, cb);
       buff.put( ( float ) this.x );
       buff.put( ( float ) this.y );
       buff.put( ( float ) this.z );

    }


}
 