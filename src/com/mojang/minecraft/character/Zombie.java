/*    */ package com.mojang.minecraft.character;
/*    */ 
/*    */ import com.mojang.minecraft.Entity;
/*    */ import com.mojang.minecraft.Textures;
/*    */ import com.mojang.minecraft.level.Level;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ public class Zombie
/*    */   extends Entity {
/*    */   public float rot;
/*    */   public float timeOffs;
/*    */   public float speed;
/*    */   public float rotA;
/* 14 */   private static ZombieModel zombieModel = new ZombieModel();
/*    */ 
/*    */   
/*    */   public Zombie(Level level, float x, float y, float z) {
/* 18 */     super(level);
/* 19 */     this.rotA = (float)(Math.random() + 1.0D) * 0.01F;
/* 20 */     setPos(x, y, z);
/* 21 */     this.timeOffs = (float)Math.random() * 1239813.0F;
/* 22 */     this.rot = (float)(Math.random() * Math.PI * 2.0D);
/* 23 */     this.speed = 1.0F;
/*    */   }
/*    */ 
/*    */   
/*    */   public void tick() {
/* 28 */     this.xo = this.x;
/* 29 */     this.yo = this.y;
/* 30 */     this.zo = this.z;
/* 31 */     float xa = 0.0F;
/* 32 */     float ya = 0.0F;
/*    */     
/* 34 */     if (this.y < -100.0F) remove();
/*    */     
/* 36 */     this.rot += this.rotA;
/* 37 */     this.rotA = (float)(this.rotA * 0.99D);
/* 38 */     this.rotA = (float)(this.rotA + (Math.random() - Math.random()) * Math.random() * Math.random() * 0.07999999821186066D);
/* 39 */     xa = (float)Math.sin(this.rot);
/* 40 */     ya = (float)Math.cos(this.rot);
/*    */     
/* 42 */     if (this.onGround && Math.random() < 0.08D)
/*    */     {
/* 44 */       this.yd = 0.5F;
/*    */     }
/*    */     
/* 47 */     moveRelative(xa, ya, this.onGround ? 0.1F : 0.02F);
/*    */     
/* 49 */     this.yd = (float)(this.yd - 0.08D);
/* 50 */     move(this.xd, this.yd, this.zd);
/* 51 */     this.xd *= 0.91F;
/* 52 */     this.yd *= 0.98F;
/* 53 */     this.zd *= 0.91F;
/*    */     
/* 55 */     if (this.onGround) {
/*    */       
/* 57 */       this.xd *= 0.7F;
/* 58 */       this.zd *= 0.7F;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void render(float a) {
/* 64 */     GL11.glEnable(3553);
/* 65 */     GL11.glBindTexture(3553, Textures.loadTexture("/char.png", 9728));
/*    */     
/* 67 */     GL11.glPushMatrix();
/* 68 */     double time = System.nanoTime() / 1.0E9D * 10.0D * this.speed + this.timeOffs;
/*    */     
/* 70 */     float size = 0.058333334F;
/* 71 */     float yy = (float)(-Math.abs(Math.sin(time * 0.6662D)) * 5.0D - 23.0D);
/* 72 */     GL11.glTranslatef(this.xo + (this.x - this.xo) * a, this.yo + (this.y - this.yo) * a, this.zo + (this.z - this.zo) * a);
/* 73 */     GL11.glScalef(1.0F, -1.0F, 1.0F);
/* 74 */     GL11.glScalef(size, size, size);
/* 75 */     GL11.glTranslatef(0.0F, yy, 0.0F);
/* 76 */     float c = 57.29578F;
/* 77 */     GL11.glRotatef(this.rot * c + 180.0F, 0.0F, 1.0F, 0.0F);
/*    */     
/* 79 */     zombieModel.render((float)time);
/* 80 */     GL11.glPopMatrix();
/* 81 */     GL11.glDisable(3553);
/*    */   }
/*    */ }


/* Location:              /home/segfault/.minecraft/versions/rd-20090515/rd-20090515.jar!/com/mojang/minecraft/character/Zombie.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */