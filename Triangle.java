import java.nio.FloatBuffer;

public class Triangle {

   private Vertex a, b, c;

   public Triangle( Vertex aIn, Vertex bIn, Vertex cIn ) {
      a = aIn;  b = bIn;  c = cIn;
   }

   // put all the data for this triangle to
   // pb and cb
   public void sendData( FloatBuffer pb, FloatBuffer cb ) {
      a.sendData( pb, cb );
      b.sendData( pb, cb );
      c.sendData( pb, cb );
   }

}
