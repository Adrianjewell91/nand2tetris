//
//
//
//
//
//
//push constant 10
@10 
D=A 
@SP 
A=M 
M=D 
@SP 
M=M+1 

//pop local 0
@LCL 
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

//push constant 21
@21 
D=A 
@SP 
A=M 
M=D 
@SP 
M=M+1 

//push constant 22
@22 
D=A 
@SP 
A=M 
M=D 
@SP 
M=M+1 

//pop argument 2
@ARG 
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

//pop argument 1
@ARG 
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

//push constant 36
@36 
D=A 
@SP 
A=M 
M=D 
@SP 
M=M+1 

//pop this 6
@THIS 
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

//push constant 42
@42 
D=A 
@SP 
A=M 
M=D 
@SP 
M=M+1 

//push constant 45
@45 
D=A 
@SP 
A=M 
M=D 
@SP 
M=M+1 

//pop that 5
@THAT 
D=M 
@5 
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

//pop that 2
@THAT 
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

//push constant 510
@510 
D=A 
@SP 
A=M 
M=D 
@SP 
M=M+1 

//pop temp 6
@R5 
D=A 
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

//push that 5
@THAT 
D=M 
@5 
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

//push this 6
@THIS 
D=M 
@6 
A=D+A 
D=M 
@SP 
A=M 
M=D 
@SP 
M=M+1 

//push this 6
@THIS 
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

//push temp 6
@R5 
D=A 
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

