import pymongo
import consoleExecute as consoleExecute


def main_implementation(param):
    print(param)
    comand = "echo 'get startup-config /tmp/{0}.backup' | tftp {0}"
    for line in consoleExecute.run_command(comand.format(param)):
        lineDecoded = line.decode('utf-8').strip()
        print(lineDecoded)
    return "/tmp/{0}.backup".format(param)


if __name__ == "__main__":
    main_implementation("10.0.27.1")
