package com.starfighter.game.engine.contracts;

import com.starfighter.game.game.units.PlayerGameObject;

public interface RenderManager {
    void render();

    void registerPlayer(PlayerGameObject player);
}
