/*     */ package com.mojang.minecraft.phys;
/*     */ 
/*     */ public class AABB
/*     */ {
/*   5 */   private float epsilon = 0.0F;
/*     */   public float x0;
/*     */   public float y0;
/*     */   public float z0;
/*     */   
/*     */   public AABB(float x0, float y0, float z0, float x1, float y1, float z1) {
/*  11 */     this.x0 = x0;
/*  12 */     this.y0 = y0;
/*  13 */     this.z0 = z0;
/*  14 */     this.x1 = x1;
/*  15 */     this.y1 = y1;
/*  16 */     this.z1 = z1;
/*     */   }
/*     */   public float x1; public float y1; public float z1;
/*     */   
/*     */   public AABB expand(float xa, float ya, float za) {
/*  21 */     float _x0 = this.x0;
/*  22 */     float _y0 = this.y0;
/*  23 */     float _z0 = this.z0;
/*  24 */     float _x1 = this.x1;
/*  25 */     float _y1 = this.y1;
/*  26 */     float _z1 = this.z1;
/*     */     
/*  28 */     if (xa < 0.0F) _x0 += xa; 
/*  29 */     if (xa > 0.0F) _x1 += xa;
/*     */     
/*  31 */     if (ya < 0.0F) _y0 += ya; 
/*  32 */     if (ya > 0.0F) _y1 += ya;
/*     */     
/*  34 */     if (za < 0.0F) _z0 += za; 
/*  35 */     if (za > 0.0F) _z1 += za;
/*     */     
/*  37 */     return new AABB(_x0, _y0, _z0, _x1, _y1, _z1);
/*     */   }
/*     */ 
/*     */   
/*     */   public AABB grow(float xa, float ya, float za) {
/*  42 */     float _x0 = this.x0 - xa;
/*  43 */     float _y0 = this.y0 - ya;
/*  44 */     float _z0 = this.z0 - za;
/*  45 */     float _x1 = this.x1 + xa;
/*  46 */     float _y1 = this.y1 + ya;
/*  47 */     float _z1 = this.z1 + za;
/*     */     
/*  49 */     return new AABB(_x0, _y0, _z0, _x1, _y1, _z1);
/*     */   }
/*     */ 
/*     */   
/*     */   public float clipXCollide(AABB c, float xa) {
/*  54 */     if (c.y1 <= this.y0 || c.y0 >= this.y1) return xa; 
/*  55 */     if (c.z1 <= this.z0 || c.z0 >= this.z1) return xa;
/*     */     
/*  57 */     if (xa > 0.0F && c.x1 <= this.x0) {
/*     */       
/*  59 */       float max = this.x0 - c.x1 - this.epsilon;
/*  60 */       if (max < xa) xa = max; 
/*     */     } 
/*  62 */     if (xa < 0.0F && c.x0 >= this.x1) {
/*     */       
/*  64 */       float max = this.x1 - c.x0 + this.epsilon;
/*  65 */       if (max > xa) xa = max;
/*     */     
/*     */     } 
/*  68 */     return xa;
/*     */   }
/*     */ 
/*     */   
/*     */   public float clipYCollide(AABB c, float ya) {
/*  73 */     if (c.x1 <= this.x0 || c.x0 >= this.x1) return ya; 
/*  74 */     if (c.z1 <= this.z0 || c.z0 >= this.z1) return ya;
/*     */     
/*  76 */     if (ya > 0.0F && c.y1 <= this.y0) {
/*     */       
/*  78 */       float max = this.y0 - c.y1 - this.epsilon;
/*  79 */       if (max < ya) ya = max; 
/*     */     } 
/*  81 */     if (ya < 0.0F && c.y0 >= this.y1) {
/*     */       
/*  83 */       float max = this.y1 - c.y0 + this.epsilon;
/*  84 */       if (max > ya) ya = max;
/*     */     
/*     */     } 
/*  87 */     return ya;
/*     */   }
/*     */ 
/*     */   
/*     */   public float clipZCollide(AABB c, float za) {
/*  92 */     if (c.x1 <= this.x0 || c.x0 >= this.x1) return za; 
/*  93 */     if (c.y1 <= this.y0 || c.y0 >= this.y1) return za;
/*     */     
/*  95 */     if (za > 0.0F && c.z1 <= this.z0) {
/*     */       
/*  97 */       float max = this.z0 - c.z1 - this.epsilon;
/*  98 */       if (max < za) za = max; 
/*     */     } 
/* 100 */     if (za < 0.0F && c.z0 >= this.z1) {
/*     */       
/* 102 */       float max = this.z1 - c.z0 + this.epsilon;
/* 103 */       if (max > za) za = max;
/*     */     
/*     */     } 
/* 106 */     return za;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean intersects(AABB c) {
/* 111 */     if (c.x1 <= this.x0 || c.x0 >= this.x1) return false; 
/* 112 */     if (c.y1 <= this.y0 || c.y0 >= this.y1) return false; 
/* 113 */     if (c.z1 <= this.z0 || c.z0 >= this.z1) return false; 
/* 114 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void move(float xa, float ya, float za) {
/* 119 */     this.x0 += xa;
/* 120 */     this.y0 += ya;
/* 121 */     this.z0 += za;
/* 122 */     this.x1 += xa;
/* 123 */     this.y1 += ya;
/* 124 */     this.z1 += za;
/*     */   }
/*     */ }


/* Location:              /home/segfault/.minecraft/versions/rd-20090515/rd-20090515.jar!/com/mojang/minecraft/phys/AABB.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */