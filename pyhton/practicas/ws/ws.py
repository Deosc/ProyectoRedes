import json
import hug
import requests
import netflowImp as netflow

@hug.get("/cve")
def cve(cve_number):
    get_cve = requests.get('http://cve.mitre.org/cgi-bin/cvename.cgi?name='+cve_number)
    return get_cve




@hug.get("/netflowScan")
def netflowScan():
    netflow.main_implementation()
    x = {
        "code": "200",
        "response":"Exito"
    }
    return x
