//push constant 7
@7 
D=A 
@SP 
A=M 
M=D 
A=A+1 
D=A 
@SP 
M=D 

//push constant 8
@8 
D=A 
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

