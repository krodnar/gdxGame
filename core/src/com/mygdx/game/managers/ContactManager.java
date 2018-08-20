package com.mygdx.game.managers;

import com.badlogic.gdx.physics.box2d.Contact;
import com.mygdx.game.contacts.ContactResolver;
import com.mygdx.game.utils.DoubleKeyMap;
import com.mygdx.game.contacts.PlayerObjectsContactResolver;
import com.mygdx.game.contacts.PlayerPortalContactResolver;
import com.mygdx.game.entities.Player;
import com.mygdx.game.entities.Portal;
import com.mygdx.game.entities.WorldObject;

public class ContactManager {

    private DoubleKeyMap<Class, ContactResolver> resolvers;

    public ContactManager() {
        this.resolvers = new DoubleKeyMap<Class, ContactResolver>();

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
        resolvers.put(objectAClass, objectBClass, resolver);
    }

    private ContactResolver getResolver(Object o1, Object o2) {
        return resolvers.get(o1.getClass(), o2.getClass());
    }

    private void initResolvers() {
        addResolver(new PlayerPortalContactResolver(), Player.class, Portal.class);
        addResolver(new PlayerObjectsContactResolver(), Player.class, WorldObject.class);
    }
}
