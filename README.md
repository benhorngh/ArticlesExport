# Articles_export

In general, the purpose of the tool is to export articles from news sites to an excel file, by key words input.
The tool works with a selenium library that allows you to work on the html of the site.
The program input: search string, number of articles for a site, which sites to include, search type and keywords.
The search type (Normal / Headline / Body / Comments) affects the selection of articles. For Search mode: Headline, the program only selects articles with the keywords in their header. (Unlike the search string, which is only for the site search fiels)
The Excel file contains 2 sheets. 
sheets 1 - Articles - Each article is taken apart into title, subheading, date, name of the writer and so on.
sheets 2 - Comments - Each comment on each article is numbered and divided into name, title, body , etc.

see @site list.xlsx
site list:
Ynet
The Marker
Globes
Bloomberg
Reuters
CNN
BBC
USAtoday
New York Times
Business Insider
Alternet
Daily Mail
Fox News
International Business Times


Ben Horn
benhorenn@gmail.com
1/2018
