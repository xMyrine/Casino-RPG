package cz.cvut.fel.pjv.entity;

/**
 * Interface for all NPCs in the game.
 * 
 * @Author Minh Tu Pham
 */
public interface NPC {

    /**
     * Method for the NPC to talk.
     * This method will be called when the player collides with the NPC and has
     * interraction ON.
     */
    public void talk();

    /**
     * Method for the NPC to move.
     */
    public void move();

    /**
     * Method for the NPC to set the dialogue message.
     * This message will be displayed when the player interacts with the NPC.
     * It adds Strings to the dialogues array.
     */
    public void setDialogueMessage();

}