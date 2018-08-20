package com.mygdx.game.contacts;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.Fixture;

public abstract class AbstractContactResolver<V, S> implements ContactResolver<V, S> {

    private Fixture fixtureA;
    private Fixture fixtureB;

    @Override
    public void resolveContact(Contact contact) {
        setFixtureA(contact.getFixtureA());
        setFixtureB(contact.getFixtureB());

        if (!isObjectA(getFixtureA())) {
            swapFixtures();
        }

        V v = getObjectA(getFixtureA());
        S s = getObjectB(getFixtureB());

        resolveContact(v, s);
    }

    protected abstract boolean isObjectA(Fixture fixture);

    @SuppressWarnings("unchecked")
    protected V getObjectA(Fixture fixture) {
        return (V) fixture.getBody().getUserData();
    }

    @SuppressWarnings("unchecked")
    protected S getObjectB(Fixture fixture) {
        return (S) fixture.getBody().getUserData();
    }

    //region Get/Set
    protected Fixture getFixtureA() {
        return fixtureA;
    }

    protected void setFixtureA(Fixture fixtureA) {
        this.fixtureA = fixtureA;
    }

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
