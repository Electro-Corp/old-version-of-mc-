/*     */ package com.mojang.minecraft.level;
/*     */ 
/*     */ import com.mojang.minecraft.Player;
/*     */ import com.mojang.minecraft.level.tile.Tile;
/*     */ import com.mojang.minecraft.phys.AABB;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class Chunk
/*     */ {
/*     */   public AABB aabb;
/*     */   public final Level level;
/*     */   public final int x0;
/*     */   public final int y0;
/*     */   public final int z0;
/*     */   public final int x1;
/*  16 */   private int lists = -1; public final int y1; public final int z1; public final float x; public final float y; public final float z; private boolean dirty = true;
/*  17 */   public long dirtiedTime = 0L;
/*     */   
/*  19 */   private static Tesselator t = Tesselator.instance;
/*     */   
/*  21 */   public static int updates = 0;
/*     */ 
/*     */   
/*     */   public Chunk(Level level, int x0, int y0, int z0, int x1, int y1, int z1) {
/*  25 */     this.level = level;
/*  26 */     this.x0 = x0;
/*  27 */     this.y0 = y0;
/*  28 */     this.z0 = z0;
/*  29 */     this.x1 = x1;
/*  30 */     this.y1 = y1;
/*  31 */     this.z1 = z1;
/*     */     
/*  33 */     this.x = (x0 + x1) / 2.0F;
/*  34 */     this.y = (y0 + y1) / 2.0F;
/*  35 */     this.z = (z0 + z1) / 2.0F;
/*     */     
/*  37 */     this.aabb = new AABB(x0, y0, z0, x1, y1, z1);
/*  38 */     this.lists = GL11.glGenLists(2);
/*     */   }
/*     */ 
/*     */   
/*  42 */   private static long totalTime = 0L;
/*  43 */   private static int totalUpdates = 0;
/*     */ 
/*     */   
/*     */   private void rebuild(int layer) {
/*  47 */     this.dirty = false;
/*     */     
/*  49 */     updates++;
/*     */     
/*  51 */     long before = System.nanoTime();
/*  52 */     GL11.glNewList(this.lists + layer, 4864);
/*  53 */     t.init();
/*  54 */     int tiles = 0;
/*  55 */     for (int x = this.x0; x < this.x1; x++) {
/*  56 */       for (int y = this.y0; y < this.y1; y++) {
/*  57 */         for (int z = this.z0; z < this.z1; z++) {
/*     */           
/*  59 */           int tileId = this.level.getTile(x, y, z);
/*  60 */           if (tileId > 0)
/*     */           
/*  62 */           { Tile.tiles[tileId].render(t, this.level, layer, x, y, z);
/*  63 */             tiles++; } 
/*     */         } 
/*     */       } 
/*  66 */     }  t.flush();
/*  67 */     GL11.glEndList();
/*  68 */     long after = System.nanoTime();
/*  69 */     if (tiles > 0) {
/*     */       
/*  71 */       totalTime += after - before;
/*  72 */       totalUpdates++;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void rebuild() {
/*  82 */     rebuild(0);
/*  83 */     rebuild(1);
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(int layer) {
/*  88 */     GL11.glCallList(this.lists + layer);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDirty() {
/*  93 */     if (!this.dirty)
/*     */     {
/*  95 */       this.dirtiedTime = System.currentTimeMillis();
/*     */     }
/*  97 */     this.dirty = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isDirty() {
/* 102 */     return this.dirty;
/*     */   }
/*     */ 
/*     */   
/*     */   public float distanceToSqr(Player player) {
/* 107 */     float xd = player.x - this.x;
/* 108 */     float yd = player.y - this.y;
/* 109 */     float zd = player.z - this.z;
/* 110 */     return xd * xd + yd * yd + zd * zd;
/*     */   }
/*     */ }


/* Location:              /home/segfault/.minecraft/versions/rd-20090515/rd-20090515.jar!/com/mojang/minecraft/level/Chunk.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */