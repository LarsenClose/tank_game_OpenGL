import java.nio.FloatBuffer;

public class Vertex {

   public Triple position;
   private Triple color;

   public Vertex( Triple p, Triple c ) {
      position = p;
      color = c;
   }

   public void sendData( FloatBuffer pb, FloatBuffer cb ) {
      position.sendData( pb );
      color.sendData( cb );
   }

}
