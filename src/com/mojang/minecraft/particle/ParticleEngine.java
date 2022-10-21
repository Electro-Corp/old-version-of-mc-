/*    */ package com.mojang.minecraft.particle;
/*    */ 
/*    */ import com.mojang.minecraft.Player;
/*    */ import com.mojang.minecraft.Textures;
/*    */ import com.mojang.minecraft.level.Level;
/*    */ import com.mojang.minecraft.level.Tesselator;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ 
/*    */ public class ParticleEngine
/*    */ {
/*    */   protected Level level;
/* 15 */   private List<Particle> particles = new ArrayList<Particle>();
/*    */ 
/*    */   
/*    */   public ParticleEngine(Level level) {
/* 19 */     this.level = level;
/*    */   }
/*    */ 
/*    */   
/*    */   public void add(Particle p) {
/* 24 */     this.particles.add(p);
/*    */   }
/*    */ 
/*    */   
/*    */   public void tick() {
/* 29 */     for (int i = 0; i < this.particles.size(); i++) {
/*    */       
/* 31 */       Particle p = this.particles.get(i);
/* 32 */       p.tick();
/* 33 */       if (p.removed)
/*    */       {
/* 35 */         this.particles.remove(i--);
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void render(Player player, float a, int layer) {
/* 42 */     GL11.glEnable(3553);
/* 43 */     int id = Textures.loadTexture("/terrain.png", 9728);
/* 44 */     GL11.glBindTexture(3553, id);
/* 45 */     float xa = -((float)Math.cos(player.yRot * Math.PI / 180.0D));
/* 46 */     float za = -((float)Math.sin(player.yRot * Math.PI / 180.0D));
/*    */     
/* 48 */     float xa2 = -za * (float)Math.sin(player.xRot * Math.PI / 180.0D);
/* 49 */     float za2 = xa * (float)Math.sin(player.xRot * Math.PI / 180.0D);
/* 50 */     float ya = (float)Math.cos(player.xRot * Math.PI / 180.0D);
/*    */     
/* 52 */     Tesselator t = Tesselator.instance;
/* 53 */     GL11.glColor4f(0.8F, 0.8F, 0.8F, 1.0F);
/* 54 */     t.init();
/* 55 */     for (int i = 0; i < this.particles.size(); i++) {
/*    */       
/* 57 */       Particle p = this.particles.get(i);
/* 58 */       if ((p.isLit() ^ (((layer == 1) ? 1 : 0)) != 0))
/*    */       {
/* 60 */         p.render(t, a, xa, ya, za, xa2, za2);
/*    */       }
/*    */     } 
/* 63 */     t.flush();
/* 64 */     GL11.glDisable(3553);
/*    */   }
/*    */ }


/* Location:              /home/segfault/.minecraft/versions/rd-20090515/rd-20090515.jar!/com/mojang/minecraft/particle/ParticleEngine.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */