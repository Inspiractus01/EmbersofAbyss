package entity;

import map.Tile;
import nl.saxion.app.SaxionApp;
import nl.saxion.app.interaction.KeyboardEvent;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import main.GameSettings;
import game.Camera;

public class Player {
    private int x = 500;
    private int y = 500 - GameSettings.tileSize;
    private final int size = GameSettings.tileSize;
    private final int moveSpeed = 4;
    private int verticalVelocity = -20;
    private final int gravity = 3;
    private boolean isJumping = false;
    private boolean isFalling = false;
    private boolean canJump = true;
    private long lastJumpTime = 0;
    private final Set<Integer> activeKeys = new HashSet<>();
    private int stamina = 100;
    private final int maxStamina = 100;
    private final int staminaDepletion = 20;
    private final int staminaAttack = 15;
    private long lastStaminaChange = 0;
    private final int staminaRegenDelay = 3000;
    private boolean hasJumped = false; // Flag to track jump initiation
    private int health = 100;
    private final int attackCooldown = 1000;
    private long lastAttackTime = 0;
    private boolean isAttacking = false; // Flag to track if an attack is in progress
    private long attackStartTime = 0; // Time when the attack started
    private boolean attackHit = false; // Flag to indicate when the attack should hit

    // Animation frames
    private List<String> idleFrames = new ArrayList<>();
    private List<String> walkingFrames = new ArrayList<>();
    private List<String> jumpingFrames = new ArrayList<>();
    private List<String> fallingFrames = new ArrayList<>();
    private List<String> attackingFrames = new ArrayList<>();
    
    private int currentFrame = 0;
    private long lastFrameTime = 0;
    private final int frameDuration = 100; // Duration for each frame in milliseconds
    private List<String> previousFrames = null; // To track the previous animation frames

    // Collision box size (smaller than the player's visible size)
    private final int collisionBoxWidth = size - 20; // 10 pixels smaller in width
    private final int collisionBoxHeight = size - 0; // 0 pixels smaller in height
    private Rectangle collisionBox;

    public Player() {
        // Initialize the collision box
        updateCollisionBox();
        loadAnimationFrames();
    }

    private void loadAnimationFrames() {
        // Load idle frames
        idleFrames.add("assets/images/Player/idle/Player1.png");
        idleFrames.add("assets/images/Player/idle/Player2.png");
        idleFrames.add("assets/images/Player/idle/Player3.png");
        idleFrames.add("assets/images/Player/idle/Player4.png");
        idleFrames.add("assets/images/Player/idle/Player5.png");
        idleFrames.add("assets/images/Player/idle/Player6.png");
        idleFrames.add("assets/images/Player/idle/Player7.png");
        idleFrames.add("assets/images/Player/idle/Player8.png");
        idleFrames.add("assets/images/Player/idle/Player9.png");
        idleFrames.add("assets/images/Player/idle/Player10.png");
        idleFrames.add("assets/images/Player/idle/Player11.png");
        idleFrames.add("assets/images/Player/idle/Player12.png");

        // Load walking frames
        walkingFrames.add("assets/images/Player/run/Player25.png");
        walkingFrames.add("assets/images/Player/run/Player26.png");
        walkingFrames.add("assets/images/Player/run/Player27.png");
        walkingFrames.add("assets/images/Player/run/Player28.png");
        walkingFrames.add("assets/images/Player/run/Player29.png");
        walkingFrames.add("assets/images/Player/run/Player30.png");
        walkingFrames.add("assets/images/Player/run/Player31.png");
        walkingFrames.add("assets/images/Player/run/Player32.png");

        // Load jumping frames
        jumpingFrames.add("assets/images/Player/jump/Player41.png");
        jumpingFrames.add("assets/images/Player/jump/Player42.png");
        jumpingFrames.add("assets/images/Player/jump/Player43.png");
        jumpingFrames.add("assets/images/Player/jump/Player44.png");

        // Load falling frames
        fallingFrames.add("assets/images/Player/fall/Player57.png");
        fallingFrames.add("assets/images/Player/fall/Player58.png");
        fallingFrames.add("assets/images/Player/fall/Player59.png");
        fallingFrames.add("assets/images/Player/fall/Player60.png");

        // Load attacking frames
        attackingFrames.add("assets/images/Player/attack/Player73.png");
        attackingFrames.add("assets/images/Player/attack/Player74.png");
        attackingFrames.add("assets/images/Player/attack/Player75.png");
        attackingFrames.add("assets/images/Player/attack/Player76.png");
        attackingFrames.add("assets/images/Player/attack/Player77.png");
        attackingFrames.add("assets/images/Player/attack/Player78.png");
        attackingFrames.add("assets/images/Player/attack/Player79.png");
        attackingFrames.add("assets/images/Player/attack/Player80.png");
        attackingFrames.add("assets/images/Player/attack/Player81.png");
        attackingFrames.add("assets/images/Player/attack/Player82.png");
    }

