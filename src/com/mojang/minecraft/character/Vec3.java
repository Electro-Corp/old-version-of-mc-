/*    */ package com.mojang.minecraft.character;
/*    */ 
/*    */ public class Vec3 {
/*    */   public float x;
/*    */   public float y;
/*    */   public float z;
/*    */   
/*    */   public Vec3(float x, float y, float z) {
/*  9 */     this.x = x;
/* 10 */     this.y = y;
/* 11 */     this.z = z;
/*    */   }
/*    */ 
/*    */   
/*    */   public Vec3 interpolateTo(Vec3 t, float p) {
/* 16 */     float xt = this.x + (t.x - this.x) * p;
/* 17 */     float yt = this.y + (t.y - this.y) * p;
/* 18 */     float zt = this.z + (t.z - this.z) * p;
/*    */     
/* 20 */     return new Vec3(xt, yt, zt);
/*    */   }
/*    */ 
/*    */   
/*    */   public void set(float x, float y, float z) {
/* 25 */     this.x = x;
/* 26 */     this.y = y;
/* 27 */     this.z = z;
/*    */   }
/*    */ }


/* Location:              /home/segfault/.minecraft/versions/rd-20090515/rd-20090515.jar!/com/mojang/minecraft/character/Vec3.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */