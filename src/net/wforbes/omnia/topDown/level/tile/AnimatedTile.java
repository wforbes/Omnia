package net.wforbes.omnia.topDown.level.tile;


public class AnimatedTile extends BasicTile{
    private int[][] animationTileCoords;
    private int currentAnimationIndex;
    private long lastIterationTime; //ms that we last did the update
    private int animationSwitchDelay;

    public AnimatedTile
            (int id, int[][] animationCoords, int tileColor, int levelColor, int animationSwitchDelay) {
        super(id, animationCoords[0][0], animationCoords[0][1], tileColor, levelColor);

        this.animationTileCoords = animationCoords;
        this.currentAnimationIndex = 0;
        this.lastIterationTime = System.currentTimeMillis();
        this.animationSwitchDelay = animationSwitchDelay;
    }

    public void tick(){
        //if the the difference between the current time and the last iteration time is greater
        //than the animation switch delay value, then switch to the next tile in the animation
        if((System.currentTimeMillis() - lastIterationTime) >= (animationSwitchDelay)){
            lastIterationTime = System.currentTimeMillis();

            //Below, currentAnimationIndex+1 to progress to the next tile,
            //    but modulus the tile coord array length in order to stay in the bounds of the amount
            //    of tiles being used and thus loop back through the number of tiles used for the
            //    animation
            currentAnimationIndex = (currentAnimationIndex + 1) % animationTileCoords.length;

            //Below, this will update the tileId with whatever the tileId should be at the present time
            this.tileId = (animationTileCoords[currentAnimationIndex][0]
                    + (animationTileCoords[currentAnimationIndex][1] * 32));

        }
    }
}
