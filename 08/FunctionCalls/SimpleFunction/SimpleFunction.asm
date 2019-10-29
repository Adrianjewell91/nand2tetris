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
//*ARG = pop() // pop the value into the register to which arg points. 
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
//    FRAME = LCL
//    THAT = *(FRAME-1)
@LCL 
M=M-1
A=M
D=M 
@THAT 
M=D
//    THIS = *(FRAME-2)
@LCL 
M=M-1
A=M
D=M 
@THIS 
M=D
//    ARG = *(FRAME-3)
@LCL 
M=M-1
A=M
D=M 
@ARG 
M=D
//    LCL = *(FRAME-4)
@LCL 
D=M-1 
@SP 
A=M
M=D 
A=D 
D=M
@LCL
M=D
//    RET = *(FRAME-5)
@SP
A=M
A=M-1 
A=M
0;JMP
//    goto RET

//
