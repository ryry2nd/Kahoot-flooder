#imports
from selenium import webdriver
from selenium.webdriver.common.by import By
import json, os

options = webdriver.ChromeOptions()

driver = webdriver.Chrome(executable_path="chromedriver.exe", options=options)