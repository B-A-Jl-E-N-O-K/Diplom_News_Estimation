# import pandas as pd
# import numpy as np
from bs4 import BeautifulSoup
import random
# from selenium import webdriver
# from webdriver_manager.chrome import ChromeDriverManager
# from selenium.webdriver.chrome.options import Options
# from selenium.webdriver.support.ui import WebDriverWait
# from selenium.webdriver.support.select import Select
# from selenium.webdriver.common.action_chains import ActionChains
# from selenium.webdriver.common.by import By
# import shutil
import requests
# import os
import time
# import random
# from random_user_agent.user_agent import UserAgent
# from random_user_agent.params import SoftwareName, OperatingSystem
from threading import Thread, Lock
import threading
import pickle
# import datetime
from pathlib import Path

############# Комерсант  ############
# Получаем ссылки на отдельные статьи
def save_komersant_urls(link):
    urls = []
    txt = requests.get(link).text
    soup = BeautifulSoup(txt, 'lxml')
    lst = soup.find_all('h2', class_='uho__name rubric_lenta__item_name')

    for elem in lst:
        urls.append('https://www.kommersant.ru/' + elem.find('a', class_='uho__link uho__link--overlay').get('href'))
        
    return urls

def load_links(rubric):
    rubrics = {'2': 'politics', '3': 'economics', '4': 'business', '40':'finance', '41': 'consumer_market'}
    urls = []

    for year in range(2025, 2016, -1):
        for month in range(12, 0, -1):
            if year == 2025 and month > 3:
                continue
            if month in [1, 3, 5, 7, 8, 10, 12]:
                if len(str(month)) == 1:
                    month = '0' + str(month)
                for day in range(31, 0, -1):
                    if len(str(day)) == 1:
                        day = '0' + str(day)
                    link = f'https://www.kommersant.ru/archive/rubric/{rubric}/day/{year}-{month}-{day}'
                    urls.extend(save_komersant_urls(link))
                    time.sleep(1 + random.random())
            elif month in [4, 6, 9, 11]:
                if len(str(month)) == 1:
                    month = '0' + str(month)
                for day in range(30, 0, -1):
                    if len(str(day)) == 1:
                        day = '0' + str(day)
                    link = f'https://www.kommersant.ru/archive/rubric/{rubric}/day/{year}-{month}-{day}'
                    urls.extend(save_komersant_urls(link))
                    time.sleep(1 + random.random())
            else:
                if len(str(month)) == 1:
                    month = '0' + str(month)
                if year % 4 == 0:
                    for day in range(29, 0, -1):
                        if len(str(day)) == 1:
                            day = '0' + str(day)
                        link = f'https://www.kommersant.ru/archive/rubric/{rubric}/day/{year}-{month}-{day}'
                        urls.extend(save_komersant_urls(link))
                        time.sleep(1 + random.random())
                else:
                    for day in range(28, 0, -1):
                        if len(str(day)) == 1:
                            day = '0' + str(day)
                        link = f'https://www.kommersant.ru/archive/rubric/{rubric}/day/{year}-{month}-{day}'
                        urls.extend(save_komersant_urls(link))
                        time.sleep(1 + random.random())

            print(f'{month} {year} ссылки получены')
            filename = Path(f'news_links/komersant_{rubrics[rubric]}/urls{year}-{month}.txt')
            filename.touch(exist_ok=True)
            with open(filename, 'w') as file:
                for url in urls:
                    file.write(f'{url}\n')


def parse_from_link_komersant(link, user_agent):

    
    try:
        with requests.session() as s:
            txt = s.get(link, headers={'User-Agent':user_agent}).text
            soup = BeautifulSoup(txt, 'lxml')
            try:
                timestamp = soup.find('time', class_='doc_header__publish_time').get('datetime')
            except:
                timestamp = 'No time'
            
            try:
                title = soup.find('h1', class_='doc_header__name js-search-mark').text
            except:
                title = 'No title'
            try:
                announce = soup.find('h2', class_='doc_header__subheader').text
            except:
                announce = 'No announce'
            try:
                try:
                    text = soup.find('p', class_='doc__text doc__intro').text
                    txt = soup.find_all('p', class_='doc__text')
                    text = text + ' ' + ' '.join([el.text for el in txt])
                except:
                    txt = soup.find_all('p', class_='doc__text')
                    text = ' '.join([el.text for el in txt])
                    
                try:
                    txt = soup.find_all('p', class_='doc__thought')
                    text = text + ' ' + ' '.join([el.text for el in txt])
                except:
                    pass
            except:
                text = 'No text'
            return timestamp, title, announce, text

    except Exception as ex:
        print(link)
        print(ex)
        return ex

    except KeyboardInterrupt:
        return
    

data = {'link': [], 'data_or_ex': []}
current_val = 0

######## Общие функция для парсинга ##########
def start_parsing(root, target, parse_from_link, links, total_news, n_threads, save_evry):
    
    lock = Lock()
    threads = []
    run_event = threading.Event()
    run_event.set()

    for i in range(n_threads):
        t = Thread(target=target, args=(root, lock, parse_from_link, links, total_news, save_evry))
        t.start()
        time.sleep(2)
        threads.append(t)

    for thread in threads:
        thread.join()

def parse(root, lock, parse_from_link, links, total_news, save_evry):

    global data, current_val

    user_agent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 12_3_1) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/15.4 Safari/605.1.15"

    while current_val < total_news:
        with lock:
            thread_value = current_val
            link = links[current_val]
            current_val += 1
        
        parsed = parse_from_link(link, user_agent)
        if parsed == None:
            return

        with lock:
            data['link'].append(link)
            data['data_or_ex'].append(parsed)
            if thread_value % save_evry == 0:
                with open(f'{root}/{thread_value}.pkl', 'wb') as f:
                    pickle.dump(data, f)
                    print(f'{thread_value} saved')
                data.clear()
                data['link'] = []
                data['data_or_ex'] = []

        time.sleep(1 + random.random())

    with open(f'{root}/{thread_value}.pkl', 'wb') as f:
        pickle.dump(data, f)

    return 'Конец'