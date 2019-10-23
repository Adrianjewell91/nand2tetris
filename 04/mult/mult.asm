// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/04/Mult.asm

// Multiplies R0 and R1 and stores the result in R2.
// (R0, R1, R2 refer to RAM[0], RAM[1], and RAM[2], respectively.)

// Put your code here.

// Add R0 to R2, as many times as specified by R1. (multiplication)
// Define a counter to track it.

// Set R2 to 0;
// Define i, set it equal to R1,

@R2 // Load R2
M=0 // R2=0
@R1 // Load R1
D=M // Hold R1 in D
@i  // Load i
M=D // Assign it to D (which olds R1)

(LOOP)

// END loop when i == 0:
@i
D=M
@END
D; JEQ

// Decrement i: 
@i
M=M-1
D=M

// Add R0 to R2:
@R0
D=M
@R2
M=D+M

@LOOP
0; JMP

(END)

@END
0; JMP