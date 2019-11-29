import telnetlib
from time import sleep
import consoleExecute as consoleExecute


def telnet(host):
	ip = "{0}_bckp.txt\n".format(host)
	consoleExecute.run_command("sudo chmod -f 777 /tftpboot/" + "{0}_bckp.txt".format(host))
	print ("sudo chmod -f 777 /tftpboot/" + "{0}_bckp.txt".format(host))
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
	tn.write(b"copy tftp: running-config\n")
	tn.read_until(b"Address or name of remote host ")
	tn.write(b"10.0.27.2\n")
	tn.read_until(b"Source filename")
	print (ip)
	tn.write(ip.encode('utf-8'))
	tn.read_until(b"Destination filename")
	tn.write(b"running-config\n")
	tn.write(b"exit\n")

	sleep(20)


	#Escribimos y cerramos el archivo
	#print (tn.read_all().decode('ascii'))



	return {'message': 'Conexion al router {0} correcto!'.format(host)}



def main_implementation(param):
	telnet(param)


if __name__ == '__main__':
	telnet("10.0.20.1")