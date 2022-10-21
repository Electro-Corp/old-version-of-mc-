/*     */ package com.mojang.minecraft.level;
/*     */ 
/*     */ import com.mojang.minecraft.phys.AABB;
/*     */ import java.nio.FloatBuffer;
/*     */ import org.lwjgl.BufferUtils;
/*     */ import org.lwjgl.opengl.GL11;
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
/*     */ public class Frustum
/*     */ {
/*  33 */   public float[][] m_Frustum = new float[6][4];
/*     */   
/*     */   public static final int RIGHT = 0;
/*     */   
/*     */   public static final int LEFT = 1;
/*     */   
/*     */   public static final int BOTTOM = 2;
/*     */   
/*     */   public static final int TOP = 3;
/*     */   
/*     */   public static final int BACK = 4;
/*     */   public static final int FRONT = 5;
/*     */   public static final int A = 0;
/*     */   public static final int B = 1;
/*     */   public static final int C = 2;
/*     */   public static final int D = 3;
/*  49 */   private static Frustum frustum = new Frustum(); private FloatBuffer _proj;
/*     */   private FloatBuffer _modl;
/*     */   private FloatBuffer _clip;
/*     */   float[] proj;
/*     */   float[] modl;
/*     */   float[] clip;
/*     */   
/*     */   public static Frustum getFrustum() {
/*  57 */     frustum.calculateFrustum();
/*  58 */     return frustum;
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
/*     */   private void normalizePlane(float[][] frustum, int side) {
/*  72 */     float magnitude = (float)Math.sqrt((frustum[side][0] * frustum[side][0] + frustum[side][1] * frustum[side][1] + frustum[side][2] * frustum[side][2]));
/*     */ 
/*     */ 
/*     */     
/*  76 */     frustum[side][0] = frustum[side][0] / magnitude;
/*  77 */     frustum[side][1] = frustum[side][1] / magnitude;
/*  78 */     frustum[side][2] = frustum[side][2] / magnitude;
/*  79 */     frustum[side][3] = frustum[side][3] / magnitude;
/*     */   }
/*     */   private Frustum() {
/*  82 */     this._proj = BufferUtils.createFloatBuffer(16);
/*  83 */     this._modl = BufferUtils.createFloatBuffer(16);
/*  84 */     this._clip = BufferUtils.createFloatBuffer(16);
/*  85 */     this.proj = new float[16];
/*  86 */     this.modl = new float[16];
/*  87 */     this.clip = new float[16];
/*     */   }
/*     */   
/*     */   private void calculateFrustum() {
/*  91 */     this._proj.clear();
/*  92 */     this._modl.clear();
/*  93 */     this._clip.clear();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  98 */     GL11.glGetFloat(2983, this._proj);
/*     */ 
/*     */ 
/*     */     
/* 102 */     GL11.glGetFloat(2982, this._modl);
/*     */     
/* 104 */     this._proj.flip().limit(16);
/* 105 */     this._proj.get(this.proj);
/* 106 */     this._modl.flip().limit(16);
/* 107 */     this._modl.get(this.modl);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 112 */     this.clip[0] = this.modl[0] * this.proj[0] + this.modl[1] * this.proj[4] + this.modl[2] * this.proj[8] + this.modl[3] * this.proj[12];
/* 113 */     this.clip[1] = this.modl[0] * this.proj[1] + this.modl[1] * this.proj[5] + this.modl[2] * this.proj[9] + this.modl[3] * this.proj[13];
/* 114 */     this.clip[2] = this.modl[0] * this.proj[2] + this.modl[1] * this.proj[6] + this.modl[2] * this.proj[10] + this.modl[3] * this.proj[14];
/* 115 */     this.clip[3] = this.modl[0] * this.proj[3] + this.modl[1] * this.proj[7] + this.modl[2] * this.proj[11] + this.modl[3] * this.proj[15];
/*     */     
/* 117 */     this.clip[4] = this.modl[4] * this.proj[0] + this.modl[5] * this.proj[4] + this.modl[6] * this.proj[8] + this.modl[7] * this.proj[12];
/* 118 */     this.clip[5] = this.modl[4] * this.proj[1] + this.modl[5] * this.proj[5] + this.modl[6] * this.proj[9] + this.modl[7] * this.proj[13];
/* 119 */     this.clip[6] = this.modl[4] * this.proj[2] + this.modl[5] * this.proj[6] + this.modl[6] * this.proj[10] + this.modl[7] * this.proj[14];
/* 120 */     this.clip[7] = this.modl[4] * this.proj[3] + this.modl[5] * this.proj[7] + this.modl[6] * this.proj[11] + this.modl[7] * this.proj[15];
/*     */     
/* 122 */     this.clip[8] = this.modl[8] * this.proj[0] + this.modl[9] * this.proj[4] + this.modl[10] * this.proj[8] + this.modl[11] * this.proj[12];
/* 123 */     this.clip[9] = this.modl[8] * this.proj[1] + this.modl[9] * this.proj[5] + this.modl[10] * this.proj[9] + this.modl[11] * this.proj[13];
/* 124 */     this.clip[10] = this.modl[8] * this.proj[2] + this.modl[9] * this.proj[6] + this.modl[10] * this.proj[10] + this.modl[11] * this.proj[14];
/* 125 */     this.clip[11] = this.modl[8] * this.proj[3] + this.modl[9] * this.proj[7] + this.modl[10] * this.proj[11] + this.modl[11] * this.proj[15];
/*     */     
/* 127 */     this.clip[12] = this.modl[12] * this.proj[0] + this.modl[13] * this.proj[4] + this.modl[14] * this.proj[8] + this.modl[15] * this.proj[12];
/* 128 */     this.clip[13] = this.modl[12] * this.proj[1] + this.modl[13] * this.proj[5] + this.modl[14] * this.proj[9] + this.modl[15] * this.proj[13];
/* 129 */     this.clip[14] = this.modl[12] * this.proj[2] + this.modl[13] * this.proj[6] + this.modl[14] * this.proj[10] + this.modl[15] * this.proj[14];
/* 130 */     this.clip[15] = this.modl[12] * this.proj[3] + this.modl[13] * this.proj[7] + this.modl[14] * this.proj[11] + this.modl[15] * this.proj[15];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 136 */     this.m_Frustum[0][0] = this.clip[3] - this.clip[0];
/* 137 */     this.m_Frustum[0][1] = this.clip[7] - this.clip[4];
/* 138 */     this.m_Frustum[0][2] = this.clip[11] - this.clip[8];
/* 139 */     this.m_Frustum[0][3] = this.clip[15] - this.clip[12];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 145 */     normalizePlane(this.m_Frustum, 0);
/*     */ 
/*     */     
/* 148 */     this.m_Frustum[1][0] = this.clip[3] + this.clip[0];
/* 149 */     this.m_Frustum[1][1] = this.clip[7] + this.clip[4];
/* 150 */     this.m_Frustum[1][2] = this.clip[11] + this.clip[8];
/* 151 */     this.m_Frustum[1][3] = this.clip[15] + this.clip[12];
/*     */ 
/*     */     
/* 154 */     normalizePlane(this.m_Frustum, 1);
/*     */ 
/*     */     
/* 157 */     this.m_Frustum[2][0] = this.clip[3] + this.clip[1];
/* 158 */     this.m_Frustum[2][1] = this.clip[7] + this.clip[5];
/* 159 */     this.m_Frustum[2][2] = this.clip[11] + this.clip[9];
/* 160 */     this.m_Frustum[2][3] = this.clip[15] + this.clip[13];
/*     */ 
/*     */     
/* 163 */     normalizePlane(this.m_Frustum, 2);
/*     */ 
/*     */     
/* 166 */     this.m_Frustum[3][0] = this.clip[3] - this.clip[1];
/* 167 */     this.m_Frustum[3][1] = this.clip[7] - this.clip[5];
/* 168 */     this.m_Frustum[3][2] = this.clip[11] - this.clip[9];
/* 169 */     this.m_Frustum[3][3] = this.clip[15] - this.clip[13];
/*     */ 
/*     */     
/* 172 */     normalizePlane(this.m_Frustum, 3);
/*     */ 
/*     */     
/* 175 */     this.m_Frustum[4][0] = this.clip[3] - this.clip[2];
/* 176 */     this.m_Frustum[4][1] = this.clip[7] - this.clip[6];
/* 177 */     this.m_Frustum[4][2] = this.clip[11] - this.clip[10];
/* 178 */     this.m_Frustum[4][3] = this.clip[15] - this.clip[14];
/*     */ 
/*     */     
/* 181 */     normalizePlane(this.m_Frustum, 4);
/*     */ 
/*     */     
/* 184 */     this.m_Frustum[5][0] = this.clip[3] + this.clip[2];
/* 185 */     this.m_Frustum[5][1] = this.clip[7] + this.clip[6];
/* 186 */     this.m_Frustum[5][2] = this.clip[11] + this.clip[10];
/* 187 */     this.m_Frustum[5][3] = this.clip[15] + this.clip[14];
/*     */ 
/*     */     
/* 190 */     normalizePlane(this.m_Frustum, 5);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean pointInFrustum(float x, float y, float z) {
/* 195 */     for (int i = 0; i < 6; i++) {
/*     */       
/* 197 */       if (this.m_Frustum[i][0] * x + this.m_Frustum[i][1] * y + this.m_Frustum[i][2] * z + this.m_Frustum[i][3] <= 0.0F)
/*     */       {
/* 199 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 203 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean sphereInFrustum(float x, float y, float z, float radius) {
/* 208 */     for (int i = 0; i < 6; i++) {
/*     */       
/* 210 */       if (this.m_Frustum[i][0] * x + this.m_Frustum[i][1] * y + this.m_Frustum[i][2] * z + this.m_Frustum[i][3] <= -radius)
/*     */       {
/* 212 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 216 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean cubeFullyInFrustum(float x1, float y1, float z1, float x2, float y2, float z2) {
/* 221 */     for (int i = 0; i < 6; i++) {
/*     */       
/* 223 */       if (this.m_Frustum[i][0] * x1 + this.m_Frustum[i][1] * y1 + this.m_Frustum[i][2] * z1 + this.m_Frustum[i][3] <= 0.0F) return false; 
/* 224 */       if (this.m_Frustum[i][0] * x2 + this.m_Frustum[i][1] * y1 + this.m_Frustum[i][2] * z1 + this.m_Frustum[i][3] <= 0.0F) return false; 
/* 225 */       if (this.m_Frustum[i][0] * x1 + this.m_Frustum[i][1] * y2 + this.m_Frustum[i][2] * z1 + this.m_Frustum[i][3] <= 0.0F) return false; 
/* 226 */       if (this.m_Frustum[i][0] * x2 + this.m_Frustum[i][1] * y2 + this.m_Frustum[i][2] * z1 + this.m_Frustum[i][3] <= 0.0F) return false; 
/* 227 */       if (this.m_Frustum[i][0] * x1 + this.m_Frustum[i][1] * y1 + this.m_Frustum[i][2] * z2 + this.m_Frustum[i][3] <= 0.0F) return false; 
/* 228 */       if (this.m_Frustum[i][0] * x2 + this.m_Frustum[i][1] * y1 + this.m_Frustum[i][2] * z2 + this.m_Frustum[i][3] <= 0.0F) return false; 
/* 229 */       if (this.m_Frustum[i][0] * x1 + this.m_Frustum[i][1] * y2 + this.m_Frustum[i][2] * z2 + this.m_Frustum[i][3] <= 0.0F) return false; 
/* 230 */       if (this.m_Frustum[i][0] * x2 + this.m_Frustum[i][1] * y2 + this.m_Frustum[i][2] * z2 + this.m_Frustum[i][3] <= 0.0F) return false;
/*     */     
/*     */     } 
/* 233 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean cubeInFrustum(float x1, float y1, float z1, float x2, float y2, float z2) {
/* 238 */     for (int i = 0; i < 6; ) {
/*     */       
/* 240 */       if (this.m_Frustum[i][0] * x1 + this.m_Frustum[i][1] * y1 + this.m_Frustum[i][2] * z1 + this.m_Frustum[i][3] > 0.0F || 
/* 241 */         this.m_Frustum[i][0] * x2 + this.m_Frustum[i][1] * y1 + this.m_Frustum[i][2] * z1 + this.m_Frustum[i][3] > 0.0F || 
/* 242 */         this.m_Frustum[i][0] * x1 + this.m_Frustum[i][1] * y2 + this.m_Frustum[i][2] * z1 + this.m_Frustum[i][3] > 0.0F || 
/* 243 */         this.m_Frustum[i][0] * x2 + this.m_Frustum[i][1] * y2 + this.m_Frustum[i][2] * z1 + this.m_Frustum[i][3] > 0.0F || 
/* 244 */         this.m_Frustum[i][0] * x1 + this.m_Frustum[i][1] * y1 + this.m_Frustum[i][2] * z2 + this.m_Frustum[i][3] > 0.0F || 
/* 245 */         this.m_Frustum[i][0] * x2 + this.m_Frustum[i][1] * y1 + this.m_Frustum[i][2] * z2 + this.m_Frustum[i][3] > 0.0F || 
/* 246 */         this.m_Frustum[i][0] * x1 + this.m_Frustum[i][1] * y2 + this.m_Frustum[i][2] * z2 + this.m_Frustum[i][3] > 0.0F || 
/* 247 */         this.m_Frustum[i][0] * x2 + this.m_Frustum[i][1] * y2 + this.m_Frustum[i][2] * z2 + this.m_Frustum[i][3] > 0.0F) {
/*     */         i++; continue;
/* 249 */       }  return false;
/*     */     } 
/*     */     
/* 252 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isVisible(AABB aabb) {
/* 257 */     return cubeInFrustum(aabb.x0, aabb.y0, aabb.z0, aabb.x1, aabb.y1, aabb.z1);
/*     */   }
/*     */ }


/* Location:              /home/segfault/.minecraft/versions/rd-20090515/rd-20090515.jar!/com/mojang/minecraft/level/Frustum.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */