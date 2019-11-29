import json
import hug
import requests
import netflowImp as netflow
import netflowImp2 as netflowStatics
import getConfigImp as configurationBackup
import diffImp as diff
import host_discoverer as hd
import uploadConfig as uploadConfig
import changes as changesImp



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
