package com.mygdx.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Application;
import com.mygdx.game.IGameRenderer;
import com.mygdx.game.utils.BodyBuilder;

public class Portal extends AbstractEntity {

    private Body body;
    private Vector2 portDestination = new Vector2();

    public Portal(Application app, World world, Shape shape) {
        super(app, world);
        createBody(shape);
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void render(IGameRenderer renderer, float delta) {

    }

    @Override
    public void dispose() {

    }

    public void setPortDestination(Vector2 portDestination) {
        this.portDestination = portDestination;
    }

    public Vector2 getPortDestination() {
        return portDestination;
    }

    private void createBody(Shape shape) {
        BodyBuilder builder = new BodyBuilder(world);
        builder
                .setBodyType(BodyDef.BodyType.StaticBody)
                .setSensor(true)
                .setShape(shape);

        body = builder.build();
        body.setUserData(this);
    }

    public void portPlayer(final Player player) {
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                player.setPosition(portDestination);
            }
        });
    }
}
