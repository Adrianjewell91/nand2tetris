@256 
D=A 
@SP 
M=D

@RETURN_ADDRESS0 
D=A 
@SP 
A=M 
M=D 
@SP 
M=M+1 
@LCL 
D=M 
@SP 
A=M 
M=D 
@SP 
M=M+1 
@ARG 
D=M 
@SP 
A=M 
M=D 
@SP 
M=M+1 
@THIS 
D=M 
@SP 
A=M 
M=D 
@SP 
M=M+1 
@THAT 
D=M 
@SP 
A=M 
M=D 
@SP 
M=M+1 
@SP 
D=M 
@0 
D=D-A 
@5 
D=D-A
@ARG 
M=D 
@SP 
D=M 
@LCL 
M=D 
@Sys.init 
0;JMP 
(RETURN_ADDRESS0)

//
//
//
//
//
//
//
//function Sys.init 0
(Sys.init)

//push constant 6
@6 
D=A 
@SP 
A=M 
M=D 
@SP 
M=M+1 

//push constant 8
@8 
D=A 
@SP 
A=M 
M=D 
@SP 
M=M+1 

//call Class1.set 2
@RETURN_ADDRESS1 
D=A 
@SP 
A=M 
M=D 
@SP 
M=M+1 
@LCL 
D=M 
@SP 
A=M 
M=D 
@SP 
M=M+1 
@ARG 
D=M 
@SP 
A=M 
M=D 
@SP 
M=M+1 
@THIS 
D=M 
@SP 
A=M 
M=D 
@SP 
M=M+1 
@THAT 
D=M 
@SP 
A=M 
M=D 
@SP 
M=M+1 
@SP 
D=M 
@2 
D=D-A 
@5 
D=D-A
@ARG 
M=D 
@SP 
D=M 
@LCL 
M=D 
@Class1.set 
0;JMP 
(RETURN_ADDRESS1)

//pop temp 0
@R5 
D=A 
@0 
D=D+A 
 
@R14 
M=D 
@SP 
M=M-1 
A=M 
D=M 
@R14 
A=M 
 M=D 

//push constant 23
@23 
D=A 
@SP 
A=M 
M=D 
@SP 
M=M+1 

//push constant 15
@15 
D=A 
@SP 
A=M 
M=D 
@SP 
M=M+1 

//call Class2.set 2
@RETURN_ADDRESS2 
D=A 
@SP 
A=M 
M=D 
@SP 
M=M+1 
@LCL 
D=M 
@SP 
A=M 
M=D 
@SP 
M=M+1 
@ARG 
D=M 
@SP 
A=M 
M=D 
@SP 
M=M+1 
@THIS 
D=M 
@SP 
A=M 
M=D 
@SP 
M=M+1 
@THAT 
D=M 
@SP 
A=M 
M=D 
@SP 
M=M+1 
@SP 
D=M 
@2 
D=D-A 
@5 
D=D-A
@ARG 
M=D 
@SP 
D=M 
@LCL 
M=D 
@Class2.set 
0;JMP 
(RETURN_ADDRESS2)

//pop temp 0
@R5 
D=A 
@0 
D=D+A 
 
@R14 
M=D 
@SP 
M=M-1 
A=M 
D=M 
@R14 
A=M 
 M=D 

//call Class1.get 0
@RETURN_ADDRESS3 
D=A 
@SP 
A=M 
M=D 
@SP 
M=M+1 
@LCL 
D=M 
@SP 
A=M 
M=D 
@SP 
M=M+1 
@ARG 
D=M 
@SP 
A=M 
M=D 
@SP 
M=M+1 
@THIS 
D=M 
@SP 
A=M 
M=D 
@SP 
M=M+1 
@THAT 
D=M 
@SP 
A=M 
M=D 
@SP 
M=M+1 
@SP 
D=M 
@0 
D=D-A 
@5 
D=D-A
@ARG 
M=D 
@SP 
D=M 
@LCL 
M=D 
@Class1.get 
0;JMP 
(RETURN_ADDRESS3)

//call Class2.get 0
@RETURN_ADDRESS4 
D=A 
@SP 
A=M 
M=D 
@SP 
M=M+1 
@LCL 
D=M 
@SP 
A=M 
M=D 
@SP 
M=M+1 
@ARG 
D=M 
@SP 
A=M 
M=D 
@SP 
M=M+1 
@THIS 
D=M 
@SP 
A=M 
M=D 
@SP 
M=M+1 
@THAT 
D=M 
@SP 
A=M 
M=D 
@SP 
M=M+1 
@SP 
D=M 
@0 
D=D-A 
@5 
D=D-A
@ARG 
M=D 
@SP 
D=M 
@LCL 
M=D 
@Class2.get 
0;JMP 
(RETURN_ADDRESS4)

//label WHILE
(WHILE)

//goto WHILE
@WHILE 
0;JMP 

//
//
//
//
//
//
//function Class1.set 0
(Class1.set)

//push argument 0
@ARG 
D=M 
@0 
A=D+A 
D=M 
@SP 
A=M 
M=D 
@SP 
M=M+1 

//pop static 0
@Class1.0 
D=A 
 
@R14 
M=D 
@SP 
M=M-1 
A=M 
D=M 
@R14 
A=M 
 M=D 

//push argument 1
@ARG 
D=M 
@1 
A=D+A 
D=M 
@SP 
A=M 
M=D 
@SP 
M=M+1 

//pop static 1
@Class1.1 
D=A 
 
@R14 
M=D 
@SP 
M=M-1 
A=M 
D=M 
@R14 
A=M 
 M=D 

