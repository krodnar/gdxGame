package com.mygdx.game.contacts;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.game.managers.ContactManager;

public class WorldContactListener implements ContactListener {

    private ContactManager contactManager;

    public WorldContactListener(ContactManager contactManager) {
        this.contactManager = contactManager;
    }

    @Override
    public void beginContact(Contact contact) {
        ContactResolver resolver = contactManager.getResolver(contact);

        if (resolver != null) {
            resolver.resolveContact(contact);
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
