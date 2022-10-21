/*     */ package com.mojang.minecraft;
/*     */ 
/*     */ import com.mojang.minecraft.level.Level;
/*     */ import com.mojang.minecraft.phys.AABB;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Entity
/*     */ {
/*     */   protected Level level;
/*     */   public float xo;
/*     */   public float yo;
/*     */   public float zo;
/*     */   public float x;
/*     */   public float y;
/*     */   public float z;
/*  19 */   protected float heightOffset = 0.0F; public float xd; public float yd; public float zd; public float yRot; public float xRot; public AABB bb; public boolean onGround = false;
/*     */   public boolean removed = false;
/*  21 */   protected float bbWidth = 0.6F;
/*  22 */   protected float bbHeight = 1.8F;
/*     */ 
/*     */   
/*     */   public Entity(Level level) {
/*  26 */     this.level = level;
/*  27 */     resetPos();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void resetPos() {
/*  32 */     float x = (float)Math.random() * this.level.width;
/*  33 */     float y = (this.level.depth + 10);
/*  34 */     float z = (float)Math.random() * this.level.height;
/*  35 */     setPos(x, y, z);
/*     */   }
/*     */ 
/*     */   
/*     */   public void remove() {
/*  40 */     this.removed = true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setSize(float w, float h) {
/*  45 */     this.bbWidth = w;
/*  46 */     this.bbHeight = h;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setPos(float x, float y, float z) {
/*  51 */     this.x = x;
/*  52 */     this.y = y;
/*  53 */     this.z = z;
/*  54 */     float w = this.bbWidth / 2.0F;
/*  55 */     float h = this.bbHeight / 2.0F;
/*  56 */     this.bb = new AABB(x - w, y - h, z - w, x + w, y + h, z + w);
/*     */   }
/*     */ 
/*     */   
/*     */   public void turn(float xo, float yo) {
/*  61 */     this.yRot = (float)(this.yRot + xo * 0.15D);
/*  62 */     this.xRot = (float)(this.xRot - yo * 0.15D);
/*  63 */     if (this.xRot < -90.0F) this.xRot = -90.0F; 
/*  64 */     if (this.xRot > 90.0F) this.xRot = 90.0F;
/*     */   
/*     */   }
/*     */   
/*     */   public void tick() {
/*  69 */     this.xo = this.x;
/*  70 */     this.yo = this.y;
/*  71 */     this.zo = this.z;
/*     */   }
/*     */ 
/*     */   
/*     */   public void move(float xa, float ya, float za) {
/*  76 */     float xaOrg = xa;
/*  77 */     float yaOrg = ya;
/*  78 */     float zaOrg = za;
/*     */     
/*  80 */     List<AABB> aABBs = this.level.getCubes(this.bb.expand(xa, ya, za));
/*     */     int i;
/*  82 */     for (i = 0; i < aABBs.size(); i++)
/*  83 */       ya = ((AABB)aABBs.get(i)).clipYCollide(this.bb, ya); 
/*  84 */     this.bb.move(0.0F, ya, 0.0F);
/*     */     
/*  86 */     for (i = 0; i < aABBs.size(); i++)
/*  87 */       xa = ((AABB)aABBs.get(i)).clipXCollide(this.bb, xa); 
/*  88 */     this.bb.move(xa, 0.0F, 0.0F);
/*     */     
/*  90 */     for (i = 0; i < aABBs.size(); i++)
/*  91 */       za = ((AABB)aABBs.get(i)).clipZCollide(this.bb, za); 
/*  92 */     this.bb.move(0.0F, 0.0F, za);
/*     */     
/*  94 */     this.onGround = (yaOrg != ya && yaOrg < 0.0F);
/*     */     
/*  96 */     if (xaOrg != xa) this.xd = 0.0F; 
/*  97 */     if (yaOrg != ya) this.yd = 0.0F; 
/*  98 */     if (zaOrg != za) this.zd = 0.0F;
/*     */     
/* 100 */     this.x = (this.bb.x0 + this.bb.x1) / 2.0F;
/* 101 */     this.y = this.bb.y0 + this.heightOffset;
/* 102 */     this.z = (this.bb.z0 + this.bb.z1) / 2.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public void moveRelative(float xa, float za, float speed) {
/* 107 */     float dist = xa * xa + za * za;
/* 108 */     if (dist < 0.01F)
/*     */       return; 
/* 110 */     dist = speed / (float)Math.sqrt(dist);
/* 111 */     xa *= dist;
/* 112 */     za *= dist;
/*     */ 
/*     */     
/* 115 */     float sin = (float)Math.sin(this.yRot * Math.PI / 180.0D);
/* 116 */     float cos = (float)Math.cos(this.yRot * Math.PI / 180.0D);
/*     */     
/* 118 */     this.xd += xa * cos - za * sin;
/* 119 */     this.zd += za * cos + xa * sin;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isLit() {
/* 124 */     int xTile = (int)this.x;
/* 125 */     int yTile = (int)this.y;
/* 126 */     int zTile = (int)this.z;
/* 127 */     return this.level.isLit(xTile, yTile, zTile);
/*     */   }
/*     */ }


/* Location:              /home/segfault/.minecraft/versions/rd-20090515/rd-20090515.jar!/com/mojang/minecraft/Entity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */