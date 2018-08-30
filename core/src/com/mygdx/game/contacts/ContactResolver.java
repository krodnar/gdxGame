package com.mygdx.game.contacts;

import com.badlogic.gdx.physics.box2d.Contact;

public interface ContactResolver<V, S> {

    void resolveBeginContact(Contact contact);

    void resolveBeginContact(V o1, S o2);

    void resolveEndContact(Contact contact);

    void resolveEndContact(V o1, S o2);
}
