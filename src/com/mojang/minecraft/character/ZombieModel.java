/*    */ package com.mojang.minecraft.character;
/*    */ 
/*    */ public class ZombieModel {
/*    */   public Cube head;
/*    */   public Cube body;
/*    */   public Cube arm0;
/*    */   
/*    */   public ZombieModel() {
/*  9 */     this.head = new Cube(0, 0);
/* 10 */     this.head.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8);
/*    */     
/* 12 */     this.body = new Cube(16, 16);
/* 13 */     this.body.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4);
/*    */     
/* 15 */     this.arm0 = new Cube(40, 16);
/* 16 */     this.arm0.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4);
/* 17 */     this.arm0.setPos(-5.0F, 2.0F, 0.0F);
/*    */     
/* 19 */     this.arm1 = new Cube(40, 16);
/* 20 */     this.arm1.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4);
/* 21 */     this.arm1.setPos(5.0F, 2.0F, 0.0F);
/*    */     
/* 23 */     this.leg0 = new Cube(0, 16);
/* 24 */     this.leg0.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4);
/* 25 */     this.leg0.setPos(-2.0F, 12.0F, 0.0F);
/*    */     
/* 27 */     this.leg1 = new Cube(0, 16);
/* 28 */     this.leg1.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4);
/* 29 */     this.leg1.setPos(2.0F, 12.0F, 0.0F);
/*    */   }
/*    */   public Cube arm1; public Cube leg0; public Cube leg1;
/*    */   
/*    */   public void render(float time) {
/* 34 */     this.head.yRot = (float)Math.sin(time * 0.83D) * 1.0F;
/* 35 */     this.head.xRot = (float)Math.sin(time) * 0.8F;
/*    */     
/* 37 */     this.arm0.xRot = (float)Math.sin(time * 0.6662D + Math.PI) * 2.0F;
/* 38 */     this.arm0.zRot = (float)(Math.sin(time * 0.2312D) + 1.0D) * 1.0F;
/*    */     
/* 40 */     this.arm1.xRot = (float)Math.sin(time * 0.6662D) * 2.0F;
/* 41 */     this.arm1.zRot = (float)(Math.sin(time * 0.2812D) - 1.0D) * 1.0F;
/*    */     
/* 43 */     this.leg0.xRot = (float)Math.sin(time * 0.6662D) * 1.4F;
/* 44 */     this.leg1.xRot = (float)Math.sin(time * 0.6662D + Math.PI) * 1.4F;
/*    */     
/* 46 */     this.head.render();
/* 47 */     this.body.render();
/* 48 */     this.arm0.render();
/* 49 */     this.arm1.render();
/* 50 */     this.leg0.render();
/* 51 */     this.leg1.render();
/*    */   }
/*    */ }


/* Location:              /home/segfault/.minecraft/versions/rd-20090515/rd-20090515.jar!/com/mojang/minecraft/character/ZombieModel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */