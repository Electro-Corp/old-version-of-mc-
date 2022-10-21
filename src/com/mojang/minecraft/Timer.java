/*    */ package com.mojang.minecraft;
/*    */ 
/*    */ 
/*    */ public class Timer
/*    */ {
/*    */   private static final long NS_PER_SECOND = 1000000000L;
/*    */   private static final long MAX_NS_PER_UPDATE = 1000000000L;
/*    */   private static final int MAX_TICKS_PER_UPDATE = 100;
/*    */   private float ticksPerSecond;
/*    */   private long lastTime;
/*    */   public int ticks;
/*    */   public float a;
/* 13 */   public float timeScale = 1.0F;
/* 14 */   public float fps = 0.0F;
/* 15 */   public float passedTime = 0.0F;
/*    */ 
/*    */   
/*    */   public Timer(float ticksPerSecond) {
/* 19 */     this.ticksPerSecond = ticksPerSecond;
/* 20 */     this.lastTime = System.nanoTime();
/*    */   }
/*    */ 
/*    */   
/*    */   public void advanceTime() {
/* 25 */     long now = System.nanoTime();
/* 26 */     long passedNs = now - this.lastTime;
/* 27 */     this.lastTime = now;
/*    */     
/* 29 */     if (passedNs < 0L) passedNs = 0L; 
/* 30 */     if (passedNs > 1000000000L) passedNs = 1000000000L;
/*    */     
/* 32 */     this.fps = (float)(1000000000L / passedNs);
/*    */     
/* 34 */     this.passedTime += (float)passedNs * this.timeScale * this.ticksPerSecond / 1.0E9F;
/*    */     
/* 36 */     this.ticks = (int)this.passedTime;
/* 37 */     if (this.ticks > 100) this.ticks = 100; 
/* 38 */     this.passedTime -= this.ticks;
/* 39 */     this.a = this.passedTime;
/*    */   }
/*    */ }


/* Location:              /home/segfault/.minecraft/versions/rd-20090515/rd-20090515.jar!/com/mojang/minecraft/Timer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */