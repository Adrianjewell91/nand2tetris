//push constant 111
@111 
D=A 
@SP 
A=M 
M=D 
A=A+1 
D=A 
@SP 
M=D 

//push constant 333
@333 
D=A 
@SP 
A=M 
M=D 
A=A+1 
D=A 
@SP 
M=D 

//push constant 888
@888 
D=A 
@SP 
A=M 
M=D 
A=A+1 
D=A 
@SP 
M=D 

//pop static 8

//pop static 3

//pop static 1

//push static 3
@SP 
A=M 
M=D 
A=A+1 
D=A 
@SP 
M=D 

//push static 1
@SP 
A=M 
M=D 
A=A+1 
D=A 
@SP 
M=D 

//sub

//push static 8
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

