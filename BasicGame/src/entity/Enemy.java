package entity;

import nl.saxion.app.SaxionApp;
import java.awt.Color;
import java.awt.Rectangle;
import game.Game;
import main.GameSettings;
import game.Camera;
import map.Tile;

import java.util.ArrayList;
import java.util.List;

public class Enemy {
    private int x, y;
    private int size = GameSettings.tileSize;
    private int health = 30;
    private boolean isDead = false;
    private int moveSpeed = 1;
    private int runSpeed = 2; // Speed when running towards the player
    private int leftBorder, rightBorder, topBorder, bottomBorder;
    private long lastAttackTime = 0;
    private final int attackCooldown = 2000; // Time between attacks in milliseconds
    private final int attackRange = size + 20; // Distance within which the enemy will attack the player
    private final int detectionRange = 200; // Distance within which the enemy will detect the player
    private final int gravity = 3;
    private int verticalVelocity = 0;
    private Game game;
    private boolean isAttacking = false;
    private long attackStartTime = 0;
    private final int attackDelay = 1000; // 2 seconds delay for attack animation
    private boolean playerDetected = false;
    private boolean facingRight = true;

    // Animation frames
    private List<String> idleFramesLeft = new ArrayList<>();
    private List<String> idleFramesRight = new ArrayList<>();
    private List<String> runningFramesLeft = new ArrayList<>();
    private List<String> runningFramesRight = new ArrayList<>();
    private List<String> attackingFramesLeft = new ArrayList<>();
    private List<String> attackingFramesRight = new ArrayList<>();
    private List<String> deathFramesLeft = new ArrayList<>();
    private List<String> deathFramesRight = new ArrayList<>();
    private int currentFrame = 0;
    private long lastFrameTime = 0;
    private final int frameDuration = 100; // Duration for each frame in milliseconds
    private List<String> previousFrames = null; // To track the previous animation frames

    public Enemy(int startX, int startY, int leftBorder, int rightBorder, int topBorder, int bottomBorder, Game game) {
        this.x = startX;
        this.y = startY;
        this.leftBorder = leftBorder;
        this.rightBorder = rightBorder;
        this.topBorder = topBorder;
        this.bottomBorder = bottomBorder;
        this.game = game;
        loadAnimationFrames();
    }

    private void loadAnimationFrames() {
        idleFramesLeft = AnimationLoader.loadEnemyIdleFramesLeft();
        idleFramesRight = AnimationLoader.loadEnemyIdleFramesRight();
        runningFramesLeft = AnimationLoader.loadEnemyRunningFramesLeft();
        runningFramesRight = AnimationLoader.loadEnemyRunningFramesRight();
        attackingFramesLeft = AnimationLoader.loadEnemyAttackingFramesLeft();
        attackingFramesRight = AnimationLoader.loadEnemyAttackingFramesRight();
        deathFramesLeft = AnimationLoader.loadEnemyDeathFramesLeft();
        deathFramesRight = AnimationLoader.loadEnemyDeathFramesRight();
    }

    public void takeDamage(int damage) {
        health -= damage;
        if (health <= 0) {
            die();
        }
    }

    private void die() {
        isDead = true;
        System.out.println("Enemy marked as dead");
    }

    public boolean isDead() {
        return isDead;
    }

    public Rectangle getBounds() {
        if (isDead) {
            return new Rectangle(0, 0, 0, 0);
        }
        return new Rectangle(x, y, size, size);
    }

    public void update(Player player, List<Tile> tiles) {
        if (isDead) {
            return;
        }

        // Apply gravity
        verticalVelocity += gravity;
        y += verticalVelocity;

        // Check for collision when moving downwards
        boolean onGround = false;
        if (verticalVelocity > 0) {
            for (Tile tile : tiles) {
                Rectangle tileBounds = tile.getBounds();
                if (tile.isSolid() && getCollisionBoxWithOffset(0, verticalVelocity).intersects(tileBounds)) {
                    y = tileBounds.y - size;
                    verticalVelocity = 0;
                    onGround = true;
                    break;
                }
            }
        }

        // Ensure enemy stays on the ground
        if (!onGround) {
            verticalVelocity += gravity;
        } else {
            verticalVelocity = 0;
        }

        long currentTime = System.currentTimeMillis();

        // Check if player is within detection range
        if (Math.abs(player.getX() - x) < detectionRange && Math.abs(player.getY() - y) < detectionRange) {
            playerDetected = true;
            // Check if player is within attack range
            if (Math.abs(player.getX() - x) < attackRange && Math.abs(player.getY() - y) < attackRange) {
                if (!isAttacking && currentTime - lastAttackTime >= attackCooldown) {
                    isAttacking = true;
                    attackStartTime = currentTime;
                }
            }
        }

        if (isAttacking) {
            // Wait for attack delay
            if (currentTime - attackStartTime >= attackDelay) {
                Rectangle attackBox = getAttackBox();
                if (attackBox.intersects(player.getBounds())) {
                    player.takeDamage(15); // Attack the player if within attack box
                }
                lastAttackTime = currentTime;
                isAttacking = false;
            }
        } else if (playerDetected) {
            // Move towards the player if not too close
            if (Math.abs(player.getX() - x) > size / 2) {
                if (player.getX() > x) {
                    x += runSpeed;
                    facingRight = true;
                } else if (player.getX() < x) {
                    x -= runSpeed;
                    facingRight = false;
                }

                if (player.getY() > y) {
                    y += runSpeed;
                } else if (player.getY() < y) {
                    y -= runSpeed;
                }
            }
        } else {
            // Wandering logic
            if (x < leftBorder || x > rightBorder) {
                moveSpeed = -moveSpeed; // Change direction when hitting borders
                facingRight = !facingRight;
            }
            x += moveSpeed;
        }

        // Update the current frame based on time
        if (System.currentTimeMillis() - lastFrameTime >= frameDuration) {
            currentFrame = (currentFrame + 1) % getCurrentFrames().size();
            lastFrameTime = System.currentTimeMillis();
        }
    }

    private List<String> getCurrentFrames() {
        if (isDead) {
            return facingRight ? deathFramesRight : deathFramesLeft;
        } else if (isAttacking) {
            return facingRight ? attackingFramesRight : attackingFramesLeft;
        } else if (playerDetected || moveSpeed != 0) {
            return facingRight ? runningFramesRight : runningFramesLeft;
        } else {
            return facingRight ? idleFramesRight : idleFramesLeft;
        }
    }

    private Rectangle getCollisionBoxWithOffset(int offsetX, int offsetY) {
        return new Rectangle(x + offsetX, y + offsetY, size, size);
    }

    private Rectangle getAttackBox() {
        int attackBoxWidth = size;
        int attackBoxHeight = size;
        if (facingRight) {
            return new Rectangle(x + size, y, attackBoxWidth, attackBoxHeight);
        } else {
            return new Rectangle(x - attackBoxWidth, y, attackBoxWidth, attackBoxHeight);
        }
    }

    public void draw(Camera camera) {
        if (!isDead) {
            List<String> currentFrames = getCurrentFrames();
            if (!currentFrames.isEmpty() && currentFrame < currentFrames.size()) {
                String currentFrameImage = currentFrames.get(currentFrame);
                SaxionApp.drawImage(currentFrameImage, x - camera.getX(), y - camera.getY()-29, size+50, size+50);
            }

            // Draw attack range
            Color transparentOrange = new Color(255, 165, 0, 0); // Orange with alpha for transparency
            SaxionApp.setFill(transparentOrange);
            Rectangle attackBox = getAttackBox();
            if (facingRight) {
                SaxionApp.drawRectangle(attackBox.x - camera.getX()-30, attackBox.y - camera.getY(), attackBox.width+50, attackBox.height);
            } else {
                SaxionApp.drawRectangle(attackBox.x - camera.getX()+50, attackBox.y - camera.getY(), attackBox.width+50, attackBox.height);
            }
        }
    }
}