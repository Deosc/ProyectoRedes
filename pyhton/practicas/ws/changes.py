import telnetlib

def telnet(host):
	user = "usuario"
	password = "password"



	#Conexion Telnet
	tn = telnetlib.Telnet(host)

	#Conexion al router
	tn.read_until(b"Username: ")
	tn.write(user.encode('ascii') + b"\n")
	if password:
	    tn.read_until(b"Password: ")
	    tn.write(password.encode('ascii') + b"\n")

	#Colocamos los comandos
	tn.write(b"enable\n")
	tn.write(b"123a\n")
	tn.write(b"terminal length 0\n")
	tn.write(b"show archive log config all provisioning\n")
	tn.write(b"exit\n")

	objeto = tn.read_all().decode('ascii').splitlines()
	objeto.remove('')
	list = []
	for x in objeto:
		if( (not x.startswith('R')) and (not x.startswith('Password:'))):
			list.append(x)
	list.remove('')

	while("" in list) :
		list.remove("")
	return {'message':list }


def main_implementation(param):
	return telnet(param)

if __name__ == '__main__':
	print (telnet("10.0.27.1"))