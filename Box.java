import java.nio.FloatBuffer;

public class Box {

    // statics set from settings class
    private float speed = Settings.speed;
    private float bullets = Settings.initial_bullets;


    private float x; 
    private float y;
    private Float angle;


    public Box(Triangle original) {
        Triangle origin = original;
        new Triangle()

       this.x = x;
       this.y = y;

    }
 
    // put all the data for this triangle to
    // pb and cb
    public void sendData( FloatBuffer pb) {
       this.speed.sendData( pb);

    }

}
