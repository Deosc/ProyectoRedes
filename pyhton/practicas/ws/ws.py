import json
import hug
import pymongo
import requests
import netflowImp as netflow
import netflowImp2 as netflowStatics
import getConfigImp as configurationBackup
import diffImp as diff
import host_discoverer as hd
import uploadConfig as uploadConfig
import changes as changesImp
import telnetlib
from os import remove




@hug.get("/changes")
def changes(ipChanges):
    a = changesImp.main_implementation(ipChanges)
    return a

@hug.get("/restoreConfig")
def restoreConfig(ipRestore):
    uploadConfig.main_implementation(ipRestore)
    return "ok"

@hug.get("/ping")
def pingPooler(ipRoot):
    reachable, unreachable = hd.main_implementation(ipRoot)
    x = {
        "reachable":reachable,
        "unreachable":unreachable
    }
    return   x


@hug.get("/netflowScan")
def netflowScan():
    netflow.main_implementation()
    return   netflow.main_implementation()

@hug.get("/netflowStatics")
def netflowScan(command):
    return   netflowStatics.main_implementation(command)

@hug.get("/backup")
def netflowScan(ipBackup):
    x = {
        "code": "200",
        "path":configurationBackup.main_implementation(ipBackup)
    }
    return x


@hug.get("/diff")
def netflowScan(ipSource, ipTarget):
    stringReturn = ""
    for line in diff.main_implementation(ipSource, ipTarget):
        stringReturn = stringReturn +  line.decode('utf-8').strip()

    x = {
        "code": "200",
        "diff": stringReturn
    }
    return x

@hug.get(examples='archivo_conf=10.0.27.1.txt')
@hug.local()
def template(archivo_conf):


	myclient = pymongo.MongoClient("mongodb://localhost:27017/")
	mydb = myclient["proyect"]
	mycol = mydb["routers"]
	templates = mydb["templates"]



	ENCONTRADO = 0

	interfaces = []
	ip_interface = []
	ip_routes = []

	#Se crea la plantilla del router
	f = open(""+archivo_conf, "r")
	for linea in f:

		if linea.find("hostname") == ENCONTRADO:
			router = linea.strip()
			print(linea.strip())

		if linea.find("interface") == ENCONTRADO:
			interfaces.append([{"interface":linea.strip()},{"ip address":""}])
			aux = linea.strip()
		if linea.lstrip().find("ip address") == ENCONTRADO:
			interface = interfaces.pop()
			interface=[{"interface":aux},{"ip address":linea.strip()}]
			interfaces.append(interface)
		if linea.lstrip().find("shutdown") == ENCONTRADO:
			interface = interfaces.pop()
			interface=[{"interface":aux},{"ip address":linea.strip()}, {"shutdown":linea.strip()}]
			interfaces.append(interface)

		if linea.lstrip().find("ip route") == ENCONTRADO:
			ip_route = [{"ip route":linea.strip()}]
			ip_routes.append(ip_route)


		#or linea.lstrip().find("ip route") == ENCONTRADO 	if
	print(interfaces)
	document = { "router": router, "interfaces": interfaces, "ip routes": ip_routes}
	#document = { "prueba": prueba}
	mycol.insert_one(document)

	f.close()

@hug.get("/insert")
@hug.local()
def insertTemplate():

	myclient = pymongo.MongoClient("mongodb://localhost:27017/")
	mydb = myclient["proyect"]
	mycol = mydb["routers"]
	templates = mydb["templates"]

	template = mycol.find_one()

	templates.insert_one(template)


	mycol.delete_many({})

@hug.get("/comparate")
@hug.local()
def comparate(routerIn: hug.types.text):

	myclient = pymongo.MongoClient("mongodb://localhost:27017/")
	mydb = myclient["proyect"]
	mycol = mydb["routers"]
	templates = mydb["templates"]

	router = mycol.find_one({},{"_id":0})
	template_found = templates.find_one({ "router": routerIn},{"_id":0})
	mycol.delete_many({})
	return {router == template_found}

@hug.get("/telnet")
@hug.local()
def telnet(host: hug.types.text):
	ENCONTRADO = 0
	user = "usuario"
	password = "password"

	#Abrimos archivo donde colocaremos la informacion
	archivo = open(host+"Aux.txt", "w+")

	#Conexion Telnet
	tn = telnetlib.Telnet(host)

	#Conexion al router
	tn.read_until(b"Username: ")
	tn.write(user.encode('ascii') + b"\n")
	if password:
	    tn.read_until(b"Password: ")
	    tn.write(password.encode('ascii') + b"\n")

	#Colocamos los comandos
	tn.write(b"terminal length 0\n")
	tn.write(b"show version\n")
	tn.write(b"end\n")
	tn.write(b"exit\n")

	#Escribimos y cerramos el archivo
	archivo.write (tn.read_all().decode('ascii'))
	archivo.close()


	f = open(host+"Aux.txt", "r")
	newFile = open(host, "w")
	for linea in f:

		if linea.find("Cisco IOS") == ENCONTRADO:
			newFile.write(linea.strip())
			print(linea.strip())
		if linea.find("PCI") == ENCONTRADO:
			newFile.write(linea.strip())
			print(linea.strip())
		if linea.strip().find("FastEthernet") == ENCONTRADO:
			newFile.write(linea.strip())
			print(linea.strip())

	remove(host+"Aux.txt")
	return {'message': 'Conexion al router {0} correcto!'.format(host)}
