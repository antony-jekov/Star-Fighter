package com.starfighter.game.game.units;

import java.util.ArrayList;
import java.util.List;

public class FighterGameObject extends AliveGameObject {

    private List<OnFighterAttackedListener> attackListeners;

    public interface OnFighterAttackedListener {
        void OnFighterAttack(FighterGameObject fighter);
    }

    private long nextAvailableFireTime;

    protected Weapon weapon = new Weapon(1, 150);

    public FighterGameObject() {
        this.attackListeners = new ArrayList<OnFighterAttackedListener>();
    }

    protected void fire() {
        if (System.currentTimeMillis() > nextAvailableFireTime) {
            nextAvailableFireTime = System.currentTimeMillis() + weapon.getFireRate();
            OnAttack();
        }
    }

    private void OnAttack() {
        for (int i = 0; i < attackListeners.size(); i++) {
            attackListeners.get(i).OnFighterAttack(this);
        }
    }

    @Override
    protected void onAliveStatusChanged() {
        if (!alive)
            weapon.setWeapon(1);

        super.onAliveStatusChanged();
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public void addAttackListener(OnFighterAttackedListener listener) {
        this.attackListeners.add(listener);
    }

    public void removeAttackListener(OnFighterAttackedListener listener) {
        this.attackListeners.remove(listener);
    }
}