    public void update(List<Enemy> enemies, List<Tile> tiles, Camera camera) {
        // Handle horizontal movement
        if (activeKeys.contains(KeyEvent.VK_A)) {
            if (!willCollide(x - moveSpeed, y, tiles)) {
                x -= moveSpeed; // Move left
            }
        }
        if (activeKeys.contains(KeyEvent.VK_D)) {
            if (!willCollide(x + moveSpeed, y, tiles)) {
                x += moveSpeed; // Move right
            }
        }

        // Update vertical movement
        if (isJumping || !isOnGround(tiles)) {
            y += verticalVelocity / 2;
            verticalVelocity += gravity / 2; // Simulate gravity

            // Check for collision when moving upwards
            if (verticalVelocity < 0) {
                for (Tile tile : tiles) {
                    Rectangle tileBounds = tile.getBounds();
                    if (tile.isSolid() && getCollisionBoxWithOffset(0, verticalVelocity).intersects(tileBounds)) {
                        y = tileBounds.y + tileBounds.height;
                        verticalVelocity = 0;
                        isJumping = false;
                        isFalling = true;
                        canJump = false;
                        lastJumpTime = System.currentTimeMillis();
                        break;
                    }
                }
            }
        } else {
            verticalVelocity = 0; // Reset vertical velocity if on ground
        }

        // Resolve landing
        if (verticalVelocity > 0) {
            for (Tile tile : tiles) {
                Rectangle tileBounds = tile.getBounds();
                if (tile.isSolid() && getCollisionBoxWithOffset(0, verticalVelocity).intersects(tileBounds)) {
                    y = tileBounds.y - size;
                    verticalVelocity = 0;
                    isJumping = false;
                    isFalling = false;
                    canJump = false;
                    lastJumpTime = System.currentTimeMillis();
                    break;
                }
            }
        }

        // Cool down jumping
        if (!canJump && System.currentTimeMillis() - lastJumpTime >= 500) {
            if (isOnGround(tiles)) { // Ensure player is on the ground before allowing jump reset
                canJump = true;
            }
        }

        // Initiate a jump
        if (!isJumping && canJump && (activeKeys.contains(KeyEvent.VK_W) || activeKeys.contains(KeyEvent.VK_SPACE))
                && stamina >= staminaDepletion && !hasJumped) {
            isJumping = true;
            isFalling = false;
            hasJumped = true; // Mark jump initiation
            verticalVelocity = -20;
            stamina -= staminaDepletion;
            lastStaminaChange = System.currentTimeMillis();
        }

        // Reset hasJumped when keys are released
        if (!(activeKeys.contains(KeyEvent.VK_W) || activeKeys.contains(KeyEvent.VK_SPACE))) {
            hasJumped = false;
        }

        // Set falling state
        if (verticalVelocity > 0 && !isOnGround(tiles)) {
            isFalling = true;
        }

        // Regenerate stamina
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastStaminaChange >= staminaRegenDelay && stamina < maxStamina) {
            if (currentTime - lastStaminaChange >= 200) {
                stamina += 1;
                if (stamina > maxStamina) {
                    stamina = maxStamina;
                }
                lastStaminaChange += 10;
            }
        }

        // Handle attack hit delay
        if (isAttacking && !attackHit && System.currentTimeMillis() - attackStartTime >= 400) {
            performAttack(enemies, camera);
            attackHit = true;
        }

