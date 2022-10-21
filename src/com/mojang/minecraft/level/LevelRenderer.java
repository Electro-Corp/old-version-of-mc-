/*     */ package com.mojang.minecraft.level;
/*     */ 
/*     */ import com.mojang.minecraft.HitResult;
/*     */ import com.mojang.minecraft.Player;
/*     */ import com.mojang.minecraft.Textures;
/*     */ import com.mojang.minecraft.level.tile.Tile;
/*     */ import com.mojang.minecraft.phys.AABB;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LevelRenderer
/*     */   implements LevelListener
/*     */ {
/*     */   public static final int MAX_REBUILDS_PER_FRAME = 8;
/*     */   public static final int CHUNK_SIZE = 16;
/*     */   private Level level;
/*     */   private Chunk[] chunks;
/*     */   private int xChunks;
/*     */   private int yChunks;
/*     */   private int zChunks;
/*     */   
/*     */   public LevelRenderer(Level level) {
/*  28 */     this.level = level;
/*  29 */     level.addListener(this);
/*     */     
/*  31 */     this.xChunks = level.width / 16;
/*  32 */     this.yChunks = level.depth / 16;
/*  33 */     this.zChunks = level.height / 16;
/*     */     
/*  35 */     this.chunks = new Chunk[this.xChunks * this.yChunks * this.zChunks];
/*  36 */     for (int x = 0; x < this.xChunks; x++) {
/*  37 */       for (int y = 0; y < this.yChunks; y++) {
/*  38 */         for (int z = 0; z < this.zChunks; z++) {
/*     */           
/*  40 */           int x0 = x * 16;
/*  41 */           int y0 = y * 16;
/*  42 */           int z0 = z * 16;
/*  43 */           int x1 = (x + 1) * 16;
/*  44 */           int y1 = (y + 1) * 16;
/*  45 */           int z1 = (z + 1) * 16;
/*     */           
/*  47 */           if (x1 > level.width) x1 = level.width; 
/*  48 */           if (y1 > level.depth) y1 = level.depth; 
/*  49 */           if (z1 > level.height) z1 = level.height; 
/*  50 */           this.chunks[(x + y * this.xChunks) * this.zChunks + z] = new Chunk(level, x0, y0, z0, x1, y1, z1);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   public List<Chunk> getAllDirtyChunks() {
/*  56 */     ArrayList<Chunk> dirty = null;
/*  57 */     for (int i = 0; i < this.chunks.length; i++) {
/*     */       
/*  59 */       Chunk chunk = this.chunks[i];
/*  60 */       if (chunk.isDirty()) {
/*     */         
/*  62 */         if (dirty == null) dirty = new ArrayList<Chunk>(); 
/*  63 */         dirty.add(chunk);
/*     */       } 
/*     */     } 
/*  66 */     return dirty;
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(Player player, int layer) {
/*  71 */     GL11.glEnable(3553);
/*  72 */     int id = Textures.loadTexture("/terrain.png", 9728);
/*  73 */     GL11.glBindTexture(3553, id);
/*  74 */     Frustum frustum = Frustum.getFrustum();
/*  75 */     for (int i = 0; i < this.chunks.length; i++) {
/*     */       
/*  77 */       if (frustum.isVisible((this.chunks[i]).aabb)) this.chunks[i].render(layer); 
/*     */     } 
/*  79 */     GL11.glDisable(3553);
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateDirtyChunks(Player player) {
/*  84 */     List<Chunk> dirty = getAllDirtyChunks();
/*  85 */     if (dirty == null)
/*     */       return; 
/*  87 */     Collections.sort(dirty, new DirtyChunkSorter(player, Frustum.getFrustum()));
/*  88 */     for (int i = 0; i < 8 && i < dirty.size(); i++)
/*     */     {
/*  90 */       ((Chunk)dirty.get(i)).rebuild();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void pick(Player player, Frustum frustum) {
/*  96 */     Tesselator t = Tesselator.instance;
/*  97 */     float r = 3.0F;
/*  98 */     AABB box = player.bb.grow(r, r, r);
/*  99 */     int x0 = (int)box.x0;
/* 100 */     int x1 = (int)(box.x1 + 1.0F);
/* 101 */     int y0 = (int)box.y0;
/* 102 */     int y1 = (int)(box.y1 + 1.0F);
/* 103 */     int z0 = (int)box.z0;
/* 104 */     int z1 = (int)(box.z1 + 1.0F);
/*     */     
/* 106 */     GL11.glInitNames();
/* 107 */     GL11.glPushName(0);
/* 108 */     GL11.glPushName(0);
/* 109 */     for (int x = x0; x < x1; x++) {
/*     */       
/* 111 */       GL11.glLoadName(x);
/* 112 */       GL11.glPushName(0);
/* 113 */       for (int y = y0; y < y1; y++) {
/*     */         
/* 115 */         GL11.glLoadName(y);
/* 116 */         GL11.glPushName(0);
/* 117 */         for (int z = z0; z < z1; z++) {
/*     */           
/* 119 */           Tile tile = Tile.tiles[this.level.getTile(x, y, z)];
/* 120 */           if (tile != null && frustum.isVisible(tile.getTileAABB(x, y, z))) {
/*     */             
/* 122 */             GL11.glLoadName(z);
/* 123 */             GL11.glPushName(0);
/* 124 */             for (int i = 0; i < 6; i++) {
/*     */               
/* 126 */               GL11.glLoadName(i);
/* 127 */               t.init();
/* 128 */               tile.renderFaceNoTexture(t, x, y, z, i);
/* 129 */               t.flush();
/*     */             } 
/* 131 */             GL11.glPopName();
/*     */           } 
/*     */         } 
/* 134 */         GL11.glPopName();
/*     */       } 
/* 136 */       GL11.glPopName();
/*     */     } 
/* 138 */     GL11.glPopName();
/* 139 */     GL11.glPopName();
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderHit(HitResult h) {
/* 144 */     Tesselator t = Tesselator.instance;
/* 145 */     GL11.glEnable(3042);
/*     */     
/* 147 */     GL11.glBlendFunc(770, 1);
/* 148 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, ((float)Math.sin(System.currentTimeMillis() / 100.0D) * 0.2F + 0.4F) * 0.5F);
/* 149 */     t.init();
/* 150 */     Tile.rock.renderFaceNoTexture(t, h.x, h.y, h.z, h.f);
/* 151 */     t.flush();
/* 152 */     GL11.glDisable(3042);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDirty(int x0, int y0, int z0, int x1, int y1, int z1) {
/* 157 */     x0 /= 16;
/* 158 */     x1 /= 16;
/* 159 */     y0 /= 16;
/* 160 */     y1 /= 16;
/* 161 */     z0 /= 16;
/* 162 */     z1 /= 16;
/*     */     
/* 164 */     if (x0 < 0) x0 = 0; 
/* 165 */     if (y0 < 0) y0 = 0; 
/* 166 */     if (z0 < 0) z0 = 0; 
/* 167 */     if (x1 >= this.xChunks) x1 = this.xChunks - 1; 
/* 168 */     if (y1 >= this.yChunks) y1 = this.yChunks - 1; 
/* 169 */     if (z1 >= this.zChunks) z1 = this.zChunks - 1;
/*     */     
/* 171 */     for (int x = x0; x <= x1; x++) {
/* 172 */       for (int y = y0; y <= y1; y++) {
/* 173 */         for (int z = z0; z <= z1; z++)
/*     */         {
/* 175 */           this.chunks[(x + y * this.xChunks) * this.zChunks + z].setDirty(); } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void tileChanged(int x, int y, int z) {
/* 181 */     setDirty(x - 1, y - 1, z - 1, x + 1, y + 1, z + 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public void lightColumnChanged(int x, int z, int y0, int y1) {
/* 186 */     setDirty(x - 1, y0 - 1, z - 1, x + 1, y1 + 1, z + 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public void allChanged() {
/* 191 */     setDirty(0, 0, 0, this.level.width, this.level.depth, this.level.height);
/*     */   }
/*     */ }


/* Location:              /home/segfault/.minecraft/versions/rd-20090515/rd-20090515.jar!/com/mojang/minecraft/level/LevelRenderer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */