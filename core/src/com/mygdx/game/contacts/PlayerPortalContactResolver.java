package com.mygdx.game.contacts;

import com.badlogic.gdx.physics.box2d.Fixture;
import com.mygdx.game.entities.InvisiblePortal;
import com.mygdx.game.entities.Player;

/**
 * A resolver that handles contact between {@link Player} and a {@link InvisiblePortal portal}.
 */
public class PlayerPortalContactResolver extends AbstractContactResolver<Player, InvisiblePortal> {

    @Override
    public void resolveBeginContact(Player player, InvisiblePortal invisiblePortal) {
        invisiblePortal.portPlayer(player);
    }

    @Override
    public void resolveEndContact(Player player, InvisiblePortal portal) {

    }

    @Override
    protected boolean isObjectA(Fixture fixture) {
        return fixture.getBody().getUserData() instanceof Player;
    }
}
