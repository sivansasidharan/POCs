# Java ref type org.apache.spark.sql.SQLContext id 1

 

# Load the CSV file (flight info)using `read.df`. Note that we use the CSV reader Spark package here.

flights <- read.df(sqlContext, "./sample.csv", "com.databricks.spark.csv", header="true")

 

# Print the first few rows

head(flights)

 

# Run a query to print the top 5 most frequent destinations from JFK

jfk_flights <- filter(flights, flights$origin == "JFK")

 

# Group the flights by destination and aggregate by the number of flights

dest_flights <- agg(group_by(jfk_flights, jfk_flights$dest), count = n(jfk_flights$dest))

 

# Now sort by the `count` column and print the first few rows

head(arrange(dest_flights, desc(dest_flights$count)))

 

##  dest count

##1  LAX 11262

##2  SFO  8204

##3  BOS  5898

 

# Combine the whole query into two lines using magrittr

library(magrittr)

dest_flights <- filter(flights, flights$origin == "JFK") %>% group_by(flights$dest) %>% summarize(count = n(flights$dest))

arrange(dest_flights, desc(dest_flights$count)) %>% head

