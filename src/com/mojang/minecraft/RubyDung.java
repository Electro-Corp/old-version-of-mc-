/*     */ package com.mojang.minecraft;
/*     */ 
/*     */ import com.mojang.minecraft.character.Zombie;
/*     */ import com.mojang.minecraft.level.Chunk;
/*     */ import com.mojang.minecraft.level.Frustum;
/*     */ import com.mojang.minecraft.level.Level;
/*     */ import com.mojang.minecraft.level.LevelRenderer;
/*     */ import com.mojang.minecraft.level.Tesselator;
/*     */ import com.mojang.minecraft.level.tile.Tile;
/*     */ import com.mojang.minecraft.particle.ParticleEngine;
/*     */ import java.io.IOException;
/*     */ import java.nio.FloatBuffer;
/*     */ import java.nio.IntBuffer;
/*     */ import java.util.ArrayList;
/*     */ import javax.swing.JOptionPane;
/*     */ import org.lwjgl.BufferUtils;
/*     */ import org.lwjgl.LWJGLException;
/*     */ import org.lwjgl.input.Keyboard;
/*     */ import org.lwjgl.input.Mouse;
/*     */ import org.lwjgl.opengl.Display;
/*     */ import org.lwjgl.opengl.DisplayMode;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import org.lwjgl.util.glu.GLU;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RubyDung
/*     */   implements Runnable
/*     */ {
/*     */   private static final boolean FULLSCREEN_MODE = false;
/*     */   private int width;
/*     */   private int height;
/*  34 */   private FloatBuffer fogColor0 = BufferUtils.createFloatBuffer(4);
/*  35 */   private FloatBuffer fogColor1 = BufferUtils.createFloatBuffer(4);
/*  36 */   private Timer timer = new Timer(20.0F);
/*     */   private Level level;
/*     */   private LevelRenderer levelRenderer;
/*     */   private Player player;
/*  40 */   private int paintTexture = 1;
/*     */   
/*     */   private ParticleEngine particleEngine;
/*  43 */   private ArrayList<Zombie> zombies = new ArrayList<Zombie>();
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
/*     */   public void init() throws LWJGLException, IOException {
/*  59 */     int col0 = 16710650;
/*  60 */     int col1 = 920330;
/*  61 */     float fr = 0.5F;
/*  62 */     float fg = 0.8F;
/*  63 */     float fb = 1.0F;
/*  64 */     this.fogColor0.put(new float[] { (col0 >> 16 & 0xFF) / 255.0F, (col0 >> 8 & 0xFF) / 255.0F, (col0 & 0xFF) / 255.0F, 1.0F });
/*  65 */     this.fogColor0.flip();
/*  66 */     this.fogColor1.put(new float[] { (col1 >> 16 & 0xFF) / 255.0F, (col1 >> 8 & 0xFF) / 255.0F, (col1 & 0xFF) / 255.0F, 1.0F });
/*  67 */     this.fogColor1.flip();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  76 */     Display.setDisplayMode(new DisplayMode(1024, 720));
/*     */     
/*  78 */     Display.create();
/*  79 */     Keyboard.create();
/*  80 */     Mouse.create();
/*     */     
/*  82 */     this.width = Display.getDisplayMode().getWidth();
/*  83 */     this.height = Display.getDisplayMode().getHeight();
/*     */     
/*  85 */     GL11.glEnable(3553);
/*  86 */     GL11.glShadeModel(7425);
/*  87 */     GL11.glClearColor(fr, fg, fb, 0.0F);
/*  88 */     GL11.glClearDepth(1.0D);
/*  89 */     GL11.glEnable(2929);
/*  90 */     GL11.glDepthFunc(515);
/*  91 */     GL11.glEnable(3008);
/*  92 */     GL11.glAlphaFunc(516, 0.5F);
/*     */     
/*  94 */     GL11.glMatrixMode(5889);
/*  95 */     GL11.glLoadIdentity();
/*     */     
/*  97 */     GL11.glMatrixMode(5888);
/*     */     
/*  99 */     this.level = new Level(256, 256, 64);
/* 100 */     this.levelRenderer = new LevelRenderer(this.level);
/* 101 */     this.player = new Player(this.level);
/* 102 */     this.particleEngine = new ParticleEngine(this.level);
/*     */     
/* 104 */     Mouse.setGrabbed(true);
/*     */     
///* 106 */     for (int i = 0; i < 10; i++) {
///*     */       
///* 108 */       Zombie zombie = new Zombie(this.level, 128.0F, 0.0F, 128.0F);
///* 109 */       zombie.resetPos();
///* 110 */       this.zombies.add(zombie);
///*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void destroy() {
/* 116 */     this.level.save();
/*     */     
/* 118 */     Mouse.destroy();
/* 119 */     Keyboard.destroy();
/* 120 */     Display.destroy();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void run() {
/*     */     try {
/* 127 */       init();
/*     */     }
/* 129 */     catch (Exception e) {
/*     */       
/* 131 */       JOptionPane.showMessageDialog(null, e.toString(), "Failed to start RubyDung", 0);
/* 132 */       System.exit(0);
/*     */     } 
/*     */     
/* 135 */     long lastTime = System.currentTimeMillis();
/* 136 */     int frames = 0;
/*     */     
/*     */     try {
/* 139 */       while (!Keyboard.isKeyDown(1) && !Display.isCloseRequested())
/*     */       {
/* 141 */         this.timer.advanceTime();
/* 142 */         for (int i = 0; i < this.timer.ticks; i++)
/*     */         {
/* 144 */           tick();
/*     */         }
/* 146 */         render(this.timer.a);
/* 147 */         frames++;
/*     */         
/* 149 */         while (System.currentTimeMillis() >= lastTime + 1000L)
/*     */         {
/* 151 */           System.out.println(String.valueOf(frames) + " fps, " + Chunk.updates);
/* 152 */           Chunk.updates = 0;
/*     */           
/* 154 */           lastTime += 1000L;
/* 155 */           frames = 0;
/*     */         }
/*     */       
/*     */       }
/*     */     
/* 160 */     } catch (Exception e) {
/*     */       
/* 162 */       e.printStackTrace();
/*     */     }
/*     */     finally {
/*     */       
/* 166 */       destroy();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void tick() {
/* 172 */     while (Keyboard.next()) {
/*     */       
/* 174 */       if (Keyboard.getEventKeyState()) {
/*     */         
/* 176 */         if (Keyboard.getEventKey() == 28)
/*     */         {
/* 178 */           this.level.save();
/*     */         }
/* 180 */         if (Keyboard.getEventKey() == 2) this.paintTexture = 1; 
/* 181 */         if (Keyboard.getEventKey() == 3) this.paintTexture = 3; 
/* 182 */         if (Keyboard.getEventKey() == 4) this.paintTexture = 4; 
/* 183 */         if (Keyboard.getEventKey() == 5) this.paintTexture = 5; 
/* 184 */         if (Keyboard.getEventKey() == 7) this.paintTexture = 6; 
/* 185 */         if (Keyboard.getEventKey() == 34)
/*     */         {
/* 187 */           this.zombies.add(new Zombie(this.level, this.player.x, this.player.y, this.player.z,this.player));
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 192 */     this.level.tick();
/* 193 */     this.particleEngine.tick();
/*     */     
/* 195 */     for (int i = 0; i < this.zombies.size(); i++) {
/*     */       
/* 197 */       ((Zombie)this.zombies.get(i)).tick();
/* 198 */       if (((Zombie)this.zombies.get(i)).removed)
/*     */       {
/* 200 */         this.zombies.remove(i--);
/*     */       }
/*     */     } 
/*     */     
/* 204 */     this.player.tick();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void moveCameraToPlayer(float a) {
			  // Source engine like camera movement
			  GL11.glRotatef(this.player.camrot, 0.0F, 0.0F, 1.0F);
			  GL11.glRotatef(this.player.camrotY, 1.0F, 0.0F, 0.0F);
			  
/* 212 */     GL11.glTranslatef(0.0F, 0.0F, -0.3F);
/* 213 */     GL11.glRotatef(this.player.xRot, 1.0F, 0.0F, 0.0F);
/* 214 */     GL11.glRotatef(this.player.yRot, 0.0F, 1.0F, 0.0F);
			  // default rotations
			  
/* 216 */     float x = this.player.xo + (this.player.x - this.player.xo) * a;
/* 217 */     float y = this.player.yo + (this.player.y - this.player.yo) * a;
/* 218 */     float z = this.player.zo + (this.player.z - this.player.zo) * a;
/* 219 */     GL11.glTranslatef(-x, -y, -z);
/*     */   }
/*     */ 
/*     */   
/*     */   private void setupCamera(float a) {
/* 224 */     GL11.glMatrixMode(5889);
/* 225 */     GL11.glLoadIdentity();
/* 226 */     GLU.gluPerspective(70.0F, this.width / this.height, 0.05F, 1000.0F);
/* 227 */     GL11.glMatrixMode(5888);
/* 228 */     GL11.glLoadIdentity();
/* 229 */     moveCameraToPlayer(a);
/*     */   }
/*     */   
/* 232 */   private IntBuffer viewportBuffer = BufferUtils.createIntBuffer(16);
/*     */ 
/*     */   
/*     */   private void setupPickCamera(float a, int x, int y) {
/* 236 */     GL11.glMatrixMode(5889);
/* 237 */     GL11.glLoadIdentity();
/* 238 */     this.viewportBuffer.clear();
/* 239 */     GL11.glGetInteger(2978, this.viewportBuffer);
/* 240 */     this.viewportBuffer.flip();
/* 241 */     this.viewportBuffer.limit(16);
/* 242 */     GLU.gluPickMatrix(x, y, 5.0F, 5.0F, this.viewportBuffer);
/* 243 */     GLU.gluPerspective(70.0F, this.width / this.height, 0.05F, 1000.0F);
/* 244 */     GL11.glMatrixMode(5888);
/* 245 */     GL11.glLoadIdentity();
/* 246 */     moveCameraToPlayer(a);
/*     */   }
/*     */   
/* 249 */   private IntBuffer selectBuffer = BufferUtils.createIntBuffer(2000);
/* 250 */   private HitResult hitResult = null;
/*     */ 
/*     */   
/*     */   private void pick(float a) {
/* 254 */     this.selectBuffer.clear();
/* 255 */     GL11.glSelectBuffer(this.selectBuffer);
/* 256 */     GL11.glRenderMode(7170);
/* 257 */     setupPickCamera(a, this.width / 2, this.height / 2);
/* 258 */     this.levelRenderer.pick(this.player, Frustum.getFrustum());
/* 259 */     int hits = GL11.glRenderMode(7168);
/* 260 */     this.selectBuffer.flip();
/* 261 */     this.selectBuffer.limit(this.selectBuffer.capacity());
/*     */     
/* 263 */     long closest = 0L;
/* 264 */     int[] names = new int[10];
/* 265 */     int hitNameCount = 0;
/* 266 */     for (int i = 0; i < hits; i++) {
/*     */       
/* 268 */       int nameCount = this.selectBuffer.get();
/* 269 */       long minZ = this.selectBuffer.get();
/* 270 */       this.selectBuffer.get();
/*     */       
/* 272 */       long dist = minZ;
/*     */       
/* 274 */       if (dist < closest || i == 0) {
/*     */         
/* 276 */         closest = dist;
/* 277 */         hitNameCount = nameCount;
/* 278 */         for (int j = 0; j < nameCount; j++) {
/* 279 */           names[j] = this.selectBuffer.get();
/*     */         }
/*     */       } else {
/*     */         
/* 283 */         for (int j = 0; j < nameCount; j++) {
/* 284 */           this.selectBuffer.get();
/*     */         }
/*     */       } 
/*     */     } 
/* 288 */     if (hitNameCount > 0) {
/*     */       
/* 290 */       this.hitResult = new HitResult(names[0], names[1], names[2], names[3], names[4]);
/*     */     }
/*     */     else {
/*     */       
/* 294 */       this.hitResult = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(float a) {
/* 300 */     float xo = Mouse.getDX();
/* 301 */     float yo = Mouse.getDY();
/* 302 */     this.player.turn(xo, yo);
/* 303 */     pick(a);
/*     */     
/* 305 */     while (Mouse.next()) {
/*     */       
/* 307 */       if (Mouse.getEventButton() == 1 && Mouse.getEventButtonState())
/*     */       {
/* 309 */         if (this.hitResult != null) {
/*     */           
/* 311 */           Tile oldTile = Tile.tiles[this.level.getTile(this.hitResult.x, this.hitResult.y, this.hitResult.z)];
/* 312 */           boolean changed = this.level.setTile(this.hitResult.x, this.hitResult.y, this.hitResult.z, 0);
/* 313 */           if (oldTile != null && changed)
/*     */           {
/* 315 */             oldTile.destroy(this.level, this.hitResult.x, this.hitResult.y, this.hitResult.z, this.particleEngine);
/*     */           }
/*     */         } 
/*     */       }
/* 319 */       if (Mouse.getEventButton() == 0 && Mouse.getEventButtonState())
/*     */       {
/* 321 */         if (this.hitResult != null) {
/*     */           
/* 323 */           int x = this.hitResult.x;
/* 324 */           int y = this.hitResult.y;
/* 325 */           int z = this.hitResult.z;
/*     */           
/* 327 */           if (this.hitResult.f == 0) y--; 
/* 328 */           if (this.hitResult.f == 1) y++; 
/* 329 */           if (this.hitResult.f == 2) z--; 
/* 330 */           if (this.hitResult.f == 3) z++; 
/* 331 */           if (this.hitResult.f == 4) x--; 
/* 332 */           if (this.hitResult.f == 5) x++;
/*     */           
/* 334 */           this.level.setTile(x, y, z, this.paintTexture);
/*     */         } 
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 341 */     GL11.glClear(16640);
/* 342 */     setupCamera(a);
/*     */     
/* 344 */     GL11.glEnable(2884);
/*     */     
/* 346 */     Frustum frustum = Frustum.getFrustum();
/*     */     
/* 348 */     this.levelRenderer.updateDirtyChunks(this.player);
/*     */     
/* 350 */     setupFog(0);
/* 351 */     GL11.glEnable(2912);
/* 352 */     this.levelRenderer.render(this.player, 0); int i;
/* 353 */     for (i = 0; i < this.zombies.size(); i++) {
/*     */       
/* 355 */       Zombie zombie = this.zombies.get(i);
/* 356 */       if (zombie.isLit() && frustum.isVisible(zombie.bb))
/*     */       {
/* 358 */         ((Zombie)this.zombies.get(i)).render(a);
/*     */       }
/*     */     } 
/* 361 */     this.particleEngine.render(this.player, a, 0);
/* 362 */     setupFog(1);
/* 363 */     this.levelRenderer.render(this.player, 1);
/* 364 */     for (i = 0; i < this.zombies.size(); i++) {
/*     */       
/* 366 */       Zombie zombie = this.zombies.get(i);
/* 367 */       if (!zombie.isLit() && frustum.isVisible(zombie.bb))
/*     */       {
/* 369 */         ((Zombie)this.zombies.get(i)).render(a);
/*     */       }
/*     */     } 
/* 372 */     this.particleEngine.render(this.player, a, 1);
/* 373 */     GL11.glDisable(2896);
/* 374 */     GL11.glDisable(3553);
/* 375 */     GL11.glDisable(2912);
/*     */     
/* 377 */     if (this.hitResult != null) {
/*     */       
/* 379 */       GL11.glDisable(3008);
/* 380 */       this.levelRenderer.renderHit(this.hitResult);
/* 381 */       GL11.glEnable(3008);
/*     */     } 
/*     */     
/* 384 */     drawGui(a);
/*     */     
/* 386 */     Display.update();
/*     */   }
/*     */ 
/*     */   
/*     */   private void drawGui(float a) {
/* 391 */     int screenWidth = this.width * 240 / this.height;
/* 392 */     int screenHeight = this.height * 240 / this.height;
/*     */     
/* 394 */     GL11.glClear(256);
/* 395 */     GL11.glMatrixMode(5889);
/* 396 */     GL11.glLoadIdentity();
/* 397 */     GL11.glOrtho(0.0D, screenWidth, screenHeight, 0.0D, 100.0D, 300.0D);
/* 398 */     GL11.glMatrixMode(5888);
/* 399 */     GL11.glLoadIdentity();
/* 400 */     GL11.glTranslatef(0.0F, 0.0F, -200.0F);
/*     */ 
/*     */     
/* 403 */     GL11.glPushMatrix();
/* 404 */     GL11.glTranslatef((screenWidth - 16), 16.0F, 0.0F);
/* 405 */     Tesselator t = Tesselator.instance;
/* 406 */     GL11.glScalef(16.0F, 16.0F, 16.0F);
/* 407 */     GL11.glRotatef(30.0F, 1.0F, 0.0F, 0.0F);
/* 408 */     GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
/* 409 */     GL11.glTranslatef(-1.5F, 0.5F, -0.5F);
/* 410 */     GL11.glScalef(-1.0F, -1.0F, 1.0F);
/*     */     
/* 412 */     int id = Textures.loadTexture("/terrain.png", 9728);
/* 413 */     GL11.glBindTexture(3553, id);
/* 414 */     GL11.glEnable(3553);
/* 415 */     t.init();
			  
/* 416 */     Tile.tiles[this.paintTexture].render(t, this.level, 0, -2, 0, 0);
/* 417 */     t.flush();
/* 418 */     GL11.glDisable(3553);
/* 419 */     GL11.glPopMatrix();
/*     */     
/* 421 */     int wc = screenWidth / 2;
/* 422 */     int hc = screenHeight / 2;
/* 423 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 424 */     t.init();
/* 425 */     t.vertex((wc + 1), (hc - 4), 0.0F);
/* 426 */     t.vertex((wc - 0), (hc - 4), 0.0F);
/* 427 */     t.vertex((wc - 0), (hc + 5), 0.0F);
/* 428 */     t.vertex((wc + 1), (hc + 5), 0.0F);
/*     */     
/* 430 */     t.vertex((wc + 5), (hc - 0), 0.0F);
/* 431 */     t.vertex((wc - 4), (hc - 0), 0.0F);
/* 432 */     t.vertex((wc - 4), (hc + 1), 0.0F);
/* 433 */     t.vertex((wc + 5), (hc + 1), 0.0F);
/* 434 */     t.flush();
/*     */   }
/*     */   
/* 437 */   FloatBuffer lb = BufferUtils.createFloatBuffer(16);
/*     */ 
/*     */   
/*     */   private void setupFog(int i) {
/* 441 */     if (i == 0) {
/*     */       
/* 443 */       GL11.glFogi(2917, 2048);
/* 444 */       GL11.glFogf(2914, 0.001F);
/* 445 */       GL11.glFog(2918, this.fogColor0);
/* 446 */       GL11.glDisable(2896);
/*     */     }
/* 448 */     else if (i == 1) {
/*     */       
/* 450 */       GL11.glFogi(2917, 2048);
/* 451 */       GL11.glFogf(2914, 0.06F);
/* 452 */       GL11.glFog(2918, this.fogColor1);
/* 453 */       GL11.glEnable(2896);
/* 454 */       GL11.glEnable(2903);
/*     */       
/* 456 */       float br = 0.6F;
/* 457 */       GL11.glLightModel(2899, getBuffer(br, br, br, 1.0F));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private FloatBuffer getBuffer(float a, float b, float c, float d) {
/* 463 */     this.lb.clear();
/* 464 */     this.lb.put(a).put(b).put(c).put(d);
/* 465 */     this.lb.flip();
/* 466 */     return this.lb;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void checkError() {
/* 471 */     int e = GL11.glGetError();
/* 472 */     if (e != 0)
/*     */     {
/* 474 */       throw new IllegalStateException(GLU.gluErrorString(e));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static void main(String[] args) throws LWJGLException {
/* 480 */     (new Thread(new RubyDung())).start();
/*     */   }
/*     */ }


/* Location:              /home/segfault/.minecraft/versions/rd-20090515/rd-20090515.jar!/com/mojang/minecraft/RubyDung.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */