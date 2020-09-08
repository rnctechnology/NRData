# NRData
Near real data generation is target to 2 things
1, create near real data set with deviations
2, provide apis for json/csv for full/incremental data

Docker image for easy setup by itself
Free service on cloud (AWS / GCP)

based on Java 8 and Spring Boot 2.3, main tech points list as below
	MySQL / Postgres db for admin taskes such as user registry / generation schema define
	Redis as key data cache
	Graphql for schema definition 

Workflows
    1, For standalone object (such as one table / one csv file) 
		define columns/fields which including type, range etc. online (looking for UI now)
		hit generate job and download files
		     
    2, For multiple objects with foreign key relations (composite) or m-n relationship (association)
    	Registered User required
    	define schema with Graphql schema standard 
    	test models and relationship by generate sample data with Graphql query
    	Generate data by define data volumn, deviation (json payload) and export as sql, csv or json file  
    	
    3, For Time Series / Time Line objects (topic and replies, time line events)
    	event objects and their dependency
    	generate events sequentially 
    	
    4, API style access (/api/v1) 
		Registered User required
		define schema/event as above
		define startTime/endTime, or dailey/hourly, or cron-style for incremental data generation
		 
