/*     */ package com.mojang.minecraft.level.tile;
/*     */ 
/*     */ import com.mojang.minecraft.level.Level;
/*     */ import com.mojang.minecraft.level.Tesselator;
/*     */ import com.mojang.minecraft.particle.Particle;
/*     */ import com.mojang.minecraft.particle.ParticleEngine;
/*     */ import com.mojang.minecraft.phys.AABB;
/*     */ import java.util.Random;
/*     */ 
/*     */ 
/*     */ public class Tile
/*     */ {
/*  13 */   public static final Tile[] tiles = new Tile[256];
/*  14 */   public static final Tile empty = null;
/*  15 */   public static final Tile rock = new Tile(1, 1);
/*  16 */   public static final Tile grass = new GrassTile(2);
/*  17 */   public static final Tile dirt = new DirtTile(3, 2);
/*  18 */   public static final Tile stoneBrick = new Tile(4, 16);
/*  19 */   public static final Tile wood = new Tile(5, 4);
/*  20 */   public static final Tile bush = new Bush(6);
/*     */   
/*     */   public int tex;
/*     */   
/*     */   public final int id;
/*     */   
/*     */   protected Tile(int id) {
/*  27 */     tiles[id] = this;
/*  28 */     this.id = id;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Tile(int id, int tex) {
/*  33 */     this(id);
/*  34 */     this.tex = tex;
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(Tesselator t, Level level, int layer, int x, int y, int z) {
/*  39 */     float c1 = 1.0F;
/*  40 */     float c2 = 0.8F;
/*  41 */     float c3 = 0.6F;
/*     */     
/*  43 */     if (shouldRenderFace(level, x, y - 1, z, layer)) {
/*     */       
/*  45 */       t.color(c1, c1, c1);
/*  46 */       renderFace(t, x, y, z, 0);
/*     */     } 
/*     */     
/*  49 */     if (shouldRenderFace(level, x, y + 1, z, layer)) {
/*     */       
/*  51 */       t.color(c1, c1, c1);
/*  52 */       renderFace(t, x, y, z, 1);
/*     */     } 
/*     */     
/*  55 */     if (shouldRenderFace(level, x, y, z - 1, layer)) {
/*     */       
/*  57 */       t.color(c2, c2, c2);
/*  58 */       renderFace(t, x, y, z, 2);
/*     */     } 
/*     */     
/*  61 */     if (shouldRenderFace(level, x, y, z + 1, layer)) {
/*     */       
/*  63 */       t.color(c2, c2, c2);
/*  64 */       renderFace(t, x, y, z, 3);
/*     */     } 
/*     */     
/*  67 */     if (shouldRenderFace(level, x - 1, y, z, layer)) {
/*     */       
/*  69 */       t.color(c3, c3, c3);
/*  70 */       renderFace(t, x, y, z, 4);
/*     */     } 
/*     */     
/*  73 */     if (shouldRenderFace(level, x + 1, y, z, layer)) {
/*     */       
/*  75 */       t.color(c3, c3, c3);
/*  76 */       renderFace(t, x, y, z, 5);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean shouldRenderFace(Level level, int x, int y, int z, int layer) {
/*  82 */     return (!level.isSolidTile(x, y, z) && (level.isLit(x, y, z) ^ (((layer == 1) ? 1 : 0)) != 0));
/*     */   }
/*     */ 
/*     */   
/*     */   protected int getTexture(int face) {
/*  87 */     return this.tex;
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderFace(Tesselator t, int x, int y, int z, int face) {
/*  92 */     int tex = getTexture(face);
/*  93 */     float u0 = (tex % 16) / 16.0F;
/*  94 */     float u1 = u0 + 0.0624375F;
/*  95 */     float v0 = (tex / 16) / 16.0F;
/*  96 */     float v1 = v0 + 0.0624375F;
/*     */     
/*  98 */     float x0 = x + 0.0F;
/*  99 */     float x1 = x + 1.0F;
/* 100 */     float y0 = y + 0.0F;
/* 101 */     float y1 = y + 1.0F;
/* 102 */     float z0 = z + 0.0F;
/* 103 */     float z1 = z + 1.0F;
/*     */     
/* 105 */     if (face == 0) {
/*     */       
/* 107 */       t.vertexUV(x0, y0, z1, u0, v1);
/* 108 */       t.vertexUV(x0, y0, z0, u0, v0);
/* 109 */       t.vertexUV(x1, y0, z0, u1, v0);
/* 110 */       t.vertexUV(x1, y0, z1, u1, v1);
/*     */     } 
/*     */     
/* 113 */     if (face == 1) {
/*     */       
/* 115 */       t.vertexUV(x1, y1, z1, u1, v1);
/* 116 */       t.vertexUV(x1, y1, z0, u1, v0);
/* 117 */       t.vertexUV(x0, y1, z0, u0, v0);
/* 118 */       t.vertexUV(x0, y1, z1, u0, v1);
/*     */     } 
/*     */     
/* 121 */     if (face == 2) {
/*     */       
/* 123 */       t.vertexUV(x0, y1, z0, u1, v0);
/* 124 */       t.vertexUV(x1, y1, z0, u0, v0);
/* 125 */       t.vertexUV(x1, y0, z0, u0, v1);
/* 126 */       t.vertexUV(x0, y0, z0, u1, v1);
/*     */     } 
/*     */     
/* 129 */     if (face == 3) {
/*     */       
/* 131 */       t.vertexUV(x0, y1, z1, u0, v0);
/* 132 */       t.vertexUV(x0, y0, z1, u0, v1);
/* 133 */       t.vertexUV(x1, y0, z1, u1, v1);
/* 134 */       t.vertexUV(x1, y1, z1, u1, v0);
/*     */     } 
/*     */     
/* 137 */     if (face == 4) {
/*     */       
/* 139 */       t.vertexUV(x0, y1, z1, u1, v0);
/* 140 */       t.vertexUV(x0, y1, z0, u0, v0);
/* 141 */       t.vertexUV(x0, y0, z0, u0, v1);
/* 142 */       t.vertexUV(x0, y0, z1, u1, v1);
/*     */     } 
/*     */     
/* 145 */     if (face == 5) {
/*     */       
/* 147 */       t.vertexUV(x1, y0, z1, u0, v1);
/* 148 */       t.vertexUV(x1, y0, z0, u1, v1);
/* 149 */       t.vertexUV(x1, y1, z0, u1, v0);
/* 150 */       t.vertexUV(x1, y1, z1, u0, v0);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderFaceNoTexture(Tesselator t, int x, int y, int z, int face) {
/* 156 */     float x0 = x + 0.0F;
/* 157 */     float x1 = x + 1.0F;
/* 158 */     float y0 = y + 0.0F;
/* 159 */     float y1 = y + 1.0F;
/* 160 */     float z0 = z + 0.0F;
/* 161 */     float z1 = z + 1.0F;
/*     */     
/* 163 */     if (face == 0) {
/*     */       
/* 165 */       t.vertex(x0, y0, z1);
/* 166 */       t.vertex(x0, y0, z0);
/* 167 */       t.vertex(x1, y0, z0);
/* 168 */       t.vertex(x1, y0, z1);
/*     */     } 
/*     */     
/* 171 */     if (face == 1) {
/*     */       
/* 173 */       t.vertex(x1, y1, z1);
/* 174 */       t.vertex(x1, y1, z0);
/* 175 */       t.vertex(x0, y1, z0);
/* 176 */       t.vertex(x0, y1, z1);
/*     */     } 
/*     */     
/* 179 */     if (face == 2) {
/*     */       
/* 181 */       t.vertex(x0, y1, z0);
/* 182 */       t.vertex(x1, y1, z0);
/* 183 */       t.vertex(x1, y0, z0);
/* 184 */       t.vertex(x0, y0, z0);
/*     */     } 
/*     */     
/* 187 */     if (face == 3) {
/*     */       
/* 189 */       t.vertex(x0, y1, z1);
/* 190 */       t.vertex(x0, y0, z1);
/* 191 */       t.vertex(x1, y0, z1);
/* 192 */       t.vertex(x1, y1, z1);
/*     */     } 
/*     */     
/* 195 */     if (face == 4) {
/*     */       
/* 197 */       t.vertex(x0, y1, z1);
/* 198 */       t.vertex(x0, y1, z0);
/* 199 */       t.vertex(x0, y0, z0);
/* 200 */       t.vertex(x0, y0, z1);
/*     */     } 
/*     */     
/* 203 */     if (face == 5) {
/*     */       
/* 205 */       t.vertex(x1, y0, z1);
/* 206 */       t.vertex(x1, y0, z0);
/* 207 */       t.vertex(x1, y1, z0);
/* 208 */       t.vertex(x1, y1, z1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public final AABB getTileAABB(int x, int y, int z) {
/* 214 */     return new AABB(x, y, z, (x + 1), (y + 1), (z + 1));
/*     */   }
/*     */ 
/*     */   
/*     */   public AABB getAABB(int x, int y, int z) {
/* 219 */     return new AABB(x, y, z, (x + 1), (y + 1), (z + 1));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean blocksLight() {
/* 224 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSolid() {
/* 229 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void tick(Level level, int x, int y, int z, Random random) {}
/*     */ 
/*     */   
/*     */   public void destroy(Level level, int x, int y, int z, ParticleEngine particleEngine) {
/* 238 */     int SD = 4;
/* 239 */     for (int xx = 0; xx < SD; xx++) {
/* 240 */       for (int yy = 0; yy < SD; yy++) {
/* 241 */         for (int zz = 0; zz < SD; zz++) {
/*     */           
/* 243 */           float xp = x + (xx + 0.5F) / SD;
/* 244 */           float yp = y + (yy + 0.5F) / SD;
/* 245 */           float zp = z + (zz + 0.5F) / SD;
/* 246 */           particleEngine.add(new Particle(level, xp, yp, zp, xp - x - 0.5F, yp - y - 0.5F, zp - z - 0.5F, this.tex));
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              /home/segfault/.minecraft/versions/rd-20090515/rd-20090515.jar!/com/mojang/minecraft/level/tile/Tile.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */