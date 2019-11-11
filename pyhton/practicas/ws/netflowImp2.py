import pymongo
import consoleExecute as consoleExecute
import datetime



def main_implementation(param):
    currentDT = datetime.datetime.now()
    #Se crean objetos para hacer la coneccion a base de datos
    myclient = pymongo.MongoClient("mongodb://localhost:27017/")
    mydb = myclient["proyect"]
    mycol = mydb["netflow"]
    mycolSummary = mydb["netflowSummary"]
    list = []
    ##Se corre el comando que lee los archivos de nfcap
    comando = "nfdump  -R /var/cache/nfdump  {0}"
    print (comando.format(param))
    for line in consoleExecute.run_command(comando.format(param)):
        #Se decodifica la linea leida
        lineDecoded = line.decode('utf-8').strip()
        if(not lineDecoded.startswith("Top")):
            if(lineDecoded.startswith("2")):
                dataAny = lineDecoded.split("any")
                dataTmp = dataAny[1].split("  ")
                data = []
                for e in filter(None, dataTmp):
                    data.append(e)

                dataTime = []
                for e in filter(None, dataAny[0].split()):
                    dataTime.append(e)


                mydict = { "dateFirstSeen": dataTime[0].strip(),
                           "timeFirstSeen": dataTime[1].strip(),
                           "duracion": dataTime[2].strip(),
                           "protocolo": "any",
                           "src": data[0].strip(),
                           "flows": data[1].strip(),
                           "paquetes": data[2].strip(),
                           "bytes": data[3].strip(),
                           "pps": data[4].strip(),
                           "bps": data[5].strip(),
                           "bpp": data[6].strip()}
                list.append(mydict)
    return list
    #Se boran archivos para evitar duplicar datos
    #for delet in consoleExecute.run_command("sudo rm /var/cache/nfdump/nfcapd.*"):
        #print(delet.decode('utf-8').strip())


if __name__ == "__main__":
    print (main_implementation("-s ip/flows"))
