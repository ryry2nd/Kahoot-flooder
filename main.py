#imports
from bot import Flood
import json, os

filePath = "config.json"
if not os.path.exists(filePath):
    open(filePath, 'x').close()
    file = open(filePath, 'w')
    file.write(json.dumps({"num": 1, "id": "", "name": "", "threads": 1}))
    file.close()
    print("file not found")
    exit()

file = open("config.json")
config = json.load(file)
file.close()

NUM = config["num"]
ID = config["id"]
NAME = config["name"]
THREADS = config["threads"]

def main():
    Flood(NUM, ID, NAME, THREADS)

if __name__ == '__main__':
    main()