/*    */ package com.mojang.minecraft.level;
/*    */ 
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ public class PerlinNoiseFilter
/*    */ {
/*  8 */   Random random = new Random();
/*  9 */   int seed = this.random.nextInt();
/* 10 */   int levels = 0;
/* 11 */   int fuzz = 16;
/*    */ 
/*    */   
/*    */   public PerlinNoiseFilter(int levels) {
/* 15 */     this.levels = levels;
/*    */   }
/*    */ 
/*    */   
/*    */   public int[] read(int width, int height) {
/* 20 */     Random random = new Random();
/*    */     
/* 22 */     int[] tmp = new int[width * height];
/*    */     
/* 24 */     int level = this.levels;
/*    */ 
/*    */     
/* 27 */     int step = width >> level; int y;
/* 28 */     for (y = 0; y < height; y += step) {
/*    */       
/* 30 */       for (int x = 0; x < width; x += step)
/*    */       {
/* 32 */         tmp[x + y * width] = (random.nextInt(256) - 128) * this.fuzz;
/*    */       }
/*    */     } 
/*    */ 
/*    */     
/* 37 */     for (step = width >> level; step > 1; step /= 2) {
/*    */       
/* 39 */       int val = 256 * (step << level);
/* 40 */       int ss = step / 2;
/*    */       int i;
/* 42 */       for (i = 0; i < height; i += step) {
/*    */         
/* 44 */         for (int x = 0; x < width; x += step) {
/*    */           
/* 46 */           int ul = tmp[(x + 0) % width + (i + 0) % height * width];
/* 47 */           int ur = tmp[(x + step) % width + (i + 0) % height * width];
/* 48 */           int dl = tmp[(x + 0) % width + (i + step) % height * width];
/* 49 */           int dr = tmp[(x + step) % width + (i + step) % height * width];
/*    */           
/* 51 */           int m = (ul + dl + ur + dr) / 4 + random.nextInt(val * 2) - val;
/*    */           
/* 53 */           tmp[x + ss + (i + ss) * width] = m;
/*    */         } 
/*    */       } 
/*    */       
/* 57 */       for (i = 0; i < height; i += step) {
/*    */         
/* 59 */         for (int x = 0; x < width; x += step) {
/*    */           
/* 61 */           int c = tmp[x + i * width];
/* 62 */           int r = tmp[(x + step) % width + i * width];
/* 63 */           int d = tmp[x + (i + step) % width * width];
/*    */           
/* 65 */           int mu = tmp[(x + ss & width - 1) + (i + ss - step & height - 1) * width];
/* 66 */           int ml = tmp[(x + ss - step & width - 1) + (i + ss & height - 1) * width];
/* 67 */           int m = tmp[(x + ss) % width + (i + ss) % height * width];
/*    */           
/* 69 */           int u = (c + r + m + mu) / 4 + random.nextInt(val * 2) - val;
/* 70 */           int l = (c + d + m + ml) / 4 + random.nextInt(val * 2) - val;
/*    */           
/* 72 */           tmp[x + ss + i * width] = u;
/* 73 */           tmp[x + (i + ss) * width] = l;
/*    */         } 
/*    */       } 
/*    */     } 
/*    */ 
/*    */ 
/*    */     
/* 80 */     int[] result = new int[width * height];
/* 81 */     for (y = 0; y < height; y++) {
/*    */       
/* 83 */       for (int x = 0; x < width; x++)
/*    */       {
/* 85 */         result[x + y * width] = tmp[x % width + y % height * width] / 512 + 128;
/*    */       }
/*    */     } 
/* 88 */     return result;
/*    */   }
/*    */ }


/* Location:              /home/segfault/.minecraft/versions/rd-20090515/rd-20090515.jar!/com/mojang/minecraft/level/PerlinNoiseFilter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */