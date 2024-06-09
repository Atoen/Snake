package Entities;

/**
 * The ScoreEntity interface defines methods for entities that have a score value
 * and a growth length, which typically represents the effect of the entity when
 * consumed by a snake or similar in a game.
 */
public interface ScoreEntity {

    /**
     * Gets the score associated with the entity.
     *
     * @return The score value.
     */
    int getScore();

    /**
     * Gets the growth length associated with the entity.
     *
     * @return The growth length value.
     */
    int getGrowLength();
}
