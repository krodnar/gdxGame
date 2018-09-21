package com.mygdx.game.contacts;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;

/**
 * A partial implementation of a {@link ContactResolver} for easy use of an end implementation.
 * <p>
 * Fixtures in contact can be in any order depending on many factors and this class provides an easy
 * way of dealing with determining which of the fixtures is which based on user data attached to
 * them.
 *
 * @param <V> first entity
 * @param <S> second entity
 * @see com.badlogic.gdx.physics.box2d.Body
 */
public abstract class AbstractContactResolver<V, S> implements ContactResolver<V, S> {

    private Fixture fixtureA;
    private Fixture fixtureB;

    @Override
    public void resolveBeginContact(Contact contact) {
        determineFixtures(contact);

        V v = getObjectA(getFixtureA());
        S s = getObjectB(getFixtureB());

        resolveBeginContact(v, s);
    }

    @Override
    public void resolveEndContact(Contact contact) {
        determineFixtures(contact);

        V v = getObjectA(getFixtureA());
        S s = getObjectB(getFixtureB());

        resolveEndContact(v, s);
    }

    /**
     * Determines which fixture to which entity belongs.
     *
     * @param contact contact of bodies
     */
    protected void determineFixtures(Contact contact) {
        setFixtureA(contact.getFixtureA());
        setFixtureB(contact.getFixtureB());

        if (!isObjectA(getFixtureA())) {
            swapFixtures();
        }
    }

    /**
     * Checks if given {@link Fixture} contains proper user data - instance of first entity (V type).
     *
     * @param fixture fixture from a contact
     * @return true if user data is an instance of first entity, false otherwise
     */
    protected abstract boolean isObjectA(Fixture fixture);

    /**
     * @param fixture fixture from a contact
     * @return first entity
     */
    @SuppressWarnings("unchecked")
    protected V getObjectA(Fixture fixture) {
        return (V) fixture.getBody().getUserData();
    }

    /**
     * @param fixture fixture from a contact
     * @return second entity
     */
    @SuppressWarnings("unchecked")
    protected S getObjectB(Fixture fixture) {
        return (S) fixture.getBody().getUserData();
    }

    /**
     * @return fixture of a first entity
     */
    //region Get/Set
    protected Fixture getFixtureA() {
        return fixtureA;
    }

    protected void setFixtureA(Fixture fixtureA) {
        this.fixtureA = fixtureA;
    }

    /**
     * @return fixture of a second entity
     */
    protected Fixture getFixtureB() {
        return fixtureB;
    }

    protected void setFixtureB(Fixture fixtureB) {
        this.fixtureB = fixtureB;
    }
    //endregion

    private void swapFixtures() {
        Fixture temp = fixtureA;
        fixtureA = fixtureB;
        fixtureB = temp;
    }
}
