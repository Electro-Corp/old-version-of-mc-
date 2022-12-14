/*    */ package com.mojang.minecraft;
/*    */ 
/*    */ import com.mojang.minecraft.level.Level;
/*    */ import org.lwjgl.input.Keyboard;
/*    */ import java.util.Random;
/*    */ 
/*    */ public class Player
/*    */   extends Entity
/*    */ {
/*    */   public Player(Level level) {
/* 11 */     super(level);
/* 12 */     this.heightOffset = 1.62F;
/*    */   }
		   Random rand = new Random();
/*    */   public float camrot = 0;
		   private float val = 1F;
		   private float yMax = 16.0F;
		   private float xMax = 5.0F;
/*    */   public float camrotY = 0;
		   public float speed = 1.0F;
		   private float maxSpeed = 1.3F;
		   private float speedVal = 0.1F;
		   public float stamina = 100F;
		   private float prevX, prevY, prevZ;
		   //private boolean isReturning;
/*    */   public void tick() {
/* 17 */     this.xo = this.x;
/* 18 */     this.yo = this.y;
/* 19 */     this.zo = this.z;
/* 20 */     float xa = 0.0F;
/* 21 */     float ya = 0.0F;
/*    */     
/* 23 */     if (Keyboard.isKeyDown(19))
/*    */     {
/* 25 */       resetPos();
/*    */     }
/* 27 */     if (Keyboard.isKeyDown(200) || Keyboard.isKeyDown(17)) { 
				ya-= 10; 
				if(camrotY < yMax) {
				camrotY+=val * 2;
				}
			}
/* 28 */      else if (Keyboard.isKeyDown(208) || Keyboard.isKeyDown(31)) {
	ya+= 10;
	if(camrotY > yMax * -1) {
	camrotY -= val * 2;
	}
}else {
	
	if(camrotY < 0F) {
		camrotY += val;
	}else if(camrotY > 0F) {
		camrotY -= val;
	}else {
	camrotY = 0F;
	}
}
/* 29 */      if (Keyboard.isKeyDown(203) || Keyboard.isKeyDown(30)) {
	xa-= 10; 
	if(camrot < xMax) {
	camrot+=val;
	}
}
/* 30 */      else if (Keyboard.isKeyDown(205) || Keyboard.isKeyDown(32)) {
					xa+= 10; 
					if(camrot > xMax * -1) {
					camrot-=val;
					}
				}else {
					if(camrot < 0F) {
						camrot += val;
					}else if(camrot > 0F) {
						camrot -= val;
					}else {
					camrot = 0F;
					}
				}
/* 31 */      if (Keyboard.isKeyDown(57) || Keyboard.isKeyDown(219))
/*    */     {
/* 33 */       if (this.onGround)
/*    */       {
/* 35 */         this.yd = 0.5F;
				if(camrotY < yMax) {
					camrotY+=val * 2;
					}
/*    */       }
/*    */     }
             if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
            	 if(stamina > 0) {
            		 stamina -= 1;
                	 System.out.println("Stamina: "+stamina);
            	 }else if(stamina <= 5) {
            		 speed = 1F;
            	 }
            	 if(speed < maxSpeed ) {
            		 speed += speedVal;
            	
            	 }
             }else {
            	 if(stamina < 100) {
            		 stamina += 0.5;
            	 }
            	 if(speed > 1F) {
            		 speed -= speedVal;
            	 }else {
            		 if(speed < 1F) {
            			 speed = 1F;
            		 }
            	 }
             }
///*    */     if(Keyboard.isKeyDown(17)) {
//	            //System.out.println("Oh hey x:"+x+" Y: "+y);
//				if((Math.abs(x - prevX) < 0.001) && (Math.abs(y - prevY) < 0.01)){//&& rand.nextFloat() > 0.8) {
//					this.yd = 0.5F;
//				}
//			 }
             
/* 39 */     moveRelative(xa, ya, this.onGround ? 0.1F : 0.02F);
/*    */     
/* 41 */     this.yd = (float)(this.yd - 0.08D);
/* 42 */     move(this.xd, this.yd, this.zd);
/* 43 */     this.xd *= 0.91F;
/* 44 */     this.yd *= 0.98F;
/* 45 */     this.zd *= 0.91F;
/*    */     
/* 47 */     //if (this.onGround) {
/*    */       
/* 49 */       this.xd *= 0.9F * speed;
/* 50 */       this.zd *= 0.9F * speed;
/*    */     //} 
            if(rand.nextFloat() > 0.8) {
				prevX = x;
				prevY = y;
				prevZ = z;
            }
/*    */   }
/*    */ }


/* Location:              /home/segfault/.minecraft/versions/rd-20090515/rd-20090515.jar!/com/mojang/minecraft/Player.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */