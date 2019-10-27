//push constant 3030
@3030 
D=A 
@SP 
A=M 
M=D 
A=A+1 
D=A 
@SP 
M=D 

//pop pointer 0

//push constant 3040
@3040 
D=A 
@SP 
A=M 
M=D 
A=A+1 
D=A 
@SP 
M=D 

//pop pointer 1

//push constant 32
@32 
D=A 
@SP 
A=M 
M=D 
A=A+1 
D=A 
@SP 
M=D 

//pop this 2

//push constant 46
@46 
D=A 
@SP 
A=M 
M=D 
A=A+1 
D=A 
@SP 
M=D 

//pop that 6

//push pointer 0
@SP 
A=M 
M=D 
A=A+1 
D=A 
@SP 
M=D 

//push pointer 1
@SP 
A=M 
M=D 
A=A+1 
D=A 
@SP 
M=D 

//add
@SP 
D=M 
D=D-1 
A=D 
D=M  
A=A-1 
D=D+M 
M=D 
D=A+1 
@SP 
M=D 

//push this 2
@SP 
A=M 
M=D 
A=A+1 
D=A 
@SP 
M=D 

//sub

//push that 6
@SP 
A=M 
M=D 
A=A+1 
D=A 
@SP 
M=D 

//add
@SP 
D=M 
D=D-1 
A=D 
D=M  
A=A-1 
D=D+M 
M=D 
D=A+1 
@SP 
M=D 

