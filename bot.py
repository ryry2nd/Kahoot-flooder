from selenium import webdriver
from selenium.webdriver.common.by import By

def flood(numBots, id, name=""):
    options = webdriver.ChromeOptions()
    #options.add_argument('headless')

    for botNum in range(numBots):
        driver = webdriver.Chrome(executable_path="chromedriver.exe", options=options)

        driver.get("https://kahoot.it/")

        yield driver