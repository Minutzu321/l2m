import time
import socket
import logging
import json
import os.path
from collections import namedtuple
from time import gmtime, strftime
import datetime
import numpy as np
import matplotlib.pyplot as plt
import base64

membri = []

class Membru:
    def __init__(self, nume, email, tel, masc, rol, rolid):
        if not os.path.exists("riverwolves/membri/"+nume.lower().replace(" ","")+'.json'):
            self.nume = nume
            self.email = email
            self.tel = tel
            self.masc = masc
            self.rol = rol
            self.rolid = rolid
            self.prezente = []
            self.rapoarte = []
            self.descriere = ""
            self.save()
        else:
            self.fromJSON(nume)


    def save(self):
        data = self.toJSON()
        with open("riverwolves/membri/"+self.nume.lower().replace(" ","")+'.json', 'w') as outfile:
            json.dump(data, outfile)

    def toJSON(self):
        return json.dumps(self, default=lambda o: o.__dict__, 
            sort_keys=True, indent=4)

    def fromJSON(self, nume):
        with open("riverwolves/membri/"+nume.lower().replace(" ","")+'.json', 'r') as file:
            data = file.read()
            x = json.loads(json.loads(data))
            self.nume = x['nume']
            self.email = x['email']
            self.tel = x['tel']
            self.masc = x['masc']
            self.rol = x['rol']
            self.rolid = x['rolid']
            self.prezente = x['prezente']
            self.rapoarte = x['rapoarte']
            self.descriere = x['descriere']

def loadMembri():
    membri.append(Membru("Exemplu Exemplu","test@gmail.com","-",False,"Media",2))
        

def getMembruByEmail(email):
    for membru in membri:
        if membru.email == email:
            return membru

def getGrafic(email):
    membru = getMembruByEmail(email)
    lu = 0
    ma = 0
    mi = 0
    jo = 0
    vi = 0
    sa = 0

    for prezenta in membru.prezente:
        if prezenta[1] == 0:
            lu += 1
        if prezenta[1] == 1:
            ma += 1
        if prezenta[1] == 2:
            mi += 1
        if prezenta[1] == 3:
            jo += 1
        if prezenta[1] == 4:
            vi += 1
        if prezenta[1] == 5:
            sa += 1

    N = 6
    menMeans = (lu, ma, mi, jo, vi, sa)
    ind = np.arange(N)
    width = 0.35

    plt.bar(ind, menMeans, width)

    plt.ylabel('Prezente')
    plt.title('Prezenta in acest sezon: '+membru.nume)
    plt.xticks(ind, ('Luni', 'Marti', 'Miercuri', 'Joi', 'Vineri', 'Sambata'))

    plt.savefig('riverwolves/data.png')

    with open("riverwolves/data.png", "rb") as image:
        b64string = base64.b64encode(image.read())
        return b64string

def proceseaza(data):
    if "parola-secreta" in data:
        jdata = json.loads(data.replace("parola-secreta",""))
        cmd = jdata['cmd'].lower()
        args = jdata['args']

        rasp = {}

        if cmd == 'login':
            for membru in membri:
                if membru.email == args[0]:
                    rasp.status = True
                    rasp.tel = membru.tel
                    rasp.rol = membru.rol
                    rasp.poza = getGrafic(args[0])
                    return json.dumps(rasp)
            rasp.status = False
            return json.dumps(rasp)

        elif cmd == 'prezent':
            email = args[0]
            membru = getMembruByEmail(email)
            l = len(membru.prezente)
            if l > 0:
                if membru.prezente[l-1][0] != strftime("%Y-%m-%d", gmtime()):
                    membru.prezente.append([strftime("%Y-%m-%d", gmtime()),datetime.datetime.today().weekday()])
                    membru.save()
                    rasp.status = True
                else:
                    rasp.status = False
            else:
                membru.prezente.append([strftime("%Y-%m-%d", gmtime()),datetime.datetime.today().weekday()])
                membru.save()
                rasp.status = True
            return json.dumps(rasp)

        elif cmd == 'raport':
            email = args[0]
            text = args[1]
            membru = getMembruByEmail(email)
            l = len(membru.rapoarte)
            if l > 0:
                if membru.rapoarte[l-1][0] != strftime("%Y-%m-%d", gmtime()):
                    membru.rapoarte.append([strftime("%Y-%m-%d", gmtime()),text])
                    membru.save()
                    rasp.status = True
                else:
                    rasp.status = False
            else:
                membru.rapoarte.append([strftime("%Y-%m-%d", gmtime()),text])
                membru.save()
                rasp.status = True
            return json.dumps(rasp)
        elif cmd == "descriere":
            email = args[0]
            text = args[1]
            membru = getMembruByEmail(email)
            membru.descriere = text
            membru.save()
            rasp.status = True
            return json.dumps(rasp)
        elif cmd == "info":
            rasp.status = True
            return json.dumps(rasp)
  

def startRVWServer():
    run = True
    HOST = 'localhost'
    PORT = 57007
    s = None
    for res in socket.getaddrinfo(HOST, PORT, socket.AF_UNSPEC, socket.SOCK_STREAM, 0, socket.AI_PASSIVE):
        af, socktype, proto, canonname, sa = res
        try:
            s = socket.socket(af, socktype, proto)
        except socket.error as er:
            logging.info('Eroare la conectarea serverului: '+str(er))
            s = None
            continue
        try:
            s.bind(sa)
            s.listen(1)
        except socket.error as er:
            logging.info('Eroare la scoket binding/listening: '+str(er))    
            s.close()
            s = None
            continue
        break
    if s is None:
        logging.info("Nu s-a putut deschide serverul")
    else:
        print("Serverul s-a deschis")
        logging.info("Serverul s-a deschis")
        while run:
            conn, addr = s.accept()
            data = conn.recv(8192)
            raspuns = proceseaza(data)
            conn.send(raspuns.encode())
            conn.close()
        s.close()

#se asigura existenta directoarelor
if not os.path.exists("riverwolves/loguri"):
    os.makedirs("riverwolves/loguri")
if not os.path.exists("riverwolves/membri"):
    os.makedirs("riverwolves/membri")
#se seteaza logul
LOG_FILENAME = "riverwolves/loguri/log-"+strftime("%Y-%m-%d", gmtime())+".out"
logging.basicConfig(filename=LOG_FILENAME,level=logging.DEBUG)
logging.info("--Server RiverWolves--")
logging.info(" -- ORA "+strftime("%H-%M-%S", gmtime())+" -- ")

loadMembri()
# print(getGrafic("chirusmina@gmail.com"))
startRVWServer()