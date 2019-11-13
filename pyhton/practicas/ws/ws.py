import json
import hug
import requests
import netflowImp as netflow
import netflowImp2 as netflowStatics
import getConfigImp as configurationBackup
import diffImp as diff




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
        "path": stringReturn
    }
    return x