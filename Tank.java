import java.nio.FloatBuffer;
import java.lang.Math;
import java.awt.Point;



public class Tank extends Box {
    public Tank(){
        System.out.println("Super");
    }


    public float speed;

    public float ammunition;
    public String kind;

    // center
    public double x;
    public double y;
    public double z;

    // center as point
    public Point center;


    private Triple color;

    private Float width;
    private Float height;
    public Float bearing;

    private Triangle a, b;

    public Tank(Triple centered, Triple colors) {

        this.speed = 0;
        this.ammunition = Constants.starting_ammunition;
        this.kind = "tank";

        this.x = centered.x;
        this.y = centered.y;
        this.z = centered.z;

        this.color = colors;


        this.width = Constants.tank_width;
        this.height = Constants.tank_height;


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
   
        this.center = new Point( (int) this.x, (int) this.y);
        this.bearing = getAngle(this.center);

    }



    public float getAngle(Point target) {
        float angle = (float) Math.toDegrees(Math.atan2(target.y - y, target.x - x));
    
        if(angle < 0){
            angle += 360;
        }
    
        return angle;
    }

    public void speed(int up_or_down){
        if (up_or_down == 1 ) {
            this.speed += Constants.speed_increment;
        }
        else if (up_or_down == 0) {
            this.speed = 0;
        }

    }

    public void turn(String direction){
        if (direction == "left" ) {
            this.bearing += Constants.degree_change;
        }
        else if (direction == "right") {
            this.bearing -= Constants.degree_change;
        }
    }

    // public void fire(int round){
    //     if (ammunition >0){
    //         this.bullet = new Bullet( new Triple(this.x, this.y, this.z), this.bearing);
    //         Ex3.boxes.add(this.bullet);
    //     }
//  }


    // put box data in a buffer
    public void sendData( FloatBuffer pb, FloatBuffer cb) {
        a.a.sendData(pb, cb);
        a.b.sendData(pb, cb);
        a.c.sendData(pb, cb);
        b.a.sendData(pb, cb);
        b.b.sendData(pb, cb);
        b.c.sendData(pb, cb);
    }


}
 