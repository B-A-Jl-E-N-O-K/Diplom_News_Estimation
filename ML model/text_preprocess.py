# import re
import os
# import warnings

# import sys
# module_path = os.path.abspath(os.path.join('..'))
# if module_path not in sys.path:
#     sys.path.append(module_path+"\\src")

# import numpy as np
import pandas as pd
# from pymystem3 import Mystem
# from nltk.corpus import stopwords




def lemm_file(root, file_path, mystem, swords):

    try:
        df = pd.DataFrame(pd.read_pickle(f'{root}/raw_cites/{file_path}'))
        df['tp'] = df['data_or_ex'].map(lambda x: isinstance(x, tuple))
        df.drop(df[df.tp != True].index, inplace=True)
        df[['date', 'title', 'announce', 'text']] = pd.DataFrame(df['data_or_ex'].tolist(), index=df.index)

        df['title origin'] = df['title'].copy()
        df['announce origin'] = df['announce'].copy()
        df['text origin'] = df['text'].copy()

        df.drop(columns=['data_or_ex', 'tp'], inplace=True)
        cols = ['title', 'announce', 'text', 'title origin', 'announce origin', 'text origin']
        for col in cols:
            df[col] = df[col].str.lower()
            # df[col] = df[col].str.replace('\n', '')
            df[col] = df[col].str.strip()
            if col == 'title origin' or col == 'announce origin' or col == 'text origin':
                continue
            df[col] = df[col].str.replace('\W+', ' ', regex=True)
            res = []
            doc = []
            large_str = ' '.join([txt + ' splitter ' for txt in df[col]])
            large_str = mystem.lemmatize(large_str)

            for word in large_str:
                if word.strip() != '' and word not in swords:
                    if word == 'splitter':
                        res.append(doc)
                        doc = []
                    else:
                        doc.append(word)
            del large_str
            del doc
            res = [' '.join(lst) for lst in res]
            df[col] = res
            print(f'{col} ready')
        return df
    except KeyboardInterrupt:
        raise
    except Exception as ex:
        print(ex)
        return None
    

def add_company(roots, for_regex, companies):
    
    for root in roots:

        df = pd.read_csv(f'{root}/targeted.csv')

        for company in companies:
            reg = for_regex[company]
            reg = ' | '.join(reg)
            in_str = df['title'].str.contains(reg) | df['text'].str.contains(reg)
            df[company] = in_str

        df.to_csv(f'{root}/targeted.csv', index=False)

        print(f'{root} done')



def add_industry(roots, industries, for_regex_industry):
    for root in roots:
        df = pd.read_csv(f'{root}/targeted.csv')
        for industry in industries:
            reg = for_regex_industry[industry]
            reg = ' | '.join(reg)
            in_str = df['title'].str.contains(reg) | df['text'].str.contains(reg)
            df[industry] = in_str
        df.to_csv(f'{root}/targeted.csv', index=False)
        print(f'{root} done')


def add_target(root, companies):
    cols = ['1 день']
    times = [pd.Timedelta(1, unit='day')]
    deltas = {i:j for i, j in zip(cols, times)}
    comp = [com for com in companies]
    files = os.listdir(f'{root}/prepared_cites')
    df = pd.DataFrame()
    for file in files:
        tmp = pd.read_csv(f'{root}/prepared_cites/{file}')
        tmp = tmp[tmp['date'] != 'No time']
        tmp['date'] = pd.to_datetime(tmp['date']).dt.date
        tmp['date'] = pd.to_datetime(tmp['date'])
        df = pd.concat([df, tmp], axis=0)
    df.drop_duplicates(inplace=True)
    df.sort_values('date', ignore_index=True, inplace=True)
    print('file gathered')

    for com in comp:
        price = pd.read_csv(f'quotes_moex/{com}.csv')
        price['date'] = pd.to_datetime(price['date']).dt.date
        price['date'] = pd.to_datetime(price['date'])
        price.sort_values('date', ignore_index=True, inplace=True)
        for col in cols:
            df['date'] += deltas[col]
            df = pd.merge_asof(df, price, on='date', direction='forward')
            df.rename(columns={'close': f'{col} {com} close'}, inplace=True)
            df['date'] -= deltas[col]
        print(f'{com} готов')
    df.to_csv(f'{root}/targeted.csv', index=False)


def komersant_to_standard_date(folders):
    for folder in folders:
        c = 0
        files = os.listdir(f'{folder}/prepared_cites_v5')
        if '.DS_Store' in files:
            files.remove('.DS_Store')
        
        for file in files:
            df = pd.read_csv(f'{folder}/prepared_cites_v5/{file}')
            df = df[df['date'] != 'No time']
            df['date'] = pd.to_datetime(df['date'].map(lambda x: x[:-6]))
            df.to_csv(f'{folder}/prepared_cites_v5/{file}', index=False)
            c += 1
            if c%100 == 0:
                print(f'Обработано {c} из {len(files)}')


def add_target_v5(root, companies):
    
    comp = [com for com in companies]
    files = os.listdir(f'{root}/prepared_cites_v5')
    df = pd.DataFrame()
    for file in files:
        tmp = pd.read_csv(f'{root}/prepared_cites_v5/{file}')
        tmp = tmp[tmp['date'] != 'No time']
        tmp['date'] = pd.to_datetime(tmp['date'])
        df = pd.concat([df, tmp], axis=0)
    df.drop_duplicates(inplace=True)
    df.sort_values('date', ignore_index=True, inplace=True)
    print('file gathered')

    for com in comp:
        price = pd.read_csv(f'quotes_moex/{com}.csv')
        price['date'] = pd.to_datetime(price['date'])
        price['date'] = price['date'] + pd.DateOffset(hours=19)
        price.sort_values('date', ignore_index=True, inplace=True)

        df = pd.merge_asof(df, price, on='date', direction='backward')
        df.rename(columns={'close': f'{com} close prev'}, inplace=True)

        df = pd.merge_asof(df, price, on='date', direction='forward')
        df.rename(columns={'close': f'{com} close'}, inplace=True)
        print(f'{com} готов')

    df.to_csv(f'{root}/targeted_v5.csv', index=False)


def add_company_v5(for_regex, companies):
    
    
    df = pd.read_csv(f'news_links/final_v5.csv')

    for company in companies:
        reg = for_regex[company]
        reg = ' | '.join(reg)
        in_str = df['title'].str.contains(reg) | df['text'].str.contains(reg) | df['title origin'].str.contains(reg)
        df[company] = in_str

    df.to_csv(f'news_links/final_v5.csv', index=False)

    print(f'done')


def add_industry_v5(industries, for_regex_industry):
    
    df = pd.read_csv(f'news_links/final_v5.csv')
    for industry in industries:
        reg = for_regex_industry[industry]
        reg = ' | '.join(reg)
        in_str = df['title'].str.contains(reg) | df['text'].str.contains(reg) | df['title origin'].str.contains(reg)
        df[industry] = in_str
    df.to_csv(f'news_links/final_v5.csv', index=False)
    print(f'done')