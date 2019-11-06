import pymongo
import consoleExecute as consoleExecute
import datetime



def main_implementation():
    currentDT = datetime.datetime.now()
    #Se crean objetos para hacer la coneccion a base de datos
    myclient = pymongo.MongoClient("mongodb://localhost:27017/")
    mydb = myclient["proyect"]
    mycol = mydb["netflow"]
    mycolSummary = mydb["netflowSummary"]
    ##Se corre el comando que lee los archivos de nfcap
    for line in consoleExecute.run_command("nfdump  -R /var/cache/nfdump -o fmt:'|%sap|%dap|%td|%pkt|%byt|%fl'"):
        #Se decodifica la linea leida
        lineDecoded = line.decode('utf-8').strip()
        if(not lineDecoded.startswith("Src")):
            if(lineDecoded.startswith("|")):
                data = lineDecoded.split("|")
                mydict = { "source": data[1].strip(),
                           "destiny": data[2].strip(),
                           "duration": data[3].strip(),
                           "packets": data[4].strip(),
                           "bytes": data[5].strip(),
                           "flows": data[6].strip(),
                           "date":currentDT.strftime("%Y-%m-%d %H:%M:%S")}
                x = mycol.insert_one(mydict)
    #Se boran archivos para evitar duplicar datos
    for delet in consoleExecute.run_command("sudo rm /var/cache/nfdump/nfcapd.*"):
        print(delet.decode('utf-8').strip())


if __name__ == "__main__":
    main_implementation()
