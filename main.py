from sec_edgar_downloader import Downloader as SEC
from nltk.stem.snowball import SnowballStemmer
from spacy.matcher import Matcher
from datetime import datetime as dt
import PyPDF2 as PDF
import lxml.html
import spacy
import nltk
import os
import re



cwd = os.getcwd()
nlp = spacy.load('en_core_web_sm')

# date/time formatting: https://strftime.org/
def day_of_week():
    weekday = dt.today().strftime('%A')
    print(weekday)
    return weekday

#
def get_sec_filing(form_type, ticker):
    sec_form = SEC(cwd) # Configuring SEC Downloader
    sec_form.get(form_type, ticker)


def read_sec(form_type, ticker):
    sec_directory = cwd + '/sec-edgar-filings/' + ticker + "/" + form_type
    sec_file_paths = [x[0] for x in os.walk(sec_directory)]
    del sec_file_paths[0]
    sec_text = []
    for path in sec_file_paths:
        with open(path + '/full-submission.txt', 'r') as sec_filing:
            content = sec_filing.read()
            sec_text.append(content)
    print(sec_text[0])
    return sec_text


def read_pdf():
    with open('/Users/kylanthomson/Downloads/mvis.pdf', mode = 'rb') as pdf_file:
        pdf_reader = PDF.PdfFileReader(pdf_file)
        page_one = pdf_reader.getPage(0)
        print(page_one.extractText())

def spacy_processing(raw_text):
    sec_text = read_sec("10-K", "MVIS")
    if len(sec_text[0]) > 1000000:
        nlp.max_length = 2250000
    doc = nlp(sec_text[0])
    for sentence in doc.sents:
        print(sentence)


def stop_words():
    # nlp.Defaults.stop_words.add('word')
    print(nlp.Defaults.stop_words)

def risk_disclosure():
    matcher = Matcher(nlp.vocab)
    market_risk = [{'LOWER': 'QUANTITATIVE AND QUALITATIVE DISCLOSURES ABOUT MARKET RISK'}]
    matcher.add('risk', None, market_risk)
    sec_text = read_sec("10-K", "MVIS")
    if len(sec_text[0]) > 1000000:
        nlp.max_length = 2250000
    doc = nlp(sec_text[0])

# Control + R
if __name__ == '__main__':
    day_of_week()
    read_pdf()
    # get_sec_filing("10-K", "MVIS")
    read_sec("10-K", "MVIS")
    # spacy_processing('testing')
    stop_words()

