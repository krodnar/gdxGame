package com.mygdx.game.contacts;

import com.badlogic.gdx.physics.box2d.Fixture;
import com.mygdx.game.entities.Player;
import com.mygdx.game.entities.WorldObject;

/**
 * A resolver that handles contact between {@link Player} and a {@link WorldObject}.
 */
public class PlayerObjectsContactResolver extends AbstractContactResolver<Player, WorldObject> {

    @Override
    public void resolveBeginContact(Player player, WorldObject worldObject) {
        if (worldObject.getType() == WorldObject.Type.COLLISION) {
            System.out.println("Player hit a collision object");
        }
    }

    @Override
    public void resolveEndContact(Player player, WorldObject worldObject) {

    }

    @Override
    protected boolean isObjectA(Fixture fixture) {
        return fixture.getBody().getUserData() instanceof Player;
    }
}
