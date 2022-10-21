/*    */ package com.mojang.minecraft.level.tile;
/*    */ 
/*    */ import com.mojang.minecraft.level.Level;
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ public class GrassTile
/*    */   extends Tile
/*    */ {
/*    */   protected GrassTile(int id) {
/* 11 */     super(id);
/* 12 */     this.tex = 3;
/*    */   }
/*    */ 
/*    */   
/*    */   protected int getTexture(int face) {
/* 17 */     if (face == 1) return 0; 
/* 18 */     if (face == 0) return 2; 
/* 19 */     return 3;
/*    */   }
/*    */ 
/*    */   
/*    */   public void tick(Level level, int x, int y, int z, Random random) {
/* 24 */     if (!level.isLit(x, y, z)) {
/*    */       
/* 26 */       level.setTile(x, y, z, Tile.dirt.id);
/*    */     }
/*    */     else {
/*    */       
/* 30 */       for (int i = 0; i < 4; i++) {
/*    */         
/* 32 */         int xt = x + random.nextInt(3) - 1;
/* 33 */         int yt = y + random.nextInt(5) - 3;
/* 34 */         int zt = z + random.nextInt(3) - 1;
/* 35 */         if (level.getTile(xt, yt, zt) == Tile.dirt.id && level.isLit(xt, yt, zt))
/*    */         {
/* 37 */           level.setTile(xt, yt, zt, Tile.grass.id);
/*    */         }
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              /home/segfault/.minecraft/versions/rd-20090515/rd-20090515.jar!/com/mojang/minecraft/level/tile/GrassTile.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */