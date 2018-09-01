package com.mygdx.game.managers;

import com.badlogic.gdx.physics.box2d.Contact;
import com.mygdx.game.contacts.ContactResolver;
import com.mygdx.game.entities.InvisiblePortal;
import com.mygdx.game.contacts.PlayerObjectsContactResolver;
import com.mygdx.game.contacts.PlayerPortalContactResolver;
import com.mygdx.game.entities.Player;
import com.mygdx.game.entities.WorldObject;

import java.util.HashMap;

public class ContactManager {

    private HashMap<Integer, ContactResolver> resolvers;

    public ContactManager() {
        this.resolvers = new HashMap<Integer, ContactResolver>();

        initResolvers();
    }

    public ContactResolver getResolver(Contact contact) {

        Object ea = contact.getFixtureA().getBody().getUserData();
        Object eb = contact.getFixtureB().getBody().getUserData();

        if (ea == null || eb == null) {
            return null;
        }

        return getResolver(ea, eb);
    }

    public void addResolver(ContactResolver resolver, Class objectAClass, Class objectBClass) {
        int hash = objectAClass.hashCode() + objectBClass.hashCode();
        resolvers.put(hash, resolver);
    }

    private ContactResolver getResolver(Object o1, Object o2) {
        int hash = o1.getClass().hashCode() + o2.getClass().hashCode();
        return resolvers.get(hash);
    }

    private void initResolvers() {
        addResolver(new PlayerPortalContactResolver(), Player.class, InvisiblePortal.class);
        addResolver(new PlayerObjectsContactResolver(), Player.class, WorldObject.class);
    }
}
