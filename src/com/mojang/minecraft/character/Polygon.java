/*    */ package com.mojang.minecraft.character;
/*    */ 
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ public class Polygon {
/*    */   public Vertex[] vertices;
/*  7 */   public int vertexCount = 0;
/*    */ 
/*    */   
/*    */   public Polygon(Vertex[] vertices) {
/* 11 */     this.vertices = vertices;
/* 12 */     this.vertexCount = vertices.length;
/*    */   }
/*    */ 
/*    */   
/*    */   public Polygon(Vertex[] vertices, int u0, int v0, int u1, int v1) {
/* 17 */     this(vertices);
/*    */     
/* 19 */     vertices[0] = vertices[0].remap(u1, v0);
/* 20 */     vertices[1] = vertices[1].remap(u0, v0);
/* 21 */     vertices[2] = vertices[2].remap(u0, v1);
/* 22 */     vertices[3] = vertices[3].remap(u1, v1);
/*    */   }
/*    */ 
/*    */   
/*    */   public void render() {
/* 27 */     GL11.glColor3f(1.0F, 1.0F, 1.0F);
/* 28 */     for (int i = 3; i >= 0; i--) {
/*    */       
/* 30 */       Vertex v = this.vertices[i];
/* 31 */       GL11.glTexCoord2f(v.u / 63.999F, v.v / 31.999F);
/* 32 */       GL11.glVertex3f(v.pos.x, v.pos.y, v.pos.z);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              /home/segfault/.minecraft/versions/rd-20090515/rd-20090515.jar!/com/mojang/minecraft/character/Polygon.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */