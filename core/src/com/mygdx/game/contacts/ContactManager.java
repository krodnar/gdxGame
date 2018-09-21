package com.mygdx.game.contacts;

import com.badlogic.gdx.physics.box2d.Contact;
import com.mygdx.game.contacts.ContactResolver;
import com.mygdx.game.entities.InvisiblePortal;
import com.mygdx.game.contacts.PlayerObjectsContactResolver;
import com.mygdx.game.contacts.PlayerPortalContactResolver;
import com.mygdx.game.entities.Player;
import com.mygdx.game.entities.WorldObject;

import java.util.HashMap;

/**
 * A class that manages contacts of fixtures in Box2D world. The choice of a {@link ContactResolver}
 * is based on user data attached to a bodies in a {@link Contact contact}.
 *
 * @see Contact
 * @see ContactResolver
 */
public class ContactManager {

    private HashMap<Integer, ContactResolver> resolvers;

    /**
     * Creates ContactManager with default {@link ContactResolver contact resolvers}.
     */
    public ContactManager() {
        this.resolvers = new HashMap<Integer, ContactResolver>();

        initResolvers();
    }

    /**
     * Returns {@link ContactResolver contact resolver} for given {@link Contact}.
     *
     * @param contact Box2D contact of bodies
     * @return appropriate contact resolver or null if there is none
     */
    public ContactResolver getResolver(Contact contact) {

        Object ea = contact.getFixtureA().getBody().getUserData();
        Object eb = contact.getFixtureB().getBody().getUserData();

        if (ea == null || eb == null) {
            return null;
        }

        return getResolver(ea, eb);
    }

    /**
     * Adds a resolver to current list of resolvers.
     *
     * @param resolver     contact resolver
     * @param objectAClass class of a first body
     * @param objectBClass class of a second body
     */
    public void addResolver(ContactResolver resolver, Class objectAClass, Class objectBClass) {
        int hash = objectAClass.hashCode() + objectBClass.hashCode();
        resolvers.put(hash, resolver);
    }

    /**
     * @param o1 first body
     * @param o2 second body
     * @return appropriate contact resolver for bodies or null if there is none
     */
    private ContactResolver getResolver(Object o1, Object o2) {
        int hash = o1.getClass().hashCode() + o2.getClass().hashCode();
        return resolvers.get(hash);
    }

    private void initResolvers() {
        addResolver(new PlayerPortalContactResolver(), Player.class, InvisiblePortal.class);
        addResolver(new PlayerObjectsContactResolver(), Player.class, WorldObject.class);
    }
}
