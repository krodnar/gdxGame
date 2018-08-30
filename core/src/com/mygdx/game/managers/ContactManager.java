package com.mygdx.game.managers;

import com.badlogic.gdx.physics.box2d.Contact;
import com.mygdx.game.contacts.ContactResolver;
import com.mygdx.game.contacts.PlayerObjectsContactResolver;
import com.mygdx.game.contacts.PlayerPortalContactResolver;
import com.mygdx.game.entities.Player;
import com.mygdx.game.entities.Portal;
import com.mygdx.game.entities.WorldObject;

import java.util.HashMap;

public class ContactManager {

    private HashMap<Integer, ContactResolver> resolvers;

    public ContactManager() {
        this.resolvers = new HashMap<Integer, ContactResolver>();

        initResolvers();
    }

    public ContactResolver getResolver(Contact contact) {

        Object o1 = contact.getFixtureA().getBody().getUserData();
        Object o2 = contact.getFixtureB().getBody().getUserData();

        if (o1 == null || o2 == null) {
            return null;
        }

        return getResolver(o1, o2);
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
        addResolver(new PlayerPortalContactResolver(), Player.class, Portal.class);
        addResolver(new PlayerObjectsContactResolver(), Player.class, WorldObject.class);
    }
}
