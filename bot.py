from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.common.keys import Keys
from queue import Queue
import time, threading

class Flood:
    def __init__(self, numBots, id, name="", numThreads=1):
        self.numBots = numBots
        self.id = id
        self.name = name
        self.numThreads = numThreads
        self.drivers = []
        self.options = webdriver.ChromeOptions()
        self.options.add_argument('headless')

        self.q = Queue()

        self.sendRequests()
        self.startThreads()

        while not self.q.empty():
            pass

    def worker(self):
        while not self.q.empty():
            item = self.q.get()
            try:
                self.drivers.append(self.logInBot(item))
            except Exception as e:
                print(e)
            self.q.task_done()

    def startThreads(self):
        if self.numThreads <= 1:
            self.worker()
        else:
            for numT in range(self.numThreads):
                threading.Thread(target=self.worker).start()

    def sendRequests(self):
        for item in range(self.numBots):
            self.q.put(item)

    def logInBot(self, botNum):
        driver = webdriver.Chrome(executable_path="chromedriver.exe", options=self.options)
        driver.set_page_load_timeout(30)

        driver.get("https://kahoot.it?pin=" + self.id + "&refer_method=link")

        time.sleep(0.5)

        nameBox = driver.find_element(By.XPATH, "//input[@name='nickname']")
        
        nameBox.send_keys(self.name + str(botNum))
        nameBox.send_keys(Keys.ENTER)

        time.sleep(0.5)

        if driver.find_elements(By.XPATH, "//input[@name='nickname']"):
            driver.close()

        return driver