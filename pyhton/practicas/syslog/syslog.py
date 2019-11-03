
import os, time, itertools, pymongo

while True:

    #Lista de syslogs
    lista_ip = os.listdir("/var/log/remotelogs")
    #Lista donde se almacenará la IP y sus respectivos syslogs
    lista_ip_syslogs = []

    """

    Se itera la lista de ip, que son las carpetas en el servidor
    omitiendo la carpeta que contiene por default.

    Se obtienen los logs por cada carpeta, y se iteran.

    A cada log iterado se le obtiene el texto dentro de el 
    para posteriormente encontrar el nivel de log

    TODO Almacenar el log, nivel en la BD
    """

    myclient = pymongo.MongoClient("mongodb://localhost:27017/")
    mydb = myclient["proyect"]
    mycol = mydb["syslogs"]


    mycol.delete_many({})

    print("LISTA IPS   --->  " , len(lista_ip))


    for ip in lista_ip:
        if ip != "127.0.0.1" and  ip != "daniel" and ip != "_gateway" :
            lista_syslogs = os.listdir("/var/log/remotelogs/" + ip)
            print(ip, len(lista_syslogs))

            for syslog in lista_syslogs:
                archivo_syslog = open(r"/var/log/remotelogs/" + ip + "/" + syslog, "r")
                syslog_read = archivo_syslog.read()
                #print(syslog_read)
                marca = syslog_read.find("%")
                isLevel = False 

                """while not isLevel:
                    
                    if syslog_read[marca].isdigit():
                        isLevel = True
                        nivel = syslog_read[marca]
                    else:
                        marca = marca + 1 
                print(nivel)
                """
                lista_ip_syslogs.append([ip, syslog_read, 1])
                document = { "ip": ip, "syslog": syslog_read, "nivel":1 }

                mycol.insert_one(document)

                
                #print("----------------" + ip +"------------------")
                #print(syslog_read)
                #print(nivel)

    #print(lista_ip_syslogs)
    print("LISTA SYSLOGS  IP   --->  " , len(lista_ip_syslogs))

    time.sleep(7)



    # Run I/O dispatcher which would receive queries and send confirmations

        
        

            



        
        


