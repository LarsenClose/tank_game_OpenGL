/*
   draw a triangle using very simple shaders and
   a vertex array object and vertex buffer objects
*/

import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.lang.Math.*;

// import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*; // just for the key constants
// import static org.lwjgl.system.MemoryUtil.*;

import java.util.ArrayList;

public class Ex3 extends Basic {

   private final static int MAX = 10;

   public static void main(String[] args) {
      Ex3 app = new Ex3("adversarial Euclidean amoeboids", 500, 500, 30);
      app.start();
   }// main

   // instance variables

   private Shader v1, f1;
   private int hp1; // handle for the GLSL program

   private int vao; // handle to the vertex array object

   private ArrayList<Box> boxes;
   private Tank red_tank;
   private Tank blue_tank;

   public Box box;

   public double top;
   public double right;
   public double bottom;
   public double left;



   private int positionHandle, colorHandle;
   private FloatBuffer positionBuffer, colorBuffer;

   // construct basic application with given title, pixel width and height
   // of drawing area,northWalland frames per second
   public Ex3(String appTitle, int pw, int ph, int fps) {
      super(appTitle, pw, ph, (long) ((1.0 / fps) * 1000000000));

      top = 1 - Constants.wall_depth * 3;
      right = 1 - Constants.wall_depth * 3;
      bottom = -1 + Constants.wall_depth * 3;
      left = -1 + Constants.wall_depth * 3;

      boxes = new ArrayList<Box>();

      red_tank = new Tank(Constants.red_start_location, Colors.red, true);
      blue_tank = new Tank(Constants.blue_start_location, Colors.blue, false);

      boxes.add(red_tank);
      boxes.add(blue_tank);

      Box box1 = new Box(Constants.box_location_one, "horizontal");
      Box box2 = new Box(Constants.box_location_two, "horizontal");
      Box box3 = new Box(Constants.box_location_three, "verticle");
      Box box4 = new Box(Constants.box_location_four, "verticle");

      boxes.add(box1);
      boxes.add(box2);
      boxes.add(box3);
      boxes.add(box4);

      Border border = new Border(Constants.NW, Constants.NE, Constants.SE, Constants.SW);

      boxes.add(border);

   }

   protected void init() {
      String vertexShaderCode = "#version 330 core\n" + "layout (location = 0 ) in vec3 vertexPosition;\n"
            + "layout (location = 1 ) in vec3 vertexColor;\n" + "out vec3 color;\n" + "void main(void)\n" + "{\n"
            + "  color = vertexColor;\n" + "  gl_Position = vec4( vertexPosition, 1.0);\n" + "}\n";

      System.out.println("Vertex shader:\n" + vertexShaderCode + "\n\n");

      v1 = new Shader("vertex", vertexShaderCode);

      String fragmentShaderCode = "#version 330 core\n" + "in vec3 color;\n"
            + "layout (location = 0 ) out vec4 fragColor;\n" + "void main(void)\n" + "{\n"
            + "  fragColor = vec4(color, 1.0 );\n" + "}\n";

      System.out.println("Fragment shader:\n" + fragmentShaderCode + "\n\n");

      f1 = new Shader("fragment", fragmentShaderCode);
      hp1 = GL20.glCreateProgram();
      Util.error("after create program");
      System.out.println("program handle is " + hp1);

      GL20.glAttachShader(hp1, v1.getHandle());
      Util.error("after attach vertex shader to program");

      GL20.glAttachShader(hp1, f1.getHandle());
      Util.error("after attach fragment shader to program");

      GL20.glLinkProgram(hp1);
      Util.error("after link program");

      GL20.glUseProgram(hp1);
      Util.error("after use program");

      // create vertex buffer objects and their handles one at a time
      positionHandle = GL15.glGenBuffers();
      colorHandle = GL15.glGenBuffers();

      System.out.println("have position handle " + positionHandle + " and color handle " + colorHandle);

      // create the buffers (data doesn't matter so much, just the size)
      positionBuffer = Util.createFloatBuffer(MAX * 15 * 15);
      colorBuffer = Util.createFloatBuffer(MAX * 15 * 15);

      // set the background color
      GL11.glClearColor(1.0f, 1.0f, 1.0f, 0.0f);

   }

   protected void processInputs() {
      // process all waiting input eventsd

      while (InputInfo.size() > 0) {
         InputInfo info = InputInfo.get();

         if (info.kind == 'k' && (info.action == GLFW_PRESS || info.action == GLFW_REPEAT)) {
            int code = info.code;

            if (code == GLFW_KEY_B) {
               GL11.glClearColor(0, 0, 1, 0);
            }

            else if (code == GLFW_KEY_W) {
               System.out.println("trying w \n" + red_tank.bearing + " <-red_tank bearing, red_tank speed -> "
                     + red_tank.speed + "\nlocation: " + red_tank.x + ", " + red_tank.y);

               red_tank.speed += Constants.speed_increment;

            } else if (code == GLFW_KEY_S) {
               red_tank.speed = 0;

            } else if (code == GLFW_KEY_A) {

               red_tank.bearing += Constants.degree_change;
               normalize(red_tank);

            } else if (code == GLFW_KEY_D) {

               red_tank.bearing -= Constants.degree_change;
               normalize(red_tank);

            } else if (code == GLFW_KEY_LEFT_CONTROL) {
               fire(red_tank);
            }

            // Blue tank events

            else if (code == GLFW_KEY_UP) {
               System.out.println("trying w \n" + blue_tank.bearing + " <-blue bearing, blue speed -> "
                     + blue_tank.speed + "\nlocation: " + blue_tank.x + ", " + blue_tank.y);

               blue_tank.speed += Constants.speed_increment;

            } else if (code == GLFW_KEY_DOWN) {
               blue_tank.speed = 0;
            } else if (code == GLFW_KEY_LEFT) {

               blue_tank.bearing += Constants.degree_change;
               normalize(blue_tank);

            } else if (code == GLFW_KEY_RIGHT) {

               blue_tank.bearing -= Constants.degree_change;
               normalize(blue_tank);

            } else if (code == GLFW_KEY_RIGHT_SHIFT) {
               fire(blue_tank);
            }

         } // input event is a key

         else if (info.kind == 'm') {// mouse moved
            System.out.println(info);
         }

         else if (info.kind == 'b') {// button action
            System.out.println(info);
         }

      } // loop to process all input events

   }

   protected void update() {



      for (int i = 0; i < boxes.size(); i++) {


         if (boxes.get(i).getClass() == Tank.class) {

            // initialize outer loop if class tank
            Tank box = (Tank) boxes.get(i);

            double next_x = (box.x + Math.cos(Math.toRadians(box.bearing)) * box.speed);
            double next_y = (box.y + Math.sin(Math.toRadians(box.bearing)) * box.speed);

            double next_x_range_start = next_x - (.5 * (box.width));
            double next_x_range_stop = next_x + (.5 * (box.width));

            double next_y_range_start = next_y - (.5 * (box.height));
            double next_y_range_stop = next_y + (.5 * (box.height));


            //    first check the boundary domain
            if (  (next_y_range_stop >= top) ||
                  (next_y_range_start <= bottom) ||
                  (next_x_range_start <= left) ||
                  (next_x_range_stop >= right))
            {
               box.speed = 0;
               box.updateLocation(box.x, box.y);
               
            }
            // start inner loop
            for (int iIn = 0; iIn < boxes.size(); iIn++){
               if (boxes.get(iIn).getClass() == Tank.class && boxes.get(i)!= boxes.get(iIn)) {


                     // initialize inner if tank and not self
                     Tank boxIn = (Tank) boxes.get(iIn);

                     double x_start = boxIn.x - (.5 * (boxIn.width));
                     double x_stop = boxIn.x + (.5 * (boxIn.width));

                     double y_start = boxIn.y - (.5 * (boxIn.height));
                     double y_stop = boxIn.y + (.5 * (boxIn.height));

                        // if no collision between tanks update else stop
                  if (
                     next_x_range_stop < x_start ||
                     next_x_range_start > x_stop ||
                     next_y_range_stop < y_start ||
                     next_y_range_start > y_stop)
                     {
                        box.updateLocation(next_x, next_y);
                     }
                  else  
                     {
                        box.speed = 0;
                        box.updateLocation(box.x, box.y);
                        boxIn.speed = 0;
                     }
               } // end tank tank collision check

               //start tank outer bullet inner check
               else if (boxes.get(iIn).getClass() == Bullet.class){
                  Bullet boxIn = (Bullet) boxes.get(iIn);

                  double x_start = boxIn.x - (.5 * (boxIn.width));
                  double x_stop = boxIn.x + (.5 * (boxIn.width));

                  double y_start = boxIn.y - (.5 * (boxIn.height));
                  double y_stop = boxIn.y + (.5 * (boxIn.height));
               if (
                  next_x_range_stop < x_start ||
                  next_x_range_start > x_stop ||
                  next_y_range_stop < y_start ||
                  next_y_range_start > y_stop)
                  {
                     box.updateLocation(next_x, next_y); // update if no collision
                  }
               else
                  {
                        if (box.is_red){
                           System.out.println("Congratulations! Blue tank has won the game, the red tank has been destroyed.");
                           System.exit(0);

                        }
                        else{
                           System.out.println("Congratulations! Red tank has won the game, the blue tank has been destroyed.");
                           System.exit(0);
                        }

                  }



               } // end tank outer, bullet inner check



            } // end inner loop
         
            
            
            
         }  // end outer loop initialized as tank

         //    outer loop if bullet
         if (boxes.get(i).getClass() == Bullet.class) {

            // initialize variables
            Bullet box = (Bullet) boxes.get(i);

            double next_x = (box.x + Math.cos(Math.toRadians(box.bearing)) * box.speed);
            double next_y = (box.y + Math.sin(Math.toRadians(box.bearing)) * box.speed);

            double next_x_range_start = next_x - (.5 * (box.width));
            double next_x_range_stop = next_x + (.5 * (box.width));

            double next_y_range_start = next_y - (.5 * (box.height));
            double next_y_range_stop = next_y + (.5 * (box.height));


            // first check boundary 
            if (  (next_y_range_stop >= top) ||
                  (next_y_range_start <= bottom) ||
                  (next_x_range_start <= left) ||
                  (next_x_range_stop >= right))
                  {  
                     box.speed = 0;             
                     box.updateLocation(box.x, box.y);
                  }
            else{
               box.updateLocation(next_x, next_y);
            }

         } // end outer bullet


         // outer loop class box
         if (boxes.get(i).getClass() == Box.class) {
            Box box = (Box) boxes.get(i);
         }
        

      // System.out.println( box.kind );
      // System.out.println( box.bearing );

      }

      // if (box.getClass() == Tank.class || box.getClass() == Bullet.class){
      // box = (Tank) box;

      // System.out.println( box.kind );
      // System.out.println( box.bearing );

      // }

      // double next_x = box.x + Math.cos(Math.toRadians(box.bearing) * box.speed);
      // double next_y = box.y + Math.cos(Math.toRadians(box.bearing) * box.speed);

      // double next_x_range_start = next_x - (.5 * (box.width));
      // double next_x_range_stop = next_x + (.5 * (box.width));

      // double next_y_range_start = next_y - (.5 * (box.height));
      // double next_y_range_stop = next_y + (.5 * (box.height));

      // for (Box boxIn : boxes){
      // double x_start = boxIn.x - (.5 * (boxIn.width));
      // double x_stop = boxIn.x + (.5 * (boxIn.width));

      // double y_start = boxIn.y - (.5 * (boxIn.height));
      // double y_stop = boxIn.y + (.5 * (boxIn.height));

      // if (
      // next_x_range_start < x_start ||
      // next_x_range_stop > x_stop ||
      // next_y_range_start < y_start ||
      // next_y_range_stop < y_stop)
      // {
      // box.x = next_x;
      // box.y = next_y;
      // }

      // if (
      // (next_x_range_start >= x_start) &&

      // (next_x_range_stop <= x_stop) &&

      // (next_y_range_start >= y_start) &&
      // (next_y_range_stop <= y_stop)) {}
      // }

   }

   protected void display() {
      super.display(); // just clears the color and depth buffers

      // System.out.println( getStepNumber() );

      sendData();

      // draw the buffers

      GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, (48 + boxes.size() * 30));
      Util.error("after draw arrays");

   }

   private void sendData() {

      // delete previous handle and binding
      // before doing a new one
      if (vao != -1) {
         GL30.glBindVertexArray(0);
         GL30.glDeleteVertexArrays(vao);
      }

      // using convenience form that produces one vertex array handle
      vao = GL30.glGenVertexArrays();
      Util.error("after generate single vertex array");
      GL30.glBindVertexArray(vao);
      Util.error("after bind the vao");
      // System.out.println("vao is " + vao );

      // connect data to the VBO's

      // trying to handle having one quad in my boxes

      // actually get the data in positionBuffer, colorBuffer):
      positionBuffer.rewind();
      colorBuffer.rewind();
      for (int k = 0; k < boxes.size(); k++) {

         boxes.get(k).sendData(positionBuffer, colorBuffer);

      }
      positionBuffer.rewind();
      colorBuffer.rewind();

      // Util.showBuffer("position buffer: ", positionBuffer );
      // positionBuffer.rewind();
      // Util.showBuffer("color buffer: ", colorBuffer ); colorBuffer.rewind();

      // now connect the buffers
      GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, positionHandle);
      Util.error("after bind positionHandle");
      GL15.glBufferData(GL15.GL_ARRAY_BUFFER, positionBuffer, GL15.GL_STATIC_DRAW);
      Util.error("after set position data");

      GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, colorHandle);
      Util.error("after bind colorHandle");
      GL15.glBufferData(GL15.GL_ARRAY_BUFFER, colorBuffer, GL15.GL_STATIC_DRAW);
      Util.error("after set color data");

      // enable the vertex array attributes
      GL20.glEnableVertexAttribArray(0); // position
      Util.error("after enable attrib 0");
      GL20.glEnableVertexAttribArray(1); // color
      Util.error("after enable attrib 1");

      // map index 0 to the position buffer index 1 to the color buffer
      GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, positionHandle);
      Util.error("after bind position buffer");
      GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0);
      Util.error("after do position vertex attrib pointer");

      // map index 1 to the color buffer
      GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, colorHandle);
      Util.error("after bind color buffer");
      GL20.glVertexAttribPointer(1, 3, GL11.GL_FLOAT, false, 0, 0);
      Util.error("after do color vertex attrib pointer");

   }// sendData

   // given an array with data in it and an allocbox.x

   public void fire(Tank tank) {
      if (tank.ammunition > 0) {

         // System.out.println((tank.bearing));
         // System.out.println(tank.x +Math.sin(Math.toRadians(tank.bearing)*.5));

         boxes.add(new Bullet(new Triple(tank.x + (Math.sin(Math.toRadians(tank.bearing)) * .4),
               tank.y + (Math.cos(Math.toRadians(tank.bearing)) * .4), tank.z), tank.bearing));
         tank.ammunition -= 1;
      }
   }

   public void normalize(Tank tank) {

      if (tank.bearing < 0) {
         tank.bearing += 360;
      }

      tank.bearing = tank.bearing % 360;
   }

   // public double getRange(double single_axis){
   // return double next_x_range_start = next_x - (.5 * (box.width)), double
   // next_x_range_stop = next_x + (.5 * (box.width));

   // }

}// Ex3
