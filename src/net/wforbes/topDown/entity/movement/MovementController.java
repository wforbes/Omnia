package net.wforbes.topDown.entity.movement;

import net.wforbes.topDown.entity.Mob;

import java.util.Random;

public class MovementController {
    protected Mob mover;
    private int[] xya = new int[3];
    private int a, xa, ya;
    private long waitCount = 0;
    private boolean moveReady = false;
    private Random gen = new Random();

    public MovementController(Mob mover) {
        this.mover = mover;
    }

    /*
        XYA
        A Values (advancement direction)
            0 - Up, 1 - Down, 2 - Left, 3 - Right
            4 - UpLeft, 5 - UpRight, 6 - DownLeft, 7 - DownRight
    */
    private String getFacingString() {
        String[] dirs = new String[]{
                "n", "s", "w", "e",
                "nw", "ne", "sw", "se"
        };
        return dirs[this.xya[2]];
    }

    public void tick() {
        //System.out.println(this.waitCount);
        this.paceUpAndDown(5, 10, 40, 100);
    }

    private void paceUpAndDown(int waitMultiple, int waitReset, int minYBound, int maxYBound) {
        if (this.waitCount == 0 || this.waitCount == (waitMultiple + 1) * waitReset) {
            //System.out.println("waitCount 0 or 5");
            this.waitCount = 1;
            this.moveReady = false;
        } else if (this.waitCount % waitMultiple == 0) {
            if (this.mover.y < 40) {
                this.xya = new int[]{0, 1, 1};
                this.moveReady = true;
            }

            if (this.mover.y >= 40 && this.mover.y <= 100) {
                //this.xya = this.getContinuousMove(this.xya);
                if(this.xya[2] == 0) this.xya[1] = -1;
                if(xya[2] == 1) this.xya[1] = 1;
                this.moveReady = true;
            }

            if (this.mover.y > 100) {
                //this.mover.move(0, -1);
                this.xya = new int[]{0, -1, 0};
                this.moveReady = true;
            }
        }

        if(this.moveReady) this.mover.move(xya[0], xya[1]);
        this.moveReady = false;
        this.waitCount++;
    }

    //TODO: add interpolation for a smooth walking motion
    private void wanderingMovement(){

        if(waitCount == 0 || waitCount == 160){
            this.xya = getRandomMove();
            waitCount = 1;
        }else if(waitCount % 35 == 0 ){
            this.xya = getContinuousMove(xya);
        }

        if(this.mover.hasCollided(xya[0], xya[1])){
            xya = redirectMovement(xya);
        }

        if(waitCount % 13 == 0){
            this.mover.move(xya[0], xya[1]);
            this.mover.isMoving = true;
        }


        if(this.mover.level.getTile(this.mover.x >>3, this.mover.y >>3).getId() == 3){
            this.mover.isSwimming = true;
        }
        if(this.mover.isSwimming && this.mover.level.getTile(this.mover.x >> 3, this.mover.y >> 3).getId() != 3){
            this.mover.isSwimming = false;
        }

        waitCount++;
    }

    //TODO: add consideration for isCollided to turn
    //  an accessible direction for a few movements
    //  to get away from the obstruction (in progress)
    //TODO: instead of calling a new move function again when redirecting,
    //  keep track of the position right before this tick's movement and redirection
    //  to then revert to the initial position and redirect/move then.
    private int[] redirectMovement(int[] xya){
        int[] cardinals = new int[]{0, 1, 2, 3};
        int[] diagonals = new int[]{4, 5, 6, 7};
        int a = xya[2];//get the direction from the movement set
        int[] new_xya;//an array for a new movement, so we don't change the current one
        if(a == 0 || a == 2){
            int opposite = a + 1;
            new_xya = new int[]{xya[0], xya[1], a};
            new_xya = getContinuousMove(xya);
            if(this.mover.hasCollided(new_xya[0], new_xya[1])){
                if(opposite == 1) {
                    new_xya[2] = 3;
                }else{
                    new_xya[2] = 1;
                }
                new_xya = getContinuousMove(new_xya);
                if(this.mover.hasCollided(new_xya[0], new_xya[1])){
                    new_xya[2] = 3;//int perpendicular = 1;
                    return getContinuousMove(new_xya);
                }
            }
        }else if(a==1 || a ==3) {
            int opposite = a - 1;
            new_xya = new int[]{xya[0], xya[1], a};
            new_xya = getContinuousMove(xya);
            if(this.mover.hasCollided(new_xya[0], new_xya[1])){
                if(opposite == 0){
                    new_xya[2] = 2;
                }else{
                    new_xya[2] = 0;
                }
                new_xya[2] = 1;//int perpendicular = 1;
                new_xya = getContinuousMove(new_xya);
                if(this.mover.hasCollided(new_xya[0], new_xya[1])){
                    new_xya[2] = 3;//int perpendicular = 1;
                    return getContinuousMove(new_xya);
                }
            }
        }else{
            new_xya = new int[]{xya[0], xya[1], a};
            //if 4, check 0 and 2
            if(a == 4){
                int perp = 0;
                new_xya = new int[]{xya[0], xya[1], perp};
                new_xya = getContinuousMove(xya);
                if(this.mover.hasCollided(new_xya[0], new_xya[1])){
                    new_xya[2] = 2;
                    new_xya = getContinuousMove(xya);
                }
            }
            //if 5, check 0 and 3
            if(a == 5){
                int perp = 0;
                new_xya = new int[]{xya[0], xya[1], perp};
                new_xya = getContinuousMove(xya);
                if(this.mover.hasCollided(new_xya[0], new_xya[1])){
                    new_xya[2] = 3;
                    new_xya = getContinuousMove(xya);
                }
            }
            //if 6, check 1 and 2
            if(a == 6){
                int perp = 1;
                new_xya = new int[]{xya[0], xya[1], perp};
                new_xya = getContinuousMove(xya);
                if(this.mover.hasCollided(new_xya[0], new_xya[1])){
                    new_xya[2] = 2;
                    new_xya = getContinuousMove(xya);
                }
            }
            //if 7, check 1 and 3
            if(a == 7){
                int perp = 1;
                new_xya = new int[]{xya[0], xya[1], perp};
                new_xya = getContinuousMove(xya);
                if(this.mover.hasCollided(new_xya[0], new_xya[1])){
                    new_xya[2] = 3;
                    new_xya = getContinuousMove(xya);
                }
            }
        }

        //if cardinal, check it's opposite direction


        //  if its not colliding, go the opposite direction
        //  if it is colliding, check a perpendicular direction (0/1..2/3)
        //      if perpendicular is colliding, check it's opposite
        //          if opposite perpendicular is colliding, check

        //if diagonal, check it's component directions
        //  if one is colliding, go the opposite direction of it
        //  if both are colliding, go the opposite direction of the diagonal

        return new_xya;
    }

    private int[] getRandomMove(){
        int a = Math.abs(gen.nextInt()) % 8;
        int nxa = this.xa, nya = this.ya;
        if(a == 0)nya--;
        if(a == 1)nya++;
        if(a == 2)nxa--;
        if(a == 3)nxa++;
        if(a == 4){nxa--; nya--;}
        if(a == 5){nxa++; nya--;}
        if(a == 6){nxa--; nya++;}
        if(a == 7){ nxa++; nya++;}
        return new int[]{nxa, nya, a};
    }

    private int[] getContinuousMove(int[] xya){
        if(xya[2] == 0)xya[1]--;
        if(xya[2] == 1)xya[1]++;
        if(xya[2] == 2)xya[0]--;
        if(xya[2] == 3)xya[0]++;
        if(xya[2] == 4){xya[0]--; xya[1]--;}
        if(xya[2] == 5){xya[0]++; xya[1]--;}
        if(xya[2] == 6){xya[0]--; xya[1]++;}
        if(xya[2] == 7){ xya[0]++; xya[1]++;}
        return xya;
    }

    private void randomMovement(){
        if(waitCount == 20 || waitCount == 0){
            int[] xya = getRandomMove();
            this.mover.move(xya[0], xya[1]);
            this.mover.isMoving = true;
            waitCount = 1;
        }else{
            this.mover.isMoving = false;
        }

        if(this.mover.level.getTile(this.mover.x >>3, this.mover.y >>3).getId() == 3){
            this.mover.isSwimming = true;
        }
        if(this.mover.isSwimming && this.mover.level.getTile(this.mover.x >> 3, this.mover.y >> 3).getId() != 3){
            this.mover.isSwimming = false;
        }
        waitCount++;
    }
}
