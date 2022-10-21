/*    */ package com.mojang.minecraft.particle;
/*    */ 
/*    */ import com.mojang.minecraft.Entity;
/*    */ import com.mojang.minecraft.level.Level;
/*    */ import com.mojang.minecraft.level.Tesselator;
/*    */ 
/*    */ public class Particle
/*    */   extends Entity {
/*    */   private float xd;
/*    */   private float yd;
/*    */   private float zd;
/* 12 */   private int age = 0; public int tex; private float uo; private float vo;
/* 13 */   private int lifetime = 0;
/*    */   
/*    */   private float size;
/*    */   
/*    */   public Particle(Level level, float x, float y, float z, float xa, float ya, float za, int tex) {
/* 18 */     super(level);
/* 19 */     this.tex = tex;
/* 20 */     setSize(0.2F, 0.2F);
/* 21 */     this.heightOffset = this.bbHeight / 2.0F;
/* 22 */     setPos(x, y, z);
/*    */     
/* 24 */     this.xd = xa + (float)(Math.random() * 2.0D - 1.0D) * 0.4F;
/* 25 */     this.yd = ya + (float)(Math.random() * 2.0D - 1.0D) * 0.4F;
/* 26 */     this.zd = za + (float)(Math.random() * 2.0D - 1.0D) * 0.4F;
/* 27 */     float speed = (float)(Math.random() + Math.random() + 1.0D) * 0.15F;
/*    */     
/* 29 */     float dd = (float)Math.sqrt((this.xd * this.xd + this.yd * this.yd + this.zd * this.zd));
/* 30 */     this.xd = this.xd / dd * speed * 0.4F;
/* 31 */     this.yd = this.yd / dd * speed * 0.4F + 0.1F;
/* 32 */     this.zd = this.zd / dd * speed * 0.4F;
/*    */ 
/*    */     
/* 35 */     this.uo = (float)Math.random() * 3.0F;
/* 36 */     this.vo = (float)Math.random() * 3.0F;
/*    */     
/* 38 */     this.size = (float)(Math.random() * 0.5D + 0.5D);
/*    */     
/* 40 */     this.lifetime = (int)(4.0D / (Math.random() * 0.9D + 0.1D));
/* 41 */     this.age = 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public void tick() {
/* 46 */     this.xo = this.x;
/* 47 */     this.yo = this.y;
/* 48 */     this.zo = this.z;
/*    */     
/* 50 */     if (this.age++ >= this.lifetime) remove();
/*    */     
/* 52 */     this.yd = (float)(this.yd - 0.04D);
/* 53 */     move(this.xd, this.yd, this.zd);
/* 54 */     this.xd *= 0.98F;
/* 55 */     this.yd *= 0.98F;
/* 56 */     this.zd *= 0.98F;
/*    */     
/* 58 */     if (this.onGround) {
/*    */       
/* 60 */       this.xd *= 0.7F;
/* 61 */       this.zd *= 0.7F;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void render(Tesselator t, float a, float xa, float ya, float za, float xa2, float za2) {
/* 67 */     float u0 = ((this.tex % 16) + this.uo / 4.0F) / 16.0F;
/* 68 */     float u1 = u0 + 0.015609375F;
/* 69 */     float v0 = ((this.tex / 16) + this.vo / 4.0F) / 16.0F;
/* 70 */     float v1 = v0 + 0.015609375F;
/* 71 */     float r = 0.1F * this.size;
/*    */     
/* 73 */     float x = this.xo + (this.x - this.xo) * a;
/* 74 */     float y = this.yo + (this.y - this.yo) * a;
/* 75 */     float z = this.zo + (this.z - this.zo) * a;
/* 76 */     t.vertexUV(x - xa * r - xa2 * r, y - ya * r, z - za * r - za2 * r, u0, v1);
/* 77 */     t.vertexUV(x - xa * r + xa2 * r, y + ya * r, z - za * r + za2 * r, u0, v0);
/* 78 */     t.vertexUV(x + xa * r + xa2 * r, y + ya * r, z + za * r + za2 * r, u1, v0);
/* 79 */     t.vertexUV(x + xa * r - xa2 * r, y - ya * r, z + za * r - za2 * r, u1, v1);
/*    */   }
/*    */ }


/* Location:              /home/segfault/.minecraft/versions/rd-20090515/rd-20090515.jar!/com/mojang/minecraft/particle/Particle.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */