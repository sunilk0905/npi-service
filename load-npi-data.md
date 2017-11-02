# NPI File 

The NPI File can be downloaded from the following location 
http://download.cms.gov/nppes/NPI_Files.html

## Initial Setup 

- Download the 'Full Replacement Monthly NPI File'
- Make sure that you have a Mongo DB that is capable of holding over 10G of data 
- Make sure you have access to the mongoimport tool 
- Unzip the file 
- import the CSV file into MongoDB using the below 

```

mongoimport -d db_name -c npi-data --type csv --file <filename.csv> --headerline --host hostname:portnumber --authenticationDatabase admin --username 'iamauser' --password 'pwd123'

```
- Replace the filename.csv with the path to the CSV file


Sample 
```

mongoimport -d npi_dev_db -c npi-data --type csv --file  --headerline --host mongodb.sidgs.net:27017 --username 'npi-dev' --password 'npi'

```

