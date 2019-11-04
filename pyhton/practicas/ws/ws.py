import json
import hug
import requests
import netflowImp as netflow
import getConfigImp as configurationBackup
import diffImp as diff




@hug.get("/netflowScan")
def netflowScan():
    netflow.main_implementation()
    x = {
        "code": "200",
        "response":"Exito"
    }
    return x

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
        "path": stringReturn
    }
    return x