        // Update the collision box position
        updateCollisionBox();
    }

    private boolean willCollide(int futureX, int futureY, List<Tile> tiles) {
        Rectangle futureBounds = new Rectangle(futureX + (size - collisionBoxWidth) / 2,
                futureY + (size - collisionBoxHeight) / 2, collisionBoxWidth, collisionBoxHeight);
        for (Tile tile : tiles) {
            if (tile.isSolid() && futureBounds.intersects(tile.getBounds())) {
                return true;
            }
        }
        return false;
    }

    private boolean isOnGround(List<Tile> tiles) {
        Rectangle belowBounds = new Rectangle(x + (size - collisionBoxWidth) / 2,
                y + size, collisionBoxWidth, 2);
        for (Tile tile : tiles) {
            if (tile.isSolid() && belowBounds.intersects(tile.getBounds())) {
                return true;
            }
        }
        return false;
    }

    private void performAttack(List<Enemy> enemies, Camera camera) {
        Rectangle attackHitbox = new Rectangle(x + 35, y, size, size);  //To change the attack hitbox size, change the values of x or add some to the size

        for (Enemy enemy : enemies) {
            if (attackHitbox.intersects(enemy.getBounds())) {
                enemy.takeDamage(10);
            }
        }

        // Draw the attack hitbox around the player
        SaxionApp.setFill(Color.RED);
        SaxionApp.drawRectangle(attackHitbox.x - camera.getX(), attackHitbox.y - camera.getY(), attackHitbox.width, attackHitbox.height);
    }

    public void render(Camera camera) {
        // Draw the collision box for debugging
        SaxionApp.setBorderColor(Color.BLUE); // Set a different color for the collision box
        SaxionApp.setFill(null);
        SaxionApp.drawRectangle(collisionBox.x - camera.getX(), collisionBox.y - camera.getY(), collisionBox.width, collisionBox.height);

        // Determine the current animation frames
        List<String> currentFrames;
        if (isAttacking) {
            currentFrames = attackingFrames;
            // Check if the attack animation duration has passed
            if (System.currentTimeMillis() - attackStartTime >= attackingFrames.size() * frameDuration) {
                isAttacking = false;
                attackHit = false; // Reset attack hit flag
            }
        } else if (isJumping && !isFalling) {
            currentFrames = jumpingFrames;
        } else if (isFalling) {
            currentFrames = fallingFrames;
        } else if (activeKeys.contains(KeyEvent.VK_A) || activeKeys.contains(KeyEvent.VK_D)) {
            currentFrames = walkingFrames;
        } else {
            currentFrames = idleFrames;
        }

        // Reset currentFrame if the animation state has changed
        if (currentFrames != previousFrames) {
            currentFrame = 0;
            previousFrames = currentFrames;
        }

        // Ensure currentFrames is not empty
        if (!currentFrames.isEmpty()) {
            // Update the current frame based on time
            if (System.currentTimeMillis() - lastFrameTime >= frameDuration) {
                currentFrame = (currentFrame + 1) % currentFrames.size();
                lastFrameTime = System.currentTimeMillis();
            }

            // Draw the current frame
            SaxionApp.drawImage(currentFrames.get(currentFrame), x - camera.getX() - 20, y - camera.getY() - 35, size + 60, size + 60);
        }
    }

    private void updateCollisionBox() {
        collisionBox = new Rectangle(x + (size - collisionBoxWidth) / 2,
                y + (size - collisionBoxHeight) / 2, collisionBoxWidth, collisionBoxHeight);
    }

    private Rectangle getCollisionBoxWithOffset(int offsetX, int offsetY) {
        return new Rectangle(collisionBox.x + offsetX, collisionBox.y + offsetY,
                collisionBox.width, collisionBox.height);
    }

    public void handleKeyboard(KeyboardEvent keyboardEvent) {
        int keyCode = keyboardEvent.getKeyCode();
        if (keyboardEvent.isKeyPressed()) {
            activeKeys.add(keyCode);
            if ((keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_SPACE) && !isJumping && canJump
                    && stamina >= staminaDepletion && !hasJumped) {
                isJumping = true;
                isFalling = false;
                hasJumped = true;
                verticalVelocity = -20;
                stamina -= staminaDepletion;
                lastStaminaChange = System.currentTimeMillis();
            }
            if (keyCode == KeyEvent.VK_K && System.currentTimeMillis() - lastAttackTime > attackCooldown && stamina >= staminaDepletion) {
                isAttacking = true;
                attackStartTime = System.currentTimeMillis();
                lastAttackTime = attackStartTime;
                attackHit = false; // Reset attack hit flag
                lastStaminaChange = System.currentTimeMillis(); 
                stamina -= staminaAttack;
            }
        } else {
            activeKeys.remove(keyCode);
            if (keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_SPACE) {
                hasJumped = false;
            }
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getHealth() {
        return health;
    }

    public int getStamina() {
        return stamina;
    }

    public int getMaxStamina() {
        return maxStamina;
    }
}