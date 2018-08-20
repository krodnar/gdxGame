package com.mygdx.game.contacts;

import com.badlogic.gdx.physics.box2d.Contact;

public interface ContactResolver<V, S> {

    void resolveContact(Contact contact);

    void resolveContact(V o1, S o2);
}
