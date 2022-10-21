/*     */ package com.mojang.minecraft.level;
/*     */ import com.mojang.minecraft.level.tile.Tile;
/*     */ import com.mojang.minecraft.phys.AABB;
/*     */ import java.io.DataInputStream;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.File;
import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Random;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
/*     */ 
/*     */ public class Level {
/*     */   private static final int TILE_UPDATE_INTERVAL = 400;
/*     */   public final int width;
/*     */   public final int height;
/*     */   public final int depth;
/*     */   private byte[] blocks;
/*     */   private int[] lightDepths;
/*  18 */   private ArrayList<LevelListener> levelListeners = new ArrayList<LevelListener>();
/*  19 */   private Random random = new Random();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   int unprocessed;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void generateMap() {
/*  37 */     int w = this.width;
/*  38 */     int h = this.height;
/*  39 */     int d = this.depth;
/*  40 */     int[] heightmap1 = (new PerlinNoiseFilter(0)).read(w, h);
/*  41 */     int[] heightmap2 = (new PerlinNoiseFilter(0)).read(w, h);
/*  42 */     int[] cf = (new PerlinNoiseFilter(1)).read(w, h);
/*  43 */     int[] rockMap = (new PerlinNoiseFilter(1)).read(w, h);
/*     */     
/*  45 */     for (int x = 0; x < w; x++) {
/*  46 */       for (int y = 0; y < d; y++) {
/*  47 */         for (int z = 0; z < h; z++) {
/*     */           
/*  49 */           int dh1 = heightmap1[x + z * this.width];
/*  50 */           int dh2 = heightmap2[x + z * this.width];
/*  51 */           int cfh = cf[x + z * this.width];
/*     */           
/*  53 */           if (cfh < 128) dh2 = dh1;
/*     */           
/*  55 */           int dh = dh1;
/*  56 */           if (dh2 > dh) {
/*  57 */             dh = dh2;
/*     */           } else {
/*  59 */             dh2 = dh1;
/*  60 */           }  dh = dh / 8 + d / 3;
/*     */           
/*  62 */           int rh = rockMap[x + z * this.width] / 8 + d / 3;
/*  63 */           if (rh > dh - 2) rh = dh - 2;
/*     */ 
/*     */           
/*  66 */           int i = (y * this.height + z) * this.width + x;
/*  67 */           int id = 0;
/*  68 */           if (y == dh) id = Tile.grass.id; 
/*  69 */           if (y < dh) id = Tile.dirt.id; 
/*  70 */           if (y <= rh) id = Tile.rock.id; 
/*  71 */           this.blocks[i] = (byte)id;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean load() {
/*     */     try {
/*  79 */       DataInputStream dis = new DataInputStream(new GZIPInputStream(new FileInputStream(new File("level.dat"))));
/*  80 */       dis.readFully(this.blocks);
/*  81 */       calcLightDepths(0, 0, this.width, this.height);
/*  82 */       for (int i = 0; i < this.levelListeners.size(); i++)
/*  83 */         ((LevelListener)this.levelListeners.get(i)).allChanged(); 
/*  84 */       dis.close();
/*  85 */       return true;
/*     */     }
/*  87 */     catch (Exception e) {
/*     */       
/*  89 */       e.printStackTrace();
/*  90 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void save() {
/*     */     try {
/*  98 */       DataOutputStream dos = new DataOutputStream(new GZIPOutputStream(new FileOutputStream(new File("level.dat"))));
/*  99 */       dos.write(this.blocks);
/* 100 */       dos.close();
/*     */     }
/* 102 */     catch (Exception e) {
/*     */       
/* 104 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void calcLightDepths(int x0, int y0, int x1, int y1) {
/* 110 */     for (int x = x0; x < x0 + x1; x++) {
/* 111 */       for (int z = y0; z < y0 + y1; z++) {
/*     */         
/* 113 */         int oldDepth = this.lightDepths[x + z * this.width];
/* 114 */         int y = this.depth - 1;
/* 115 */         while (y > 0 && !isLightBlocker(x, y, z))
/* 116 */           y--; 
/* 117 */         this.lightDepths[x + z * this.width] = y;
/*     */         
/* 119 */         if (oldDepth != y) {
/*     */           
/* 121 */           int yl0 = (oldDepth < y) ? oldDepth : y;
/* 122 */           int yl1 = (oldDepth > y) ? oldDepth : y;
/* 123 */           for (int i = 0; i < this.levelListeners.size(); i++)
/* 124 */             ((LevelListener)this.levelListeners.get(i)).lightColumnChanged(x, z, yl0, yl1); 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void addListener(LevelListener levelListener) {
/* 131 */     this.levelListeners.add(levelListener);
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeListener(LevelListener levelListener) {
/* 136 */     this.levelListeners.remove(levelListener);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isLightBlocker(int x, int y, int z) {
/* 152 */     Tile tile = Tile.tiles[getTile(x, y, z)];
/* 153 */     if (tile == null) return false; 
/* 154 */     return tile.blocksLight();
/*     */   }
/*     */ 
/*     */   
/*     */   public ArrayList<AABB> getCubes(AABB aABB) {
/* 159 */     ArrayList<AABB> aABBs = new ArrayList<AABB>();
/* 160 */     int x0 = (int)aABB.x0;
/* 161 */     int x1 = (int)(aABB.x1 + 1.0F);
/* 162 */     int y0 = (int)aABB.y0;
/* 163 */     int y1 = (int)(aABB.y1 + 1.0F);
/* 164 */     int z0 = (int)aABB.z0;
/* 165 */     int z1 = (int)(aABB.z1 + 1.0F);
/*     */     
/* 167 */     if (x0 < 0) x0 = 0; 
/* 168 */     if (y0 < 0) y0 = 0; 
/* 169 */     if (z0 < 0) z0 = 0; 
/* 170 */     if (x1 > this.width) x1 = this.width; 
/* 171 */     if (y1 > this.depth) y1 = this.depth; 
/* 172 */     if (z1 > this.height) z1 = this.height;
/*     */     
/* 174 */     for (int x = x0; x < x1; x++) {
/* 175 */       for (int y = y0; y < y1; y++) {
/* 176 */         for (int z = z0; z < z1; z++) {
/*     */           
/* 178 */           Tile tile = Tile.tiles[getTile(x, y, z)];
/* 179 */           if (tile != null) {
/*     */             
/* 181 */             AABB aabb = tile.getAABB(x, y, z);
/* 182 */             if (aabb != null) aABBs.add(aabb); 
/*     */           } 
/*     */         } 
/*     */       } 
/* 186 */     }  return aABBs;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean setTile(int x, int y, int z, int type) {
/* 200 */     if (x < 0 || y < 0 || z < 0 || x >= this.width || y >= this.depth || z >= this.height) return false; 
/* 201 */     if (type == this.blocks[(y * this.height + z) * this.width + x]) return false;
/*     */     
/* 203 */     this.blocks[(y * this.height + z) * this.width + x] = (byte)type;
/* 204 */     calcLightDepths(x, z, 1, 1);
/* 205 */     for (int i = 0; i < this.levelListeners.size(); i++) {
/* 206 */       ((LevelListener)this.levelListeners.get(i)).tileChanged(x, y, z);
/*     */     }
/* 208 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isLit(int x, int y, int z) {
/* 213 */     if (x < 0 || y < 0 || z < 0 || x >= this.width || y >= this.depth || z >= this.height) {
				return true;  
			  }
/* 214 */     return (y >= this.lightDepths[x + z * this.width]);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getTile(int x, int y, int z) {
/* 219 */     if (x < 0 || y < 0 || z < 0 || x >= this.width || y >= this.depth || z >= this.height) return 0; 
/* 220 */     return this.blocks[(y * this.height + z) * this.width + x];
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSolidTile(int x, int y, int z) {
/* 225 */     Tile tile = Tile.tiles[getTile(x, y, z)];
/* 226 */     if (tile == null) return false; 
/* 227 */     return tile.isSolid();
/*     */   }
/*     */   
/* 230 */   public Level(int w, int h, int d) { this.unprocessed = 0; this.width = w; this.height = h; this.depth = d; this.blocks = new byte[w * h * d]; this.lightDepths = new int[w * h];
/*     */     boolean mapLoaded = load();
/*     */     if (!mapLoaded)
/*     */       generateMap(); 
/* 234 */     calcLightDepths(0, 0, w, h); } public void tick() { this.unprocessed += this.width * this.height * this.depth;
/* 235 */     int ticks = this.unprocessed / 400;
/* 236 */     this.unprocessed -= ticks * 400;
/* 237 */     for (int i = 0; i < ticks; i++) {
/*     */       
/* 239 */       int x = this.random.nextInt(this.width);
/* 240 */       int y = this.random.nextInt(this.depth);
/* 241 */       int z = this.random.nextInt(this.height);
/* 242 */       Tile tile = Tile.tiles[getTile(x, y, z)];
/* 243 */       if (tile != null)
/*     */       {
/* 245 */         tile.tick(this, x, y, z, this.random);
/*     */       }
/*     */     }  }
/*     */ 
/*     */ }


/* Location:              /home/segfault/.minecraft/versions/rd-20090515/rd-20090515.jar!/com/mojang/minecraft/level/Level.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */