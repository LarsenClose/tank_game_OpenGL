public class Constants {


    static float bullet_width = .01f;
    static float bullet_height = .01f;
    static float bullet_speed = .005f;
    static int starting_ammunition = 100;

    static int ammunition_box = 20;

    static float speed_increment = .0001f;
    static float degree_change = 8;

    static float basic_width = .05f;
    static float basic_height = .1f;


    static float ammo_dimensions = .04f;

    static float mine_dimensions = .02f;


    static double wall_depth = .02f;


    static float tank_width = .1f;
    static float tank_height = .1f;

    static Triple red_start_location = new Triple((double) -.8, (double) -.8, (double) 0);
    static Triple blue_start_location = new Triple((double) .8, (double) .8, (double) 0);

    static Triple box_location_one = new Triple((double) -.1, (double) -.1,(double) 0);
    static Triple box_location_two = new Triple((double) .3, (double) .7,(double) 0);
    static Triple box_location_three = new Triple((double) -.6, (double) .4,(double) 0);
    static Triple box_location_four = new Triple((double) .2, (double) -.7,(double) 0);

    static Triple NW = new Triple(-1, 1, 0);
    static Triple NE = new Triple(1, 1, 0);
    static Triple SE = new Triple(1, -1, 0);
    static Triple SW = new Triple(-1, -1, 0);

    static Triple ammo_location_one = new Triple((double) .1, (double) .1,(double) 0);
    static Triple ammo_location_two = new Triple((double) -.3, (double) .7,(double) 0);
    static Triple ammo_location_three = new Triple((double) -.6, (double) -.4,(double) 0);

    static Triple mine_location_one = new Triple((double) -.8, (double) .8,(double) 0);
    static Triple mine_location_two = new Triple((double) .8, (double) -.8,(double) 0);
}
