# Articles_export

In general, the purpose of the tool is to export articles from news sites to an excel file, according to the search words it receives.
Currently the sites are: Ynet, TheMarker, Bloomberg. I add sites from a list.
The tool works with a selenium library that allows you to work on the html of the site.
The program accepts: search string, number of articles for a site, which sites to include, search type and keywords.
The search type (Normal / Headline / Body / Comments) affects the selection of articles. For Search mode: Headline, the program only selects articles with the keywords in their header. (Unlike the search string, which is only for the site search fiels)
The Excel file contains 2 sheets. 
sheets 1 - Articles - Each article is taken apart into title, subheading, date, name of the writer and so on.
sheets 2 - Comments - Each comment on each article is numbered and divided into title, body talkback, talkback, etc.
