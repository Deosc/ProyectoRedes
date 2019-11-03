import subprocess
from time import sleep
import pymongo


def run_command(command):
    p = subprocess.Popen(command,
                         stdout=subprocess.PIPE,
                         stderr=subprocess.PIPE,
                         shell=True)
    # Read stdout from subprocess until the buffer is empty !
    for line in iter(p.stdout.readline, b''):
        if line: # Don't print blank lines
            yield line
    # This ensures the process has completed, AND sets the 'returncode' attr
    while p.poll() is None:                                                                                                                                        
        sleep(.1) #Don't waste CPU-cycles
    # Empty STDERR buffer
    err = p.stderr.read()
    if p.returncode != 0:
       # The run_command() function is responsible for logging STDERR 
       print("Error: " + err.decode('utf-8').strip())

def main_implementation():
    #Se crean objetos para hacer la coneccion a base de datos
    myclient = pymongo.MongoClient("mongodb://localhost:27017/")
    mydb = myclient["proyect"]
    mycol = mydb["netflow"]
    mycolSummary = mydb["netflowSummary"]
    ##Se corre el comando que lee los archivos de nfcap
    for line in run_command("nfdump -R /var/cache/nfdump"):
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
    for delet in run_command("sudo rm /var/cache/nfdump/nfcapd.*"):
        print(delet.decode('utf-8').strip())


if __name__ == "__main__":
    main_implementation()
