import pymongo
import consoleExecute as consoleExecute


def main_implementation(ipSource, ipTarget):
    print(ipSource)
    print (ipTarget)
    comand = "wdiff -3 -wd -xd -yi -zi /tmp/{0}.backup /tmp/{1}.backup"
    return consoleExecute.run_command(comand.format(ipSource,ipTarget))


if __name__ == "__main__":
    main_implementation("10.0.27.1","10.0.27.1")
