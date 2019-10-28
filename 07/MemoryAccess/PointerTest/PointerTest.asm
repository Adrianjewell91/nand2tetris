//
//
//
//
//
//
//
//push constant 3030
@3030 
D=A 
@SP 
A=M 
M=D 
@SP 
M=M+1 

//pop pointer 0
@null 
D=M 
@0 
D=D+A
@SP 
A=M
M=D
A=A-1 
D=M
A=A+1
A=M
M=D 
@SP
M=M-1 

//push constant 3040
@3040 
D=A 
@SP 
A=M 
M=D 
@SP 
M=M+1 

//pop pointer 1
@null 
D=M 
@1 
D=D+A
@SP 
A=M
M=D
A=A-1 
D=M
A=A+1
A=M
M=D 
@SP
M=M-1 

//push constant 32
@32 
D=A 
@SP 
A=M 
M=D 
@SP 
M=M+1 

//pop this 2
@THIS 
D=M 
@2 
D=D+A
@SP 
A=M
M=D
A=A-1 
D=M
A=A+1
A=M
M=D 
@SP
M=M-1 

//push constant 46
@46 
D=A 
@SP 
A=M 
M=D 
@SP 
M=M+1 

//pop that 6
@THAT 
D=M 
@6 
D=D+A
@SP 
A=M
M=D
A=A-1 
D=M
A=A+1
A=M
M=D 
@SP
M=M-1 

//push pointer 0
@null 
D=M 
@0 
A=D+A 
D=M 
@SP 
A=M 
M=D 
@SP 
M=M+1 

//push pointer 1
@null 
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

//push this 2
@THIS 
D=M 
@2 
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

//push that 6
@THAT 
D=M 
@6 
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

