/*    */ package com.mojang.minecraft.level;
/*    */ 
/*    */ import com.mojang.minecraft.Player;
/*    */ import java.util.Comparator;
/*    */ 
/*    */ public class DirtyChunkSorter
/*    */   implements Comparator<Chunk>
/*    */ {
/*    */   private Player player;
/*    */   private Frustum frustum;
/* 11 */   private long now = System.currentTimeMillis();
/*    */ 
/*    */   
/*    */   public DirtyChunkSorter(Player player, Frustum frustum) {
/* 15 */     this.player = player;
/* 16 */     this.frustum = frustum;
/*    */   }
/*    */ 
/*    */   
/*    */   public int compare(Chunk c0, Chunk c1) {
/* 21 */     boolean i0 = this.frustum.isVisible(c0.aabb);
/* 22 */     boolean i1 = this.frustum.isVisible(c1.aabb);
/* 23 */     if (i0 && !i1) return -1; 
/* 24 */     if (i1 && !i0) return 1; 
/* 25 */     int t0 = (int)((this.now - c0.dirtiedTime) / 2000L);
/* 26 */     int t1 = (int)((this.now - c1.dirtiedTime) / 2000L);
/* 27 */     if (t0 < t1) return -1; 
/* 28 */     if (t0 > t1) return 1; 
/* 29 */     return (c0.distanceToSqr(this.player) < c1.distanceToSqr(this.player)) ? -1 : 1;
/*    */   }
/*    */ }


/* Location:              /home/segfault/.minecraft/versions/rd-20090515/rd-20090515.jar!/com/mojang/minecraft/level/DirtyChunkSorter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */