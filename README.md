# JAVANewsAPI
Craft-IT Coding Challenge

## Short description

There are 2 threads (T1, T2). They are constantly retrieving news from two different sources (given in CL parameters) from newsapi.org.
The data from newsapi is stored in a remote mysql database.
A third thread (T3) polls data from the database and prints it to stdout (date-published + title).

### T1, T2: 
They are fetching a JSON block from newsapi.org. This block contains one or more articles. Each thread is parsing all articles and saves them in to a database, if they are not already stored in the database. This happens every 5 seconds.

### T3:
This thread looks up the database every 5 seconds. If there is a new entry, he will print it to stdout.

## The database table 
5 columns:
- id - auto increment - primary key
- source - the news source
- title - the title of a news article
- published - the publish date of the article
- url - the url to the article

## Usage

In the dist folder you can fin the JAVANews.jar file. 
To run the .jar you need the API key from newsapi.org and exactly 2 names of news sources.

Example: ```java -jar JAVANews.jar -a 123456789019826781aebb -s bild bbc-news```

You can also filter the articles (case sensitive):

Example: ```java -jar JAVANews.jar -a 123456789019826781aebb -s bild bbc-news -k Roger Federer```

