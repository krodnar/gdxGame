package com.mygdx.game.contacts;

import com.badlogic.gdx.physics.box2d.Contact;

/**
 * A ContactResolver is used to handle contacts of a two fixtures in Box2D world.
 * <p>
 * Usually resolving of a contact is based on user data that which was attached to a fixture.
 * The most popular way of doing it is to bind entities to a bodies and get them on a contact.
 * <p>
 * This interface provides a generic method of resolving a contacts. If you need to create
 * a new resolver its better to extend from {@link AbstractContactResolver} and add it to
 * {@link ContactManager}'s default resolvers.
 *
 * @param <V> first entity
 * @param <S> second entity
 * @see Contact
 * @see AbstractContactResolver
 * @see ContactManager
 */
public interface ContactResolver<V, S> {

    /**
     * Resolves beginning of a {@link Contact contact} of two fixtures.
     *
     * @param contact contact of fixtures
     */
    void resolveBeginContact(Contact contact);

    /**
     * Resolves beginning of a {@link Contact contact} of two entities.
     *
     * @param o1 first entity
     * @param o2 second entity
     */
    void resolveBeginContact(V o1, S o2);

    /**
     * Resolves ending of a {@link Contact contact} of two fixtures.
     *
     * @param contact contact of fixtures
     */
    void resolveEndContact(Contact contact);

    /**
     * Resolves ending of a {@link Contact contact} of two entities.
     *
     * @param o1 first entity
     * @param o2 second entity
     */
    void resolveEndContact(V o1, S o2);
}
