package com.mygdx.game.utils;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

import static com.mygdx.game.utils.Constants.PPM;

/**
 * A builder for a Box2D bodies.
 * <p>
 * Default properties:
 * <pre>
 * Circle shape, radius 16
 *
 * Fixed rotation   = false;
 * Bullet           = false;
 * Active           = true;
 * Allow sleep      = true;
 * Awake            = true;
 * Is sensor        = false;
 *
 * Linear damping   = 0.0f;
 * Angular damping  = 0.0f;
 * Density          = 1.0f;
 * Angle            = 0.0f;
 * Gravity scale    = 1.0f;
 * Friction         = 0.2f;
 * Restitution      = 0.0f;
 * </pre>
 *
 * @see Body
 * @see World
 * @see BodyDef
 */
@SuppressWarnings({"UnusedReturnValue", "unused"})
public class BodyBuilder {

    private final World world;

    private BodyDef.BodyType bodyType = BodyDef.BodyType.StaticBody;
    private Shape shape = new CircleShape();
    private Vector2 position = new Vector2();
    private Vector2 linearVelocity = new Vector2();

    private boolean fixedRotation = false;
    private boolean bullet = false;
    private boolean active = true;
    private boolean allowSleep = true;
    private boolean awake = true;
    private boolean isSensor = false;

    private float linearDamping = 0.0f;
    private float angularDamping = 0.0f;
    private float density = 1.0f;
    private float angle = 0.0f;
    private float gravityScale = 1.0f;
    private float friction = 0.2f;
    private float restitution = 0;

    /**
     * Creates a builder for a Box2D world with set default circle shape.
     *
     * @param world Box2D world to create bodies in
     * @see Body
     * @see Shape
     */
    public BodyBuilder(World world) {
        this.world = world;
        shape.setRadius(16 / PPM);
    }

    //region Setters
    public BodyBuilder setBodyType(BodyDef.BodyType bodyType) {
        this.bodyType = bodyType;
        return this;
    }

    public BodyBuilder setPosition(Vector2 position) {
        this.position = position.cpy().scl(1 / PPM);
        return this;
    }

    public BodyBuilder setPosition(float x, float y) {
        this.position.set(x / PPM, y / PPM);
        return this;
    }

    public BodyBuilder setFixedRotation(boolean fixedRotation) {
        this.fixedRotation = fixedRotation;
        return this;
    }

    public BodyBuilder setAngularDamping(float angularDamping) {
        this.angularDamping = angularDamping;
        return this;
    }

    public BodyBuilder setDensity(float density) {
        this.density = density;
        return this;
    }

    public BodyBuilder setBullet(boolean bullet) {
        this.bullet = bullet;
        return this;
    }

    public BodyBuilder setLinearDamping(float linearDamping) {
        this.linearDamping = linearDamping;
        return this;
    }

    public BodyBuilder setAngle(float angle) {
        this.angle = angle;
        return this;
    }

    public BodyBuilder setLinearVelocity(Vector2 linearVelocity) {
        this.linearVelocity = linearVelocity;
        return this;
    }

    public BodyBuilder setActive(boolean active) {
        this.active = active;
        return this;
    }

    public BodyBuilder setAllowSleep(boolean allowSleep) {
        this.allowSleep = allowSleep;
        return this;
    }

    public BodyBuilder setAwake(boolean awake) {
        this.awake = awake;
        return this;
    }

    public BodyBuilder setGravityScale(float gravityScale) {
        this.gravityScale = gravityScale;
        return this;
    }

    public BodyBuilder setShape(Shape shape) {
        this.shape = shape;
        return this;
    }

    public BodyBuilder setFriction(float friction) {
        this.friction = friction;
        return this;
    }

    public BodyBuilder setRestitution(float restitution) {
        this.restitution = restitution;
        return this;
    }

    public BodyBuilder setSensor(boolean sensor) {
        isSensor = sensor;
        return this;
    }
    //endregion

    public BodyBuilder setCircleShape(float radius) {
        shape = new CircleShape();
        shape.setRadius(radius / PPM);
        return this;
    }

    public BodyBuilder setBoxShape(float width, float height) {
        shape = new PolygonShape();
        ((PolygonShape) shape).setAsBox(width / PPM, height / PPM);
        return this;
    }

    public BodyBuilder setPolygonShape(float[] vertices) {
        shape = new PolygonShape();
        float[] worldVertices = pixelsToMeters(vertices);
        ((PolygonShape) shape).set(worldVertices);
        return this;
    }

    public BodyBuilder setPolygonShape(float[] vertices, int offset, int len) {
        shape = new PolygonShape();
        float[] worldVertices = pixelsToMeters(vertices);
        ((PolygonShape) shape).set(worldVertices);
        ((PolygonShape) shape).set(vertices, (int) (offset / PPM), (int) (len / PPM));
        return this;
    }

    public BodyBuilder setPolygonShape(Vector2[] vertices) {
        shape = new PolygonShape();
        Vector2[] worldVertices = pixelsToMeters(vertices);
        ((PolygonShape) shape).set(worldVertices);
        return this;
    }

    public BodyBuilder setEdgeShape(float v1X, float v1Y, float v2X, float v2Y) {
        shape = new EdgeShape();
        ((EdgeShape) shape).set(v1X / PPM, v1Y / PPM, v2X / PPM, v2Y / PPM);
        return this;
    }

    public BodyBuilder setEdgeShape(Vector2 v1, Vector2 v2) {
        shape = new EdgeShape();
        ((EdgeShape) shape).set(v1.scl(1 / PPM), v2.scl(1 / PPM));
        return this;
    }

    public BodyBuilder setChainShape(float[] vertices) {
        shape = new ChainShape();
        float[] worldVertices = pixelsToMeters(vertices);
        ((ChainShape) shape).createChain(worldVertices);
        return this;
    }

    public BodyBuilder setChainShape(float[] vertices, int offset, int len) {
        shape = new ChainShape();
        float[] worldVertices = pixelsToMeters(vertices);
        ((ChainShape) shape).createChain(worldVertices, (int) (offset / PPM), (int) (len / PPM));
        return this;
    }

    public BodyBuilder setChainShape(Vector2[] vertices) {
        shape = new ChainShape();
        Vector2[] worldVertices = pixelsToMeters(vertices);
        ((ChainShape) shape).createChain(worldVertices);
        return this;
    }

    /**
     * Creates a body with specified or default properties and disposes it's shape.
     *
     * @return the body
     */
    public Body build() {
        Body body;
        BodyDef bDef = new BodyDef();

        bDef.type = bodyType;
        bDef.bullet = bullet;
        bDef.fixedRotation = fixedRotation;
        bDef.active = active;
        bDef.allowSleep = allowSleep;
        bDef.awake = awake;

        bDef.linearDamping = linearDamping;
        bDef.angularDamping = angularDamping;

        bDef.angle = angle;
        bDef.gravityScale = gravityScale;

        bDef.position.set(position);
        bDef.linearVelocity.set(linearVelocity);
        body = world.createBody(bDef);

        FixtureDef fDef = new FixtureDef();
        fDef.shape = shape;
        fDef.density = density;
        fDef.friction = friction;
        fDef.isSensor = isSensor;
        fDef.restitution = restitution;

        Fixture fixture = body.createFixture(fDef);
        shape.dispose();

        return body;
    }

    private float[] pixelsToMeters(float[] vertices) {
        float[] worldVertices = new float[vertices.length];

        for (int i = 0; i < vertices.length; i++) {
            worldVertices[i] = vertices[i] / PPM;
        }

        return worldVertices;
    }

    private Vector2[] pixelsToMeters(Vector2[] vertices) {
        Vector2[] worldVertices = new Vector2[vertices.length];

        for (int i = 0; i < vertices.length; i++) {
            worldVertices[i] = vertices[i].scl(1 / PPM);
        }

        return worldVertices;
    }
}