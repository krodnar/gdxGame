package com.mygdx.game.contacts;

import com.badlogic.gdx.physics.box2d.Fixture;
import com.mygdx.game.entities.InvisiblePortal;
import com.mygdx.game.entities.Player;

public class PlayerPortalContactResolver extends AbstractContactResolver<Player, InvisiblePortal> {

    @Override
    public void resolveContact(Player player, InvisiblePortal invisiblePortal) {
        invisiblePortal.portPlayer(player);
    }

    @Override
    protected boolean isObjectA(Fixture fixture) {
        return fixture.getBody().getUserData() instanceof Player;
    }
}
