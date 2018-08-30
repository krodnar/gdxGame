package com.mygdx.game.contacts;

import com.badlogic.gdx.physics.box2d.Fixture;
import com.mygdx.game.contacts.AbstractContactResolver;
import com.mygdx.game.entities.Player;
import com.mygdx.game.entities.Portal;

public class PlayerPortalContactResolver extends AbstractContactResolver<Player, Portal> {

    @Override
    public void resolveBeginContact(Player player, Portal portal) {
        portal.portPlayer(player);
    }

    @Override
    public void resolveEndContact(Player player, Portal portal) {

    }

    @Override
    protected boolean isObjectA(Fixture fixture) {
        return fixture.getBody().getUserData() instanceof Player;
    }
}
