// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/04/Fill.asm

// Runs an infinite loop that listens to the keyboard input.
// When a key is pressed (any key), the program blackens the screen,
// i.e. writes "black" in every pixel;
// the screen should remain fully black as long as the key is pressed. 
// When no key is pressed, the program clears the screen, i.e. writes
// "white" in every pixel;
// the screen should remain fully clear as long as no key is pressed.
// CREATIVE IDEA:For the others, find some way to turn it into 32768 (16 true bits) and assign this to the register. Creative idea here?

// Put your code here.


(LOOP)
    @i 
    M=0
    @CLR 
    M=0

   // Read the keyboard input:
   @KBD
   D=M

   // Skip to WHT setting if KBD == 0
   @WHT
   D; JEQ 

   // Set dark if necessary
   @CLR
   M=-1
   @CTR
   0; JMP 

   (WHT)
   @CLR
   M=0

   // Set the counter
   (CTR)
   @24576
   D=A
   @i 
   M=M+D // set the number of registers to fill, starting from @SCREEN's last register.

   // Fill Loop:
   (LP1)
   
   //Check if all registers have been accounted for (i has geon from 24576 to 16384):
   @16384 
   D=A
   @i
   D=M-D
   @END 
   D; JEQ 

   // Decrement i: 
   @i
   M=M-1

   // Fill the register:
   @CLR 
   D=M
   @i  
   A=M 
   M=D 

   @LP1
   0; JMP
   
(END)

@LOOP
0; JMP