import java.nio.FloatBuffer;
import java.lang.Math;
import java.awt.Point;



public class Tank extends Box {
    
    public Tank(){
        System.out.println("Super");
    }

    public String kind;
    public float speed;
    public Float bearing;
    public Float width;
    public Float height;

    public float ammunition;

    public Point tank_point;
    public Point origin;

    // center
    public double x;
    public double y;
    public double z;


    public Triple color;


    private Triangle a, b;

    public Tank(Triple centered, Triple colors) {

        this.kind = "tank"; 
        this.speed = 0;

        this.x = centered.x;
        this.y = centered.y;
        this.z = centered.z;

        this.color = colors;

        

        


        this.ammunition = Constants.starting_ammunition;
        this.width = Constants.tank_width;
        this.height = Constants.tank_height;

        buildTriangles(centered);

        // this.a = new Triangle( 
        //             new Vertex(
        //                 new Triple( (this.x + this.width),(this.y + this.height), this.z),
        //                 this.color),
        //             new Vertex(
        //                 new Triple( (this.x - this.width),(this.y - this.height), this.z),
        //                 this.color ),
        //             new Vertex(
        //                 new Triple( (this.x + this.width),(this.y - this.height),this.z),
        //                 this.color));

        // this.b = new Triangle( 
        //             new Vertex(
        //                 new Triple( (this.x + this.width),(this.y + this.height), this.z),
        //                 this.color),
        //             new Vertex(
        //                 new Triple( (this.x - this.width),(this.y - this.height), this.z),
        //                 this.color),
        //             new Vertex(
        //                 new Triple( (this.x - this.width),(this.y + this.height), this.z),
        //                 this.color));
   
        this.tank_point = new Point( (int) (this.x * 10), (int) (this.y * 10));
        this.origin = new Point(0, 0);
        
        float starting_bearing = (float) getAngle(this.x, this.y, 0d, 0d);

        this.bearing = starting_bearing;
        

    }



    // public float getAngle(Point origin, Point target) {

    //     double theta = Math.atan2(target.y - origin.y, target.x - origin.x);

    //     theta += Math.PI/2.0;

    //     double angle = Math.toDegrees(theta);

    
    //     if(angle < 0){
    //         angle += 360;
    //     }
    
    //     return (float) angle;
    // }


    public double getAngle(double x1, double y1, double x2, double y2)
    {
        double angle = Math.toDegrees(Math.atan2(x2 - x1, y2 - y1));
        // Keep angle between 0 and 360
        angle = angle + Math.ceil( -angle / 360 ) * 360;

        return angle;
    }

    // public void speed(int up_or_down){
    //     if (up_or_down == 1 ) {
    //         this.speed += Constants.speed_increment;
    //     }
    //     else if (up_or_down == 0) {
    //         this.speed = 0;
    //     }

    // }

    // public void turn(String direction){
    //     if (direction == "left" ) {
    //         this.bearing += Constants.degree_change;
    //     }
    //     else if (direction == "right") {
    //         this.bearing -= Constants.degree_change;
    //     }
    // }

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
 