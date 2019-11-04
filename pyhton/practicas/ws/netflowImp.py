import pymongo
import consoleExecute as consoleExecute


def main_implementation():
    #Se crean objetos para hacer la coneccion a base de datos
    myclient = pymongo.MongoClient("mongodb://localhost:27017/")
    mydb = myclient["proyect"]
    mycol = mydb["netflow"]
    mycolSummary = mydb["netflowSummary"]
    ##Se corre el comando que lee los archivos de nfcap
    for line in consoleExecute.run_command("nfdump -R /var/cache/nfdump"):
        #Se decodifica la linea leida
        lineDecoded = line.decode('utf-8').strip()
        if(not lineDecoded.startswith("Date first seen")):
            if(not lineDecoded.startswith("Summary")):
                print(lineDecoded)
                mydict = { "flow": lineDecoded }
                x = mycol.insert_one(mydict)
            else:
                print(lineDecoded)
                mydict = { "summary": lineDecoded }
                x = mycolSummary.insert_one(mydict)
    #Se boran archivos para evitar duplicar datos
    for delet in consoleExecute.run_command("sudo rm /var/cache/nfdump/nfcapd.*"):
        print(delet.decode('utf-8').strip())


if __name__ == "__main__":
    main_implementation()
