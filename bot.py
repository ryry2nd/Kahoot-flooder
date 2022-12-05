from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.common.keys import Keys
import time

def flood(numBots, id, name=""):
    options = webdriver.ChromeOptions()
    options.add_argument('headless')

    for botNum in range(numBots):
        driver = webdriver.Chrome(executable_path="chromedriver.exe", options=options)

        driver.get("https://kahoot.it?pin=" + id + "&refer_method=link")

        time.sleep(0.5)

        nameBox = driver.find_element(By.XPATH, "//input[@name='nickname']")
        
        nameBox.send_keys(name + str(botNum))
        nameBox.send_keys(Keys.ENTER)

        yield driver