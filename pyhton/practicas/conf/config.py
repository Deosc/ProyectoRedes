import os


comando1 = "tunctl -t tap0 -u daniel"
comando2 = "ifconfig tap0 up"
comando3 = "ifconfig tap0 10.0.27.2 netmask 255.255.255.0"
comando4 = "route add -net 10.0.{}.0 netmask 255.255.255.0 gw 10.0.27.1 dev tap0"



print (comando1)
os.system(comando1)
print (comando2)
os.system(comando2)
print (comando3)
os.system(comando3)

for i in range(1,28):
    print(comando4.format(i))
    os.system(comando4.format(i))