//push constant 0
@0 
D=A 
@SP 
A=M 
M=D 
@SP 
M=M+1 

//return
//    FRAME = LCL 
@LCL 
D=M
@R13
M=D // Stored Frame in R13 
//    RET = *(FRAME-5) 
@5 
A=D-A 
D=M
@R14 
M=D // Store *(Frame-5) in R14 
//    *ARG = pop()
@SP
M=M-1
A=M
D=M
@ARG
A=M
M=D
//    SP = ARG+1
@ARG
D=M+1
@SP
M=D
//    THAT = *(FRAME-1)
@R13 
AM=M-1 
D=M 
@THAT 
M=D 
//    THIS = *(FRAME-2)
@R13 
AM=M-1 
D=M 
@THIS 
M=D 
//    ARG = *(FRAME-3)
@R13 
AM=M-1 
D=M 
@ARG 
M=D
//    LCL = *(FRAME-4)
@R13 
AM=M-1 
D=M 
@LCL 
M=D
//    goto RET
@R14 
A=M
0;JMP

//
//
//function Class1.get 0
(Class1.get)

//push static 0
@Class1.0 
D=M 
@SP 
A=M 
M=D 
@SP 
M=M+1 

//push static 1
@Class1.1 
D=M 
@SP 
A=M 
M=D 
@SP 
M=M+1 

//sub
@SP 
A=M-1 
D=M  
A=A-1 
D=M-D 
M=D 
D=A+1 
@SP 
M=D 

//return
//    FRAME = LCL 
@LCL 
D=M
@R13
M=D // Stored Frame in R13 
//    RET = *(FRAME-5) 
@5 
A=D-A 
D=M
@R14 
M=D // Store *(Frame-5) in R14 
//    *ARG = pop()
@SP
M=M-1
A=M
D=M
@ARG
A=M
M=D
//    SP = ARG+1
@ARG
D=M+1
@SP
M=D
//    THAT = *(FRAME-1)
@R13 
AM=M-1 
D=M 
@THAT 
M=D 
//    THIS = *(FRAME-2)
@R13 
AM=M-1 
D=M 
@THIS 
M=D 
//    ARG = *(FRAME-3)
@R13 
AM=M-1 
D=M 
@ARG 
M=D
//    LCL = *(FRAME-4)
@R13 
AM=M-1 
D=M 
@LCL 
M=D
//    goto RET
@R14 
A=M
0;JMP

//
//
//
//
//
//
//function Class2.set 0
(Class2.set)

//push argument 0
@ARG 
D=M 
@0 
A=D+A 
D=M 
@SP 
A=M 
M=D 
@SP 
M=M+1 

//pop static 0
@Class2.0 
D=A 
 
@R14 
M=D 
@SP 
M=M-1 
A=M 
D=M 
@R14 
A=M 
 M=D 

//push argument 1
@ARG 
D=M 
@1 
A=D+A 
D=M 
@SP 
A=M 
M=D 
@SP 
M=M+1 

//pop static 1
@Class2.1 
D=A 
 
@R14 
M=D 
@SP 
M=M-1 
A=M 
D=M 
@R14 
A=M 
 M=D 

//push constant 0
@0 
D=A 
@SP 
A=M 
M=D 
@SP 
M=M+1 

//return
//    FRAME = LCL 
@LCL 
D=M
@R13
M=D // Stored Frame in R13 
//    RET = *(FRAME-5) 
@5 
A=D-A 
D=M
@R14 
M=D // Store *(Frame-5) in R14 
//    *ARG = pop()
@SP
M=M-1
A=M
D=M
@ARG
A=M
M=D
//    SP = ARG+1
@ARG
D=M+1
@SP
M=D
//    THAT = *(FRAME-1)
@R13 
AM=M-1 
D=M 
@THAT 
M=D 
//    THIS = *(FRAME-2)
@R13 
AM=M-1 
D=M 
@THIS 
M=D 
//    ARG = *(FRAME-3)
@R13 
AM=M-1 
D=M 
@ARG 
M=D
//    LCL = *(FRAME-4)
@R13 
AM=M-1 
D=M 
@LCL 
M=D
//    goto RET
@R14 
A=M
0;JMP

//
//
//function Class2.get 0
(Class2.get)

//push static 0
@Class2.0 
D=M 
@SP 
A=M 
M=D 
@SP 
M=M+1 

//push static 1
@Class2.1 
D=M 
@SP 
A=M 
M=D 
@SP 
M=M+1 

//sub
@SP 
A=M-1 
D=M  
A=A-1 
D=M-D 
M=D 
D=A+1 
@SP 
M=D 

//return
//    FRAME = LCL 
@LCL 
D=M
@R13
M=D // Stored Frame in R13 
//    RET = *(FRAME-5) 
@5 
A=D-A 
D=M
@R14 
M=D // Store *(Frame-5) in R14 
//    *ARG = pop()
@SP
M=M-1
A=M
D=M
@ARG
A=M
M=D
//    SP = ARG+1
@ARG
D=M+1
@SP
M=D
//    THAT = *(FRAME-1)
@R13 
AM=M-1 
D=M 
@THAT 
M=D 
//    THIS = *(FRAME-2)
@R13 
AM=M-1 
D=M 
@THIS 
M=D 
//    ARG = *(FRAME-3)
@R13 
AM=M-1 
D=M 
@ARG 
M=D
//    LCL = *(FRAME-4)
@R13 
AM=M-1 
D=M 
@LCL 
M=D
//    goto RET
@R14 
A=M
0;JMP

