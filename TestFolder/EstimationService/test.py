import pytest
from main import *
import pandas as pd

@pytest.fixture()
def get_base_rubric_url():
    return 'https://www.kommersant.ru/archive/rubric/3'

@pytest.fixture()
def get_user_agent():
    return "Mozilla/5.0 (Macintosh; Intel Mac OS X 12_3_1) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/15.4 Safari/605.1.15"


def test_save_urls(get_base_rubric_url):
   print('test_save_urls')
   url = get_base_rubric_url
   res = save_urls(url)
   isstr = True
   isdate = True
   for k, v in res.items():
        if not (isinstance(k, str) and isinstance(v, str)):
            isstr = False
        if v[3:10] != "05.2025":
            isdate = False
       
   assert res != None and len(res) > 0 and isstr and isdate

def test_load_links():
    res = load_links()
    isdict = True
    for i in res:
        if not isinstance(i, dict):
            isdict = False
        
    assert res != None and len(res) > 0 and isdict and len(res) == 3

def test_to_standard_date():
    res = to_standard_date(load_links())
    isddf = True
    isdate = True
    for index, row in res.iterrows():
        if not isinstance(row['link'], str):
            isddf = False
        if (row['datetime'].date()  != pd.to_datetime('today').normalize().date() and row['datetime'].date()  != (pd.to_datetime('today').normalize() - pd.DateOffset(days=1)).date()):
            isdate = False
        
    assert len(res) > 0 and isddf and isdate


def test_parse_from_link_komersant():
    res = parse_from_link_komersant('http://www.kommersant.ru/doc/7737652', 
        "Mozilla/5.0 (Macintosh; Intel Mac OS X 12_3_1) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/15.4 Safari/605.1.15")    
    assert len(res) > 0 and isinstance(res[0], str) and isinstance(res[1], str) and isinstance(res[2], str) and res[0] != 'No title' and res[2] != 'No text'


def test_add_company(get_user_agent):
    res = parse_from_link_komersant('http://www.kommersant.ru/doc/7737652', get_user_agent)
    res_dict = {'title': res[0], 'announce': res[1], 'text': res[2]}
    df = pd.DataFrame([res_dict])
    res_comp = add_company(df)
      
    assert len(df) == len(res_comp) and (res_comp.loc[0, "company"] == "" or len(res_comp.loc[0, "company"]) >= 4)

def test_results_out():
    inp = {'title out': 'title', 'class': 'class', 'datetime': pd.to_datetime('today').normalize().date()}
    res = results_out(pd.DataFrame([inp]))
    isKey = True
    isStr = True
    print(res[0].items())
    isIndex = False
    for k, v in res[0].items():
        if(isIndex):
            if not (k == "title_out" or k == "class_news" or k == "datetime"):
                isKey = False
            if not isinstance(v, str):
                isStr = False
        else: isIndex = True
    assert isKey and isStr
