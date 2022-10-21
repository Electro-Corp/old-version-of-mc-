/*    */ package com.mojang.minecraft.character;
/*    */ 
/*    */ 
/*    */ public class Vertex
/*    */ {
/*    */   public Vec3 pos;
/*    */   public float u;
/*    */   public float v;
/*    */   
/*    */   public Vertex(float x, float y, float z, float u, float v) {
/* 11 */     this(new Vec3(x, y, z), u, v);
/*    */   }
/*    */ 
/*    */   
/*    */   public Vertex remap(float u, float v) {
/* 16 */     return new Vertex(this, u, v);
/*    */   }
/*    */ 
/*    */   
/*    */   public Vertex(Vertex vertex, float u, float v) {
/* 21 */     this.pos = vertex.pos;
/* 22 */     this.u = u;
/* 23 */     this.v = v;
/*    */   }
/*    */ 
/*    */   
/*    */   public Vertex(Vec3 pos, float u, float v) {
/* 28 */     this.pos = pos;
/* 29 */     this.u = u;
/* 30 */     this.v = v;
/*    */   }
/*    */ }


/* Location:              /home/segfault/.minecraft/versions/rd-20090515/rd-20090515.jar!/com/mojang/minecraft/character/Vertex.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */