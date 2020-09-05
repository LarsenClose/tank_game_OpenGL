/*  
   draw a triangle using very simple shaders and
   a vertex array object and vertex buffer objects
*/

import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

// import static org.lwjgl.glfw.Callbacks.*;
 import static org.lwjgl.glfw.GLFW.*;   // just for the key constants
// import static org.lwjgl.system.MemoryUtil.*;

import java.util.ArrayList;

public class Ex3 extends Basic {

   private final static int MAX = 10;

   public static void main(String[] args) {
      Ex3 app = new Ex3( "One Triangle", 500, 500, 30 );
      app.start();
   }// main
 
   // instance variables 
 
   private Shader v1, f1;
   private int hp1;  // handle for the GLSL program
 
   private int vao;  // handle to the vertex array object
 
   private ArrayList<Triangle> tris;
 
   private int positionHandle, colorHandle;
   private FloatBuffer positionBuffer, colorBuffer;

   // construct basic application with given title, pixel width and height
   // of drawing area, and frames per second
   public Ex3( String appTitle, int pw, int ph, int fps ) {
      super( appTitle, pw, ph, (long) ((1.0/fps)*1000000000) );

      tris = new ArrayList<Triangle>();
    
      tris.add( new Triangle( 
                             new Vertex( new Triple( -1, -.8, 0 ),
                                          new Triple( 0, 0, 1 ) ),
                             new Vertex( new Triple( -1, -1, 0 ),
                                          new Triple( 0, 0, 1 ) ),
                             new Vertex( new Triple( -.8, -1, 0 ),
                                          new Triple( 0, 0, 1 ) )  ) );
      
      tris.add( new Triangle( 
                           new Vertex( new Triple( -1, -.8, 0 ),
                                       new Triple( 0, 0, 1) ),
                           new Vertex( new Triple( -.8, -.8, 0 ),
                                       new Triple( 0, 0, 1) ),
                           new Vertex( new Triple( -.8, -1, 0 ),
                                       new Triple( 0, 0, 1 ) )  ) );

      


      tris.add( new Triangle( 
                              new Vertex( new Triple( 1, .8, 0 ),
                                          new Triple( 1, 0, 0 ) ),
                              new Vertex( new Triple( 1, 1, 0 ),
                                          new Triple( 1, 0, 0 ) ),
                              new Vertex( new Triple( .8, 1, 0 ),
                                          new Triple( 1, 0, 0 ) )  ) );

      tris.add( new Triangle( 
                              new Vertex( new Triple( 1, .8, 0 ),
                                          new Triple( 1, 0, 0 ) ),
                              new Vertex( new Triple( .8, .8, 0 ),
                                          new Triple( 1, 0, 0 ) ),
                              new Vertex( new Triple( .8, 1, 0 ),
                                          new Triple( 1, 0, 0 ) )  ) );


      // tris.add( new Triangle( 
      //                        new Vertex( new Triple( .25, -.75, 0 ),
      //                                     new Triple( 0, 0, 1 ) ),
      //                        new Vertex( new Triple( .75, -.75, 0 ),
      //                                     new Triple( 0, 0, 1 ) ),
      //                        new Vertex( new Triple( .5, -.25, 0 ),
      //                                     new Triple( 0, 0, 1 ) )  ) );
   }
 
   protected void init() {
      String vertexShaderCode =
"#version 330 core\n"+
"layout (location = 0 ) in vec3 vertexPosition;\n"+
"layout (location = 1 ) in vec3 vertexColor;\n"+
"out vec3 color;\n"+
"void main(void)\n"+
"{\n"+
"  color = vertexColor;\n"+
"  gl_Position = vec4( vertexPosition, 1.0);\n"+
"}\n";
 
     System.out.println("Vertex shader:\n" + vertexShaderCode + "\n\n" );
 
     v1 = new Shader( "vertex", vertexShaderCode );
 
     String fragmentShaderCode =
"#version 330 core\n"+
"in vec3 color;\n"+
"layout (location = 0 ) out vec4 fragColor;\n"+
"void main(void)\n"+
"{\n"+
"  fragColor = vec4(color, 1.0 );\n"+
"}\n";
 
     System.out.println("Fragment shader:\n" + fragmentShaderCode + "\n\n" );
 
     f1 = new Shader( "fragment", fragmentShaderCode );
 
     hp1 = GL20.glCreateProgram();
          Util.error("after create program");
          System.out.println("program handle is " + hp1 );
 
     GL20.glAttachShader( hp1, v1.getHandle() );
          Util.error("after attach vertex shader to program");
 
     GL20.glAttachShader( hp1, f1.getHandle() );
          Util.error("after attach fragment shader to program");
 
     GL20.glLinkProgram( hp1 );
          Util.error("after link program" );
 
     GL20.glUseProgram( hp1 );
          Util.error("after use program");
 
     // create vertex buffer objects and their handles one at a time
     positionHandle = GL15.glGenBuffers();
     colorHandle = GL15.glGenBuffers();
     System.out.println("have position handle " + positionHandle +
                        " and color handle " + colorHandle );
 
     // create the buffers (data doesn't matter so much, just the size)
     positionBuffer = Util.createFloatBuffer( MAX * 3 * 3 );
     colorBuffer = Util.createFloatBuffer( MAX * 3 * 3 );

     // set the background color
     GL11.glClearColor( 1.0f, 1.0f, 1.0f, 0.0f );

   }
 
   protected void processInputs() {
      // process all waiting input events
      while( InputInfo.size() > 0 ) {
         InputInfo info = InputInfo.get();
 
         if ( info.kind == 'k' && (info.action == GLFW_PRESS || 
                                  info.action == GLFW_REPEAT) ) {
            int code = info.code;
 
            if ( code == GLFW_KEY_B ) {
               GL11.glClearColor( 0, 0, 1, 0 );
            }

         }// input event is a key
 
         else if ( info.kind == 'm' ) {// mouse moved
            //  System.out.println( info );
         }
 
         else if( info.kind == 'b' ) {// button action
            //  System.out.println( info );
         }
 
      }// loop to process all input events
 
   }
 
   protected void update() {
   }
 
   protected void display() {
      super.display();  // just clears the color and depth buffers

      // System.out.println( getStepNumber() );
 
      sendData();

      // draw the buffers
      GL11.glDrawArrays( GL11.GL_TRIANGLES, 0, tris.size() * 4 );
            Util.error("after draw arrays");
 
   }
 
   private void sendData() {
 
      // delete previous handle and binding
      // before doing a new one
      if ( vao != -1 ) {
         GL30.glBindVertexArray(0);
         GL30.glDeleteVertexArrays( vao );
      }
 
      // using convenience form that produces one vertex array handle
      vao = GL30.glGenVertexArrays();
            Util.error("after generate single vertex array");
      GL30.glBindVertexArray( vao );
            Util.error("after bind the vao");
      // System.out.println("vao is " + vao );
 
      // connect data to the VBO's
     
      // actually get the data in positionBuffer, colorBuffer):

      positionBuffer.rewind();  colorBuffer.rewind();

      for (int k=0; k<tris.size(); k++) {
         tris.get(k).sendData( positionBuffer, colorBuffer );
      }
      positionBuffer.rewind();  colorBuffer.rewind();

// Util.showBuffer("position buffer: ", positionBuffer );  positionBuffer.rewind();
// Util.showBuffer("color buffer: ", colorBuffer );  colorBuffer.rewind();
 
        // now connect the buffers
        GL15.glBindBuffer( GL15.GL_ARRAY_BUFFER, positionHandle );
              Util.error("after bind positionHandle");
        GL15.glBufferData( GL15.GL_ARRAY_BUFFER, 
                                      positionBuffer, GL15.GL_STATIC_DRAW );
              Util.error("after set position data");

        GL15.glBindBuffer( GL15.GL_ARRAY_BUFFER, colorHandle );
              Util.error("after bind colorHandle");
        GL15.glBufferData( GL15.GL_ARRAY_BUFFER, 
                                      colorBuffer, GL15.GL_STATIC_DRAW );
              Util.error("after set color data");
 
       // enable the vertex array attributes
       GL20.glEnableVertexAttribArray(0);  // position
              Util.error("after enable attrib 0");
       GL20.glEnableVertexAttribArray(1);  // color
              Util.error("after enable attrib 1");
   
       // map index 0 to the position buffer index 1 to the color buffer
       GL15.glBindBuffer( GL15.GL_ARRAY_BUFFER, positionHandle );
              Util.error("after bind position buffer");
       GL20.glVertexAttribPointer( 0, 3, GL11.GL_FLOAT, false, 0, 0 );
              Util.error("after do position vertex attrib pointer");
 
       // map index 1 to the color buffer
       GL15.glBindBuffer( GL15.GL_ARRAY_BUFFER, colorHandle );
              Util.error("after bind color buffer");
       GL20.glVertexAttribPointer( 1, 3, GL11.GL_FLOAT, false, 0, 0 );
              Util.error("after do color vertex attrib pointer");
 
   }// sendData

   // given an array with data in it and an allocated buffer,
   // overwrite buffer contents with array data
   private void sendArrayToBuffer( float[] array, FloatBuffer buffer ) {
      buffer.rewind();
      for (int k=0; k<array.length; k++) {
         buffer.put( array[k] );
      }
   }// sendArrayToBuffer

}// Ex3
