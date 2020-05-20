package Controller;

public interface BattleEventObserver extends RemovableObserver {
    public void notify(String message);
}
