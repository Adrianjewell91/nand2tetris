//
//
//
//
//
//
//function SimpleFunction.test 2
(SimpleFunction.test)
@0 
D=A 
@SP 
A=M 
M=D 
@SP 
M=M+1 
@0 
D=A 
@SP 
A=M 
M=D 
@SP 
M=M+1 

//push local 0
@LCL 
D=M 
@0 
A=D+A 
D=M 
@SP 
A=M 
M=D 
@SP 
M=M+1 

//push local 1
@LCL 
D=M 
@1 
A=D+A 
D=M 
@SP 
A=M 
M=D 
@SP 
M=M+1 

//add
@SP 
A=M-1 
D=M  
A=A-1 
D=D+M 
M=D 
D=A+1 
@SP 
M=D 

//not
@SP 
A=M-1 
D=!M 
M=D 
D=A+1 
@SP 
M=D 

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

//add
@SP 
A=M-1 
D=M  
A=A-1 
D=D+M 
M=D 
D=A+1 
@SP 
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
