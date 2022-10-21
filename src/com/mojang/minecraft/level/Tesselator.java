/*     */ package com.mojang.minecraft.level;
/*     */ 
/*     */ import java.nio.FloatBuffer;
/*     */ import org.lwjgl.BufferUtils;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Tesselator
/*     */ {
/*     */   private static final int MAX_MEMORY_USE = 4194304;
/*     */   private static final int MAX_FLOATS = 524288;
/*  14 */   private FloatBuffer buffer = BufferUtils.createFloatBuffer(524288);
/*  15 */   private float[] array = new float[524288];
/*     */ 
/*     */ 
/*     */   
/*  19 */   private int vertices = 0;
/*     */   
/*     */   private float u;
/*     */   private float v;
/*     */   private float r;
/*  24 */   private int len = 3; private float g; private float b; private boolean hasColor = false; private boolean hasTexture = false;
/*  25 */   private int p = 0;
/*     */   
/*  27 */   public static Tesselator instance = new Tesselator();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void flush() {
/*  35 */     this.buffer.clear();
/*  36 */     this.buffer.put(this.array, 0, this.p);
/*  37 */     this.buffer.flip();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  44 */     if (this.hasTexture && this.hasColor) {
/*  45 */       GL11.glInterleavedArrays(10794, 0, this.buffer);
/*  46 */     } else if (this.hasTexture) {
/*  47 */       GL11.glInterleavedArrays(10791, 0, this.buffer);
/*  48 */     } else if (this.hasColor) {
/*  49 */       GL11.glInterleavedArrays(10788, 0, this.buffer);
/*     */     } else {
/*  51 */       GL11.glInterleavedArrays(10785, 0, this.buffer);
/*     */     } 
/*  53 */     GL11.glEnableClientState(32884);
/*  54 */     if (this.hasTexture) GL11.glEnableClientState(32888); 
/*  55 */     if (this.hasColor) GL11.glEnableClientState(32886);
/*     */ 
/*     */ 
/*     */     
/*  59 */     GL11.glDrawArrays(7, 0, this.vertices);
/*     */     
/*  61 */     GL11.glDisableClientState(32884);
/*  62 */     if (this.hasTexture) GL11.glDisableClientState(32888); 
/*  63 */     if (this.hasColor) GL11.glDisableClientState(32886);
/*     */     
/*  65 */     clear();
/*     */   }
/*     */ 
/*     */   
/*     */   private void clear() {
/*  70 */     this.vertices = 0;
/*     */     
/*  72 */     this.buffer.clear();
/*  73 */     this.p = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void init() {
/*  80 */     clear();
/*  81 */     this.hasColor = false;
/*  82 */     this.hasTexture = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void tex(float u, float v) {
/*  87 */     if (!this.hasTexture) {
/*  88 */       this.len += 2;
/*     */     }
/*  90 */     this.hasTexture = true;
/*  91 */     this.u = u;
/*  92 */     this.v = v;
/*     */   }
/*     */ 
/*     */   
/*     */   public void color(float r, float g, float b) {
/*  97 */     if (!this.hasColor) {
/*  98 */       this.len += 3;
/*     */     }
/* 100 */     this.hasColor = true;
/* 101 */     this.r = r;
/* 102 */     this.g = g;
/* 103 */     this.b = b;
/*     */   }
/*     */ 
/*     */   
/*     */   public void vertexUV(float x, float y, float z, float u, float v) {
/* 108 */     tex(u, v);
/* 109 */     vertex(x, y, z);
/*     */   }
/*     */ 
/*     */   
/*     */   public void vertex(float x, float y, float z) {
/* 114 */     if (this.hasTexture) {
/*     */       
/* 116 */       this.array[this.p++] = this.u;
/* 117 */       this.array[this.p++] = this.v;
/*     */     } 
/* 119 */     if (this.hasColor) {
/*     */       
/* 121 */       this.array[this.p++] = this.r;
/* 122 */       this.array[this.p++] = this.g;
/* 123 */       this.array[this.p++] = this.b;
/*     */     } 
/* 125 */     this.array[this.p++] = x;
/* 126 */     this.array[this.p++] = y;
/* 127 */     this.array[this.p++] = z;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 132 */     this.vertices++;
/* 133 */     if (this.vertices % 4 == 0 && this.p >= 524288 - this.len * 4)
/*     */     {
/* 135 */       flush();
/*     */     }
/*     */   }
/*     */ }


/* Location:              /home/segfault/.minecraft/versions/rd-20090515/rd-20090515.jar!/com/mojang/minecraft/level/Tesselator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */