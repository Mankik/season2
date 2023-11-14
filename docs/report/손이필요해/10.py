
def add(x, y):
   return (x+y)
def sub(x,y):
    return(x-y)
def mul(x,y):
    return(x*y)
def div(x,y):
    return(x/y)

x = int(input(" "))
y = int(input(" "))

print(x , " + " , y , "=", add(x,y))
print(x , "  - " , y , "=", sub(x,y))
print(x , " x " , y , "=", mul(x,y))
print(x, " / " , y, "=", div(x,y))


