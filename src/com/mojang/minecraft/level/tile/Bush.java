/*    */ package com.mojang.minecraft.level.tile;
/*    */ 
/*    */ import com.mojang.minecraft.level.Level;
/*    */ import com.mojang.minecraft.level.Tesselator;
/*    */ import com.mojang.minecraft.phys.AABB;
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ public class Bush
/*    */   extends Tile
/*    */ {
/*    */   protected Bush(int id) {
/* 13 */     super(id);
/* 14 */     this.tex = 15;
/*    */   }
/*    */ 
/*    */   
/*    */   public void tick(Level level, int x, int y, int z, Random random) {
/* 19 */     int below = level.getTile(x, y - 1, z);
/* 20 */     if (!level.isLit(x, y, z) || (below != Tile.dirt.id && below != Tile.grass.id))
/*    */     {
/* 22 */       level.setTile(x, y, z, 0);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void render(Tesselator t, Level level, int layer, int x, int y, int z) {
/* 28 */     if ((level.isLit(x, y, z) ^ (((layer != 1) ? 1 : 0)) != 0))
/*    */       return; 
/* 30 */     int tex = getTexture(15);
/* 31 */     float u0 = (tex % 16) / 16.0F;
/* 32 */     float u1 = u0 + 0.0624375F;
/* 33 */     float v0 = (tex / 16) / 16.0F;
/* 34 */     float v1 = v0 + 0.0624375F;
/*    */     
/* 36 */     int rots = 2;
/* 37 */     t.color(1.0F, 1.0F, 1.0F);
/* 38 */     for (int r = 0; r < rots; r++) {
/*    */       
/* 40 */       float xa = (float)(Math.sin(r * Math.PI / rots + 0.7853981633974483D) * 0.5D);
/* 41 */       float za = (float)(Math.cos(r * Math.PI / rots + 0.7853981633974483D) * 0.5D);
/* 42 */       float x0 = x + 0.5F - xa;
/* 43 */       float x1 = x + 0.5F + xa;
/* 44 */       float y0 = y + 0.0F;
/* 45 */       float y1 = y + 1.0F;
/* 46 */       float z0 = z + 0.5F - za;
/* 47 */       float z1 = z + 0.5F + za;
/*    */       
/* 49 */       t.vertexUV(x0, y1, z0, u1, v0);
/* 50 */       t.vertexUV(x1, y1, z1, u0, v0);
/* 51 */       t.vertexUV(x1, y0, z1, u0, v1);
/* 52 */       t.vertexUV(x0, y0, z0, u1, v1);
/*    */       
/* 54 */       t.vertexUV(x1, y1, z1, u0, v0);
/* 55 */       t.vertexUV(x0, y1, z0, u1, v0);
/* 56 */       t.vertexUV(x0, y0, z0, u1, v1);
/* 57 */       t.vertexUV(x1, y0, z1, u0, v1);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public AABB getAABB(int x, int y, int z) {
/* 63 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean blocksLight() {
/* 68 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isSolid() {
/* 73 */     return false;
/*    */   }
/*    */ }


/* Location:              /home/segfault/.minecraft/versions/rd-20090515/rd-20090515.jar!/com/mojang/minecraft/level/tile/Bush.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */