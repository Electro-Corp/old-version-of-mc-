/*    */ package com.mojang.minecraft;
/*    */ 
/*    */ import java.awt.image.BufferedImage;
/*    */ import java.io.IOException;
/*    */ import java.nio.ByteBuffer;
/*    */ import java.nio.IntBuffer;
/*    */ import java.util.HashMap;
/*    */ import javax.imageio.ImageIO;
/*    */ import org.lwjgl.BufferUtils;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ import org.lwjgl.util.glu.GLU;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Textures
/*    */ {
/* 18 */   private static HashMap<String, Integer> idMap = new HashMap<String, Integer>();
/*    */ 
/*    */ 
/*    */   
/*    */   public static int loadTexture(String resourceName, int mode) {
/*    */     try {
/* 24 */       if (idMap.containsKey(resourceName))
/*    */       {
/* 26 */         return ((Integer)idMap.get(resourceName)).intValue();
/*    */       }
/*    */ 
/*    */       
/* 30 */       IntBuffer ib = BufferUtils.createIntBuffer(1);
/* 31 */       ib.clear();
/* 32 */       GL11.glGenTextures(ib);
/* 33 */       int id = ib.get(0);
/* 34 */       idMap.put(resourceName, Integer.valueOf(id));
/* 35 */       System.out.println(String.valueOf(resourceName) + " -> " + id);
/*    */       
/* 37 */       GL11.glBindTexture(3553, id);
/*    */ 
/*    */ 
/*    */       
/* 41 */       GL11.glTexParameteri(3553, 10241, mode);
/* 42 */       GL11.glTexParameteri(3553, 10240, mode);
/*    */ 
/*    */ 
/*    */       
/* 46 */       BufferedImage img = ImageIO.read(Textures.class.getResourceAsStream(resourceName));
/* 47 */       int w = img.getWidth();
/* 48 */       int h = img.getHeight();
/*    */       
/* 50 */       ByteBuffer pixels = BufferUtils.createByteBuffer(w * h * 4);
/* 51 */       int[] rawPixels = new int[w * h];
/* 52 */       img.getRGB(0, 0, w, h, rawPixels, 0, w);
/* 53 */       for (int i = 0; i < rawPixels.length; i++) {
/*    */         
/* 55 */         int a = rawPixels[i] >> 24 & 0xFF;
/* 56 */         int r = rawPixels[i] >> 16 & 0xFF;
/* 57 */         int g = rawPixels[i] >> 8 & 0xFF;
/* 58 */         int b = rawPixels[i] & 0xFF;
/*    */         
/* 60 */         rawPixels[i] = a << 24 | b << 16 | g << 8 | r;
/*    */       } 
/* 62 */       pixels.asIntBuffer().put(rawPixels);
/* 63 */       GLU.gluBuild2DMipmaps(3553, 6408, w, h, 6408, 5121, pixels);
/*    */ 
/*    */       
/* 66 */       return id;
/*    */     }
/* 68 */     catch (IOException e) {
/*    */       
/* 70 */       throw new RuntimeException("!!");
/*    */     } 
/*    */   }
/*    */ }


/* Location:              /home/segfault/.minecraft/versions/rd-20090515/rd-20090515.jar!/com/mojang/minecraft/Textures.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */