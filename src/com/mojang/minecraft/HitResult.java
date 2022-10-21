/*    */ package com.mojang.minecraft;
/*    */ 
/*    */ public class HitResult
/*    */ {
/*    */   public int type;
/*    */   public int x;
/*    */   
/*    */   public HitResult(int type, int x, int y, int z, int f) {
/*  9 */     this.type = type;
/* 10 */     this.x = x;
/* 11 */     this.y = y;
/* 12 */     this.z = z;
/* 13 */     this.f = f;
/*    */   }
/*    */   
/*    */   public int y;
/*    */   public int z;
/*    */   public int f;
/*    */ }


/* Location:              /home/segfault/.minecraft/versions/rd-20090515/rd-20090515.jar!/com/mojang/minecraft/HitResult.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */