import pytest
from main import save_urls, load_links

@pytest.fixture()
def get_base_rubric_url():
    return 'https://www.kommersant.ru/archive/rubric/3'

def test_save_urls(get_base_rubric_url):
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
        
    assert res != None and len(res) > 0 and isdict and len(res) == 4


