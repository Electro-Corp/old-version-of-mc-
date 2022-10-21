/*     */ package com.mojang.minecraft.character;
/*     */ 
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Cube
/*     */ {
/*     */   private Vertex[] vertices;
/*     */   private Polygon[] polygons;
/*     */   private int xTexOffs;
/*     */   private int yTexOffs;
/*     */   public float x;
/*  15 */   private int list = 0; public float y; public float z; public float xRot; public float yRot; public float zRot;
/*     */   private boolean compiled = false;
/*     */   
/*     */   public Cube(int xTexOffs, int yTexOffs) {
/*  19 */     this.xTexOffs = xTexOffs;
/*  20 */     this.yTexOffs = yTexOffs;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setTexOffs(int xTexOffs, int yTexOffs) {
/*  25 */     this.xTexOffs = xTexOffs;
/*  26 */     this.yTexOffs = yTexOffs;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addBox(float x0, float y0, float z0, int w, int h, int d) {
/*  31 */     this.vertices = new Vertex[8];
/*  32 */     this.polygons = new Polygon[6];
/*     */     
/*  34 */     float x1 = x0 + w;
/*  35 */     float y1 = y0 + h;
/*  36 */     float z1 = z0 + d;
/*     */     
/*  38 */     Vertex u0 = new Vertex(x0, y0, z0, 0.0F, 0.0F);
/*  39 */     Vertex u1 = new Vertex(x1, y0, z0, 0.0F, 8.0F);
/*  40 */     Vertex u2 = new Vertex(x1, y1, z0, 8.0F, 8.0F);
/*  41 */     Vertex u3 = new Vertex(x0, y1, z0, 8.0F, 0.0F);
/*     */     
/*  43 */     Vertex l0 = new Vertex(x0, y0, z1, 0.0F, 0.0F);
/*  44 */     Vertex l1 = new Vertex(x1, y0, z1, 0.0F, 8.0F);
/*  45 */     Vertex l2 = new Vertex(x1, y1, z1, 8.0F, 8.0F);
/*  46 */     Vertex l3 = new Vertex(x0, y1, z1, 8.0F, 0.0F);
/*     */     
/*  48 */     this.vertices[0] = u0;
/*  49 */     this.vertices[1] = u1;
/*  50 */     this.vertices[2] = u2;
/*  51 */     this.vertices[3] = u3;
/*  52 */     this.vertices[4] = l0;
/*  53 */     this.vertices[5] = l1;
/*  54 */     this.vertices[6] = l2;
/*  55 */     this.vertices[7] = l3;
/*     */     
/*  57 */     this.polygons[0] = new Polygon(new Vertex[] { l1, u1, u2, l2 }, this.xTexOffs + d + w, this.yTexOffs + d, this.xTexOffs + d + w + d, this.yTexOffs + d + h);
/*  58 */     this.polygons[1] = new Polygon(new Vertex[] { u0, l0, l3, u3 }, this.xTexOffs + 0, this.yTexOffs + d, this.xTexOffs + d, this.yTexOffs + d + h);
/*     */     
/*  60 */     this.polygons[2] = new Polygon(new Vertex[] { l1, l0, u0, u1 }, this.xTexOffs + d, this.yTexOffs + 0, this.xTexOffs + d + w, this.yTexOffs + d);
/*  61 */     this.polygons[3] = new Polygon(new Vertex[] { u2, u3, l3, l2 }, this.xTexOffs + d + w, this.yTexOffs + 0, this.xTexOffs + d + w + w, this.yTexOffs + d);
/*     */     
/*  63 */     this.polygons[4] = new Polygon(new Vertex[] { u1, u0, u3, u2 }, this.xTexOffs + d, this.yTexOffs + d, this.xTexOffs + d + w, this.yTexOffs + d + h);
/*  64 */     this.polygons[5] = new Polygon(new Vertex[] { l0, l1, l2, l3 }, this.xTexOffs + d + w + d, this.yTexOffs + d, this.xTexOffs + d + w + d + w, this.yTexOffs + d + h);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPos(float x, float y, float z) {
/*  69 */     this.x = x;
/*  70 */     this.y = y;
/*  71 */     this.z = z;
/*     */   }
/*     */ 
/*     */   
/*     */   public void render() {
/*  76 */     if (!this.compiled) compile();
/*     */     
/*  78 */     float c = 57.29578F;
/*  79 */     GL11.glPushMatrix();
/*  80 */     GL11.glTranslatef(this.x, this.y, this.z);
/*  81 */     GL11.glRotatef(this.zRot * c, 0.0F, 0.0F, 1.0F);
/*  82 */     GL11.glRotatef(this.yRot * c, 0.0F, 1.0F, 0.0F);
/*  83 */     GL11.glRotatef(this.xRot * c, 1.0F, 0.0F, 0.0F);
/*     */     
/*  85 */     GL11.glCallList(this.list);
/*  86 */     GL11.glPopMatrix();
/*     */   }
/*     */ 
/*     */   
/*     */   private void compile() {
/*  91 */     this.list = GL11.glGenLists(1);
/*  92 */     GL11.glNewList(this.list, 4864);
/*  93 */     GL11.glBegin(7);
/*  94 */     for (int i = 0; i < this.polygons.length; i++)
/*     */     {
/*  96 */       this.polygons[i].render();
/*     */     }
/*  98 */     GL11.glEnd();
/*  99 */     GL11.glEndList();
/* 100 */     this.compiled = true;
/*     */   }
/*     */ }


/* Location:              /home/segfault/.minecraft/versions/rd-20090515/rd-20090515.jar!/com/mojang/minecraft/character/Cube.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */