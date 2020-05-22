package game.controller;

/**
 * Interface for observers that track what they are observing and can remove themselves from these things.
 * Other observer interfaces should extend this interface.
 */
public interface RemovableObserver {
    public void removeSelf();
}
